package at.htlkaindorf.exa_development;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
                            implements View.OnLongClickListener {
    private Button bt1;
    private Button bt2;
    private Button bt3;
    private Button bt4;

    private Button bt5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt5 = findViewById(R.id.bt5);


        MyClickListener mcl = new MyClickListener();
        bt1.setOnClickListener(mcl);
        bt2.setOnClickListener(mcl);
        bt3.setOnClickListener(mcl);

        // innere anonyme Klasse
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButton5Click(view);
            }
        });

        // Variante 4: MainActivity implements sth
        bt3.setOnLongClickListener(this);

    }

    // eventhandling over .xml
    public void onButton4Click(View view){
        Toast.makeText(getApplicationContext(), "eventhandling over .xml", Toast.LENGTH_SHORT).show();
    }

    private void onButton5Click(View view){
        Toast.makeText(getApplicationContext(), "Second method for eventhandling", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(getApplicationContext(), "Long click", Toast.LENGTH_SHORT).show();
        return true;
    }


    // innere Klasse
    public class MyClickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Button bt = (Button) view;
            Toast.makeText(getApplicationContext(), "Button " + bt.getText().toString(), Toast.LENGTH_SHORT).show();

        }
    }
}
