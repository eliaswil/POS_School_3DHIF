package at.htlkaindorf.exa_q2_206_pethome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.io.IOException;

import at.htlkaindorf.exa_q2_206_pethome.beans.PetAdapter;
import at.htlkaindorf.exa_q2_206_pethome.io.IO_Helper;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainContext;
    private RecyclerView recyclerView;
    public static PetAdapter petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvPets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);

        try {
            IO_Helper.loadPets().forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
