package com.yunho.shopping.dto;

import com.yunho.shopping.domain.constant.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public record CustomPrincipal(
        String username,
        String password,
        RoleType roleType,
        String email,
        String name,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static CustomPrincipal of(
            String username,
            String password,
            RoleType roleType,
            String email,
            String name
    ){
        return CustomPrincipal.of(
                username,
                password,
                roleType,
                email,
                name,
                Map.of()
        );
    }

    public static CustomPrincipal of(
            String username,
            String password,
            RoleType roleType,
            String email,
            String name,
            Map<String, Object> oAuth2Attributes
    ){
        return new CustomPrincipal(
                username,
                password,
                roleType,
                email,
                name,
                oAuth2Attributes
        );
    }

    public static CustomPrincipal from(MemberDto dto){
        return CustomPrincipal.of(
                dto.userId(),
                dto.password(),
                dto.roleType(),
                dto.email(),
                dto.name()
        );
    }

    public MemberDto toDto(){
        return MemberDto.of(
                username,
                email,
                password,
                name,
                roleType
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(roleType.getRoleName()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }

    @Override
    public String getName() {
        return name;
    }

}
