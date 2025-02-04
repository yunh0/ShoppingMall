package com.yunho.shopping.domain;

import com.yunho.shopping.domain.constant.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Entity
@Table
public class Profile extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Setter
    @Column(length = 15, nullable = false)
    private String phoneNumber;

    @Setter
    @Column(nullable = false)
    private Integer age;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Setter
    @Column(length = 1000)
    private String introduction;


    public Profile(String phoneNumber, Integer age, Gender gender, String introduction, String createdBy) {
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.introduction = introduction;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    protected Profile(){

    }

    public static Profile of(String phoneNumber, Integer age, Gender gender, String introduction){
        return Profile.of(phoneNumber, age, gender, introduction, null);
    }

    public static Profile of(String phoneNumber, Integer age, Gender gender, String introduction, String createdBy){
        return new Profile(phoneNumber, age, gender, introduction, createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getProfileId());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Profile that)) return false;
        return this.getProfileId() != null && this.getProfileId().equals(that.getProfileId());
    }
}
