package at.htlkaindorf.exa_q2_205_game2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {
    private Button[] btNumbers = new Button[16];
    private ImageView btReset;
    private TableLayout tlNumbers;
    private int direction;
    private GestureDetectorCompat gestureDetectorCompat;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btReset = findViewById(R.id.ivReset);
        btReset.setOnClickListener(v -> onReset());

        for (int i=0; i < 16; i++) {
            btNumbers[i] = findViewById(getResources().getIdentifier("bt" + (i + 1), "id", getPackageName()));
        }

        MyGestureListener mgl = new MyGestureListener();
        gestureDetectorCompat = new GestureDetectorCompat(this, mgl);
        tlNumbers = findViewById(R.id.tlNumbers);
        tlNumbers.setOnTouchListener(this::onTouch);



    }

    private void onReset(){
        // ToDo: implement onReset()
    }

    private void onSwipe(){
        // ToDo: implement Swipe event
    }

    private boolean onTouch(View v, MotionEvent event) {
        boolean finished = gestureDetectorCompat.onTouchEvent(event);
        return finished;
    }


    class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        public static final int MIN_DIST = 100;
        public static final int MAX_DIST = 1000;

        /**
         * wird erst aufgerufen wenn das event zu Ende ist
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float deltaX = e1.getX() - e2.getX();
            float deltaY = e1.getY() - e2.getY();
            float deltaXAbs = Math.abs(deltaX);
            float deltaYAbs = Math.abs(deltaY);


            if(deltaXAbs > MIN_DIST && deltaXAbs < MAX_DIST){
                if(deltaX > 0){
                    direction = -1; // left
                }else{
                    direction = 1;
                }

            }
            if(deltaYAbs > MIN_DIST && deltaYAbs < MAX_DIST){
                if(deltaY > 0){
                    direction = -4; // up
                }else{
                    direction = 4;
                }
            }
            onSwipe();
            return true;
        }
    }


}
