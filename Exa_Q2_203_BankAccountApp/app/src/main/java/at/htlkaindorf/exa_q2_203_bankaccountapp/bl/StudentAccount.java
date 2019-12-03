package at.htlkaindorf.exa_q2_203_bankaccountapp.bl;

import java.util.Objects;

public class StudentAccount extends Account{
    private boolean debitCard;

    public StudentAccount(String iban, double balance, float interest, boolean debitCard) {
        super(iban, balance, interest);
        this.debitCard = debitCard;
    }

    @Override
    public String toString() {
        return "StudentAccount{" +
                "debitCard=" + debitCard +
                '}';
    }

    public boolean isDebitCard() {
        return debitCard;
    }

    public void setDebitCard(boolean debitCard) {
        this.debitCard = debitCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentAccount that = (StudentAccount) o;
        return debitCard == that.debitCard;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), debitCard);
    }
}
