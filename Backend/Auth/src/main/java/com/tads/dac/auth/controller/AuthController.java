
package com.tads.dac.auth.controller;

import com.tads.dac.auth.DTOs.AuthDTO;
import com.tads.dac.auth.exception.ContaNotAprovedException;
import com.tads.dac.auth.exception.ContaNotExistException;
import com.tads.dac.auth.exception.ContaWrongPassword;
import com.tads.dac.auth.exception.EncryptionException;
import com.tads.dac.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService serv;
    
    //R2
    @GetMapping("/{email}/{senha}")
    public ResponseEntity<?> fazLogin(@PathVariable(value = "email") String email,
            @PathVariable(value = "senha") String senha){
        try {
            AuthDTO dto2 = serv.getAuth(email, senha);
            return new ResponseEntity<>(dto2, HttpStatus.OK);
        } catch (ContaWrongPassword | ContaNotAprovedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(ContaNotExistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EncryptionException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{email}")
    public ResponseEntity<?> verificaSeExiste(@PathVariable(value = "email") String email){
            Boolean ver = serv.verifyIfExist(email);
            if(ver){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
