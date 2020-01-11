package at.htlkaindorf.exa_q2_205_game2048.bl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private int[] values;
    private int[] directions = {1,-4,-1,4};
    private final int N;
    private List<Integer> free = new ArrayList<>(); // list of free positions
    private int points;
    private static final Random RAND = new Random();

    public GameLogic(int[] values, int points) {
        this.values = values;
        this.points = points;
        this.N = (int)Math.sqrt(values.length);
        resetGame();
    }

    public static void main(String[] args) {
        int[] values = new int[16];
        GameLogic gl = new GameLogic(values, 0);
        for (int i = 0; i < 5; i++) {
            gl.setNewValue();
        }

        Arrays.fill(values, 0);
        values[2] = 4;
        values[7] = 4;
        values[8] = 2;
        values[13] = 2;
        values[15] = 2;

        gl.prettyPrint();
        gl.makeMove(1);
        gl.prettyPrint();

    }

    private void prettyPrint(){
        String x = "";
        for (int i = 0; i < N*N; i+=N) {
            x += "[ ";
            for (int j = i; j < i+N; j++) {
                x += values[j] + ", ";
            }
            x += "]\n";
        }
        System.out.println(x);
    }

    public void makeMove(int direction){
        double[] subdirs = {0.25, 4.0};
        double subdir = subdirs[(direction * (-1) + 4*subdirs.length) % subdirs.length];
        // ToDo: add free space to list
        int[] startValues = {0, (N*N)-1, 0};
        int startValue = startValues[(direction + 2*startValues.length) % startValues.length] - direction;
        int endValue = startValue + N * (int)(direction * (-1) * subdir);


        // step 1: nach [direction] aufrücken
        for (int i = startValue; i != endValue; i += (int)(direction * (-1) * subdir)) {
            //int jStartIndex = i + ((direction * (-1) + N-1) % (N-1)); //+ (int)(Math.pow(0.0, multiplier[(direction + 2*multiplier.length) % multiplier.length]));
            for (int j = i; j != (i - (N-1)*direction); j -= direction) {
                // if elements davor sind frei: move
                move(direction, j, i);
                prettyPrint();
            }
        }
        prettyPrint();

        // addieren
        int[] startJ = {N, N, 0};
        for (int i = 0; Math.abs(i) < N*N; i += subdir) {
            int jStartIndex = i + startJ[(direction + 2*startJ.length) % startJ.length];
            for (int j = jStartIndex; j != startJ[(direction + 1 + 2*startJ.length) % startJ.length]-direction - i; j -= direction) {
                if(values[j] == values[j - direction]){
                    values[j] *= 2;
                    values[j + direction] = 0;
                    move(direction, j, jStartIndex);
                }
            }
        }

        // ToDo: does it work ?? please test !! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    }

    private void move(int direction, int k, int jStartIndex){
        int i = k;
        while(i+direction >= 0 && i+direction <= N*N && (jStartIndex + 2*direction) != i+direction){  // try filling up empty space // ToDo: free() aktualisieren !!
            if(values[i + direction] == 0){
                values[i + direction] = values[i];
                values[i] = 0;
                i += direction;
            }else{
                break;
            }

        }
    }

    public void resetGame(){
        Arrays.fill(values, 0);
        free.clear();
        for (int i = 0; i < N*N; i++) {
            free.add(i);
        }
        points = 0;


    }


    public void setNewValue(){
        int[] possibleValues = {2, 2, 4};
        int index = RAND.nextInt(possibleValues.length);
        if(free.size() > 0){
            int position = RAND.nextInt(free.size());
            values[free.get(position)] = possibleValues[index];
            free.remove(position);
        }else{
            System.out.println("!!!!!!!!!!!!!!! Alles Voll !!!!!!!!!!!!!!"); // sollte nie kommen !!
        }
    }



    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
