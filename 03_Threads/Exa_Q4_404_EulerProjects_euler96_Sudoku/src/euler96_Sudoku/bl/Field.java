/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package euler96_Sudoku.bl;

/**
 *
 * @author Elias Wilfinger
 */
public class Field {
    private int number;
    private int blockID;
    private int rowID;
    private int columnID;

    public Field(int number, int i, int j) {
        this.number = number;
        this.blockID = i - (i % 3) + (j / 3);
        this.rowID = i;
        this.columnID = j;
    }

    public int getNumber() {
        return number;
    }
    
    public int getBlockID(){
        return this.blockID;
    }

    public int getRowID() {
        return rowID;
    }

    public int getColumnID() {
        return columnID;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + this.number;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        if (this.number != other.number) {
            return false;
        }
        return true;
    }
 
}
