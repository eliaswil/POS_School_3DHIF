/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler96_Sudoku;

import euler96_Sudoku.bl.Field;
import euler96_Sudoku.bl.Sudoku;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Elias Wilfinger
 */
public class SudokuWorker implements Callable<Integer>{
    
    private final int SIZE = 9;
    private int compCounter = 0;
    
    private final int[] NUMBERS;

    public SudokuWorker(String grid) {
        this.NUMBERS = new int[grid.length()];
        int i = 0;
        for(char ch : grid.toCharArray()){
            this.NUMBERS[i++] = Integer.parseInt(Character.toString(ch));
        }
    }

    @Override
    public Integer call() throws Exception {
        Sudoku sudoku = new Sudoku(SIZE, NUMBERS);
        
        sudoku = recursiveBackTracking(sudoku, 0);
//        System.out.println("\n\n" + sudoku.toString() + "\n#####################################\n");
//        System.out.println("Comparisons: " + (compCounter + sudoku.getCompCounter()));
       
        int number = 0;
        int factor = (int)Math.pow(10, Math.sqrt(SIZE)-1);
        for (int i = 0; i < Math.sqrt(SIZE); i++) {
            number += sudoku.getRows().get(0).get(i).getNumber() * factor;
            factor /= 10;
            
        }
        
        return number;
    }
    
    private Sudoku recursiveBackTracking(Sudoku sudoku, int startI){
        
        compCounter++;
        if(sudoku.getNoEmptyFields() == 0){
            return sudoku;
        }
        
        List<List<Field>> rows = sudoku.getRows();
        for(int i = startI; i < rows.size(); i++){
            List<Field> row = rows.get(i);
            
            for (int j = 0; j < row.size(); j++) {
                
                compCounter++;
                if(row.get(j).getNumber() == 0){ // if field is empty 
                    sudoku.setNoEmptyFields(sudoku.getNoEmptyFields() - 1);
                    
                    for (int k = 1; k <= SIZE; k++) {
                        Field field = sudoku.getRows().get(i).get(j);
                        
                        compCounter++;
                        if(sudoku.isFieldAllowed(k, field.getBlockID(), field.getRowID(), field.getColumnID())){   
                            sudoku.getRows().get(i).get(j).setNumber(k);
                            sudoku = recursiveBackTracking(sudoku, i);
                            
                            compCounter++;
                            if(sudoku.getNoEmptyFields() == 0){
                                return sudoku;
                            }
                        }    
                    }
                    
                    // unplace number, because of failure
                    sudoku.getRows().get(i).get(j).setNumber(0); 
                    sudoku.setNoEmptyFields(sudoku.getNoEmptyFields() + 1);
                    
                    return sudoku;
                }
            }
        }
        return sudoku;
    }
    
}
