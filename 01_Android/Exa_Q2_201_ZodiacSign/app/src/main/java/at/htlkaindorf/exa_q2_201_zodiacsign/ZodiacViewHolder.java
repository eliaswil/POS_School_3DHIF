package at.htlkaindorf.exa_q2_201_zodiacsign;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class ZodiacViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{
    private ImageView ivSymbol;
    private TextView tvH1;
    private TextView tvDescription;
    private RelativeLayout relativeLayout;

    public ZodiacViewHolder(@NonNull View itemView, ImageView ivSymbol, TextView tvH1, TextView tvDescription, RelativeLayout relativeLayout) {
        super(itemView);
        this.ivSymbol = ivSymbol;
        this.tvH1 = tvH1;
        this.tvDescription = tvDescription;
        this.relativeLayout = relativeLayout;
        relativeLayout.setOnClickListener(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZodiacViewHolder that = (ZodiacViewHolder) o;
        return Objects.equals(ivSymbol, that.ivSymbol) &&
                Objects.equals(tvH1, that.tvH1) &&
                Objects.equals(tvDescription, that.tvDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ivSymbol, tvH1, tvDescription);
    }

    public ImageView getIvSymbol() {
        return ivSymbol;
    }

    public void setIvSymbol(ImageView ivSymbol) {
        this.ivSymbol = ivSymbol;
    }

    public TextView getTvH1() {
        return tvH1;
    }

    public void setTvH1(TextView tvH1) {
        this.tvH1 = tvH1;
    }

    public TextView getTvDescription() {
        return tvDescription;
    }

    public void setTvDescription(TextView tvDescription) {
        this.tvDescription = tvDescription;
    }

    @Override
    public void onClick(View v) {
        String url = MainActivity.main.getString(R.string.wikipedia_url, tvH1.getText().toString());
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        MainActivity.main.startActivity(viewIntent);
    }
}
