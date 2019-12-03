package at.htlkaindorf.exa_q2_203_bankaccountapp.bl;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import at.htlkaindorf.exa_q2_203_bankaccountapp.AccountViewHolder;

public class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {
    List<Account> accounts;
    List<Account> filteredAccounts;

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = filteredAccounts.get(position);

        holder.setAccount(account);
        String url = "kkasdfasdasfasdfasfasdf"; // ToDo: unterscheiden zw. Student / Giro

        Picasso.get().load(url).into(holder.getIvIcon());
    }

    @Override
    public int getItemCount() {
        return filteredAccounts.size();
    }
}
