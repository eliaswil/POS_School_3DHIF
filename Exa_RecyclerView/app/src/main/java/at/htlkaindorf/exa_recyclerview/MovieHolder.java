package at.htlkaindorf.exa_recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieHolder extends RecyclerView.ViewHolder{
    /* Für jedes Element in der .xml datei --> eine Instanzvariable */
    private TextView tvMovieName;
    private TextView tvDuration;
    private TextView tvReleased;

    public MovieHolder(@NonNull View itemView, TextView tvMovieName, TextView tvDuration, TextView tvReleased) {
        super(itemView);
        this.tvMovieName = tvMovieName;
        this.tvDuration = tvDuration;
        this.tvReleased = tvReleased;
    }

    public TextView getTvMovieName() {
        return tvMovieName;
    }

    public void setTvMovieName(TextView tvMovieName) {
        this.tvMovieName = tvMovieName;
    }

    public TextView getTvDuration() {
        return tvDuration;
    }

    public void setTvDuration(TextView tvDuration) {
        this.tvDuration = tvDuration;
    }

    public TextView getTvReleased() {
        return tvReleased;
    }

    public void setTvReleased(TextView tvReleased) {
        this.tvReleased = tvReleased;
    }
}
