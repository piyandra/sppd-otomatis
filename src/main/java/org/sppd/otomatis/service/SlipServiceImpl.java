package org.sppd.otomatis.service;

import org.sppd.otomatis.dto.SlipRequests;
import org.sppd.otomatis.entity.Slip;
import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.repository.SlipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SlipServiceImpl implements SlipService{

    private final SlipRepository slipRepository;

    private final TokenService tokenService;

    public SlipServiceImpl(SlipRepository slipRepository, TokenService tokenService) {
        this.slipRepository = slipRepository;
        this.tokenService = tokenService;
    }

    @Override
    public Slip addSlip(SlipRequests slipRequests, Users users) {
        Slip slip = Slip.builder()
                .keterangan(slipRequests.getKeterangan())
                .createdAt(LocalDateTime.now())
                .bank(slipRequests.getBank())
                .nominal(slipRequests.getNominal())
                .pimpinan(slipRequests.getPimpinan())
                .users(users)
                .tanggalTransfer(slipRequests.getTransferDate())
                .build();
        return slipRepository.save(slip);

    }

    @Override
    public Optional<Slip> editSlip(Long slipId) {
        Slip slip = slipRepository.findById(slipId)
                .orElseThrow(() -> new RuntimeException("Slip not found"));
        return Optional.of(slipRepository.save(slip));
    }

    @Override
    public Optional<Slip> findSlipByDate(LocalDateTime localDateTime) {
        return slipRepository.findByCreatedAt(localDateTime);
    }

    @Override
    public Optional<Slip> findSlipById(Long id) {
        return slipRepository.findById(id);
    }

    @Override
    public Optional<Slip> findSlipByKeterangan(String keterangan) {
        return slipRepository.findByKeteranganContainingIgnoreCase(keterangan);
    }

    @Override
    public Optional<Slip> findSlipByUser(Users users) {
        return slipRepository.findByUsers(users);
    }

    @Override
    public Optional<Slip> findSlipByUserAndDate(SlipRequests slipRequests, LocalDateTime localDateTime) {
        return slipRepository.findByUsersAndCreatedAt(slipRequests.getUsers(), localDateTime);
    }
}
