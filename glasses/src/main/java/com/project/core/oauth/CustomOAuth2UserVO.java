package com.project.core.oauth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2UserVO implements OAuth2User {
    private Map<String, Object> attributes;

    public CustomOAuth2UserVO(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // 필요시 추가
    }

    @Override
    public String getName() {
        return (String) attributes.get("sub"); // 사용자 고유ID
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }
}

