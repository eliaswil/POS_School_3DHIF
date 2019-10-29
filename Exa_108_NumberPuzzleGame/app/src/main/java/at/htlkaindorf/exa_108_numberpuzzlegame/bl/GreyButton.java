package at.htlkaindorf.exa_108_numberpuzzlegame.bl;

import java.util.Arrays;
import java.util.Objects;

public class GreyButton {
    private int index;
    /** dircetionNeigbours:
     * ++++++++++++++++++++++
     * Top: -4
     * Bottom: +4
     * left: -1
     * right 1
     */
    private int[] directNeighbours;

    public GreyButton(int index, int[] directNeighbours) {
        this.index = index;
        this.directNeighbours = directNeighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GreyButton that = (GreyButton) o;
        return index == that.index &&
                Arrays.equals(directNeighbours, that.directNeighbours);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(index);
        result = 31 * result + Arrays.hashCode(directNeighbours);
        return result;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int[] getDirectNeighbours() {
        return directNeighbours;
    }

    public void setDirectNeighbours(int[] directNeighbours) {
        this.directNeighbours = directNeighbours;
    }
}
