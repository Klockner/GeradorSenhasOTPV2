/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeradorSenha;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class GeradorOTP {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmm");
    String primeiraDataHora;
    String dataHora;
    
    class TimerToken extends Thread {

        @Override
        public void run() {
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GeradorOTP.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (true) {
                Date data = new Date();
                dataHora = simpleDateFormat.format(data);
                if (!primeiraDataHora.equals(dataHora)) {
                    gerarTokens(hashMd5Semente, numeroTokens);
                    primeiraDataHora = dataHora;
                }
            }
        }
    }
    
    private final long SALT = 1234554321;
    private byte[] hashMd5Semente;
    private List listaTokens;
    public int numeroTokens = 0;
    private String token;
    
    public GeradorOTP() {
        TimerToken timerToken = new TimerToken();
        timerToken.start();
    }
    
    // Fórmula token
    //(hashSemente + SALT) * i
    public void gerarTokens(byte[] hashMd5Semente, int numeroTokens) {
        System.out.println("GERADOR SENHA: ");
        this.numeroTokens = numeroTokens;
        this.hashMd5Semente = hashMd5Semente;
        
        Date data = new Date();
        primeiraDataHora = simpleDateFormat.format(data);
        String concatDataHora = primeiraDataHora.concat(hashMd5Semente.toString());
        listaTokens = new ArrayList<>();
//        byte[] hashToken = concatDataHora.getBytes();

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
    
    public String getToken() {
        if (listaTokens.size() > 0) {
            String token = listaTokens.get(listaTokens.size()-1).toString();
            listaTokens.remove(listaTokens.size()-1);
            return token;
        } else {
            return null;
        } 
    }
}
