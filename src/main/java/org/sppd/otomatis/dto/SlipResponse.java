package org.sppd.otomatis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sppd.otomatis.entity.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlipResponse {
    private Long id;

    private LocalDateTime tanggalTransfer;

    private Bank bank;
    private Users users;

    private String keterangan;

    private LocalDateTime createdAt;

    private Long nominal;

    private Kantor kantor;

    private Perkiraan perkiraan;

    private String pimpinan;

    public SlipResponse(Slip slip) {
        this.id = slip.getId();
        this.keterangan = slip.getKeterangan();
        this.bank = slip.getBank();
        this.nominal = slip.getNominal();
        this.pimpinan = slip.getPimpinan();
        this.tanggalTransfer = slip.getTanggalTransfer();
        this.createdAt = slip.getCreatedAt();
    }
}
