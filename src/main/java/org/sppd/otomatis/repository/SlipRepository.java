package org.sppd.otomatis.repository;

import org.sppd.otomatis.entity.Slip;
import org.sppd.otomatis.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface SlipRepository extends JpaRepository<Slip, Long> {
    Optional<Slip> findByCreatedAt(LocalDateTime createdAt);
    Optional<Slip> findByKeteranganContainingIgnoreCase(String keterangan);
    Optional<Slip> findByUsers(Users users);
    Optional<Slip> findByUsersAndCreatedAt(Users user, LocalDateTime localDateTime);
}
