package at.htlkaindorf.exa_q2_201_contactsapp.io;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import at.htlkaindorf.exa_q2_201_contactsapp.bl.Contact;

public class IOHandler {
    private static Context mainContext;
    public static void init(Context context){
        mainContext = context;
    }

    public static List<Contact> readContacts() throws IOException {
        List<Contact> contacts;
        AssetManager assetManager = mainContext.getAssets();
        InputStream is = assetManager.open("contact_data.csv");
        contacts = new BufferedReader(new InputStreamReader(is))
                .lines()
                .skip(1)
                .map(Contact::new)
                .collect(Collectors.toList());

        return contacts;
    }
}
