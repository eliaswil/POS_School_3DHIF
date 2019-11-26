package at.htlkaindorf.exa_q2_201_contactsapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

    private ImageView ivPicture;
    private TextView tvName;

    public ContactViewHolder(@NonNull View itemView, ImageView ivPicture, TextView tvName) {
        super(itemView);
        this.ivPicture = ivPicture;
        this.tvName = tvName;
    }


    public ImageView getIvPicture() {
        return ivPicture;
    }

    public void setIvPicture(ImageView ivPicture) {
        this.ivPicture = ivPicture;
    }

    public TextView getTvName() {
        return tvName;
    }

    public void setTvName(TextView tvName) {
        this.tvName = tvName;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.mainContext, DetailedActivity.class);
        MainActivity.mainContext.startActivity(intent);
    }
}
