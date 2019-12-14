package at.htlkaindorf.exa_q2_203_bankaccountapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.Account;
import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.GiroAccount;
import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.StudentAccount;

public class TransferActivity extends AppCompatActivity {
    private TextView tvFromAccountType;
    private TextView tvFromIban;
    private TextView tvFromBalance;
    private TextView tvFromAvailable;
    private EditText edToAmount;
    private AutoCompleteTextView actvToIbann;
    public static List<String> listOfIbans;
    private String accountType;
    private Button btTransfer;
    private String iban;
    private double amount;
    private boolean correctIban = false;
    private boolean correctAmount = false;



    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        tvFromAccountType = findViewById(R.id.tvFromAccountType);
        tvFromIban = findViewById(R.id.tvIban);
        tvFromBalance = findViewById(R.id.tvFromBalance);
        tvFromAvailable = findViewById(R.id.tvAvailableAmount);
        edToAmount = findViewById(R.id.edToAmount);
        actvToIbann = findViewById(R.id.actvIban);
        btTransfer = findViewById(R.id.btTransfer);

        // set From Account
        Account fromAccount = getIntent().getParcelableExtra("fromAccount");
        accountType = "";
        double available = fromAccount.getBalance();
        if(fromAccount instanceof StudentAccount){
            accountType = "Student-Account";
        }else {
            accountType = "Giro-Account";
            available += ((GiroAccount) fromAccount).getOverdraft();
        }

        // set Elements of .xml
        tvFromAccountType.setText(accountType);
        tvFromIban.setText(fromAccount.getIban());
        tvFromBalance.setText(String.format("€ %,.2f", fromAccount.getBalance()));
        tvFromAvailable.setText(String.format("€ %,.2f", available));

        // AutoCompleteTextView for IBANs
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, listOfIbans);
        actvToIbann.setAdapter(arrAdapter);
        actvToIbann.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                enterIban();
            }
        });

        // Input Amount
        edToAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                enterAmount(fromAccount);
            }
        });

        // Transfer-Button
        btTransfer.setEnabled(false);
        btTransfer.setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra("fromAccount", fromAccount);
            intent.putExtra("iban", iban);
            intent.putExtra("amount", amount);
            setResult(3, intent);
            finish();
        });
    }

    private void enterIban(){
        iban = actvToIbann.getText().toString();
        correctIban = listOfIbans.contains(iban);
        actvToIbann.setTextColor(getResources().getColor(correctIban
                ? R.color.black
                : R.color.red, null));
        btTransfer.setEnabled(correctIban && correctAmount);
    }

    @SuppressLint("DefaultLocale")
    private void enterAmount(Account fromAccount){
        if(edToAmount.getText().toString().isEmpty()){
            amount = 0;
        }else{
            amount = Double.parseDouble(edToAmount.getText().toString());
        }
        double newFromBalance = fromAccount.getBalance() - amount;

        tvFromBalance.setText(String.format("€ %,.2f", newFromBalance));
        tvFromAvailable.setText(String.format("€ %,.2f", accountType.equals("Giro-Account")
                ? ((GiroAccount)fromAccount).getOverdraft() + newFromBalance
                : newFromBalance));


        tvFromBalance.setTextColor(getResources().getColor(newFromBalance < 0
                ? R.color.red
                : R.color.green, null));

        // check if transaction would be possible
        correctAmount = fromAccount instanceof GiroAccount
                ? ((GiroAccount)fromAccount).getOverdraft() + newFromBalance >= 0
                : newFromBalance >= 0;

        edToAmount.setTextColor(getResources().getColor(correctAmount
                ? R.color.blue
                : R.color.red, null));

        btTransfer.setEnabled(correctIban && correctAmount);
    }
}
