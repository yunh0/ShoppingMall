package com.yunho.shopping.service;

import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.Profile;
import com.yunho.shopping.domain.constant.Gender;
import com.yunho.shopping.dto.MemberDto;
import com.yunho.shopping.dto.ProfileDto;
import com.yunho.shopping.repository.MemberRepository;
import com.yunho.shopping.repository.ProfileRepository;
import jakarta.servlet.http.HttpSession;
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
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession session;

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

        Profile profile = profileRepository.findById(profileDto.profileId())
                .orElseThrow(() -> new IllegalArgumentException("Profile not found"));

        Member member = memberRepository.save(Member.of(userId, email, encodedPassword, name, profile));

        return MemberDto.from(member);
    }

    public ProfileDto saveProfile(
            String phoneNumber,
            Integer age,
            Gender gender,
            String introduction
    ){
        return ProfileDto.from(
                profileRepository.save(Profile.of(phoneNumber, age, gender, introduction))
        );
    }

    public void storeTempMemberInSession(MemberDto tempMember) {
        session.setAttribute("tempMember", tempMember);
    }

    public MemberDto getTempMemberFromSession() {
        return (MemberDto) session.getAttribute("tempMember");
    }

    public void clearTempMemberSession() {
        session.removeAttribute("tempMember");
    }
}
