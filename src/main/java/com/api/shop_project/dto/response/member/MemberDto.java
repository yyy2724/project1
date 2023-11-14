package com.api.shop_project.dto.response.member;

import com.api.shop_project.domain.BaseTime;
import com.api.shop_project.domain.member.Address;
import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Setter

//@Builder
//@AllArgsConstructor

@Getter
@NoArgsConstructor
public class MemberDto extends BaseTime{
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phone;

    private Address address;

    private Role role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;



    @Builder
    public MemberDto(Long id, String email, String password, String name, String phone, Address address, Role role, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

//    @Builder
//    public MemberDto(Long id, String email, String password, String name, String phone, Role role) {
//        this.id = id;
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.phone = phone;
//        this.role = role;
//    }

//    @Builder
//    public Update(String password, String name) {
//        this.password = password;
//        this.name = name;
//    }

//    @Builder
//    public Update(Long id, String password, String name, String email, Address address) {
//        this.id = id;
//        this.password = password;
//        this.name = name;
//        this.email = email;
//        this.address = address;
//    }

    public static MemberDto toMemberDto(Member member){
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setName(member.getName());
        memberDto.setEmail(member.getEmail());
//        memberDto.setPassword(member.getPassword());
        memberDto.setPhone(member.getPhone());
        memberDto.setRole(member.getRole());
        memberDto.setAddress(member.getAddress());
        memberDto.setCreateTime(member.getCreateTime());
        memberDto.setUpdateTime(member.getUpdateTime());
        return memberDto;
    }



}