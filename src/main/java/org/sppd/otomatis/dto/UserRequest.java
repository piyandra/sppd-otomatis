package org.sppd.otomatis.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotNull
    @Size(max = 100, min = 5, message = "Name must be 5 to 100 characters")
    private String name;
    @NotNull
    @Size(max = 100, min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotNull
    @Size(max = 100, min = 3, message = "Username Must Be 3 to 100 characters")
    private String username;
}
