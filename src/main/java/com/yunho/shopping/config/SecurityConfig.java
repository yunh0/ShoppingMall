package com.yunho.shopping.config;

import com.yunho.shopping.domain.constant.RoleType;
import com.yunho.shopping.dto.CustomPrincipal;
import com.yunho.shopping.dto.security.KakaoOAuth2Response;
import com.yunho.shopping.dto.security.NaverOAuth2Response;
import com.yunho.shopping.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity httpSecurity,
            OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService
    ) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/index", "/signin", "/signup").permitAll()
                        .requestMatchers("/seller/**").hasAuthority(RoleType.SELLER.getRoleName())
                        .anyRequest().authenticated()
                )
                .formLogin(
                        auth -> auth.loginPage("/signin")
                                .loginProcessingUrl("/signin")
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .oauth2Login(oAuth -> oAuth
                        .loginPage("/signin")
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService))
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(MemberService memberService){
        return username -> memberService
                .searchMember(username)
                .map(CustomPrincipal::from)
                .orElseThrow(() ->
                        new UsernameNotFoundException("유저를 찾을 수 없습니다. - username : " + username)
                );
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService(
            MemberService memberService,
            PasswordEncoder passwordEncoder,
            HttpServletResponse response
    ){
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            Map<String, Object> attributes = oAuth2User.getAttributes();

            String providerId;
            String email;
            String name;

            switch (registrationId){
                case "google" -> {
                    providerId = (String) attributes.get("sub");
                    email = (String) attributes.get("email");
                    name = (String) attributes.get("name");

                }
                case "kakao" -> {
                    KakaoOAuth2Response kakaoOAuth2Response = KakaoOAuth2Response.from(attributes);
                    providerId = String.valueOf(kakaoOAuth2Response.id());
                    email = kakaoOAuth2Response.email();
                    name = kakaoOAuth2Response.name();
                }
                case "naver" -> {
                    NaverOAuth2Response naverOAuth2Response = NaverOAuth2Response.from(attributes);
                    providerId = String.valueOf(naverOAuth2Response.id());
                    email = naverOAuth2Response.email();
                    name = naverOAuth2Response.name();
                }
                default -> throw new IllegalArgumentException("지원하지 않습니다. : " + registrationId);
            }

            String username = registrationId + "_" + providerId;
            String dummyPassword = passwordEncoder.encode("{bcrypt}" + UUID.randomUUID());

            return memberService.searchMember(username)
                    .map(CustomPrincipal::from)
                    .orElseGet(() ->{
                        try {
                            response.sendRedirect("/signup/profile");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        return CustomPrincipal.from(
                                memberService.saveMember(
                                        username,
                                        email,
                                        dummyPassword,
                                        name,
                                        null
                                )
                        );
                    });
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
