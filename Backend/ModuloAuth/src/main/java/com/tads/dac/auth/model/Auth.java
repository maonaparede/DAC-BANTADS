
package com.tads.dac.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("login")
public class Auth {
    
    @Id
    private String email;
    private String senha;
    private String salt;
    private String tipoUser;
}
