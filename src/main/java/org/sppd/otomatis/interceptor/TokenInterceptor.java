package org.sppd.otomatis.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sppd.otomatis.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ") || !tokenService.isValidToken(token.substring(7))) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Token Invalid\"}");
            response.getWriter().flush();
            return false;
        }
        return tokenService.isValidToken(token.substring(7));
    }




}
