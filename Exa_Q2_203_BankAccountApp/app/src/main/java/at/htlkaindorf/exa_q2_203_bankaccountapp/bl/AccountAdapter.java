package at.htlkaindorf.exa_q2_203_bankaccountapp.bl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import at.htlkaindorf.exa_q2_203_bankaccountapp.AccountViewHolder;
import at.htlkaindorf.exa_q2_203_bankaccountapp.R;
import at.htlkaindorf.exa_q2_203_bankaccountapp.io.IO_Access;

public class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {
    List<Account> accounts;
    List<Account> filteredAccounts;

    public AccountAdapter() {
        try {
            accounts = IO_Access.loadAccounts();
            filteredAccounts.clear();
            filteredAccounts.addAll(accounts);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //TODO: fill filtered Accounts
    }

    private void filterAccounts(String filter){
        filteredAccounts.clear();
        filteredAccounts.addAll(accounts);
        filteredAccounts.removeIf(account -> account.getIban().toUpperCase().contains(filter.toUpperCase()));
        sortAccounts();
        notifyDataSetChanged();
    }
    private void sortAccounts(){
        filteredAccounts.sort(Comparator.comparing(Account::getIban));
    }
    private void transferMoney(Account fromAccount, String toIban, double amount){
        for (Account toAccount : accounts) {
            if(toAccount.getIban().equals(toIban)){
                // get fromAccount of list
                for (Account fromAccountOfList : accounts) {
                    if(fromAccount.equals(fromAccountOfList)){
                        double fromBalance = fromAccountOfList.getBalance();
                        if(fromBalance >= amount){
                            fromAccountOfList.setBalance(fromBalance - amount);
                            toAccount.setBalance(toAccount.getBalance() + amount);
                        }
                    }
                }
            }
        }
    }


    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        ImageView ivIcon = view.findViewById(R.id.ivIcon);
        TextView tvIban = view.findViewById(R.id.tvIban);
        TextView tvAvailable = view.findViewById(R.id.tvAvailableAmount);
        TextView tvAmount = view.findViewById(R.id.tvAmount);
        return new AccountViewHolder(view, ivIcon, tvIban, tvAvailable, tvAmount);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = filteredAccounts.get(position);

        holder.setAccount(account);
        double available = account.getBalance();

        holder.getTvIban().setText(account.getIban());
        holder.getTvAmount().setText(String.format("%.,f", available));

        if(account instanceof GiroAccount){
            holder.getIvIcon().setImageResource(R.drawable.ic_attach_money_black_24dp);
            available += ((GiroAccount)account).getOverdraft();
        }else {
            holder.getIvIcon().setImageResource(R.drawable.ic_school_black_50dp);
        }
        holder.getTvAvailableAmount().setText(String.format("%.,f", available));
    }

    @Override
    public int getItemCount() {
        return filteredAccounts.size();
    }
}
