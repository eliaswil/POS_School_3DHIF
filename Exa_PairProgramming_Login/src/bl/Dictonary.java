package bl;

import java.util.*;

public class Dictonary {
    private Map<String, List<String>> dictonary;

    public Dictonary(Map<String, List<String>> dictonary) {
        this.dictonary = dictonary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dictonary dictonary1 = (Dictonary) o;
        return Objects.equals(dictonary, dictonary1.dictonary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictonary);
    }

    public Map<String, List<String>> getDictonary() {
        return dictonary;
    }

    public void setDictonary(Map<String, List<String>> dictonary) {
        this.dictonary = dictonary;
    }

    public void addWord(String key, String value){
        if(dictonary.containsKey(key)){
            dictonary.get(key).add(value);
        }else {
            List<String> words = new ArrayList<>();
            words.add(value);
            dictonary.put(key, words);
        }
    }
    public void sort(){
        Set<String> keys = dictonary.keySet();
        for (String key : keys) {
            dictonary.get(key).sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.toUpperCase().compareTo(o2.toUpperCase());
                }
            });
        }
    }

    public void printWordsByFirstLetter(String key){
        key = key.toUpperCase();
        if(!dictonary.containsKey(key)){
            throw new NullPointerException("key not found");
        }
        String output = key + ":\n";
        for (String word : dictonary.get(key)) {
            output += word + ", ";
        }
        System.out.println(output);
    }


}
