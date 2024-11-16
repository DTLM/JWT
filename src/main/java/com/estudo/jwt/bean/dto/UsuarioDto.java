package com.estudo.jwt.bean.dto;

import jakarta.annotation.Nonnull;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {
    @Nonnull
    private String email;
    @Nonnull
    private String senha;
    private String senhaNova;
    private String name;
    private Long RoleId;
    public boolean isValidDataToCreateToken() {
        return !this.email.isBlank();
    }
}
