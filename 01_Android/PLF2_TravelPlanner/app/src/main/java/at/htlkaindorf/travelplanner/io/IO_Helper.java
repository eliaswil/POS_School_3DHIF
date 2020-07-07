package at.htlkaindorf.travelplanner.io;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import at.htlkaindorf.travelplanner.bl.Trip;

public class IO_Helper {

    public static Map<String, Trip> loadTrips(Context context) throws IOException {

        List<Trip> tripsList = new BufferedReader(new InputStreamReader(context.getAssets()
                .open("travel_data.csv")))
                .lines().skip(4).map(Trip::new)
                .collect(Collectors.toList());
        // TODO: 24.01.2020 finish

        Map<String, Trip> trips = new HashMap<>();
        tripsList.forEach(trip -> trips.put(trip.getCountry(), trip));

        return trips;
    }

}
