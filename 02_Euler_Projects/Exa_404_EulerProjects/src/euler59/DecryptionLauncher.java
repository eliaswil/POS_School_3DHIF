/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler59;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elias Wilfinger
 */
public class DecryptionLauncher {
    
    public static List<Integer> byteList;
    public static List<String> commonWords;
    
    
    private static String decrypt(){
        try {
            ExecutorService pool =  Executors.newFixedThreadPool(6);
            List<Callable<String>> decryptWorker = new ArrayList<>();
            
            // one Thread for each (first) character [a;z]
            for (int i = 'a'; i <= 'z'; i++) {
                decryptWorker.add(new DecryptionWorker(i));
            }

            String result = pool.invokeAny(decryptWorker);
            pool.shutdown();
            
            System.out.println("Result:" + result);

            return result;
                        
            
        } catch (InterruptedException ex) {
            Logger.getLogger(DecryptionLauncher.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(DecryptionLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }
    
    private static void calculateEulerKeyForSubmission(String decryptedMessage){
        long sum = 0;
        for(byte b : decryptedMessage.getBytes()){
            sum += b;
        }
        System.out.println("Sum: " + sum);
    }
    
    public static void main(String[] args) {
        try {
            byteList = IO_Helper.readBytes();
            commonWords = IO_Helper.getCommonWords();
            
            
            String decryptedMessage = decrypt();
            calculateEulerKeyForSubmission(decryptedMessage);
            
            
        } catch (IOException ex) {
            Logger.getLogger(DecryptionLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
