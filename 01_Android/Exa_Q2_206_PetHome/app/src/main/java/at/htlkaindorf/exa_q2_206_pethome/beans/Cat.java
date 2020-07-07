package at.htlkaindorf.exa_q2_206_pethome.beans;

import android.net.Uri;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cat extends Pet {
    private CatColor color;
    private transient Uri pictureUri;


    public Cat(String name, LocalDate dateOfBirth, Gender gender, CatColor color, Uri pictureUri) {
        super(name, dateOfBirth, gender);
        this.color = color;
        this.pictureUri = pictureUri;
    }

    public Cat(String line) {
        super(null, null, null);
        String[] tokens = line.split(",");
        String name = tokens[0];
        Gender gender = Gender.valueOf(tokens[1].toUpperCase());
        LocalDate birthdate = LocalDate.parse(tokens[2], DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        CatColor catColor = CatColor.valueOf(tokens[4].toUpperCase());
        Uri pictureUri = Uri.parse(tokens[5]);

        super.setDateOfBirth(birthdate);
        super.setName(name);
        super.setGender(gender);
        this.color = catColor;
        this.pictureUri = pictureUri;
    }

    public CatColor getColor() {
        return color;
    }
    public void setColor(CatColor color) {
        this.color = color;
    }
    public Uri getPictureUri() {
        return pictureUri;
    }
    public void setPictureUri(Uri pictureUri) {
        this.pictureUri = pictureUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cat cat = (Cat) o;
        return color == cat.color &&
                Objects.equals(pictureUri, cat.pictureUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), color, pictureUri);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "color=" + color +
                ", pictureUri=" + pictureUri +
                '}';
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeUTF(pictureUri.toString());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        pictureUri = Uri.parse(ois.readUTF());
    }
}
