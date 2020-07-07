/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler96_Sudoku.bl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elias Wilfinger
 */
public class Sudoku {
    
//    private int[] fields;
    private final int SIZE;
    private int noEmptyFields;
    private int compCounter = 0;
    
    private List<List<Field>> blocks = new ArrayList<>();
    private List<List<Field>> columns = new ArrayList<>();
    private List<List<Field>> rows = new ArrayList<>();
    

    public Sudoku(int size, int[] numbers) {
        this.SIZE = size;
        this.noEmptyFields = 0;
        
        for (int i = 0; i < SIZE; i++) {
            blocks.add(new ArrayList<>(SIZE));
            columns.add(new ArrayList<>(SIZE));
            rows.add(new ArrayList<>(SIZE));
        }
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                
                Field field = new Field(numbers[i * SIZE + j], i, j);
                int num = field.getNumber();
                this.noEmptyFields += (int)(Math.pow(num, num)) * (Math.pow(0, num)); // [1;9] -> 0;  0 -> 1
                
                rows.get(i).add(field);
                columns.get(j).add(field);
                blocks.get(i - (i % 3) + (j / 3)).add(field);
            }
        }
    }
    
    public boolean isFieldAllowed(int number, int block, int row, int col){
        for (Field field : blocks.get(block)) {
            compCounter++;
            if(field.getNumber() == number){
                return false;
            }
        }
        
        for (Field field : rows.get(row)) {
            compCounter++;
            if(field.getNumber() == number){
                return false;
            }
        }
        
        for (Field field : columns.get(col)) {
            compCounter++;
            if(field.getNumber() == number){
                return false;
            }
        }
        
        return true;
    }

    public List<List<Field>> getBlocks() {
        return blocks;
    }

    public List<List<Field>> getColumns() {
        return columns;
    }

    public List<List<Field>> getRows() {
        return rows;
    }

    public int getNoEmptyFields() {
        return noEmptyFields;
    }
    
    public void setNoEmptyFields(int noEmptyFields){
        this.noEmptyFields = noEmptyFields;
    }

    @Override
    public String toString() {
        String prettySudoku = "";
        for (int i = 0; i < rows.size(); i++) {
            if(i != 0 && i%3 == 0){
                prettySudoku += "\n";
            }
            for (int j = 0; j < rows.get(i).size(); j++) {
                if(j != 0 && j%3 == 0){
                    prettySudoku += "|";
                }
                prettySudoku += rows.get(i).get(j).getNumber();
                
            }
            prettySudoku += "\n";

        }
        return prettySudoku;
    }

    public int getCompCounter() {
        return compCounter;
    }
    
    
    
    
    
    
    
    
}
