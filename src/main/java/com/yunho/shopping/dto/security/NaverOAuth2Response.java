package com.yunho.shopping.dto.security;

import java.util.Map;

@SuppressWarnings("unchecked")
public record NaverOAuth2Response(
        String id,
        String email,
        String nickname
) {
    public static NaverOAuth2Response from(Map<String, Object> attributes){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return new NaverOAuth2Response(
                String.valueOf(response.get("id")),
                String.valueOf(response.get("email")),
                String.valueOf(response.get("name"))
        );
    }

    public String email(){
        return this.email;
    }

    public String nickname(){
        return this.nickname;
    }
}
