package at.htlkaindorf.exa_q2_203_bankaccountapp;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

public class TransferActivity extends AppCompatActivity {
    private TextView tvFromAccountType;
    private TextView tvFromIban;
    private TextView tvFromBalance;
    private TextView tvFromAvailable;
    private EditText edToAmount;
    private AutoCompleteTextView actvToIbann;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        tvFromAccountType = findViewById(R.id.tvFromAccountType);
        tvFromIban = findViewById(R.id.tvIban);
        tvFromBalance = findViewById(R.id.tvAmount);
        tvFromAvailable = findViewById(R.id.tvAvailableAmount);
        edToAmount = findViewById(R.id.edToAmount);
        actvToIbann = findViewById(R.id.actvIban);

        // ToDO: implement AutoCompleteTextView

        edToAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // ToDo: Button (de)aktivieren
            }
        });

    }

}
