
package com.tads.dac.auth.repository;

import com.tads.dac.auth.model.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface AuthRepository extends MongoRepository<Auth, String>{
    
}
