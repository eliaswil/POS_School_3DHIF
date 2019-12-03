package at.htlkaindorf.exa_q2_203_bankaccountapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import at.htlkaindorf.exa_q2_203_bankaccountapp.bl.Account;

public class AccountViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivIcon;
    private TextView tvIban;
    private TextView tvAvailableAmount;
    private Account account;

    public AccountViewHolder(@NonNull View itemView, ImageView ivIcon, TextView tvIban, TextView tvAvailableAmount) {
        super(itemView);
        this.ivIcon = ivIcon;
        this.tvIban = tvIban;
        this.tvAvailableAmount = tvAvailableAmount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ImageView getIvIcon() {
        return ivIcon;
    }

    public void setIvIcon(ImageView ivIcon) {
        this.ivIcon = ivIcon;
    }

    public TextView getTvIban() {
        return tvIban;
    }

    public void setTvIban(TextView tvIban) {
        this.tvIban = tvIban;
    }

    public TextView getTvAvailableAmount() {
        return tvAvailableAmount;
    }

    public void setTvAvailableAmount(TextView tvAvailableAmount) {
        this.tvAvailableAmount = tvAvailableAmount;
    }
}
