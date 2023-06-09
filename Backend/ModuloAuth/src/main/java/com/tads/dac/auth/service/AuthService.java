
package com.tads.dac.auth.service;

import com.tads.dac.auth.DTOs.AuthDTO;
import com.tads.dac.auth.exception.ContaAlredyExists;
import com.tads.dac.auth.exception.ContaNotExistException;
import com.tads.dac.auth.exception.ContaWrongPassword;
import com.tads.dac.auth.exception.EncryptionException;
import com.tads.dac.auth.exception.InvalidUserTypeException;
import com.tads.dac.auth.model.Auth;
import com.tads.dac.auth.repository.AuthRepository;
import com.tads.dac.auth.util.Encrypt;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private AuthRepository rep;
    
    @Autowired
    private ModelMapper mapper;
    
    
    public AuthDTO getAuth(String email, String senha) throws ContaNotExistException, ContaWrongPassword, EncryptionException{
        Optional<Auth> conta = rep.findById(email);
        if(conta.isPresent()){
            senha = Encrypt.encriptarSenhaLogin(senha, conta.get().getSalt());
            if(conta.get().getSenha().equals(senha)){
                AuthDTO dto = mapper.map(conta.get(), AuthDTO.class);
                return dto;
            }
            throw new ContaWrongPassword("A Senha Está Errada!");
        }
        throw new ContaNotExistException("Essa Conta Não Existe!");
    }
    
    public AuthDTO insertAuth(AuthDTO dto) throws ContaAlredyExists, InvalidUserTypeException, EncryptionException{
        Optional<Auth> conta = rep.findById(dto.getEmail());
        if(conta.isPresent()) throw new ContaAlredyExists("Uma Conta Com Esse Email Já Existe!");
        
        //Admin, Cliente e Gerente - Se não for nenhum desses tipos manda exception
        if("A".equals(dto.getTipoUser()) || 
           "G".equals(dto.getTipoUser()) || 
           "C".equals(dto.getTipoUser())){
            
            String salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            String senha = Encrypt.encriptarInsertBd(dto.getSenha(), salt);

            Auth reg = new Auth(dto.getEmail(), senha, salt, dto.getTipoUser());
            reg = rep.save(reg);
            dto = mapper.map(reg, AuthDTO.class);
            return dto;
        }
        throw new InvalidUserTypeException("Esse Tipo de Usuário Não Existe!");
    } 
    
    //Não tem como atualizar o id(que é o email), então tem que excluir o registro e fazer outro :)
    public AuthDTO updateAuth(String oldEmail, String newEmail) throws ContaAlredyExists, ContaNotExistException{
        Optional<Auth> oldConta = rep.findById(oldEmail);
        Optional<Auth> newConta = rep.findById(newEmail);
        
        if(oldEmail.equals(newEmail)) return null;
        if(!oldConta.isPresent()) throw new ContaNotExistException("Essa Conta Que Está Tentando Mudar o Email Não Existe!");
        if(newConta.isPresent()) throw new ContaAlredyExists("Uma Conta Com Esse Email Já Existe!");       
        
        Auth conta = oldConta.get();
        conta.setEmail(newEmail);
        
        rep.deleteById(oldEmail);
        conta = rep.save(conta);
        AuthDTO dto = mapper.map(conta, AuthDTO.class);
        return dto;
    }
    
    public void deleteLogin(String email){
        rep.deleteById(email);
    }
}
