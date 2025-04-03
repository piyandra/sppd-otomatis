package org.sppd.otomatis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class Users {

    private String name;

    private String password;

    @Id
    @Column(name = "user_id")
    private String username;


    @Enumerated(EnumType.STRING)
    private UserRoles roles;

    private String token;

    private long expiredAt;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Slip> slip;
}
