package at.htlkaindorf.exa_108_numberpuzzlegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private Button[] btNumbers;
    private GestureDetectorCompat gestureDetectorCompat;
    public static final String TAG = MainActivity.class.getSimpleName();
    private int direction;
    private Button touchedButton;
    private int indexOfGreyButton;
    private Button btReset;
    private final int N = 4; // number of buttons per row

    /**
     * ToDo: everything
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
        btReset = findViewById(R.id.btReset);

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direction = 0;
                touchedButton = null;
                indexOfGreyButton = 0;
                gestureDetectorCompat = null;
                btNumbers = null;
                initButtons();
            }
        });
    }

    private void initButtons(){
        btNumbers = new Button[]{
            findViewById(R.id.bt1),
            findViewById(R.id.bt2),
            findViewById(R.id.bt3),
            findViewById(R.id.bt4),
            findViewById(R.id.bt5),
            findViewById(R.id.bt6),
            findViewById(R.id.bt7),
            findViewById(R.id.bt8),
            findViewById(R.id.bt9),
            findViewById(R.id.bt10),
            findViewById(R.id.bt11),
            findViewById(R.id.bt12),
            findViewById(R.id.bt13),
            findViewById(R.id.bt14),
            findViewById(R.id.bt15),
            findViewById(R.id.bt16),
        };

        // randomly fill buttons, set background
        List<String> numbers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            numbers.add(Integer.toString(i));
        }
        numbers.add("");

        Collections.shuffle(numbers);
        List<String> values = new ArrayList<>(numbers);
        int i = -1;
        for (Button btNumber : btNumbers) {
            i++;
            String numberString = numbers.get(0);
            if(numberString.equals("")){
                // grey button
                btNumber.setBackgroundColor(getColor(R.color.grey));
                indexOfGreyButton = i;
                btNumber.setText("");
            }else{
                int number = Integer.parseInt(numbers.get(0));
                btNumber.setText(numbers.get(0));
                if(number % 2 == 0){
                    // red
                    btNumber.setBackgroundColor(getColor(R.color.red));
                }
                else {
                    btNumber.setBackgroundColor(getColor(R.color.white));
                }
            }
            numbers.remove(0);
        }


        // set GestureDetector (Swipe-Event)
        MyGestureListener mgl = new MyGestureListener();
        gestureDetectorCompat = new GestureDetectorCompat(this, mgl);
        for (Button btNumber : btNumbers) {
            btNumber.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    boolean finished = gestureDetectorCompat.onTouchEvent(motionEvent);
                    touchedButton = (Button)view;
                    return finished;
                }
            });
        }

        // check if solvable
        if(!solvable(values)){
            Toast.makeText(getApplicationContext(), getString(R.string.not_solvable), Toast.LENGTH_SHORT).show();
        }


    }
    private boolean solvable(List<String> numbers){ // ToDO: geht noch nicht ganz
        int inversionCount = 0;

        // get the index of the blank button
        int indexOfBlank = 0;
        for (int i = 0; i < numbers.size(); i++) {
            if(numbers.get(i).equals("")){
                indexOfBlank = i;
                numbers.remove(i);
            }
        }

        // convert string-list to integer-list
        List<Integer> intList = new ArrayList<>();
        for(String s : numbers) {
            intList.add(Integer.valueOf(s));
        }

        // determine no inversions
        for (int i = 1; i < intList.size(); i++) {
            for (int j = i-1; j >= 0; j--) {
                if(intList.get(i) < intList.get(j)){
                    inversionCount++;
                }
            }
        }

        if(N % 2 == 0){

            int rowIndex = (int)Math.floor(indexOfBlank / N);

            // true if: rowIndex == even AND inversionCount == odd
            if((rowIndex % 2 == 0) && (inversionCount % 2 == 1)){
                return true;
            }
            // true if: rowIndex == odd AND inversionCount == even
            return (rowIndex % 2 == 1) && (inversionCount % 2 == 1);

        }else{
            return (inversionCount % 2 == 0);
        }
    }


    private void swapButtons(int touchedIndex, int targetIndex){
        Button touched = btNumbers[touchedIndex];
        Button target = btNumbers[targetIndex];

        String targetText = touched.getText().toString();
        Drawable background = touched.getBackground();

        touched.setText(target.getText());
        touched.setBackground(target.getBackground());

        target.setText(targetText);
        target.setBackground(background);
    }
    private void onSwipe(){
        Button touchedButton = this.touchedButton;
        int touchedIndex = -1;
        int targetIndex = -1;
        boolean allowed = true;
        for (int i = 0; i < btNumbers.length; i++) {
            if(btNumbers[i].equals(touchedButton)){
                // i    ...     index of button in array
                touchedIndex = i;
                targetIndex = touchedIndex + direction;

                allowed = !(touchedIndex % N == 0 && direction == -1) && allowed; // left
                allowed = !(touchedIndex % N == 3 && direction == 1) && allowed; // right
                allowed = !(touchedIndex <= N-1 && direction == -N) && allowed; // up
                allowed = !(touchedIndex >= 3*N && direction == N) && allowed; // down
                if(allowed){
                    Button targetButton = btNumbers[targetIndex];
                    allowed = targetButton.equals(btNumbers[indexOfGreyButton]);
                }
            }
        }
        if(allowed){
            indexOfGreyButton = touchedIndex;
            swapButtons(touchedIndex, targetIndex);
        }else {
            Toast.makeText(getApplicationContext(), getString(R.string.warning_swipe_not_allowed), Toast.LENGTH_LONG).show();
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
                    Log.i(TAG, "swipe left");
                    direction = -1;
                }else{
                    Log.i(TAG, "swipe right");
                    direction = 1;
                }

            }
            if(deltaYAbs > MIN_DIST && deltaYAbs < MAX_DIST){
                if(deltaY > 0){
                    Log.i(TAG, "swipe up");
                    direction = -4;
                }else{
                    Log.i(TAG, "swipe down");
                    direction = 4;
                }
            }
            onSwipe();
            return true;
        }
    }
}
