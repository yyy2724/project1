package com.api.shop_project.service.member;

import com.api.shop_project.domain.member.Address;
import com.api.shop_project.domain.member.Member;
import com.api.shop_project.domain.member.Role;
import com.api.shop_project.dto.response.member.MemberDto;
import com.api.shop_project.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void insertMember2(MemberDto memberDto) {
        Address address = Address.builder()
                .City(memberDto.getAddress().getCity())
                .street(memberDto.getAddress().getStreet())
                .zipcode(memberDto.getAddress().getZipcode())
                .build();

        Long memberId = memberRepository.save(Member.builder()
                .email(memberDto.getEmail())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .name(memberDto.getName())
                .address(address)
                .phone(memberDto.getPhone())
                .role(Role.USER)
                .build()).getId();

        Member member1
                = memberRepository.findById(memberId).orElseThrow(() -> {
            throw new IllegalArgumentException("아이디가 없음.");
        });

    }




        public int memberDelete(Long id) {

            Optional<Member> optionalMember=
                    Optional.ofNullable(memberRepository.findById(id).orElseThrow(() -> {
                        return new IllegalArgumentException("삭제할 아이디 없음");
                    }));

            memberRepository.delete(optionalMember.get()); // 해당 id 삭제

            Optional<Member> optionalMember1
                    =  memberRepository.findById(id); // 해당 id 조회
            if(!optionalMember1.isPresent()){
                // 삭제 정상 실행
                return 1;
            }
            return 0;

        }

    @Transactional
    public int memberUpdate(MemberDto memberDto) {

        Address address = Address.builder()
                .City(memberDto.getAddress().getCity())
                .street(memberDto.getAddress().getStreet())
                .zipcode(memberDto.getAddress().getStreet())
                .build();

        // MemberEntity id확인
        Optional<Member>  optionalMemberEntity=
                Optional.ofNullable(memberRepository.findById(memberDto.getId()).orElseThrow(() -> {
                    return new IllegalArgumentException("수정할 아이디가 없습니다.");
                }));
        Member memberEntity=
                Member.builder()
                        .id(memberDto.getId())
                        .name(memberDto.getName())
                        .email(memberDto.getEmail())
                        .password(passwordEncoder.encode(memberDto.getPassword()))
                        .phone(memberDto.getPhone())
                        .address(address)
                        .role(Role.USER)
                        .build();



        Long memberId= memberRepository.save(memberEntity).getId();

        Optional<Member>  optionalMemberEntity2=
                Optional.ofNullable(memberRepository.findById(memberId).orElseThrow(() -> {
                    return new IllegalArgumentException("수정 아이디가 없습니다.");
                }));

        if(optionalMemberEntity2.isPresent()){
            // 수정이 정상 실행
            return 1;
        }
        return 0;
    }

    @Transactional
    public MemberDto memberUpdateOk(Long id) {
        // MemberEntity id 확인
        Optional<Member> optionalMemberEntity =
                Optional.ofNullable(memberRepository.findById(id).orElseThrow(()->{
                    return new IllegalArgumentException("수정할 아이디가 없습니다.");
                }));

        Member memberEntity = optionalMemberEntity.get();

        if (optionalMemberEntity.isPresent()){
            // Entity-> Dto
            MemberDto memberDto = MemberDto.builder()
                    .id(memberEntity.getId())
                    .name(memberEntity.getName())
                    .email(memberEntity.getEmail())
                    .password(passwordEncoder.encode(memberEntity.getPassword()))
                    .phone(memberEntity.getPhone())
                    .address(memberEntity.getAddress())
//                    .role(Role.USER)
                    .role(memberEntity.getRole())
                    .build();
            return memberDto;
        }

        return null;
    }

    @Transactional
    public MemberDto memberDetail(Long id) {
        Member memberEntity = memberRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("아이디가 없습니다.");
        });

        return MemberDto.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .phone(memberEntity.getPhone())
                .role(memberEntity.getRole())
                .address(memberEntity.getAddress())
                .createTime(memberEntity.getCreateTime())
                .updateTime(memberEntity.getUpdateTime())
                .build();
    }

    public List<MemberDto> findAllList() {
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members = memberRepository.findAll();

        if (!members.isEmpty()){
            for (Member member : members){
                MemberDto memberDto = MemberDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .email(member.getEmail())
                        .password(member.getPassword())
                        .phone(member.getPhone())
                        .role(member.getRole())
                        .address(member.getAddress())
                        .createTime(member.getCreateTime())
                        .updateTime(member.getUpdateTime())
                        .build();
                memberDtos.add(memberDto);
            }
        }

        return memberDtos;
    }

    public List<MemberDto> searchMemberList(String subject, String search) {
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members =new ArrayList<>();

        if (subject.equals("email")){
            members = memberRepository.findByEmailContaining(search);
        }else if(subject.equals("name")){
            members = memberRepository.findByNameContaining(search);
        }else if(subject.equals("phone")){
            members = memberRepository.findByPhoneContaining(search);
        }else {
            members = memberRepository.findAll();
        }

        if (!members.isEmpty()){
            for (Member member : members){
                MemberDto memberDto = MemberDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .email(member.getEmail())
                        .phone(member.getPhone())
                        .address(member.getAddress())
                        .role(member.getRole())
                        .createTime(member.getCreateTime())
                        .updateTime(member.getUpdateTime())
                        .build();
                memberDtos.add(memberDto);
            }
        }
        return memberDtos;
    }

    public int emailChecked(String email) throws IOException {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(!member.isPresent()){
            return 0;
        }
        return 1;
    }

    public Page<MemberDto> pagingMemberList(Pageable pageable) {
        Page<Member> members = memberRepository.findAll(pageable);

        Page<MemberDto> memberDtos = members.map(MemberDto::toMemberDto);

        return memberDtos;
    }
}
