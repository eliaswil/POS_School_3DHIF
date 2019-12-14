package at.htlkaindorf.exa_q2_203_bankaccountapp.bl;

import android.os.Parcel;

import java.util.Objects;

public class StudentAccount extends Account{
    private boolean debitCard;

    public StudentAccount(String iban, double balance, float interest, boolean debitCard) {
        super(iban, balance, interest);
        this.debitCard = debitCard;
    }

    protected StudentAccount(Parcel in){
        super(in);
        debitCard = in.readByte() != 0;
    }

    @Override
    public String toString() {
        return "StudentAccount{" +
                "debitCard=" + debitCard +
                '}';
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




    public static final Creator<StudentAccount> CREATOR = new Creator<StudentAccount>() {
        @Override
        public StudentAccount createFromParcel(Parcel in) {
            return new StudentAccount(in);
        }

        @Override
        public StudentAccount[] newArray(int size) {
            return new StudentAccount[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (debitCard ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
