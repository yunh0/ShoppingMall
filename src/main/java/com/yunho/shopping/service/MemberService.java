package com.yunho.shopping.service;

import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.Profile;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProfileDto;
import com.yunho.shopping.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
            ProfileDto profileDto
    ){
        String encodedPassword = passwordEncoder.encode(password);

        Profile profile = (profileDto != null) ? profileDto.toEntity() : null;

        Member member = memberRepository.save(Member.of(userId, email, encodedPassword, name, profile));

        return MemberDto.from(member);
    }

    public void updateProfile(String username, ProfileDto profileDto){
        Member member = memberRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다. username: " + username));

        if(profileDto != null){
            member.setProfile(profileDto.toEntity());
        }
    }
}
