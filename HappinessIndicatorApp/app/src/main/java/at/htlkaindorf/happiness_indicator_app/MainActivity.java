package at.htlkaindorf.happiness_indicator_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import at.htlkaindorf.happiness_indicator_app.bl.HappinessModel;

public class MainActivity extends AppCompatActivity {

    private SeekBar sbIndicator;
    private EditText etInput;
    private Button btSend;
    private TextView tvOutput;
    private TextView tvLabel;
    private HappinessModel happinessModel;
    private ImageButton ibSmiley;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sbIndicator = findViewById(R.id.sbIndicator);
        etInput = findViewById(R.id.etName);
        btSend = findViewById(R.id.btSend);
        tvOutput = findViewById(R.id.tvOutput);
        tvLabel = findViewById(R.id.tvHappiness);
        happinessModel = new HappinessModel();
        ibSmiley = findViewById(R.id.ibSmiley);

        MySeekbarListener msbl = new MySeekbarListener();
        sbIndicator.setOnSeekBarChangeListener(msbl);
        myOnProgressChanged(5); // init

        String output = happinessModel.getTopThreeString();
        tvOutput.setText(output);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend(v);
            }
        });


    }

    private void onSend(View v){
        String name = etInput.getText().toString();
        if(name.length() == 0){
            Toast.makeText(getApplicationContext(), getString(R.string.enterName), Toast.LENGTH_SHORT).show();
        }else{
            happinessModel.addHappinessValue(name, sbIndicator.getProgress());
            String output = happinessModel.getTopThreeString();
            tvOutput.setText(output);
        }
    }

    private void myOnProgressChanged(int progress){
        if(progress <= 3){
            ibSmiley.setImageResource(R.drawable.smiley1);
        }else if(progress <= 7){
            ibSmiley.setImageResource(R.drawable.smiley2);
        }else {
            ibSmiley.setImageResource(R.drawable.smiley3);
        }
        String output = getString(R.string.tvHappinessText) + Integer.toString(progress);
        tvLabel.setText(output);
    }


    public class MySeekbarListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            myOnProgressChanged(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }
}
