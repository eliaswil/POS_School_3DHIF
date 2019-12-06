package at.htlkaindorf.exa_q2_203_bankaccountapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.AccountAdapter;
import at.htlkaindorf.exa_q2_203_bankaccountapp.io.IO_Access;

public class MainActivity extends AppCompatActivity {
    public static MainActivity mainContext;
    private RecyclerView recyclerView;



    // ToDo: extract Hardcoded values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init Context
        mainContext = this;
        IO_Access.init(mainContext);

        AccountAdapter accountAdapter = new AccountAdapter();
        recyclerView = findViewById(R.id.rvAcounts);
        recyclerView.setAdapter(accountAdapter);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.miStudent: // ToDo: filter via adapter-class
                break;
            case R.id.miGiro:
                break;
        }
        return true;
    }
}
