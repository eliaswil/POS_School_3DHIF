package at.htlkaindorf.exa_q2_203_bankaccountapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.Account;

public class AccountViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivIcon;
    private TextView tvIban;
    private TextView tvAvailableAmount;
    private TextView tvAmount;
    private RelativeLayout relativeLayout;
    private Account account;
    private TextView tvHeading;


    public AccountViewHolder(@NonNull View itemView, ImageView ivIcon, TextView tvIban, TextView tvAvailableAmount, TextView tvAmount, TextView tvHeading, RelativeLayout relativeLayout) {
        super(itemView);
        this.ivIcon = ivIcon;
        this.tvIban = tvIban;
        this.tvAvailableAmount = tvAvailableAmount;
        this.tvAmount = tvAmount;
        this.tvHeading = tvHeading;
        this.relativeLayout = relativeLayout;

        this.relativeLayout.setOnLongClickListener(v -> {
            Intent intent = new Intent(MainActivity.mainContext, TransferActivity.class);
            intent.putExtra("fromAccount", account);
            MainActivity.mainContext.startActivityForResult(intent, 1);
            return true;
        });

    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public ImageView getIvIcon() {
        return ivIcon;
    }
    public TextView getTvHeading() {
        return tvHeading;
    }
    public TextView getTvIban() {
        return tvIban;
    }
    public TextView getTvAmount() {
        return tvAmount;
    }
    public TextView getTvAvailableAmount() {
        return tvAvailableAmount;
    }
}
