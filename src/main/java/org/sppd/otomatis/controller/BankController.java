package org.sppd.otomatis.controller;

import org.sppd.otomatis.entity.Bank;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class BankController {

    @GetMapping(path = "/bank",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getBank() {
        return Arrays.stream(Bank.values())
                .map(Enum::name)
                .toList();

    }

}
