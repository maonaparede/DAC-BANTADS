
package com.tads.dac.auth.util;


import com.tads.dac.auth.exception.EncryptionException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encrypt {
    
    public static int SALT_SIZE = 32;
    public static int SENHA_SIZE = 4;
    
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
        byte[] saltByte = new byte[size]; //gera sempre 2x mais por algum motivo
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
    
    public static void main(String[] args) throws EncryptionException {
        System.out.println(gerarSalt(2));
        String salt = Encrypt.gerarSalt(Encrypt.SALT_SIZE);
        String senha = Encrypt.encriptarSenhaLogin("1234", salt);
        //ffe1e994cb5c48791a194d4436d70290e1a310a789e6691a45bf2d406137ff8c
        //3938A75E06A96619F2D3BA83ED083E813DBF8B2995F335BDCC59F432095A1FD9
        System.out.println(salt);
        System.out.println(senha);
    }
    
}
