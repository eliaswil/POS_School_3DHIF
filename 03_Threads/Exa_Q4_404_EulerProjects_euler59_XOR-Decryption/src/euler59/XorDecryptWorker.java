/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler59;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Elias Wilfinger
 */
public class XorDecryptWorker implements Callable<String>{
    private int ch;

    public XorDecryptWorker(int ch) {
        this.ch = ch;
    }

    @Override
    public String call() throws Exception {
        List<Integer> byteList = XorDecryptLauncher.byteList;
        List<String> commonWords = XorDecryptLauncher.commonWords;
        
        int index = 0;
       
        int[] combination = new int[3];
        combination[0] = this.ch;
        
        for (int j = 'a'; j <= 'z'; j++) {
            combination[1] = j;
            
            for (int k = 'a'; k <= 'z'; k++) {
                combination[2] = k;
                String possibleSolution = "";
                
                for(Integer b : byteList){
                    possibleSolution += (char)(b ^ combination[index]);
                    index = (index+1) % 3;
                }
                int counter = 0;
                String[] words = possibleSolution.split(" ");
                
                for(String word : words){
                    if(commonWords.contains(word)){
                        counter++;
                        if(counter >= 4){
                            System.out.println("counter: " + counter);
                            System.out.println("Key: " + Arrays.toString(combination) + " = " 
                                    + (char)combination[0] + ", " 
                                    + (char)combination[1] + ", " 
                                    + (char)combination[2]);
                            System.out.println("Possible Solution: " + possibleSolution);
                            return possibleSolution;
                        }
                    }
                }
            }
        }
        
        throw new Exception("no result found");
    }
    
    
}
