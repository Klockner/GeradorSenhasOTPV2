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
public final class GeradorOTP {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
    String primeiraDataHora;
    String dataHora;

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
    
    class TimerToken extends Thread {

        @Override
        public void run() {
            while (true) {
                Date data = new Date();
                dataHora = simpleDateFormat.format(data);
                if (!primeiraDataHora.equals(dataHora)) {
                    gerarTokens(numeroTokens);
                    primeiraDataHora = dataHora;
                }
            }
        }
    }
    
    public int numeroTokens = 0;
    private String hashMd5Semente;
    private List listaTokens;
    private String token;
    private final long SALT = 1234554321;
    private String nome;
    
    public GeradorOTP() {
        lerHashSemente();
        gerarTokens(5);
        TimerToken timerToken = new TimerToken();
        timerToken.start();
    }
    
    public void lerHashSemente() {
        try {
            FileReader arq = new FileReader("HashSemente.txt");
            BufferedReader lerArq = new BufferedReader(arq); 
            hashMd5Semente = lerArq.readLine();
            nome = lerArq.readLine();
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
        
        Date data = new Date();
        primeiraDataHora = simpleDateFormat.format(data);
        
//        String concatDataHora = dataHora.concat(hashMd5Semente.toString());
        String concatDataHora = primeiraDataHora.concat(hashMd5Semente);
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
