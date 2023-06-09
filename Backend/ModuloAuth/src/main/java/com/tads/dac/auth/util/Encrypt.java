
package com.tads.dac.auth.util;


import com.tads.dac.auth.exception.EncryptionException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {
    
    public static int SALT_SIZE = 32;
    
    public static String criptoSha256(String senha) throws EncryptionException{
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = md.digest(senha.getBytes("UTF-8"));
            
            
            StringBuilder sb = new StringBuilder();
            
            for(byte b : messageDigest){
                sb.append(String.format("%02X", 0xFF & b));
            }
            String SenhaHex = sb.toString();
            return SenhaHex;
        } catch (NoSuchAlgorithmException ex) {
            throw new EncryptionException("O ALGORITMO DE ENCRIPTAÇÃO NÃO ESTÁ DISPONÍVEL", ex);
        } catch (UnsupportedEncodingException ex) {
            throw new EncryptionException("ESSA CODIFICAÇÃO NÃO É SUPORTADA", ex);
        }
    }
        
    public static String gerarSalt(int size){
        StringBuilder salt = new StringBuilder();
        SecureRandom random = new SecureRandom();
        byte[] saltByte = new byte[size];
        random.nextBytes(saltByte);
        
        for (byte b : saltByte) {
            salt.append(String.format("%02x", b)); //Converte para caracter legivel
        }
        return salt.toString();
    }

    public static String encriptarSenhaLogin(String senha, String salt) throws EncryptionException{
        senha = criptoSha256(senha);
        String resultado = criptoSha256(senha + salt);
        return resultado;
    }
    
    public static String encriptarInsertBd(String senha, String salt) throws EncryptionException{
        senha = criptoSha256(senha);
        senha = criptoSha256(senha + salt);
        return senha;
    }
    
    
    public static void main(String[] args) {
        System.out.println(gerarSalt(4));
    }
    
}
