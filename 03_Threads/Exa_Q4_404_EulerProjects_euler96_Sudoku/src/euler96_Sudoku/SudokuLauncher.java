/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler96_Sudoku;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elias Wilfinger
 */
public class SudokuLauncher {
    
    
    private static long runSudokuCalculation(List<String> grids){
        try {
            ExecutorService pool =  Executors.newFixedThreadPool(6);
            List<Callable<Integer>> decryptWorker = new ArrayList<>();
            
            for (String grid : grids) {
                decryptWorker.add(new SudokuWorker(grid));
            }
            
            List<Future<Integer>> result = pool.invokeAll(decryptWorker);
            pool.shutdown();
            
            long sum = 0;
            for (Future<Integer> future : result) {
                sum += future.get();
            }
            
            return sum;
            
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(SudokuLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public static void main(String[] args) {
        try {
            List<String> grids = IO_Helper.loadData();
//            grids = IO_Helper.loadDataForASingleSudoku();
            grids.forEach(System.out::println);
            
            long start = System.currentTimeMillis();
            
            long sum = runSudokuCalculation(grids);
            
            System.out.println("Time int ms: " + (System.currentTimeMillis() - start));
            System.out.println("Sum: " + sum);
            
        } catch (IOException ex) {
            Logger.getLogger(SudokuLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
