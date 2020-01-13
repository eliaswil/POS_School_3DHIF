package at.htlkaindorf.exa_q2_206_pethome.beans;

import android.net.Uri;

import java.time.LocalDate;
import java.util.Objects;

public class Cat extends Pet {
    private CatColor color;
    private Uri pictureUri;


    public Cat(String name, LocalDate dateOfBirth, Gender gender) {
        super(name, dateOfBirth, gender);
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
}
