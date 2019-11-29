package at.htlkaindorf.exa_q2_201_contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import at.htlkaindorf.exa_q2_201_contactsapp.bl.Contact;

public class DetailedActivity extends AppCompatActivity {
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvGender;
    private TextView tvPhone;
    private TextView tvLanguage;
    private ImageView ivPic;

    // ToDo: remove hardcoded Strings from activity_detailed.xml


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Toast.makeText(getApplicationContext(), "working", Toast.LENGTH_SHORT).show();

        tvFirstName = findViewById(R.id.tvFirstname);
        tvLanguage = findViewById(R.id.tvLanguage);
        tvLastName = findViewById(R.id.tvLastname);
        tvGender = findViewById(R.id.tvGender);
        tvPhone = findViewById(R.id.tvPhone);
        ivPic = findViewById(R.id.ivProfilePic);


        Contact contact = (Contact) getIntent().getExtras().get("contact");
        tvFirstName.setText(contact.getFirstname());
        tvLastName.setText(contact.getLastname());
        tvGender.setText(Character.toString(contact.getGender()).equals("m") ? "male" : "female");  // ToDo: null pointer Exception !!!
        tvPhone.setText(contact.getPhoneNumber());
        tvLanguage.setText(contact.getLanguage());
        Picasso.get().load(contact.getPicture().toString())
                .placeholder(R.drawable.testpic)
                .into(ivPic);

    }
}
