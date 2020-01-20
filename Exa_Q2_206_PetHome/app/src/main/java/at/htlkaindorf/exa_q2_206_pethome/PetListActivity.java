package at.htlkaindorf.exa_q2_206_pethome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import at.htlkaindorf.exa_q2_206_pethome.beans.Pet;
import at.htlkaindorf.exa_q2_206_pethome.beans.PetAdapter;

public class PetListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static PetAdapter petAdapter;
    private TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        recyclerView = findViewById(R.id.rvPets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        petAdapter = new PetAdapter();
        recyclerView.setAdapter(petAdapter);

        String type = getIntent().getStringExtra("type");
        List<Pet> filteredPets = (List<Pet>)getIntent().getSerializableExtra("list");

        petAdapter.init(filteredPets);

        tvHeading = findViewById(R.id.tvHeading);
        tvHeading.setText(type.equals("cats")
                ? "Cat list"
                : "Dog list" );





    }
}
