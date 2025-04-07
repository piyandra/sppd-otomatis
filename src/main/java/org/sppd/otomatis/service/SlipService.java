package org.sppd.otomatis.service;


import org.sppd.otomatis.dto.SlipRequests;
import org.sppd.otomatis.dto.SlipResponse;
import org.sppd.otomatis.entity.Slip;
import org.sppd.otomatis.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SlipService {
    Slip addSlip(SlipRequests slipRequests, Users users);
    Slip editSlip(Long id, SlipRequests slipRequests);
    Optional<Slip> findSlipByDate(LocalDateTime localDateTime);
    Slip findSlipById(Long id);
    Optional<Slip> findSlipByKeterangan(String keterangan);
    Optional<Slip> findSlipByUser(Users users);
    Page<SlipResponse> findSlipByUsersAndDate(Users users, LocalDateTime localDateTime, Pageable pageable);

}
