package org.sppd.otomatis.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sppd.otomatis.dto.*;
import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user/register",
    produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> register(@Valid @RequestBody UserRequest userRequest) {
        log.info(userRequest.getUsername());
        userService.registerUser(userRequest);
        return ResponseEntity.ok(WebResponse.<String>builder()
                .message("User Registered Successfully")
                .build());

    }

    @PostMapping(path = "/user/login",
    produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<TokenResponse>> login(@Valid @RequestBody LoginRequests loginRequests) {
        return ResponseEntity
                .ok(WebResponse.<TokenResponse>builder()
                        .message("Login Success")
                        .data(userService.loginUser(loginRequests))
                .build());
    }

    @PostMapping(path = "/user/logout",
                produces = MediaType.APPLICATION_JSON_VALUE,
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<String>> logout(@RequestBody LogOutRequest token) {
        userService.logOutUser(token.getToken());
        return ResponseEntity.ok(WebResponse.<String>builder()
                        .message("Sukses")
                        .build());
    }

    @GetMapping(path = "/users/{users}/me",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<UserDetails>> getMe(@PathVariable("users") String userId) {
        Users userDetails = userService.getUserDetails(userId);
        return ResponseEntity.ok(WebResponse.<UserDetails>builder()
                .data(new UserDetails(userDetails.getName(), userDetails.getUsername()))
                .build());

    }
}
