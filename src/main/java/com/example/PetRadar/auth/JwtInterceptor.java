package com.example.PetRadar.auth;

import com.example.PetRadar.auth.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HTTP 요청 헤더에서 "Authorization" 헤더를 가져옵니다.
        String authorizationHeader = request.getHeader("Authorization");
        // 헤더가 비어있거나 "Bearer "로 시작하지 않으면 유효하지 않은 요청으로 처리합니다.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("Authorization header is missing or invalid.");
            return false;
        }
        // "Bearer " 접두사를 제거하고 토큰 값만 추출합니다.
        String token = authorizationHeader.substring(7);
        // JwtTokenProvider를 사용하여 토큰의 유효성을 검증합니다.
        if (!jwtTokenProvider.validateAccessToken(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
            response.getWriter().write("Invalid or expired access token.");
            return false;
        }
        // 토큰이 유효하면 요청을 계속 진행합니다.
        return true;
    }
}
