package org.sppd.otomatis.service;

import lombok.extern.slf4j.Slf4j;
import org.sppd.otomatis.security.Bcrypt;
import org.sppd.otomatis.dto.LoginRequests;
import org.sppd.otomatis.dto.TokenResponse;
import org.sppd.otomatis.dto.UserRequest;
import org.sppd.otomatis.entity.UserRoles;
import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.exception.UserNotFoundException;
import org.sppd.otomatis.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


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
        Users user = userRepository.findById(login.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User atau password tidak valid"));
        if (!Bcrypt.checkpw(login.getPassword(), user.getPassword())){
            throw new UserNotFoundException("User atau password tidak valid");
        }
        String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setExpiredAt(System.currentTimeMillis() + 86_400_000L);
        userRepository.save(user);
        return TokenResponse.builder()
                .token(token)
                .expiredAt(user.getExpiredAt())
                .build();

    }
    @Transactional
    public void logOutUser(String token){
        userRepository.findFirstByToken(token).map(u -> {
            u.setToken(null);
            u.setExpiredAt(0);
            return userRepository.save(u);
        }).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }
    @Transactional
    public Users getUserDetails(String requests) {
        Users users = userRepository.findById(requests)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));
        if (users.getExpiredAt() < System.currentTimeMillis()){
            throw new RuntimeException("User Expired");
        }
        return users;
    }
}
