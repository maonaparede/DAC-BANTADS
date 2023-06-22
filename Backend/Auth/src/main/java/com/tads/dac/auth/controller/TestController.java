
package com.tads.dac.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TestController {
    //Só pra testar o api gateway
    @GetMapping("/")
    public ResponseEntity<?> helloworld(){ 
        return new ResponseEntity<>("hello world", HttpStatus.OK); 
    }
}
