package org.sppd.otomatis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "slips")
@Entity
public class Slip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tanggal_transfer")
    private LocalDateTime tanggalTransfer;

    @Enumerated(value = EnumType.STRING)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    private String keterangan;

    private LocalDateTime createdAt;

    private Long nominal;

    @Enumerated(value = EnumType.STRING)
    private Kantor kantor;

    @Enumerated(value = EnumType.STRING)
    private Perkiraan perkiraan;

    private String pimpinan;

    @Enumerated(EnumType.STRING)
    private SlipStatus status;
}
