package org.sppd.otomatis.controller;

import jakarta.validation.Valid;
import org.sppd.otomatis.dto.*;
import org.sppd.otomatis.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user/register",
    produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<WebResponse<String>> register(@Valid @RequestBody UserRequest userRequest) {
        userService.registerUser(userRequest);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("User Registered Sucessfully")
                .build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(path = "/user/login",
    produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<TokenResponse>> login(@Valid @RequestBody LoginRequests loginRequests) {
        TokenResponse tokenResponse = userService.loginUser(loginRequests);
        WebResponse<TokenResponse> token = WebResponse.<TokenResponse>builder()
                .message("Login Success")
                .data(tokenResponse)
                .build();
        return ResponseEntity.ok().body(token);
    }

    @PostMapping(path = "/user/logout",
    produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> logout(@RequestBody LogOutRequest token) {
        userService.logOutUser(token.getToken());
        return ResponseEntity.ok().body("Sukses Logout");
    }
}
