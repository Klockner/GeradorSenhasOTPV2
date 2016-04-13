/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeradorSenha;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class Usuario {
    private String nome;
    private String senha;
    private byte[] hashMd5Semente;
    
    
    
    public Usuario(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }
    
    public void gerarHashSemente() {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(senha.getBytes());
            hashMd5Semente = messageDigest.digest();
            
            FileWriter arq = new FileWriter("HashSemente.txt");
            PrintWriter escreveArq = new PrintWriter(arq);
            
            escreveArq.print(hashMd5Semente);
            
            arq.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the hashMd5Semente
     */
    public byte[] getHashMd5Semente() {
        return hashMd5Semente;
    }

    /**
     * @param hashMd5Semente the hashMd5Semente to set
     */
    public void setHashMd5Semente(byte[] hashMd5Semente) {
        this.hashMd5Semente = hashMd5Semente;
    }
}
