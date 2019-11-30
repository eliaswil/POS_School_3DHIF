package at.htlkaindorf.exa_q2_201_contactsapp.bl;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Contact implements Parcelable {
    private String firstname;
    private String lastname;
    private String language;
    private char gender;
    private Uri picture;
    private String phoneNumber;
    private int id;


    public Contact(String line) {
        String[] tokens = line.split(",");
        this.id = Integer.parseInt(tokens[0]);
        this.firstname = tokens[1];
        this.lastname = tokens[2];
        this.language = tokens[3];
        this.gender = tokens[4].toUpperCase().equals("MALE") ? 'm' : 'f';
        this.picture = Uri.parse(tokens[5]);
        this.phoneNumber = tokens[6];
    }


    protected Contact(Parcel in) {
        firstname = in.readString();
        lastname = in.readString();
        language = in.readString();
        gender = (char) in.readInt();
        picture = in.readParcelable(Uri.class.getClassLoader());
        phoneNumber = in.readString();
        id = in.readInt();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return gender == contact.gender &&
                id == contact.id &&
                Objects.equals(firstname, contact.firstname) &&
                Objects.equals(lastname, contact.lastname) &&
                Objects.equals(language, contact.language) &&
                Objects.equals(picture, contact.picture) &&
                Objects.equals(phoneNumber, contact.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, language, gender, picture, phoneNumber, id);
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getLanguage() {
        return language;
    }

    public char getGender() {
        return gender;
    }

    public Uri getPicture() {
        return picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(language);
        dest.writeInt((int) gender);
        dest.writeParcelable(picture, flags);
        dest.writeString(phoneNumber);
        dest.writeInt(id);
    }

}
