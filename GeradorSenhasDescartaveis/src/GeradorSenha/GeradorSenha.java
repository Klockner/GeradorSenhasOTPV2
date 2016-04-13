/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atividade2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author JamilliF
 */
public class GeradorSenha {

    private byte[] hashMd5Semente;
    private byte[] hashMd5;
    private ArrayList tokens;


    void geraHashSemente(String usuario, String senha) throws NoSuchAlgorithmException, FileNotFoundException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(senha.getBytes()); //Pega a String senha para a criptografia
        hashMd5Semente = md.digest(); //Faz o hash (criptografia) da senha e guarda em hashMd5Semente
        PrintWriter write = new PrintWriter("HashSemente.txt", "UTF-8");
        write.print(hashMd5Semente); //Escreve em um .txt a hash semente gerada a partir da senha do usuário
        write.close();

    }

    void geraSenhaDescartavel() throws NoSuchAlgorithmException {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmm"); //Formata a data e hora
        Date data = new Date(); //Pega a data e hora atual
        String dataHora = sdf.format(data);
        MessageDigest md = MessageDigest.getInstance("MD5");
        String concatDataHora = dataHora.concat(hashMd5Semente.toString()); //Concatena o hashSemente com a data e hora atual

        tokens = new ArrayList(); //Array de tokens
        byte[] hashLevel = concatDataHora.getBytes(); //Atribui a concatenação do hashSemente e data e hora
        for (int i = 0; i < 5; i++) {
            md.update(hashLevel); //Pega o hashLevel para a criptografia
            hashMd5 = md.digest(); //Faz o hash (criptografia) e guarda em hashMd5
            tokens.add(hashMd5); //Adiciona na lista o token gerado
            hashLevel = hashMd5; //Atribui o token gerado para o hashLevel para uma nova criptografia

        }
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i));
        }
        
        int aux = 0;
        for (byte b: hashLevel){
            aux += (int) b;
                
        }
        System.out.println(aux);
    }
}
