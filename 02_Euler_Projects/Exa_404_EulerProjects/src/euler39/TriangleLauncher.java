/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler39;

import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elias Wilfinger
 */
public class TriangleLauncher {
    
    public void performTriangleCalculation(){
        ExecutorService pool =  Executors.newFixedThreadPool(6);
        CompletionService<Set<Triple>> service = new ExecutorCompletionService<>(pool);
        for (int p = 10; p <= 1000; p++) {
            service.submit(new TriangleWorker(p));
        }
        pool.shutdown();
        
        Set<Triple> maxTriple = null;
        while(!pool.isTerminated()){
            try {
                Future<Set<Triple>> future = service.take();
                Set<Triple> result = future.get();
                if(maxTriple == null || result.size() > maxTriple.size()){
                    maxTriple = result;
                }
                System.out.println("-->" + result.size());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(TriangleLauncher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        for(Triple triple : maxTriple){
            System.out.println(triple.toString());
        }
        
    }
    
    public static void main(String[] args) {
        new TriangleLauncher().performTriangleCalculation();
    }
}
