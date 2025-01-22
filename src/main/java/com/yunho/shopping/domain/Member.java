package com.yunho.shopping.domain;

import com.yunho.shopping.domain.constant.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@Table
public class Member extends AuditingFields {

    @Id
    @Column(length = 50)
    private String userId;

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
    private Integer age;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Setter
    @Column(length = 1000)
    private String introduction;

    public Member(String userId, String email, String password, String name, String phoneNumber, Integer age, Gender gender, String introduction, String createdBy) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.introduction = introduction;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    protected Member(){

    }

    public static Member of(String userId, String email, String password, String name, String phoneNumber, Integer age, Gender gender, String introduction){
        return Member.of(userId, email, password, name, phoneNumber, age, gender, introduction, null);
    }

    public static Member of(String userId, String email, String password, String name, String phoneNumber, Integer age, Gender gender, String introduction, String createdBy){
        return new Member(userId, email, password, name, phoneNumber, age, gender, introduction, createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUserId());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Member that)) return false;
        return this.getUserId() != null && this.getUserId().equals(that.getUserId());
    }
}
