package at.htlkaindorf.exa_q2_203_bankaccountapp.bl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import at.htlkaindorf.exa_q2_203_bankaccountapp.AccountViewHolder;
import at.htlkaindorf.exa_q2_203_bankaccountapp.MainActivity;
import at.htlkaindorf.exa_q2_203_bankaccountapp.R;
import at.htlkaindorf.exa_q2_203_bankaccountapp.io.IO_Access;

public class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {
    private List<Account> accounts;
    private List<Account> filteredAccounts;

    public AccountAdapter() {
        try {
            accounts = IO_Access.loadAccounts();
            filteredAccounts = new ArrayList<>(accounts);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        sortAccounts();
        notifyDataSetChanged();
    }

    public List<Account> getAccounts(){
        return this.accounts;
    }

    public void filterAccounts(String filter){
        filteredAccounts.clear();
        filteredAccounts.addAll(accounts);
        sortAccounts();

        if(filter.equals("all")) {
            notifyDataSetChanged();
            return;
        }
        filteredAccounts.removeIf(filter.equals("giro")
                ? account -> account instanceof  StudentAccount
                : account -> account instanceof GiroAccount);
        notifyDataSetChanged();
    }
    private void sortAccounts(){
        filteredAccounts.sort(Comparator.comparing(Account::getIban));
    }

    public void transferMoney(Account fromAccount, String toIban, double amount){
        for (Account toAccount : accounts) {
            if(toAccount.getIban().equals(toIban)){ // get target Account
                for (Account fromAccountOfList : accounts) {
                    if(fromAccount.equals(fromAccountOfList)){ // get fromAccount of list
                        double fromBalance = fromAccountOfList.getBalance();
                        double fromAvailable = fromBalance;

                        // get overdraft
                        if(fromAccountOfList instanceof GiroAccount){
                            fromAvailable += ((GiroAccount) fromAccountOfList).getOverdraft();
                        }

                        // check if transaction is possible
                        if(fromAvailable >= amount){
                            // transaction
                            fromAccountOfList.setBalance(fromBalance - amount);
                            toAccount.setBalance(toAccount.getBalance() + amount);
                            if(fromAccountOfList instanceof GiroAccount){
                                GiroAccount fromAcc = ((GiroAccount) fromAccountOfList);
                                fromAcc.setOverdraft(fromAcc.getOverdraft() - amount);
                            }
                            notifyDataSetChanged();
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
        TextView tvHeading = view.findViewById(R.id.tvHeading);
        RelativeLayout relativeLayout = view.findViewById(R.id.rlContainer);
        return new AccountViewHolder(view, ivIcon, tvIban, tvAvailable, tvAmount, tvHeading, relativeLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = filteredAccounts.get(position);
        holder.setAccount(account); // object used for communication

        double available = account.getBalance();
        holder.getTvAmount().setTextColor(MainActivity.mainContext.getColor(available < 0 ? R.color.red : R.color.green));
        holder.getTvAmount().setText(String.format("€ %,.2f", available));
        holder.getTvIban().setText(account.getIban());

        if(account instanceof GiroAccount){
            holder.getIvIcon().setImageResource(R.drawable.ic_attach_money_black_24dp);
            available += ((GiroAccount)account).getOverdraft();
            holder.getTvHeading().setText(R.string.giro_account);
        }else {
            holder.getIvIcon().setImageResource(R.drawable.ic_school_black_50dp);
            holder.getTvHeading().setText(R.string.student_account);
        }
        holder.getTvAvailableAmount().setText(String.format("€ %,.2f", available));
    }

    @Override
    public int getItemCount() {
        return filteredAccounts.size();
    }
}
