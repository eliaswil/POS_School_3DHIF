package at.htlkaindorf.exa_q2_206_pethome.beans;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Pet implements Serializable {
    private static final long serialVersionUID = 1111111111;
    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;

    public Pet(String name, LocalDate dateOfBirth, Gender gender) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(name, pet.name) &&
                Objects.equals(dateOfBirth, pet.dateOfBirth) &&
                gender == pet.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth, gender);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                '}';
    }
}
