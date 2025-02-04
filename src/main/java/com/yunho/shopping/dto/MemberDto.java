package com.yunho.shopping.dto;

import com.yunho.shopping.domain.Member;

import java.time.LocalDateTime;
import java.util.Optional;

public record MemberDto(
        String userId,
        String email,
        String password,
        String name,
        ProfileDto profileDto,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static MemberDto of(
            String userId,
            String email,
            String password,
            String name
    ){
        return MemberDto.of(userId, email, password, name, null, null, null, null, null);
    }

    public static MemberDto of(
            String userId,
            String email,
            String password,
            String name,
            ProfileDto profileDto
    ){
        return MemberDto.of(userId, email, password, name, profileDto, null, null, null, null);
    }

    public static MemberDto of(
            String userId,
            String email,
            String password,
            String name,
            ProfileDto profileDto,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ){
        return new MemberDto(userId, email, password, name, profileDto, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static MemberDto from(Member member){
        return new MemberDto(
                member.getUserId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                Optional.ofNullable(member.getProfile())
                                .map(ProfileDto::from)
                                .orElse(null),
                member.getCreatedAt(),
                member.getCreatedBy(),
                member.getModifiedAt(),
                member.getModifiedBy()
        );
    }

    public Member toEntity(){
        return Member.of(userId, email, password, name);
    }
}
