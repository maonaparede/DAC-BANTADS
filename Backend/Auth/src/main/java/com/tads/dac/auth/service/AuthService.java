
package com.tads.dac.auth.service;

import com.tads.dac.auth.DTOs.AuthDTO;
import com.tads.dac.auth.DTOs.AuthTotalDTO;
import com.tads.dac.auth.DTOs.RejeitaClienteDTO;
import com.tads.dac.auth.exception.ContaAlredyExists;
import com.tads.dac.auth.exception.ContaNotAprovedException;
import com.tads.dac.auth.exception.ContaNotExistException;
import com.tads.dac.auth.exception.ContaWrongPassword;
import com.tads.dac.auth.exception.EncryptionException;
import com.tads.dac.auth.exception.InvalidUserTypeException;
import com.tads.dac.auth.model.Auth;
import com.tads.dac.auth.repository.AuthRepository;
import com.tads.dac.auth.util.Encrypt;
import com.tads.dac.auth.util.EnviarEmail;
import com.tads.dac.auth.util.TemplateEmailSenha;
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
    
    @Autowired
    private EnviarEmail email;
    
    
    public AuthDTO getAuth(String email, String senha) throws ContaNotExistException, ContaWrongPassword, EncryptionException, ContaNotAprovedException{
        Optional<Auth> conta = rep.findById(email);
        if(conta.isPresent()){
            senha = Encrypt.encriptarSenhaLogin(senha, conta.get().getSalt());
            if(conta.get().getSalt().equals("1")){
                throw new ContaNotAprovedException("Essa Conta Está em aguardo ou não foi aprovada!");
            }
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
        if("C".equals(dto.getTipoUser())){
            
            String salt = Encrypt.gerarSalt(4);
            String senha = Encrypt.encriptarInsertBd(salt, salt);
            
            ///colocando o salt assim hardcoded é impossível fazer login antes de aprovar a conta pro user
            Auth reg = new Auth(dto.getEmail(), senha, "1", "C");
            reg = rep.save(reg);
            dto = mapper.map(reg, AuthDTO.class);
            return dto;
        }
        throw new InvalidUserTypeException("Esse Tipo de Usuário Não Existe!");
    }
    
    public AuthDTO aprovarCliente(String emailCliente) throws EncryptionException, ContaNotExistException{
        Optional<Auth> ct = rep.findById(emailCliente);
        if(ct.isPresent()){
            Auth conta = ct.get();
            
            String senha2 = Encrypt.gerarSalt(4); //Gera 8 char aleatórios
            
            String salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            String senha = Encrypt.encriptarInsertBd(senha2, salt); 
            
            conta.setSalt(salt);
            conta.setSenha(senha);
            
            conta = rep.save(conta);
            
            TemplateEmailSenha emailTemplate = 
                    new TemplateEmailSenha(emailCliente, "C", senha2);
            
            email.sendEmail(emailTemplate);
            
            AuthDTO dto = mapper.map(conta, AuthDTO.class);
            return dto;
        }else{
            throw new ContaNotExistException("Essa Conta Não Existe!");
        }
    }
    
    public AuthDTO insertAuthGerente(AuthDTO dto) throws ContaAlredyExists, InvalidUserTypeException, EncryptionException{
        Optional<Auth> conta = rep.findById(dto.getEmail());
        if(conta.isPresent()) throw new ContaAlredyExists("Uma Conta Com Esse Email '" + dto.getEmail() +"' Já Existe!");
        
        //Admin, Cliente e Gerente - Se não for nenhum desses tipos manda exception
        if("A".equals(dto.getTipoUser()) || 
           "G".equals(dto.getTipoUser())){
            
            String senha2 = Encrypt.gerarSalt(4); //Gera 8 char aleatórios
            
            String salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
            String senha = Encrypt.encriptarInsertBd(senha2, salt);

            Auth reg = new Auth(dto.getEmail(), senha, salt, dto.getTipoUser());
            reg = rep.save(reg);
            
            TemplateEmailSenha emailTemplate = 
                    new TemplateEmailSenha(dto.getEmail(), dto.getTipoUser(), senha2);
            
            email.sendEmail(emailTemplate);
            
            dto = mapper.map(reg, AuthDTO.class);
            return dto;
        }
        throw new InvalidUserTypeException("Esse Tipo de Usuário Não Existe!");
    }    
    
    //Não tem como atualizar o id(que é o email), então tem que excluir o registro e fazer outro :)
    public AuthTotalDTO updateAuth(String oldEmail, String newEmail) throws ContaAlredyExists, ContaNotExistException{
        Optional<Auth> oldConta = rep.findById(oldEmail);
        Optional<Auth> newConta = rep.findById(newEmail);
        
        if(oldEmail.equals(newEmail)) return null;
        if(!oldConta.isPresent()) throw new ContaNotExistException("Essa Conta Que Está Tentando Mudar o Email Não Existe!");
        if(newConta.isPresent()) throw new ContaAlredyExists("Uma Conta Com Esse Email '" + newEmail + "' Já Existe!");       
        
        Auth conta = oldConta.get();
        conta.setEmail(newEmail);
        
        rep.deleteById(oldEmail);
        conta = rep.save(conta);
        
        AuthTotalDTO dto = mapper.map(conta, AuthTotalDTO.class);
        return dto;
    }
    
    public void deleteLogin(String email){
        rep.deleteById(email);
    }

    public AuthTotalDTO removeGerente(AuthDTO dto) throws ContaNotExistException {
        Optional<Auth> model = rep.findById(dto.getEmail());
        if(model.isPresent()){
            AuthTotalDTO authTotal = mapper.map(model.get(), AuthTotalDTO.class);
            rep.deleteById(dto.getEmail());
            
            return authTotal;
        }
        throw new ContaNotExistException("Essa Conta Não Existe");
        
    }

    public void aprovarClienteRollback(String emailString) throws ContaNotExistException {
        Optional<Auth> ct = rep.findById(emailString);
        if(ct.isPresent()){
            Auth reg = ct.get();
            reg.setSalt("1"); //Pra não deixar o user entrar
            reg.setTipoUser("C");
            rep.save(reg);
        }else{
           throw new ContaNotExistException("Essa Conta Não Existe");
        }
    }

    public AuthTotalDTO rejeitaCliente(RejeitaClienteDTO dto) throws ContaNotExistException {
        Optional<Auth> model = rep.findById(dto.getEmail());
        if (model.isPresent()) {
            AuthTotalDTO dtoRet = mapper.map(model.get(), AuthTotalDTO.class);
            rep.deleteById(dto.getEmail());
            return dtoRet;
        }
        throw new ContaNotExistException("Essa Conta Não Existe");
    }

    public void rejeitaClienteRollback(AuthTotalDTO dto) {
        Auth auth = mapper.map(dto, Auth.class);
        rep.save(auth);
    }
    
}
