package com.api.shop_project.repository.member;

import com.api.shop_project.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);


    List<Member> findByEmailContaining(String search);

    List<Member> findByNameContaining(String search);

    List<Member> findByPhoneContaining(String search);

    Optional<Object> findByName(String email);
}
