
package com.tads.dac.auth.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("login")
public class Auth implements Serializable{
    
    @Id
    private String email;
    
    @NonNull
    private String senha;
    private String salt;
    private String tipoUser;
}
