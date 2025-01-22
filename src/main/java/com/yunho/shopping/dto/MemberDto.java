package com.yunho.shopping.dto;

import com.yunho.shopping.domain.Member;
import com.yunho.shopping.domain.constant.Gender;

import java.time.LocalDateTime;

public record MemberDto(
        String userId,
        String email,
        String password,
        String name,
        String phoneNumber,
        Integer age,
        Gender gender,
        String introduction,
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
            String phoneNumber,
            Integer age,
            Gender gender,
            String introduction
    ){
        return new MemberDto(userId, email, password, name, phoneNumber, age, gender, introduction, null, null, null, null);
    }

    public static MemberDto of(
            String userId,
            String email,
            String password,
            String name,
            String phoneNumber,
            Integer age,
            Gender gender,
            String introduction,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ){
        return new MemberDto(userId, email, password, name, phoneNumber, age, gender, introduction, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static MemberDto from(Member member){
        return new MemberDto(
                member.getUserId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getPhoneNumber(),
                member.getAge(),
                member.getGender(),
                member.getIntroduction(),
                member.getCreatedAt(),
                member.getCreatedBy(),
                member.getModifiedAt(),
                member.getModifiedBy()
        );
    }

    public Member toEntity(){
        return Member.of(userId, email, password, name, phoneNumber, age, gender, introduction);
    }
}
