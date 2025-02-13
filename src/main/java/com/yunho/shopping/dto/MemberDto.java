package com.yunho.shopping.dto;

import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.constant.RoleType;

import java.time.LocalDateTime;
import java.util.Optional;

public record MemberDto(
        String userId,
        String email,
        String password,
        String name,
        RoleType roleType,
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
            String name,
            RoleType roleType
    ){
        return MemberDto.of(userId, email, password, name, roleType, null, null, null, null, null);
    }

    public static MemberDto of(
            String userId,
            String email,
            String password,
            String name,
            RoleType roleType,
            ProfileDto profileDto
    ){
        return MemberDto.of(userId, email, password, name, roleType, profileDto, null, null, null, null);
    }

    public static MemberDto of(
            String userId,
            String email,
            String password,
            String name,
            RoleType roleType,
            ProfileDto profileDto,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ){
        return new MemberDto(userId, email, password, name, roleType, profileDto, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static MemberDto from(Member member){
        return MemberDto.of(
                member.getUserId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getRoleType(),
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
