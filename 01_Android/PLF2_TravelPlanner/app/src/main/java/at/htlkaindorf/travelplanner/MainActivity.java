package at.htlkaindorf.travelplanner;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import at.htlkaindorf.travelplanner.bl.Trip;
import at.htlkaindorf.travelplanner.bl.TripAdapter;
import at.htlkaindorf.travelplanner.io.IO_Helper;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    public static MainActivity mainActivity;
    public static TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Map<String, Trip> tripList;
        tripAdapter = new TripAdapter();

        try {
           tripList = IO_Helper.loadTrips(this.getApplicationContext());
            tripAdapter.init(tripList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.rvTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setAdapter(tripAdapter);


    }

}
