package org.sppd.otomatis.service;

import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.repository.TokenRepository;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean isValidToken(String token) {
        return tokenRepository.existsByToken(token);
    }

    public Users findUsersByToken(String token) {
        if (token == null || !token.startsWith("Bearer ") || !isValidToken(token.substring(7))) {
            throw new IllegalArgumentException("Unauthorized");
        }
        return tokenRepository.findByToken(token.substring(7));
    }




}
