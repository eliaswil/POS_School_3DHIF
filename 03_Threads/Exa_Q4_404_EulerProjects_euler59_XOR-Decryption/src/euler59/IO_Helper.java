/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler59;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Elias Wilfinger
 */
public class IO_Helper {
    public static List<Integer> loadData() throws FileNotFoundException, IOException{
        Path path = Paths.get(System.getProperty("user.dir"), "src", "euler59", "res", "p059_cipher.txt");
        
        File file = path.toFile();
        
        String[] tokens = new BufferedReader(new FileReader(file)).readLine().trim().split(",");
        List<Integer> byteList = new ArrayList<>();
        for(String token : tokens){
            byteList.add(Integer.parseInt(token));
        }
        return byteList;
        
    }
    
    public static List<String> getCommonWords() throws FileNotFoundException, IOException{
        Path path = Paths.get(System.getProperty("user.dir"), "src", "euler59", "res", "commonWords.txt");
        File file = path.toFile();
        
        List<String> tokens = new BufferedReader(new FileReader(file)).lines().map(String::new).collect(Collectors.toList());

        return tokens;
    }
}
