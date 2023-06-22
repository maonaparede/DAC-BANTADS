package com.tads.dac.saga.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AuthDTO {
    private String email;
    private String senha;
    private String tipoUser;
}
