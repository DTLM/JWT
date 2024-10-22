package com.estudo.jwt.bean.dto;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDto {
    @NonNull
    private String email;
    @NonNull
    private Map<String, Object> dados;
    private String senha;
    private String name;
    private Long RoleId;

    public boolean isValidDataToCreateToken() {
        if(this.email.isBlank() || this.dados.isEmpty()) {
            return false;
        }
        return true;
    }
}
