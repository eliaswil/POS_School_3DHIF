/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruteforce;

import bl.Person;
import bl.PersonPassword;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Elias Wilfinger
 */
public class BruteForceManager {
    
    private static List<Person> loadData() throws FileNotFoundException{
        Path path = Paths.get(System.getProperty("user.dir"), "src", "res", "passwd_file.csv");
        
        return new BufferedReader(new FileReader(path.toFile())).lines()
                .skip(1).map(Person::new).collect(Collectors.toList());
        
    }
    
    private static void crackPasswords(List<Person> users){
        ExecutorService pool =  Executors.newFixedThreadPool(6);
        CompletionService<PersonPassword> completionService = new ExecutorCompletionService<>(pool);
        
        users.forEach(user -> completionService.submit(new BruteForceWorker(user)));
        pool.shutdown();
        
        while(!pool.isTerminated()){
            try {
                PersonPassword pw = completionService.take().get();
                if(pw != null){
                    System.out.println(pw);
                }
                
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(BruteForceManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    public static void main(String[] args) {
        
        try {
            List<Person> users = loadData();
            
            crackPasswords(users);
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BruteForceManager.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
}
