package com.project.core.config;

import com.project.core.jwt.JwtAuthenticationSuccessHandler;
import com.project.core.oauth.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationSuccessHandler successHandler) throws Exception {
        // 여기서 HttpSecurity는 컨테이너가 전달하는 객체입니다.
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/public/**").permitAll()
                        .anyRequest().authenticated()
                )
                // 토큰이 유효하지 않거나 없을 때 401 상태코드 반환
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED);
                            // 필요시 body에 메시지 추가 가능
                            response.getWriter().write("Unauthorized");
                        })
                )
                // Jwt 검증 필터 또는 OAuth2 Login 등 추가
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt->{}
                        )
                );

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        return new CustomOAuth2UserService();
    }

    @Bean
    public JwtAuthenticationSuccessHandler successHandler() {
        return new JwtAuthenticationSuccessHandler();
    }
}