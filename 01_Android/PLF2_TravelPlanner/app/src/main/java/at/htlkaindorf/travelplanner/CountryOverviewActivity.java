package at.htlkaindorf.travelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import at.htlkaindorf.travelplanner.bl.Trip;

public class CountryOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_overview);

        String country = getIntent().getStringExtra("country");
        List<Trip> tripList = (List<Trip>) getIntent().getSerializableExtra("list");

        String cities = "";
        tripList.sort(Comparator.comparing(Trip::getStartDate));
//        tripList.forEach(trip -> cities += trip.getCity() + ": "
//                + trip.getStartDate().format(DateTimeFormatter.ofPattern("d.M.yyyy")) + " - "
//                + trip.getStartDate().plusDays(trip.getDuration()).format(DateTimeFormatter.ofPattern("d.M.yyyy")) + "\n");

    }

}
