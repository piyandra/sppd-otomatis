package org.sppd.otomatis.controller;

import jakarta.validation.Valid;
import org.sppd.otomatis.dto.LoginRequests;
import org.sppd.otomatis.dto.TokenResponse;
import org.sppd.otomatis.dto.UserRequest;
import org.sppd.otomatis.dto.WebResponse;
import org.sppd.otomatis.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user/register",
    produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@Valid @RequestBody UserRequest userRequest) {
        userService.registerUser(userRequest);
        return WebResponse.<String>builder()
                .message("User Registered Sucessfully")
                .build();
    }

    @GetMapping(path = "user/login",
    produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<TokenResponse> login(@Valid @RequestBody LoginRequests loginRequests) {
        TokenResponse tokenResponse = userService.loginUser(loginRequests);
        return WebResponse.<TokenResponse>builder()
                .data(tokenResponse)
                .build();
    }
}
