package org.sppd.otomatis.service;


import org.sppd.otomatis.dto.SlipRequests;
import org.sppd.otomatis.entity.Slip;
import org.sppd.otomatis.entity.Users;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SlipService {
    Slip addSlip(SlipRequests slipRequests, Users users);
    Optional<Slip> editSlip(Long slipRequests);
    Optional<Slip> findSlipByDate(LocalDateTime localDateTime);
    Optional<Slip> findSlipById(Long id);
    Optional<Slip> findSlipByKeterangan(String keterangan);
    Optional<Slip> findSlipByUser(Users users);
    Optional<Slip> findSlipByUserAndDate(SlipRequests slipRequests, LocalDateTime localDateTime);

}
