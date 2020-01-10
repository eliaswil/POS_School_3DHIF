package at.htlkaindorf.exa_q2_205_game2048.bl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private int[][] values;
    private List<Integer> free = new ArrayList<>(); // list of free positions
    private int points;
    private static final Random RAND = new Random();

    public GameLogic(int[][] values, int points) {
        this.values = values;
        this.points = points;
        resetGame();
    }

    public static void main(String[] args) {
        int[][] values = new int[4][4];
        GameLogic gl = new GameLogic(values, 0);
        gl.setNewValue();
        for (int[] value : values) {
            System.out.println(Arrays.toString(value));
        }

    }

    public void makeMove(int direction){
        // ToDo: add free space to list
    }
    public void resetGame(){
        int i = 0;
        for (int[] value : values) {
            Arrays.fill(value, 0);
            free.add(i++);
        }
        points = 0;


    }
    public void setNewValue(){
        int[] possibleValues = {2, 2, 4};
        int index = RAND.nextInt(possibleValues.length);
        if(free.size() > 0){
            int position = RAND.nextInt(free.size());
            values[position / values.length][position % values[0].length] = possibleValues[index];
            free.remove(position);
        }else{
            System.out.println("!!!!!!!!!!!!!!! Alles Voll !!!!!!!!!!!!!!");
        }
    }



    public int[][] getValues() {
        return values;
    }

    public void setValues(int[][] values) {
        this.values = values;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
