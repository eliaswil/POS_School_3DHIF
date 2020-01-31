package at.htlkaindorf.exa_q2_207_minesweeper.bl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameLogic {
    private MineSweeperField[][] mineSweeperFields;
    private int noTaggedMines;
    private int fieldSize;
    private LocalTime startTime;
    private LocalTime endTime;
    private static final int NO_MINES = 10;

    private void placeMines(Integer[] firstPosition){
        mineSweeperFields = new MineSweeperField[fieldSize][fieldSize];

        int[][] listOfPositions = new int[NO_MINES][NO_MINES];
        List<Integer[]> listOfAvailablePositions = new ArrayList<>();
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                Integer[] pos = {i, j};
                listOfAvailablePositions.add(pos);
            }
        }
        listOfAvailablePositions.remove(firstPosition);

        Random rand = new Random();
        for (int i = 0; i < listOfPositions.length; i++) {
            int pos = rand.nextInt(listOfAvailablePositions.size());
            int[] helpPos = {listOfAvailablePositions.get(pos)[0], listOfAvailablePositions.get(pos)[1]};
            listOfPositions[i] = helpPos;
            listOfAvailablePositions.remove(pos);
        }

        // fill field
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                mineSweeperFields[i][j] = new MineSweeperField(null, null, false, false, false);
            }
        }

//         fill mines
//        for (int i = 0; i < listOfPositions.length; i++) {
//            mineSweeperFields[listOfPositions[i][0]][listOfPositions[i][1]].setMine(true);
//        }

        mineSweeperFields[3][1].setMine(true); // ToDo: set mines in new field
        mineSweeperFields[3][0].setMine(true);

        // set Neighbours
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                int[] pos = {i, j};
                setNeighbours(pos);
            }
        }

    }
    private void setNeighbours(int[] pos){
        int[] velocity = {-1, 0, 1};
        List<Integer[]> neighbouringMines = new ArrayList<>();
        List<Integer[]> neighbours = new ArrayList<>();
        for (int i = 0; i < velocity.length; i++) {
            for (int j = 0; j < velocity.length; j++) {
                int[] position = {pos[0] + velocity[i], pos[1] + velocity[j]};
                if(position[0] >= 0 && position[1] >= 0 && position[0] < fieldSize && position[1] < fieldSize
                        && (position[0] != pos[0] || position[1] != pos[1])){
                    Integer[] helpPos = {position[0], position[1]};
                    if(mineSweeperFields[position[0]][position[1]].isMine()){
                        neighbouringMines.add(helpPos);
                    }
                    neighbours.add(helpPos);
                }
            }
        }
        mineSweeperFields[pos[0]][pos[1]].setNeighbours(neighbours);
        mineSweeperFields[pos[0]][pos[1]].setNeighboursingMines(neighbouringMines);
    }

    /**
     *
     * @param pos ... position of clicked field
     * @return -1 if isMine; -2 if tagged; or the number of neighbours
     */
    private int makeMove(int[] pos, boolean tagged){
        if(tagged){
            this.noTaggedMines++;
            mineSweeperFields[pos[0]][pos[1]].setTagged(true);
            return -2;
        }
        if(mineSweeperFields[pos[0]][pos[1]].isMine()){
            // reveal all mines ?!
            return -1;
        }

        // if no_neighbours == 0 --> makeMove for each neighbours with 0 neighbours
        List<Integer[]> neighbouringMines = mineSweeperFields[pos[0]][pos[1]].getNeighboursingMines();
        List<Integer[]> neighbours = mineSweeperFields[pos[0]][pos[1]].getNeighbours();
        if(neighbouringMines.size() == 0){
            mineSweeperFields[pos[0]][pos[1]].setRevealed(true);
            neighbours.forEach(n -> {
                int[] position = {n[0], n[1]};
                if(!mineSweeperFields[position[0]][position[1]].isRevealed()){ // if not already revealed
                    this.makeMove(position, mineSweeperFields[position[0]][position[1]].isTagged());
                }
            });
        }


        return neighbouringMines.size();
    }

    public GameLogic(Integer[] firstPosition, int fieldSize) {
        this.noTaggedMines = 0;
        this.fieldSize = fieldSize;
        placeMines(firstPosition);
    }

    private void printField(){
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                System.out.println(i + "," + j + ": " + mineSweeperFields[i][j]);
            }
        }
    }

    public static void main(String[] args) {

        Integer[] firstPosition = {0,0};
        GameLogic gameLogic = new GameLogic(firstPosition, 4);
        gameLogic.printField();

        System.out.println("\n -----------------------------------------------------------\n\n");

        int[] position = {1,3};
        gameLogic.makeMove(position, false);

        gameLogic.printField();
    }
}
