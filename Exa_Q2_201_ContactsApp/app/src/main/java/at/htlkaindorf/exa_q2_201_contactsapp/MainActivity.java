package at.htlkaindorf.exa_q2_201_contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import at.htlkaindorf.exa_q2_201_contactsapp.bl.ContactAdapter;
import at.htlkaindorf.exa_q2_201_contactsapp.io.IOHandler;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchView svSearch;
    private ContactAdapter contactAdapter;
    public static MainActivity mainContext;
    // ToDo: Fast Scroll Bar



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainContext = this;

        IOHandler.init(this.getApplicationContext());

        recyclerView = findViewById(R.id.rvContacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        contactAdapter = new ContactAdapter();
        recyclerView.setAdapter(contactAdapter);

        svSearch = findViewById(R.id.svSearch);
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                contactAdapter.filterContacts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.filterContacts(newText);
                return false;
            }
        });

    }
}
