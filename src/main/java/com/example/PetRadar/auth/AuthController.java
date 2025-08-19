package com.example.PetRadar.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> requestMap) {
        String id = requestMap.get("id");
        String pw = requestMap.get("pw");

        // 실제로는 DB에서 사용자 인증을 처리해야 합니다.
        if (!"asdf".equals(id) || !"1234".equals(pw)) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid username or password");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        // 액세스 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(id);
        // 리프레시 토큰 생성
        String refreshToken = jwtTokenProvider.createRefreshToken(id);

        // 리프레시 토큰을 HttpOnly 쿠키로 설정
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                //.secure(true) // HTTPS 환경에서만 전송, 개발환경이 HTTP이므로 임시 주석처리
                .path("/api/auth")
                .maxAge(jwtTokenProvider.getRefreshTokenExpSec())
                .build();

        // 액세스 토큰은 JSON 응답으로, 리프레시 토큰은 쿠키 헤더에 담아 전송
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
        Map<String, String> response = new HashMap<>();

        if (refreshToken == null || !jwtTokenProvider.validateRefreshToken(refreshToken)) {
            response.put("message", "Invalid or expired refresh token");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        String userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        String newAccessToken = jwtTokenProvider.createAccessToken(userId);

        response.put("accessToken", newAccessToken);
        return ResponseEntity.ok(response);
    }
}
