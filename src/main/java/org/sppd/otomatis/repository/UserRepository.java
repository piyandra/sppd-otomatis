package org.sppd.otomatis.repository;

import jakarta.validation.constraints.NotNull;
import org.sppd.otomatis.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {

    Optional<Users> findFirstByToken(String token);

    boolean existsByUsername(@NotNull String username);
}
