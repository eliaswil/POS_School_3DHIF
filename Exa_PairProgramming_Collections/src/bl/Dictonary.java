package bl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Dictonary implements Comparable{
    private Map<String, List<String>> words = new TreeMap<>();

    public Dictonary(Map<String, List<String>> words) {
        this.words = words;
    }

    public Map<String, List<String>> getWords() {
        return words;
    }

    public void setWords(Map<String, List<String>> words) {
        this.words = words;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dictonary dictonary = (Dictonary) o;
        return Objects.equals(words, dictonary.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
