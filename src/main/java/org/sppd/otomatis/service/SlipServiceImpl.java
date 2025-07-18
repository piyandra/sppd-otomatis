package org.sppd.otomatis.service;

import org.sppd.otomatis.dto.SlipRequests;
import org.sppd.otomatis.dto.SlipResponse;
import org.sppd.otomatis.entity.Slip;
import org.sppd.otomatis.entity.SlipStatus;
import org.sppd.otomatis.entity.Users;
import org.sppd.otomatis.exception.SlipNotFoundException;
import org.sppd.otomatis.repository.SlipRepository;
import org.sppd.otomatis.util.LocalDateTimeStart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SlipServiceImpl implements SlipService {

    private final SlipRepository slipRepository;

    public SlipServiceImpl(SlipRepository slipRepository) {
        this.slipRepository = slipRepository;
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
                .status(SlipStatus.UNVERIVIED)
                .build();
        return slipRepository.save(slip);

    }

    @Override
    public Slip editSlip(Long id, SlipRequests slipId) {
        Slip slip = slipRepository.findById(id)
                .orElseThrow(() -> new SlipNotFoundException("Slip not found"));
        slip.setPimpinan(slipId.getPimpinan());
        slip.setNominal(slipId.getNominal());
        slip.setBank(slipId.getBank());
        slip.setKantor(slipId.getKantor());
        slip.setCreatedAt(LocalDateTime.now());
        slip.setKeterangan(slipId.getKeterangan());
        return slip;
    }

    @Override
    public Optional<Slip> findSlipByDate(LocalDateTime localDateTime) {
        return slipRepository.findByCreatedAt(localDateTime);
    }

    @Override
    public Slip findSlipById(Long id) {
        return slipRepository.findById(id).orElseThrow(() -> new SlipNotFoundException("Not Found"));
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
    public Page<SlipResponse> findSlipByUsersAndDate(Users users, LocalDateTime localDateTime, Pageable pageable) {
        return Stream.of(new LocalDateTimeStart().getStartAndEndOfDay(localDateTime))
                .map(range -> slipRepository.findByUsersAndCreatedAtBetween(users, range.getKey(), range.getValue(), pageable))
                .findFirst().orElseThrow()
                .map(SlipResponse::new);

    }

    @Override
    public Page<SlipResponse> findSlipByStatus(SlipStatus status, Pageable pageable) {
        return slipRepository.findByStatus(status, pageable).map(SlipResponse::new);
    }
}