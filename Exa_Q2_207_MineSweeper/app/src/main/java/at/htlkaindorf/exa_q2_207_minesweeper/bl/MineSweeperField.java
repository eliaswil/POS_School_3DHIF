package at.htlkaindorf.exa_q2_207_minesweeper.bl;


import java.util.List;
import java.util.Objects;

public class MineSweeperField { // one single field
    private List<Integer[]> neighbours;
    private List<Integer[]> neighboursingMines;
    private boolean tagged;
    private boolean revealed;
    private boolean isMine;

    public MineSweeperField(List<Integer[]> neighbours, List<Integer[]> neighboursingMines, boolean tagged, boolean revealed, boolean isMine) {
        this.neighbours = neighbours;
        this.neighboursingMines = neighboursingMines;
        this.tagged = tagged;
        this.revealed = revealed;
        this.isMine = isMine;
    }

    public List<Integer[]> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Integer[]> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Integer[]> getNeighboursingMines() {
        return neighboursingMines;
    }

    public void setNeighboursingMines(List<Integer[]> neighboursingMines) {
        this.neighboursingMines = neighboursingMines;
    }

    public boolean isTagged() {
        return tagged;
    }

    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MineSweeperField that = (MineSweeperField) o;
        return tagged == that.tagged &&
                revealed == that.revealed &&
                isMine == that.isMine &&
                Objects.equals(neighbours, that.neighbours) &&
                Objects.equals(neighboursingMines, that.neighboursingMines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighbours, neighboursingMines, tagged, revealed, isMine);
    }

    @Override
    public String toString() {
        String nbs = "{";
        for (Integer[] neighbour : neighbours) {
            nbs += "[" + neighbour[0] + "," + neighbour[1] + "}";
        }
        String nbm = "{";
        for (Integer[] neighboursingMine : neighboursingMines) {
            nbm += "[" + neighboursingMine[0] + "," + neighboursingMine[1] + "}";
        }
        nbs += "}";
        nbm += "}";
        return "MineSweeperField{" +
                "neighbours=" + nbs +
                ",\t neighboursingMines=" + nbm +
                ",\t tagged=" + tagged +
                ", revealed=" + revealed +
                ", isMine=" + isMine +
                '}';
    }
}
