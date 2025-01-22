package com.yunho.shopping.service;

import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.constant.Gender;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Optional<MemberDto> searchMember(String username){
        return memberRepository.findById(username)
                .map(MemberDto::from);
    }

    public MemberDto saveMember(
            String userId,
            String email,
            String password,
            String name,
            String phoneNumber,
            Integer age,
            Gender gender,
            String introduction
    ){
        return MemberDto.from(
                memberRepository.save(Member.of(userId, email, password, name, phoneNumber, age, gender, introduction))
        );
    }
}
