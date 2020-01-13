package serial;

import java.time.LocalDate;

public class Student {
    private String firstname;
    private String lastname;
    private LocalDate dataOfBirth;

    public Student(String firstname, String lastname, LocalDate dataOfBirth) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.dataOfBirth = dataOfBirth;
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
                '}';
    }
}
