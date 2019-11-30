package at.htlkaindorf.exa_q2_201_zodiacsign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import at.htlkaindorf.exa_q2_201_zodiacsign.bl.ZodiacSignAdapter;

public class MainActivity extends AppCompatActivity {
    public static MainActivity main;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = this;
        recyclerView = findViewById(R.id.rvZodiacSigns);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setAdapter(new ZodiacSignAdapter());
    }
}
