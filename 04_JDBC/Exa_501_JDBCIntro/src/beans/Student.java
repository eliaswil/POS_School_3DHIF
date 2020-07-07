/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Elias Wilfinger
 */
public class Student {
    private int studentId;
    private int catalognr;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Student() {
    }

    public Student(int studentId, int catalognr, String firstname, String lastname, LocalDate dateOfBirth) {
        this.studentId = studentId;
        this.catalognr = catalognr;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = dateOfBirth;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCatalognr() {
        return catalognr;
    }

    public void setCatalognr(int catalognr) {
        this.catalognr = catalognr;
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

    public LocalDate getDateOfBirth() {
        return birthdate;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.birthdate = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Student{" + "studentId=" + studentId + ", catalognr=" + catalognr 
                + ", firstname=" + firstname + ", lastname=" + lastname 
                + ", dateOfBirth=" + birthdate.format(DTF) + '}';
    }
    
    
    
    
}
