package at.htlkaindorf.exa_q2_203_bankaccountapp.bl;

import java.util.Objects;

public class GiroAccount extends Account {
    private double overdraft;

    public GiroAccount(String iban, double balance, float interest, double overdraft) {
        super(iban, balance, interest);
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return "GiroAccount{" +
                "overdraft=" + overdraft +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiroAccount that = (GiroAccount) o;
        return Double.compare(that.overdraft, overdraft) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), overdraft);
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }
}
