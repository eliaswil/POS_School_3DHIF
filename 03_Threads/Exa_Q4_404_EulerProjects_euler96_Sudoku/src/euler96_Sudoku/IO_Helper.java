/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler96_Sudoku;

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
    public static List<String> loadData() throws FileNotFoundException, IOException{
        Path path = Paths.get(System.getProperty("user.dir"), "src", "euler96_Sudoku", "res", "p096_sudoku.txt");
        
        List<String> lines = new BufferedReader(new FileReader(path.toFile())).lines()
                .map(String::new).collect(Collectors.toList());

        List<String> grids = new ArrayList<>();
        String grid = "";
        for(String line : lines){
            if(line.contains("Grid")){
                grids.add(grid);
                grid = "";
                continue;
            }
            grid += line;
        }
        grids.add(grid);
        grids.remove(0);
        
        return grids;
        
    }
    
    public static List<String> loadDataForASingleSudoku(){
        List<String> grids = new ArrayList<>();
        
        String sudokuGrid = "003020600\n" +
            "900305001\n" +
            "001806400\n" +
            "008102900\n" +
            "700000008\n" +
            "006708200\n" +
            "002609500\n" +
            "800203009\n" +
            "005010300";
        sudokuGrid = sudokuGrid.replaceAll("\n", "");
        
        grids.add(sudokuGrid);
        
        return grids;
        
    }
    

}

