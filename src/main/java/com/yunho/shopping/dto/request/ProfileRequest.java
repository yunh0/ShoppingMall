package com.yunho.shopping.dto.request;

import com.yunho.shopping.domain.constant.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "유효한 전화번호를 입력해주세요.")
    private String phoneNumber;

    @NotNull(message = "나이를 입력해주세요.")
    private Integer age;

    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    private String introduction;
}
