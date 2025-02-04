package com.yunho.shopping.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record CustomPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String name,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static CustomPrincipal of(
            String username,
            String password,
            String email,
            String name
    ){
        return CustomPrincipal.of(
                username,
                password,
                email,
                name,
                Map.of()
        );
    }

    public static CustomPrincipal of(
            String username,
            String password,
            String email,
            String name,
            Map<String, Object> oAuth2Attributes
    ){
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new CustomPrincipal(
                username,
                password,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet()),
                email,
                name,
                oAuth2Attributes
        );
    }

    public static CustomPrincipal from(MemberDto dto){
        return CustomPrincipal.of(
                dto.userId(),
                dto.password(),
                dto.email(),
                dto.name()
        );
    }

    public MemberDto toDto(){
        return MemberDto.of(
                username,
                email,
                password,
                name
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
        return authorities;
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

    @Getter
    public enum RoleType{
        USER("ROLE_USER");

        private final String name;

        RoleType(String name){
            this.name = name;
        }
    }
}
