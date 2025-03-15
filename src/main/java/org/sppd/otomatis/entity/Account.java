package org.sppd.otomatis.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "accounts")
public class Account {

    @Id
    @Column(name = "number", nullable = false)
    private String accountNumber;


    @Column(name = "name", nullable = false)
    private String accountName;

    @Column(name = "type", nullable = false)
    private String accountType;

    @Column(name = "address", nullable = false)
    private String accountAddress;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
}
