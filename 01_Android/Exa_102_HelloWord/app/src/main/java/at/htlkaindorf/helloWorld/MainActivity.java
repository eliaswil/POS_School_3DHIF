package at.htlkaindorf.helloWorld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText etInput;
    private Button btContinue;
    private TextView tvMessage;
    private final String TAG = this.getClass().getName();
    private boolean firstClick = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // ruft das layout auf (activity_main.xml)

        // get instance of view
        etInput = findViewById(R.id.etName);
        btContinue = findViewById(R.id.btContinue);
        tvMessage = findViewById(R.id.tvMessage);


        btContinue.setEnabled(false);

        // create Eventhandler for onClick-Event
        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstClick) {
                    firstClick = false;
                    String input = etInput.getText().toString();
                    tvMessage.setText(getString(R.string.outputMsg, input));
                    etInput.setVisibility(View.INVISIBLE);
                    btContinue.setText(R.string.finished);
                }
                else {
                    finish();
                }
//                Log.d(TAG, "button clicked");

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
                btContinue.setEnabled(editable.length() > 0);
            }
        });

    }
}
