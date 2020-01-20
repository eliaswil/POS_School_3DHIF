package at.htlkaindorf.exa_q2_206_pethome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import at.htlkaindorf.exa_q2_206_pethome.beans.Cat;
import at.htlkaindorf.exa_q2_206_pethome.beans.Dog;
import at.htlkaindorf.exa_q2_206_pethome.beans.Pet;
import at.htlkaindorf.exa_q2_206_pethome.beans.PetAdapter;
import at.htlkaindorf.exa_q2_206_pethome.io.IO_Helper;

public class MainActivity extends AppCompatActivity {
    public static Context mainContext;
    private PetAdapter petAdapter;
    private ImageView ivCats;
    private ImageView ivDogs;
    private List<Pet> pets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainContext = this.getApplicationContext();

        petAdapter = new PetAdapter();

        ivCats = findViewById(R.id.ivCats);
        ivDogs = findViewById(R.id.ivDogs);

        ivDogs.setOnClickListener(v -> onDogs());
        ivCats.setOnClickListener(v -> onCats());

        try {
            pets = IO_Helper.loadPets(mainContext);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void onDogs(){
        ArrayList<Pet> filteredPets = pets.stream()
                .filter(pet -> pet instanceof Dog)
                .sorted(Comparator.comparing((Pet t) -> ((Dog)t).getSize()).thenComparing(Pet::getDateOfBirth))
                .collect(Collectors.toCollection(ArrayList::new));

        Intent intent = new Intent(this, PetListActivity.class);
        intent.putExtra("type", "dogs");
        intent.putExtra("list", filteredPets);
        startActivityForResult(intent, 0);
    }
    private void onCats(){
        ArrayList<Pet> filteredPets = pets.stream()
                .filter(pet -> pet instanceof Cat)
                .sorted(Comparator.comparing(Pet::getDateOfBirth))
                .collect(Collectors.toCollection(ArrayList::new));
        Intent intent = new Intent(this, PetListActivity.class);
        intent.putExtra("type", "cats");
        intent.putExtra("list", filteredPets);
        startActivityForResult(intent, 1);
    }
}
