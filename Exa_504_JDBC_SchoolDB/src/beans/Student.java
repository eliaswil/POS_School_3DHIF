/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Elias Wilfinger
 */
public class Student {
    private Grade grade;
    private int catNo;
    private String firstname;
    private String surname;
    private char gender;
    private LocalDate dateOfBirth;

    /**
     * used for importing students from FILE (.csv)
     * @param line
     * @return 
     */
    public static Student of(String line){
        Student student = new Student(line);
        student.grade.addStudent(student);
        return student;
    }
    
    /**
     * used for creating single students (inserting)
     * @param firstname
     * @param surname
     * @param gender
     * @param dateOfBirth
     * @param grade
     * @return 
     */
    public static Student of(String firstname, String surname, char gender, LocalDate dateOfBirth, String grade){
        Student student = new Student(firstname, surname, gender, dateOfBirth, grade);
        if(!student.grade.addStudent(student)){
            return null;
        }
        
        return student;
    }
    
    private Student(String firstname, String surname, char gender, LocalDate dateOfBirth, String grade){
        this.firstname = firstname;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.grade = Grade.of(grade);
    }
    
    private Student(String line){
        // Klasse;Familienname;Vorname;Geschlecht;Geburtsdatum
        String[] tokens = line.split(";");
        if(tokens.length != 5){
            throw new IllegalArgumentException("Wrong/unsiutable file!.");
        }
        this.grade = Grade.of(tokens[0]);
        this.firstname = tokens[2];
        this.surname = tokens[1];
        this.gender = tokens[3].charAt(0);
        this.dateOfBirth = LocalDate.parse(tokens[4], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
    }

    /**
     * used for importing students from DB
     * @param catNo
     * @param firstname
     * @param surname
     * @param gender
     * @param dateOfBirth 
     */
    public Student(int catNo, String firstname, String surname, char gender, LocalDate dateOfBirth) {
        this.catNo = catNo;
        this.firstname = firstname;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }
    
    

    public Student() {
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public int getCatNo() {
        return catNo;
    }

    public void setCatNo(int catNo) {
        this.catNo = catNo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.firstname);
        hash = 73 * hash + Objects.hashCode(this.surname);
        hash = 73 * hash + Objects.hashCode(this.dateOfBirth);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        if (!Objects.equals(this.dateOfBirth, other.dateOfBirth)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "\nStudent{" + "catNo=" + catNo + ", firstname=" + firstname 
                + ", surname=" + surname + ", gender=" + gender + ", dateOfBirth=" 
                + dateOfBirth + '}';
    }

    
    
    
    
    
    
    

    
    
    
}
