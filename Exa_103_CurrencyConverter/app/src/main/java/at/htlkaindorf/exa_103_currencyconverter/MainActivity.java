package at.htlkaindorf.exa_103_currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tvUsdLabel;
    private TextView tvEurLabel;
    private ImageButton btChange;
    private EditText etInput;
    private TextView tvOutput;
    private boolean state = false;
    private final String TAG = this.getClass().getName();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvEurLabel = findViewById(R.id.tvEuroLabel);
        tvUsdLabel = findViewById(R.id.tvDollarLabel);
        btChange = findViewById(R.id.btChange);

        etInput = findViewById(R.id.etInput);
        etInput.setHint(R.string.InputHintEuro);
        tvOutput = findViewById(R.id.tvOutput);

        convertAndUpdate(1.0);

        // Error-Toast Message
        try{
            String x = null;
            x.toUpperCase();
        }catch (NullPointerException e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }


        btChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChangeCurrency(state);
                state = !state;

                // update result
                if(etInput.length() > 0){
                    try{
                        double currValue = Double.parseDouble(etInput.getText().toString());
                        convertAndUpdate(currValue);
                    }catch (NumberFormatException e){
                        Log.e(TAG, "onClick(): " + e.getMessage());
                    }
                }
                else{
                    convertAndUpdate(1.0);
                }

            }
        });

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length() > 0){
                    try{
                        double value = Double.parseDouble(editable.toString());
                        convertAndUpdate(value);
                    }catch (NumberFormatException e){
                        Log.e(TAG, "afterTextChanged() " + e.getMessage());
                    }
                }
                else{
                    convertAndUpdate(1.0);
                }

            }
        });


    }

    public void onChangeCurrency(boolean isDollar){
        Log.d(TAG, "state: " + isDollar);
        if(isDollar){
            tvEurLabel.setText(R.string.InputHintDollar);
            tvUsdLabel.setText(R.string.InputHintEuro);
            etInput.setHint(R.string.InputHintDollar);
        }
        else {
            tvEurLabel.setText(R.string.InputHintEuro);
            tvUsdLabel.setText(R.string.InputHintDollar);
            etInput.setHint(R.string.InputHintEuro);
        }

    }

    public void convertAndUpdate(double value){
        double factor = state ? 1.11 : 0.9;
        double newValue = value * factor;

        tvOutput.setText(getString(R.string.output, value,
                !state ? getString(R.string.dollar) : getString(R.string.euro),
                newValue,
                state ? getString(R.string.dollar) : getString(R.string.euro)));


    }




}
