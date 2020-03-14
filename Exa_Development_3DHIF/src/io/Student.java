package io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

public class Student implements Serializable {
    private static final long serialVersionUID = 1223423L;
    private String firstname;
    private String lastname;
    private LocalDate dataOfBirth;
    private transient URI uri;

    public Student(String firstname, String lastname, LocalDate dataOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dataOfBirth = dataOfBirth;
        try {
            this.uri = new URI("http://www.123");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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

    public LocalDate getDataOfBirth() {
        return dataOfBirth;
    }

    public void setDataOfBirth(LocalDate dataOfBirth) {
        this.dataOfBirth = dataOfBirth;
    }

    @Override
    public String toString() {
        return "Student{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dataOfBirth=" + dataOfBirth +
                ", uri=" + uri +
                '}';
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeUTF(uri.toString());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException, URISyntaxException {
        ois.defaultReadObject();
        String strUri = ois.readUTF();
        uri = new URI(strUri);
    }
}
