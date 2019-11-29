package at.htlkaindorf.exa_q2_201_contactsapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import at.htlkaindorf.exa_q2_201_contactsapp.bl.Contact;

public class ContactViewHolder extends RecyclerView.ViewHolder{

    private ImageView ivPicture;
    private TextView tvName;
    private Contact contact;
    private LinearLayout linearLayout;

    public void setContact(Contact contact){
        this.contact = contact;
    }

    public ContactViewHolder(@NonNull View itemView, ImageView ivPicture, TextView tvName, LinearLayout linearLayout) {
        super(itemView);
        this.ivPicture = ivPicture;
        this.tvName = tvName;
        this.linearLayout = linearLayout;
        this.linearLayout.setOnClickListener(this::onClickContact);
    }

    public LinearLayout getLinearLayout(){ return  linearLayout; }
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

    private void onClickContact(View v) {
        Intent intent = new Intent(MainActivity.mainContext, DetailedActivity.class);
        intent.putExtra("contact", contact);
        MainActivity.mainContext.startActivity(intent);
    }
}
