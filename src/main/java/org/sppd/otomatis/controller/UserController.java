package org.sppd.otomatis.controller;

import org.sppd.otomatis.dto.UserRequest;
import org.sppd.otomatis.dto.WebResponse;
import org.sppd.otomatis.service.UserService;
import org.springframework.http.MediaType;
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
    public WebResponse<String> register(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest);
        return WebResponse.<String>builder()
                .message("OK")
                .build();
    }
}
