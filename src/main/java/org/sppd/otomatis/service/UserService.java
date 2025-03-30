package org.sppd.otomatis.service;

import lombok.extern.slf4j.Slf4j;
import org.sppd.otomatis.Bcrypt;
import org.sppd.otomatis.dto.LoginRequests;
import org.sppd.otomatis.dto.TokenResponse;
import org.sppd.otomatis.dto.UserRequest;
import org.sppd.otomatis.entity.UserRoles;
import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void registerUser(UserRequest user) {
        if(userRepository.existsById(user.getUsername())){
            throw new RuntimeException("User already exists");
        }
        userRepository.save(Users.builder()
                .name(user.getName())
                .password(Bcrypt.hashpw(user.getPassword(), Bcrypt.gensalt()))
                .username(user.getUsername())
                .roles(UserRoles.OFFICER)
                .build());

    }

    @Transactional
    public TokenResponse loginUser(LoginRequests login){
        Users user = userRepository.findById(login.getUsername()).orElseThrow(() -> new RuntimeException("User tidak valid"));
        if (!Bcrypt.checkpw(login.getPassword(), user.getPassword())){
            log.info(user.getPassword());
            throw new RuntimeException("Password tidak valid");
        }
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setExpiredAt(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
        userRepository.save(user);
        return TokenResponse.builder()
                .token(token)
                .expiredAt(user.getExpiredAt())
                .build();
    }
}
