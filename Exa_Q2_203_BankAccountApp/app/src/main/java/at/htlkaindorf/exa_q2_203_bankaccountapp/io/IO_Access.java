package at.htlkaindorf.exa_q2_203_bankaccountapp.io;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.Account;
import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.GiroAccount;
import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.StudentAccount;

public class IO_Access {
    private static Context mainContext;
    public static void init(Context context){
        mainContext = context;
    }

    public static List<Account> loadAccounts() throws IOException {
        List<Account> accounts = new ArrayList<>();
        AssetManager assetManager = mainContext.getAssets();
        InputStream is = assetManager.open("account_data.csv");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        br.readLine();
        while ((line = br.readLine()) != null){
            String[] tokens = line.split(",");
            String iban = tokens[2];
            double amount = Double.parseDouble(tokens[3]);
            float interest = Float.parseFloat(tokens[6]);
            if(tokens[1].toUpperCase().equals("GIRO")){
                double overdraft = Double.parseDouble(tokens[4]);
                accounts.add(new GiroAccount(iban, amount, interest, overdraft));
            }else {
                boolean debitcard = Boolean.valueOf(tokens[5]);
                accounts.add(new StudentAccount(iban, amount, interest, debitcard));
            }
        }
        return accounts;
    }
}
