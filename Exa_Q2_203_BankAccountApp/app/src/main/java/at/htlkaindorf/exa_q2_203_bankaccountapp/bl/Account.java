package at.htlkaindorf.exa_q2_203_bankaccountapp.bl;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Account implements Parcelable {
    private String iban;
    private double balance;
    private float interest;

    public Account(String iban, double balance, float interest) {
        this.iban = iban;
        this.balance = balance;
        this.interest = interest;
    }

    protected Account(Parcel in) {
        iban = in.readString();
        balance = in.readDouble();
        interest = in.readFloat();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 &&
                Float.compare(account.interest, interest) == 0 &&
                Objects.equals(iban, account.iban);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, balance, interest);
    }

    public String getIban() {
        return iban;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", interest=" + interest +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iban);
        dest.writeDouble(balance);
        dest.writeFloat(interest);
    }
}
