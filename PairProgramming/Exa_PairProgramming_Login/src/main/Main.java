package main;

import bl.Dictonary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static String path = System.getProperty("user.dir") + File.separator + "src" + File.separator
            + "res" + File.separator + "GermanCommonWords.txt";

    private static Dictonary readWords() throws IOException {
        File germanWords = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(germanWords));
        String line = "";
        Map<String, List<String>> words = new TreeMap<>();
        Dictonary dictonary = new Dictonary(words);

        while ((line = br.readLine()) != null){
            String firstChar = line.substring(0, 1).toUpperCase();
            dictonary.addWord(firstChar, line);
        }
        dictonary.sort();
        return dictonary;

    }
    public static void main(String[] args) {
        try {
            Dictonary dictonary = readWords();
            dictonary.printWordsByFirstLetter("z");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("no words found");
        }
    }
}
