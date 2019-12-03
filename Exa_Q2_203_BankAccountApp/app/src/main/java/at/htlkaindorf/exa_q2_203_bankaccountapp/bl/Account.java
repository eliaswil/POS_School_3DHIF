package at.htlkaindorf.exa_q2_203_bankaccountapp.bl;

import java.util.Objects;

public class Account {
    private String iban;
    private double balance;
    private float interest;

    public Account(String iban, double balance, float interest) {
        this.iban = iban;
        this.balance = balance;
        this.interest = interest;
    }

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

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public float getInterest() {
        return interest;
    }

    public void setInterest(float interest) {
        this.interest = interest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                ", interest=" + interest +
                '}';
    }
}
