package at.htlkaindorf.exa_q2_201_contactsapp.bl;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Contact {
    private String firstname;
    private String lastname;
    private String language;
    private char gender;
    private URL picture;
    private String phoneNumber;
    private int id;


    public Contact(String line) {
        String[] tokens = line.split(",");
        this.id = Integer.parseInt(tokens[0]);
        this.firstname = tokens[1];
        this.lastname = tokens[2];
        this.language = tokens[3];
        this.gender = tokens[4].toUpperCase().equals("MALE") ? 'm' : 'f';
        try {
            this.picture = new URL(tokens[5]);
        } catch (MalformedURLException e) {
            this.picture = null;
        }
        this.phoneNumber = tokens[6];
    }

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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public URL getPicture() {
        return picture;
    }

    public void setPicture(URL picture) {
        this.picture = picture;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
