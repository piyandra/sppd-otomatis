package org.sppd.otomatis.entity;

import jakarta.persistence.*;
import lombok.*;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "branches")
public class Branch {

    @Id
    @Column(name = "branch_id", nullable = false)
    private String branchId;

    private String branchName;

    @OneToMany(mappedBy = "branch")
    private List<Users> users;
}
