package org.sppd.otomatis.repository;

import org.sppd.otomatis.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Users, String> {

    boolean existsByToken(String token);

    Users findByToken(String token);
}
