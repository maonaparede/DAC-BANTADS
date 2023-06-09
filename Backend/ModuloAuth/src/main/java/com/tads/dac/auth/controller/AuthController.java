
package com.tads.dac.auth.controller;

import com.tads.dac.auth.DTOs.AuthDTO;
import com.tads.dac.auth.DTOs.AuthLoginDTO;
import com.tads.dac.auth.exception.ContaAlredyExists;
import com.tads.dac.auth.exception.ContaNotExistException;
import com.tads.dac.auth.exception.ContaWrongPassword;
import com.tads.dac.auth.exception.EncryptionException;
import com.tads.dac.auth.exception.InvalidUserTypeException;
import com.tads.dac.auth.service.AuthService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {
    
    @Autowired
    private AuthService serv;
    
    @GetMapping("/auth/{email}/{senha}")
    public ResponseEntity<?> fazLogin(@PathVariable(value = "email") String email,
            @PathVariable(value = "senha") String senha){
        try {
            AuthDTO dto2 = serv.getAuth(email, senha);
            return new ResponseEntity<>(dto2, HttpStatus.OK);
        } catch (ContaWrongPassword e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch(ContaNotExistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (EncryptionException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/auth/{oldEmail}/{newEmail}")
    public ResponseEntity<?> fazUpdate(
            @PathVariable(value = "oldEmail") String oldEmail,
            @PathVariable(value = "newEmail") String newEmail
            ){
        try {
            AuthDTO dto2 = serv.updateAuth(oldEmail, newEmail);
            return new ResponseEntity<>(dto2, HttpStatus.OK);
        } catch(ContaNotExistException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (ContaAlredyExists ex) {
             return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/auth")
    public ResponseEntity<?> fazInsert(@RequestBody AuthDTO dto){
        try {
            dto = serv.insertAuth(dto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (ContaAlredyExists | InvalidUserTypeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EncryptionException ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/auth/{email}")
    public ResponseEntity<?> fazInsert(@PathVariable(value = "email") String email){
        serv.deleteLogin(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
