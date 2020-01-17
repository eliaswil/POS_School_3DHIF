package at.htlkaindorf.exa_q2_206_pethome.beans;

import java.time.LocalDate;
import java.util.Objects;

public class Dog extends Pet {
    private Size size;

    public Dog(String name, LocalDate dateOfBirth, Gender gender, Size size) {
        super(name, dateOfBirth, gender);
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Dog dog = (Dog) o;
        return size == dog.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), size);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "size=" + size +
                '}';
    }
}
