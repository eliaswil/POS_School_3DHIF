package at.htlkaindorf.exa_q2_201_contactsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import at.htlkaindorf.exa_q2_201_contactsapp.bl.Contact;
public class ContactViewHolder extends RecyclerView.ViewHolder{
    private ImageView ivPicture;
    private TextView tvName;
    private Contact contact;
    private LinearLayout linearLayout;


    @SuppressLint("ClickableViewAccessibility")
    public ContactViewHolder(@NonNull View itemView, ImageView ivPicture, TextView tvName, LinearLayout linearLayout) {
        super(itemView);
        this.ivPicture = ivPicture;
        this.tvName = tvName;
        this.linearLayout = linearLayout;

        this.linearLayout.setOnClickListener(v -> {
            MainActivity.contactAdapter.currentContact = contact;
            onClickContact();
        });

        GestureDetectorCompat gdc = new GestureDetectorCompat(MainActivity.mainContext, new MyGestureListener());
        this.linearLayout.setOnTouchListener((v, event) -> {
            MainActivity.contactAdapter.currentContact = contact;
            return gdc.onTouchEvent(event);
        });
    }

    public ImageView getIvPicture() {
        return ivPicture;
    }
    public TextView getTvName() {
        return tvName;
    }
    public void setContact(Contact contact){
        this.contact = contact;
    }

    private void onClickContact() {
        Intent intent = new Intent(MainActivity.mainContext, DetailedActivity.class);
        intent.putExtra("contact", contact);
        MainActivity.mainContext.startActivity(intent);
    }
    private void onSwipe(){
        MainActivity.contactAdapter.delete();
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        private static final int MIN_X_DIST = 100;

        /**
         * wird erst aufgerufen wenn das event zu Ende ist
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e1.getX() - e2.getX();
            float deltaXAbs = Math.abs(deltaX);

            if(deltaXAbs > MIN_X_DIST){
                if(deltaX <= 0){
                    onSwipe();
                    return true;
                }
            }
            return false;
        }
    }
}
