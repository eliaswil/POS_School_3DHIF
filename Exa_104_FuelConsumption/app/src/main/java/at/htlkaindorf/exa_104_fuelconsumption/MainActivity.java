package at.htlkaindorf.exa_104_fuelconsumption;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btCalculate;
    private EditText etFuel;
    private EditText etDistance;
    private TextView tvOutput;
    private final String TAG = this.getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get additional values from xml
        int val = getResources().getInteger(R.integer.testInt1);


        // initialise
        etFuel = findViewById(R.id.etFuelLiter);
        etDistance = findViewById(R.id.etDistance);
        tvOutput = findViewById(R.id.tvOutput);
        tvOutput.setText(R.string.tvOutput_text_initialOutput);
        btCalculate = findViewById(R.id.btCalculate);
        btCalculate.setEnabled(false);

        btCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCalculateConsumption(view);
            }
        });
        etDistance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btCalculate.setEnabled(editable.length() > 0 && etFuel.getText().length() > 0);
            }
        });
        etFuel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                btCalculate.setEnabled(editable.length() > 0 && etDistance.getText().length() > 0);
            }
        });


    }

    private void onCalculateConsumption(View view){
        String fuelInput = etFuel.getText().toString();
        String distanceInput = etDistance.getText().toString();
        double fuel = Double.parseDouble(fuelInput);
        int distance = Integer.parseInt(distanceInput);

        try{
            if(distance == 0){
                throw new ArithmeticException();
            }
            double consumption = fuel/distance * 100;
            tvOutput.setText(getString(R.string.tvOutput_text_consumption, consumption));
        }catch (ArithmeticException e){
            tvOutput.setText(getString(R.string.tvOutput_text_illegalInput));
        }

    }

}
