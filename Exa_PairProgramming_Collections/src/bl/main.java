package bl;

import java.io.*;
import java.util.*;

public class main {
    private static String file = System.getProperty("user.dir") + File.separator + "src" + File.separator + "res" + File.separator + "GermanCommonWords.txt";
    private static Dictonary dc;
    public static void readWords() throws IOException {
            Map<String, List<String>> words = new TreeMap<>();
            for(int i = 65; i<91;i++){
                words.put(Character.toString((char) i), new LinkedList<String>());
                words.put(Character.toString((char) (i+32)), new LinkedList<String>());
            }
            words.put("Ä",new LinkedList<String>());
            words.put("ä",new LinkedList<String>());
            words.put("Ö",new LinkedList<String>());
            words.put("ö",new LinkedList<String>());
            words.put("Ü",new LinkedList<String>());
            words.put("ü",new LinkedList<String>());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while((line=br.readLine()) != null){
                String key = line.charAt(0)+"";
                List<String> help=words.get(key);
                help.add(line);
                words.put(key,help);
            }
            dc = new Dictonary(words);
            System.out.println(words.toString());
    }

    public static void main(String[] args) {

        try {
            readWords();
        } catch (IOException e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
