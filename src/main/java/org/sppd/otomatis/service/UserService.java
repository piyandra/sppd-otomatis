package org.sppd.otomatis.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.sppd.otomatis.Bcrypt;
import org.sppd.otomatis.dto.UserRequest;
import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Validator validate;

    public void deleteUser(UserRequest username) {
        userRepository.deleteById(username.getUsername());
    }

    public Users getUser(UserRequest username) {
        return userRepository.findById(username.getUsername()).orElse(null);
    }

    @Transactional
    public void saveUser(UserRequest user) {
        Set<ConstraintViolation<UserRequest>> userRequest = validate.validate(user);
        if (!userRequest.isEmpty()) {
            throw new RuntimeException("Username must be 8-10 character");
        }
        if (userRepository.existsById(user.getUsername())) {
            throw new RuntimeException("Username Already Exist");
        }
        userRepository.save(Users.builder()
                .name(user.getName())
                .password(Bcrypt.hashpw(user.getPassword(), Bcrypt.gensalt()))
                .username(user.getUsername())
                .build());

    }
    public Users findToken(String token) {
        return userRepository.findFirstByToken(token).orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
