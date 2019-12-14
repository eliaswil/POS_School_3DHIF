package at.htlkaindorf.exa_q2_203_bankaccountapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.Account;
import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.AccountAdapter;
import at.htlkaindorf.exa_q2_203_bankaccountapp.io.IO_Access;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainContext;
    private RecyclerView recyclerView;
    public static AccountAdapter accountAdapter;


    // ToDo: extraFeature ??
    // ToDo: extract Hardcoded values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init Context
        mainContext = this;
        IO_Access.init(mainContext);

        // recycler view + AccountAdapter
        AccountAdapter accountAdapter = new AccountAdapter();
        recyclerView = findViewById(R.id.rvAccounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        recyclerView.setAdapter(accountAdapter);
        MainActivity.accountAdapter = accountAdapter;

        // init list of ibans in TransferActivity
        List<String> ibans = new ArrayList<>();
        accountAdapter.getAccounts().forEach(account -> ibans.add(account.getIban()));
        ibans.sort(String::compareTo);
        TransferActivity.listOfIbans = ibans;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == 3){
            Account fromAccount = data.getParcelableExtra("fromAccount");
            String toIban = data.getExtras().getString("iban");
            double amount = data.getExtras().getDouble("amount");
            accountAdapter.transferMoney(fromAccount, toIban, amount);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.miStudent:
                accountAdapter.filterAccounts("student");
                break;
            case R.id.miGiro:
                accountAdapter.filterAccounts("giro");
                break;
            case R.id.miAll:
                accountAdapter.filterAccounts("all");
        }
        return true;
    }
}
