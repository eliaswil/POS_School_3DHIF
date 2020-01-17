package at.htlkaindorf.exa_q2_206_pethome.io;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.htlkaindorf.exa_q2_206_pethome.beans.Cat;
import at.htlkaindorf.exa_q2_206_pethome.beans.CatColor;
import at.htlkaindorf.exa_q2_206_pethome.beans.Dog;
import at.htlkaindorf.exa_q2_206_pethome.beans.Gender;
import at.htlkaindorf.exa_q2_206_pethome.beans.Pet;
import at.htlkaindorf.exa_q2_206_pethome.beans.Size;

public class IO_Helper {
    private static final Path PATH = Paths.get(System.getProperty("user.dir"), "app", "src", "main", "assets", "pets.csv");
    public static List<Pet> loadPets() throws IOException {
        System.out.println(PATH.toString());
        List<Pet> pets = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(PATH.toFile()));
        String line = br.readLine();
        while ((line = br.readLine()) != null){
            String[] tokens = line.split(",");
            String type = tokens[0];
            String name = tokens[1];
            Gender gender = Gender.valueOf(tokens[2].toUpperCase());

            String[] birthdate = tokens[3].split("/");
            LocalDate dateOfBirth = LocalDate.of(Integer.parseInt(birthdate[2]), Integer.parseInt(birthdate[0]), Integer.parseInt(birthdate[1]));
            Pet pet;

            if(type.equals("dog")){
                Map<String, Size> sizeMap = new HashMap<>();
                sizeMap.put("L", Size.LARGE);
                sizeMap.put("S", Size.SMALL);
                sizeMap.put("M", Size.MEDIUM);
                Size size = sizeMap.get(tokens[4]);
                pet = new Dog(name, dateOfBirth, gender, size);
            }else {
                CatColor catColor = CatColor.valueOf(tokens[5].toUpperCase());
                System.out.println(tokens[6]);
                Uri pictureUri = null;//Uri.parse(tokens[6]); // ToDO error when parsing
                pet = new Cat(name, dateOfBirth, gender, catColor, pictureUri);
            }
            pets.add(pet);
        }
        return pets;
    }
    public static void main(String[] args) {
        System.out.println("Hello");
        try {
            IO_Helper.loadPets().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
