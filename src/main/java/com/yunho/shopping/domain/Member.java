package com.yunho.shopping.domain;

import com.yunho.shopping.domain.constant.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity
@Table
public class Member extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NotBlank(message = "이메일을 입력해주세요.")
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Setter
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Column(nullable = false)
    private String password;

    @Setter
    @NotBlank(message = "이름을 입력해주세요.")
    @Column(length = 100, nullable = false)
    private String name;

    @Setter
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Column(length = 15, nullable = false)
    private String phoneNumber;

    @Setter
    private int age;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Setter
    @Column(length = 1000)
    private String introduction;

    @Setter
    private String profileImgPath;
}
