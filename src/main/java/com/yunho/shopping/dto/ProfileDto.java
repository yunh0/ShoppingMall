package com.yunho.shopping.dto;

import com.yunho.shopping.domain.Profile;
import com.yunho.shopping.domain.constant.Gender;

import java.time.LocalDateTime;

public record ProfileDto(
        Long profileId,
        String phoneNumber,
        Integer age,
        Gender gender,
        String introduction,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) {

    public static ProfileDto of(
            Long profileId,
            String phoneNumber,
            Integer age,
            Gender gender,
            String introduction
    ){
        return ProfileDto.of(profileId, phoneNumber, age, gender, introduction, null, null, null, null);
    }

    public static ProfileDto of(
            Long profileId,
            String phoneNumber,
            Integer age,
            Gender gender,
            String introduction,
            LocalDateTime createdAt,
            String createdBy,
            LocalDateTime modifiedAt,
            String modifiedBy
    ){
        return new ProfileDto(profileId, phoneNumber, age, gender, introduction, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ProfileDto from(Profile profile){
        return new ProfileDto(
                profile.getProfileId(),
                profile.getPhoneNumber(),
                profile.getAge(),
                profile.getGender(),
                profile.getIntroduction(),
                profile.getCreatedAt(),
                profile.getCreatedBy(),
                profile.getModifiedAt(),
                profile.getModifiedBy()
        );
    }


    public Profile toEntity(){
        return Profile.of(phoneNumber, age, gender, introduction);
    }
}
