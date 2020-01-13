package at.htlkaindorf.exa_q2_205_game2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import at.htlkaindorf.exa_q2_205_game2048.bl.ColorScheme;
import at.htlkaindorf.exa_q2_205_game2048.bl.GameLogic;

public class MainActivity extends AppCompatActivity {
    private Button[] btNumbers = new Button[16];
    private ImageView btReset;
    private TextView tvPoints;
    private TableLayout tlNumbers;
    private int direction;
    private GestureDetectorCompat gestureDetectorCompat;
    private GameLogic gl;
    Map<Integer, ColorScheme> colors = new HashMap<>();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btReset = findViewById(R.id.ivReset);
        btReset.setOnClickListener(v -> onReset());

        tvPoints = findViewById(R.id.tvPoints);

        for (int i=0; i < 16; i++) {
            btNumbers[i] = findViewById(getResources().getIdentifier("bt" + (i + 1), "id", getPackageName()));
        }



        MyGestureListener mgl = new MyGestureListener();
        gestureDetectorCompat = new GestureDetectorCompat(this, mgl);
        tlNumbers = findViewById(R.id.tlNumbers);
        for (Button btNumber : btNumbers) {
            btNumber.setOnTouchListener((v, event) -> !gestureDetectorCompat.onTouchEvent(event));
        }




        // initialise Colors
        colors.put(0, ColorScheme.C0);
        colors.put(2, ColorScheme.C2);
        colors.put(4, ColorScheme.C4);
        colors.put(8, ColorScheme.C8);
        colors.put(16, ColorScheme.C16);
        colors.put(32, ColorScheme.C32);
        colors.put(64, ColorScheme.C64);
        colors.put(128, ColorScheme.C128);
        colors.put(256, ColorScheme.C256);
        colors.put(512, ColorScheme.C512);
        colors.put(1024, ColorScheme.C1024);
        colors.put(2048, ColorScheme.C2048);

        int[] values = new int[16];
        Arrays.fill(values, 0);
        gl = new GameLogic(values, 0);
        gl.resetGame();
        gl.setNewValue();


    }

    private void onReset(){
        // ToDo: implement onReset()
       gl.resetGame();
       gl.setNewValue();
       setButtons();
        tvPoints.setText("Points\n" + gl.getPoints());
    }

    private void onSwipe(){
        // ToDo: implement Swipe event
        gl.setNewValue();
        gl.makeMove(direction);
        setButtons();
        if(gl.getPoints() >= 2048){
            Toast.makeText(getApplicationContext(),"Congrats", Toast.LENGTH_SHORT).show();
        }
        tvPoints.setText("Points\n" + gl.getPoints());
    }

    private void setButtons(){
        for (int i = 0; i < btNumbers.length; i++) {
            int value = colors.get(gl.getValues()[i]).getValue();
            btNumbers[i].setText("" + value);
            btNumbers[i].setTextColor(colors.get(gl.getValues()[i]).getFontColor());
            btNumbers[i].setBackgroundColor(colors.get(gl.getValues()[i]).getBackgroundColor());
        }
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
