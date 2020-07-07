/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler39;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 *
 * @author Elias Wilfinger
 */
public class TriangleWorker implements Callable<Set<Triple>>{
    private int p;

    public TriangleWorker(int p) {
        this.p = p;
    }

    @Override
    public Set<Triple> call() throws Exception {
        Set<Triple> tripleSet = new HashSet<>();
        for (int i = 1; i <= p/2; i++) {
            for (int j = 1; j <= p/2; j++) {
                for (int k = 0; k <= p/2; k++) {
                    if((i*i + j*j == k*k) && ((i+j+k) == p)){
                        tripleSet.add(new Triple(p, i, j, k));
                    }
                    
                }
                
            }
            
        }
        
        
        return tripleSet;
    }
    
    
}
