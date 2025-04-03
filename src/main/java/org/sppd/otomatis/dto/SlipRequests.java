package org.sppd.otomatis.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sppd.otomatis.entity.Bank;
import org.sppd.otomatis.entity.Kantor;
import org.sppd.otomatis.entity.Users;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlipRequests {

    @NotNull(message = "Nama Bank Harus Diisi")
    private Bank bank;
    @NotNull(message = "Tanggal Transfer Harus Diisi")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transferDate;
    @NotBlank(message = "Keterangan Harus Diisi, atau spasi jika kosong")
    private String keterangan;
    @Digits(message = "Nominal Harus Diisi", integer = 30, fraction = 0)
    private Long nominal;
    @NotNull(message = "Kantor Harus Diisi")
    private Kantor kantor;
    private String pimpinan;
    private Users users;
}
