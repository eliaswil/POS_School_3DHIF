package at.htlkaindorf.travelplanner.bl;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import at.htlkaindorf.travelplanner.CountryOverviewActivity;
import at.htlkaindorf.travelplanner.MainActivity;


public class TripHolder extends RecyclerView.ViewHolder {
    private TextView tvCountry;
    private TextView tvNumberOfTrips;


    public TripHolder(@NonNull View itemView, TextView tvCountry, TextView tvNumberOfTrips) {
        super(itemView);
        this.tvCountry = tvCountry;
        this.tvNumberOfTrips = tvNumberOfTrips;
        tvCountry.setOnClickListener(this::onClick);
    }

    private void onClick(View v){
        Intent intent = new Intent(MainActivity.mainActivity.getApplicationContext(), CountryOverviewActivity.class);
        String country = tvCountry.getText().toString().substring(0, tvCountry.getText().toString().indexOf("(")).trim();
        intent.putExtra("country", country);
        List<Trip> trips = MainActivity.mainActivity.tripAdapter.getTripMap().get(country);
//        intent.putExtra("list", trips);
        MainActivity.mainActivity.getApplicationContext().startActivity(intent);
    }


    public TextView getTvCountry() {
        return tvCountry;
    }

    public void setTvCountry(TextView tvCountry) {
        this.tvCountry = tvCountry;
    }

    public TextView getTvNumberOfTrips() {
        return tvNumberOfTrips;
    }

    public void setTvNumberOfTrips(TextView tvNumberOfTrips) {
        this.tvNumberOfTrips = tvNumberOfTrips;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripHolder that = (TripHolder) o;
        return Objects.equals(tvCountry, that.tvCountry) &&
                Objects.equals(tvNumberOfTrips, that.tvNumberOfTrips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tvCountry, tvNumberOfTrips);
    }


}
