package org.sppd.otomatis.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sppd.otomatis.dto.TokenResponse;
import org.sppd.otomatis.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(TokenInterceptor.class);
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        log.info(token);
        TokenResponse tokenResponse = new TokenResponse();
        if (token == null || !token.startsWith("Bearer ") || !tokenService.isValidToken(token.substring(7))) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token Invalid\"}");
            response.getWriter().flush();
            return false;
        }
        return tokenService.isValidToken(token.substring(7));
    }
}
