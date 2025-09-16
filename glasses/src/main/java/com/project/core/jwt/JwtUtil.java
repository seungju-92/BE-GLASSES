package com.project.core.jwt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "your-256-bit-secret"; // 반드시 256비트(32바이트) 이상 길이
    private static final long EXPIRATION_TIME = 60 * 60 * 1000; // 1시간

    // 토큰 생성
    public String createToken(String username) throws Exception {
        // 클레임 세트 구성
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(username)
                .issueTime(new Date())
                .expirationTime(new Date(new Date().getTime() + EXPIRATION_TIME))
                .build();

        // 서명 인스턴스 생성
        JWSSigner signer = new MACSigner(SECRET.getBytes());

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader(JWSAlgorithm.HS256),
                claims
        );

        // 서명
        signedJWT.sign(signer);

        // 문자열로 직렬화
        return signedJWT.serialize();
    }

    // 토큰 검증 후 사용자명 추출
    public String validateTokenAndGetSubject(String token) throws Exception {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET.getBytes());

        if (signedJWT.verify(verifier) && !isExpired(signedJWT.getJWTClaimsSet())) {
            return signedJWT.getJWTClaimsSet().getSubject();
        }
        throw new RuntimeException("Invalid or expired token");
    }

    private boolean isExpired(JWTClaimsSet claims) {
        return new Date().after(claims.getExpirationTime());
    }
}