package org.sppd.otomatis.repository;

import org.sppd.otomatis.entity.Slip;
import org.sppd.otomatis.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SlipRepository extends JpaRepository<Slip, Long> {
    Optional<Slip> findByCreatedAt(LocalDateTime createdAt);
    Optional<Slip> findByKeteranganContainingIgnoreCase(String keterangan);
    Optional<Slip> findByUsers(Users users);

    Page<Slip> findByUsersAndCreatedAtBetween(Users users, LocalDateTime createdAtAfter, LocalDateTime createdAtBefore, Pageable pageable);
}
