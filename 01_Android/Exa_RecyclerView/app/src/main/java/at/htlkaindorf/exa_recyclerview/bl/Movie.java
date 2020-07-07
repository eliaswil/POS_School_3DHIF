package at.htlkaindorf.exa_recyclerview.bl;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Movie {
    private String name;
    private YearMonth released;
    private int duration;
    private static final DateTimeFormatter DDF = DateTimeFormatter.ofPattern("MMMM - yyyy");

    public Movie(String name, YearMonth released, int duration) {
        this.name = name;
        this.released = released;
        this.duration = duration;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return duration == movie.duration &&
                Objects.equals(name, movie.name) &&
                Objects.equals(released, movie.released);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, released, duration);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleased() {
        return DDF.format(released);
    }

    public void setReleased(YearMonth released) {
        this.released = released;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationString(){
        return String.format("%02d:%02d", duration/60, duration%60);
    }
}
