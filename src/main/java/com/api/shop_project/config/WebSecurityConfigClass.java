package com.api.shop_project.config;

import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.member.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigClass {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();

        // 사용자 페이지 설정
        http.authorizeHttpRequests()
                // 로그인시
                .antMatchers("/member/logout").authenticated()
                // OAUTH 정보 추가 페이지
                // USER, ADMIN , SELLER
                .antMatchers("/member/**").permitAll()
                // ADMIN, SELLER
                // ADMIN
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                // 모두허용
                .anyRequest().permitAll()
        ;

        // 로그인
        http.formLogin()
                .loginPage("/member/login")             //로그인페이지
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/member/login")    // POST
                .defaultSuccessUrl("/index")                 // 로그인 후
                .failureUrl("/member/login")
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/member/login")
                .successHandler(
                        new AuthenticationSuccessHandler() {
                            @Override
                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
                                Member member = myUserDetails.getMember();

                                if (member.getRole()== Role.OAUTH){
                                    response.sendRedirect("/member/oauth2add");
                                }else{
                                    response.sendRedirect("/");
                                }
                            }
                        }
                )
                .failureUrl("/member/login")
                .userInfoEndpoint()
                .userService(myAuth2UserService())
        ;

        // 로그아웃
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃URL
                .deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 제거
                .invalidateHttpSession(true) // 로그아웃 시 세션 종료
                .clearAuthentication(true) // 로그아웃 시 권한 제거
                .logoutSuccessUrl("/")
        ;



        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> myAuth2UserService(){
        return new MyOauth2UserService();
    }

}
