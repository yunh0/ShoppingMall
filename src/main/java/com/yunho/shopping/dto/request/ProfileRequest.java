package com.yunho.shopping.dto.request;

import com.yunho.shopping.domain.constant.Gender;
import com.yunho.shopping.dto.ProfileDto;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {

    @Pattern(regexp = "^[0-9]{10,11}$", message = "유효한 전화번호를 입력해주세요.")
    private String phoneNumber;

    private Integer age;

    private Gender gender;

    private String introduction;

    public ProfileDto toDto(){
        return ProfileDto.of(
                phoneNumber,
                age,
                gender,
                introduction
        );
    }
}
