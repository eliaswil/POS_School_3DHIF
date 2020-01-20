package at.htlkaindorf.exa_q2_206_pethome.beans;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dog extends Pet {
    private Size size;

    public Dog(String name, LocalDate dateOfBirth, Gender gender, Size size) {
        super(name, dateOfBirth, gender);
        this.size = size;
    }

    public Dog(String line){
        super(null, null, null);

        String[] tokens = line.split(",");
        String name = tokens[0];
        Gender gender = Gender.valueOf(tokens[1].toUpperCase());
        LocalDate birthdate = LocalDate.parse(tokens[2], DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        Map<String, Size> sizeMap = new HashMap<>();
        sizeMap.put("L", Size.LARGE);
        sizeMap.put("S", Size.SMALL);
        sizeMap.put("M", Size.MEDIUM);
        Size size = sizeMap.get(tokens[3].toUpperCase());

        super.setDateOfBirth(birthdate);
        super.setName(name);
        super.setGender(gender);

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
