/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplicativo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gabriel
 */
public class GeradorOTP {
    public int numeroTokens = 0;
//    private byte[] hashMd5Semente;
    private String hashMd5Semente;
    private List listaTokens;
    private String token;
    private final long SALT = 1234554321;
    
    public void lerHashSemente() {
        try {
            FileReader arq = new FileReader("HashSemente.txt");
            BufferedReader lerArq = new BufferedReader(arq); 
            String linha = lerArq.readLine();
            hashMd5Semente = linha;
//            hashMd5Semente = linha.getBytes();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void gerarTokens(int numeroTokens) {
        System.out.println("APLICATIVO: ");
        
        this.numeroTokens = numeroTokens;
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
        Date data = new Date();
        String dataHora = simpleDateFormat.format(data);
        
//        String concatDataHora = dataHora.concat(hashMd5Semente.toString());
        String concatDataHora = dataHora.concat(hashMd5Semente);
        listaTokens = new ArrayList<>();
        token = String.valueOf(Math.abs(concatDataHora.hashCode() + SALT));
        listaTokens.add(token);
        System.out.println(token);
        for (int i = 1; i < numeroTokens; i++) {
//                messageDigest.update(hashToken);
//                hashToken = messageDigest.digest();
//                listaTokens.add(hashToken);
//                
//                System.out.println(hashToken.toString());
            token = String.valueOf(Math.abs(token.hashCode()));
            listaTokens.add(token);
            System.out.println(token);
        }
    }
    
    public boolean validaToken(String token) {
        for (int i = 0; i < listaTokens.size(); i++) {
            if (token.equals(listaTokens.get(i).toString())) {
                listaTokens.remove(i);
                return true;
            }
        }
        return false;
    }
}
