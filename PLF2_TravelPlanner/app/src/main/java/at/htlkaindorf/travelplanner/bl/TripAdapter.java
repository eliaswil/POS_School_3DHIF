package at.htlkaindorf.travelplanner.bl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import at.htlkaindorf.travelplanner.R;

public class TripAdapter extends RecyclerView.Adapter<TripHolder> {
    private Map<String, List<Trip>> tripMap;
    private List<String> countries;

    public void init(Map<String, Trip> trips){
        countries = new ArrayList<>(trips.keySet());
        countries.sort(String::compareTo);
        tripMap = new HashMap<>();
        trips.forEach((s, trip) -> {
            if(tripMap.containsKey(s)){
                List<Trip> tripList = tripMap.get(s);
                tripList.add(trip);
                tripMap.put(s, tripList);
            }else {
                List<Trip> tripList = new ArrayList<>();
                tripList.add(trip);
                tripMap.put(s, tripList);
            }
        });

    }

    @NonNull
    @Override
    public TripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        TextView tvCountry = view.findViewById(R.id.tvCountry);
        TextView tvNumberOfTrips = view.findViewById(R.id.tvNumberOfTrips);
        return new TripHolder(view, tvCountry, tvNumberOfTrips);
    }

    @Override
    public void onBindViewHolder(@NonNull TripHolder holder, int position) {
        String country = countries.get(position);
        holder.getTvCountry().setText(country + "( "+ tripMap.get(country).get(0).getCountryCode() + ")");

        int noTrips = tripMap.get(country).size();
        int noDays = tripMap.get(country).stream().mapToInt(Trip::getDuration).sum();
        LocalDate minDate = tripMap.get(country).stream().min(Comparator.comparing(Trip::getStartDate)).get().getStartDate();
        LocalDate maxDate = tripMap.get(country).stream().max(Comparator.comparing(Trip::getStartDate)).get().getStartDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String text = noTrips
                + " trip - " + noDays
                + " days(" + minDate.format(dateTimeFormatter) + " - "
                + maxDate.format(dateTimeFormatter) + ")";

        holder.getTvNumberOfTrips().setText(text);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public Map<String, List<Trip>> getTripMap(){
        return tripMap;
    }
}
