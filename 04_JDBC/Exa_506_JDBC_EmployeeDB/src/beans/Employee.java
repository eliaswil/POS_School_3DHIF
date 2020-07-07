/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author Elias Wilfinger
 */
public class Employee implements Cloneable{
    private String last_name;
    private String first_name;
    private char gender;
    private LocalDate birth_date;
    private LocalDate hire_date;
    
    public static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Employee(String last_name, String first_name, char gender, LocalDate birth_date, LocalDate hire_date) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.gender = gender;
        this.birth_date = birth_date;
        this.hire_date = hire_date;
    }

    @Override
    public String toString() {
        return "Employee{" + "last_name=" + last_name + ", first_name=" + first_name 
                + ", gender=" + gender + ", birth_date=" + birth_date 
                + ", hire_date=" + hire_date + "}\n";
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.last_name);
        hash = 23 * hash + Objects.hashCode(this.first_name);
        hash = 23 * hash + this.gender;
        hash = 23 * hash + Objects.hashCode(this.birth_date);
        hash = 23 * hash + Objects.hashCode(this.hire_date);
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
        final Employee other = (Employee) obj;
        if (this.gender != other.gender) {
            return false;
        }
        if (!Objects.equals(this.last_name, other.last_name)) {
            return false;
        }
        if (!Objects.equals(this.first_name, other.first_name)) {
            return false;
        }
        if (!Objects.equals(this.birth_date, other.birth_date)) {
            return false;
        }
        if (!Objects.equals(this.hire_date, other.hire_date)) {
            return false;
        }
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public LocalDate getHire_date() {
        return hire_date;
    }

    public void setHire_date(LocalDate hire_date) {
        this.hire_date = hire_date;
    }   
}
