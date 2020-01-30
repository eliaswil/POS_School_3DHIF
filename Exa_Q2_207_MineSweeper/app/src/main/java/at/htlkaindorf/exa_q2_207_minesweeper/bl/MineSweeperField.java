package at.htlkaindorf.exa_q2_207_minesweeper.bl;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MineSweeperField { // one single field
    protected List<Integer[]> neighbours;
    private boolean tagged;
    private boolean revealed;
    private boolean isMine;

    public MineSweeperField(List<Integer[]> neighbours, boolean tagged, boolean revealed, boolean isMine) {
        this.neighbours = neighbours;
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
                Objects.equals(neighbours, that.neighbours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(neighbours, tagged, revealed, isMine);
    }

    @Override
    public String toString() {
        String nbs = "{";
        for (Integer[] neighbour : neighbours) {
            nbs += "[" + neighbour[0] + "," + neighbour[1] + "}";
        }
        nbs += "}";
        return "MineSweeperField{" +
                "neighbours=" + nbs +
                ", tagged=" + tagged +
                ", revealed=" + revealed +
                ", isMine=" + isMine +
                '}';
    }
}
