package at.htlkaindorf.exa_q2_206_pethome;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetHolder extends RecyclerView.ViewHolder {
    private RelativeLayout relativeLayout;
    private ImageView ivPicture;
    private TextView tvName;
    private ImageView ivGender;
    private TextView tvBirthdate;
    private TextView tvSize;

    public PetHolder(@NonNull View itemView, RelativeLayout relativeLayout, ImageView ivPicture, TextView tvName, ImageView ivGender, TextView tvBirthdate, TextView tvSize) {
        super(itemView);
        this.relativeLayout = relativeLayout;
        this.ivPicture = ivPicture;
        this.tvName = tvName;
        this.ivGender = ivGender;
        this.tvBirthdate = tvBirthdate;
        this.tvSize = tvSize;
    }

     public ImageView getIvPicture() {
        return ivPicture;
    }

    public TextView getTvName() {
        return tvName;
    }

    public ImageView getIvGender() {
        return ivGender;
    }

    public TextView getTvBirthdate() {
        return tvBirthdate;
    }

    public TextView getTvSize() {
        return tvSize;
    }
}
