package org.sppd.otomatis.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.sppd.otomatis.dto.SlipRequests;
import org.sppd.otomatis.dto.SlipResponse;
import org.sppd.otomatis.dto.SlipResponseWithManyData;
import org.sppd.otomatis.dto.WebResponse;
import org.sppd.otomatis.entity.Slip;
import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.service.SlipServiceImpl;
import org.sppd.otomatis.service.TokenService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("api/v1/slip")
public class SlipController {

    private final SlipServiceImpl slipService;

    public SlipController(SlipServiceImpl slipService, TokenService tokenService) {
        this.slipService = slipService;
        this.tokenService = tokenService;
    }

    private final TokenService tokenService;

    @PostMapping(path = "/create",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<SlipResponse>> createSlip(@Valid @RequestBody SlipRequests slipRequests, @RequestHeader ("Authorization") String token) {
        Users usersByToken = tokenService.findUsersByToken(token);
        log.info(usersByToken.getUsername());
        Slip slip = slipService.addSlip(slipRequests, usersByToken);
        return ResponseEntity.ok(WebResponse.<SlipResponse>builder()
                .message("Slip Created Successfully")
                .data(new SlipResponse(slip))
                .build());

    }
    @GetMapping(path = "/{slipId}/details",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<SlipResponse>> getSlipById(@Valid @PathVariable("slipId") Long slipId) {
        Slip slip = slipService.findSlipById(slipId);
        log.info(slip.getKeterangan());
        SlipResponse response = new SlipResponse(slip);
        return ResponseEntity.ok(WebResponse.<SlipResponse>builder()
                        .message("Sukses").data(response)
                .build());
    }
    @PutMapping(path = "/update/{slipId}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<SlipResponse>> updateSlip (@Valid @PathVariable("slipId") Long slipId, SlipRequests slipRequests) {
        return ResponseEntity.ok(WebResponse.<SlipResponse>builder()
                .data(new SlipResponse(slipService.editSlip(slipId, slipRequests)))
                .message("Sukses")
                .build());
    }
    @GetMapping(path = "/get/by-date")
    public ResponseEntity<WebResponse<SlipResponseWithManyData<SlipResponse>>>
    getSlipByUsersAndDate(@RequestHeader("Authorization") String token,
                          @RequestParam("date") LocalDateTime date,
                          Pageable pageable) {
        Users usersByToken = tokenService.findUsersByToken(token);
        SlipResponseWithManyData<SlipResponse> slipResponseWithManyData = new SlipResponseWithManyData<>(slipService.findSlipByUsersAndDate(usersByToken, date, pageable));
        return ResponseEntity.ok(WebResponse.<SlipResponseWithManyData<SlipResponse>>builder()
                .message("Sukses")
                .data(slipResponseWithManyData)
                .build());
    }


}
