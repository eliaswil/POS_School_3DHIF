/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruteforce;

import bl.Person;
import bl.PersonPassword;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

/**
 *
 * @author Elias Wilfinger
 */
public class BruteForceWorker implements Callable<PersonPassword>{
    private final int PASSWORD_LENGTH = 5;
    private final Person PERSON;

    public BruteForceWorker(Person person) {
        this.PERSON = person;
    }
    
    private String printHexBinary(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    

    @Override
    public PersonPassword call() throws Exception {
        
        System.out.println("started Current Thread: " + Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        
        // fill array with possible chars
        int i = 0;
        char[] chars = new char['z'-'a' + '9'-'0' + 2];
        for(char ch = 'a'; ch <= 'z'; ch++, i++){
            chars[i] = ch;
        }
        for (char ch = '0'; ch <= '9'; ch++, i++) {
            chars[i] = ch; 
        }
        
        // bruteforce
        char[] combination = new char[PASSWORD_LENGTH];
        String hashString = "";
        
        for (int j = 0; j < chars.length; j++) { // 9j0g3
            combination[0] = chars[j];
            for (int k = 0; k < chars.length; k++) {
                combination[1] = chars[k];
                for (int l = 0; l < chars.length; l++) {
                    combination[2] = chars[l];
                    for (int m = 0; m < chars.length; m++) {
                        combination[3] = chars[m];
                        for (int n = 0; n < chars.length; n++) {
                            combination[4] = chars[n];
                            
                            String passwordString = PERSON.getFirstName() 
                                    + PERSON.getLastName()
                                    + String.valueOf(combination);

                            MessageDigest md = MessageDigest.getInstance("MD5");
                            byte[] hash = md.digest(passwordString.getBytes());
                            hashString = printHexBinary(hash).toLowerCase();
                            
                            
                            if(hashString.equals(PERSON.getHash())){
                                
                                System.out.println("finished with Thread: " + Thread.currentThread().getName() 
                                        + "\twithin "
                                        + (System.currentTimeMillis() - start)
                                        + " ms");
                                return new PersonPassword(PERSON, String.valueOf(combination));
                            }
                        }   
                    } 
                } 
            }
        }
        
        System.out.println("finished with Thread: " + Thread.currentThread().getName() 
                                        + "\twithin "
                                        + (System.currentTimeMillis() - start)
                                        + " ms"
                                        + "\t no password found!");
              
        return null;
        
    } 
}
