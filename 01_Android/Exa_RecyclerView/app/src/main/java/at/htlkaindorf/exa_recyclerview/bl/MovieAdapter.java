package at.htlkaindorf.exa_recyclerview.bl;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.htlkaindorf.exa_recyclerview.MovieHolder;
import at.htlkaindorf.exa_recyclerview.R;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private List<Movie> movieList = Arrays.asList(
            new Movie("Herr der Ringe", YearMonth.of(2003, 3), 228),
            new Movie("Star wars", YearMonth.of(1980, 9), 113),
            new Movie("Spectre", YearMonth.of(2015, 11), 160)
    );

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        TextView tvMovieName = view.findViewById(R.id.tvMovieName);
        TextView tvMovieDuration = view.findViewById(R.id.tvMovieDuration);
        TextView tvReleased = view.findViewById(R.id.tvReleaseDate);

        MovieHolder movieHolder = new MovieHolder(view, tvMovieName, tvMovieDuration, tvReleased);
        return movieHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.getTvMovieName().setText(movie.getName());
        holder.getTvReleased().setText(movie.getReleased());
        holder.getTvDuration().setText(movie.getDurationString());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
