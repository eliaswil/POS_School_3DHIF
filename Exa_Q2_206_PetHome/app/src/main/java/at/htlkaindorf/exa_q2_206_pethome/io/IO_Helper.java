package at.htlkaindorf.exa_q2_206_pethome.io;


import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import at.htlkaindorf.exa_q2_206_pethome.beans.Cat;
import at.htlkaindorf.exa_q2_206_pethome.beans.Dog;
import at.htlkaindorf.exa_q2_206_pethome.beans.Pet;

public class IO_Helper {

    public static List<Pet> loadPets(Context context) throws IOException {
        List<Pet> pets;

        Function<String, Pet> mapper = l ->{
            String[] tokens = l.split(",", 2);
            return tokens[0].equals("cat")
                    ? new Cat(tokens[1])
                    : new Dog(tokens[1]);
        };

        pets = new BufferedReader(new InputStreamReader(
                context.getAssets()
                        .open("pets.csv")))
                .lines()
                .skip(1)
                .map(mapper)
                .collect(Collectors.toList());

        return pets;
    }
   /* public static void main(String[] args) {
        System.out.println("Hello");
        try {
            IO_Helper.loadPets().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
