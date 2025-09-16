package com.project.glasses.user;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "회원 관련 API")
@RestController
public class UserController {

    @Operation(summary = "내 정보 조회", description = "로그인한 사용자의 이메일을 반환")
    @GetMapping("/api/user/me")
    public String me(@AuthenticationPrincipal Jwt jwt) {
        return "Hello, " + jwt.getClaim("email");
    }
}