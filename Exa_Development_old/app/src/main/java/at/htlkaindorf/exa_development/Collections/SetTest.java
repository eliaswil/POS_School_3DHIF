package at.htlkaindorf.exa_development.Collections;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class SetTest {
    public static void main(String[] args) {
        Set<Integer> lottoNumbers = new TreeSet<>();
        Random rand = new Random();
        do{
            lottoNumbers.add(rand.nextInt(45) + 1);
        }while(lottoNumbers.size() < 6);
        for(int value : lottoNumbers){
            System.out.println(value);
        }
    }
}
