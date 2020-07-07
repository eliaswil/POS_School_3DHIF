package at.htlkaindorf.exa_development.Collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapTest {
    public static void main(String[] args) {
        Map<String, Integer> myMap = new HashMap<>();
        myMap.put("Homer", 1200);
        myMap.put("Lisa", 9800);
        myMap.put("Bart", 2100);

        System.out.println(myMap.get("Bart"));

        for(String name : myMap.keySet()){
            System.out.println(myMap.get(name));
        }
    }
}
