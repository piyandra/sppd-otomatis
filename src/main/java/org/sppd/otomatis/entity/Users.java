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

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "users")
    private List<Transaction> transaction;

    @Enumerated(EnumType.STRING)
    private UserRoles roles;

    private String token;

    private long expiredAt;
}
