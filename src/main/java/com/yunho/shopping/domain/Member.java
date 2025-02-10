package com.yunho.shopping.domain;

import com.yunho.shopping.domain.constant.RoleType;
import jakarta.persistence.*;
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
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType = RoleType.USER;

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", unique = true)
    private Profile profile;

    public Member(String userId, String email, String password, String name, Profile profile, String createdBy) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.profile = profile;
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }

    protected Member(){

    }

    public static Member of(String userId, String email, String password, String name){
        return Member.of(userId, email, password, name, null, null);
    }

    public static Member of(String userId, String email, String password, String name, Profile profile){
        return Member.of(userId, email, password, name, profile, null);
    }

    public static Member of(String userId, String email, String password, String name, Profile profile, String createdBy){
        return new Member(userId, email, password, name, profile, createdBy);
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
