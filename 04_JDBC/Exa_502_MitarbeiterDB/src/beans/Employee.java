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
public class Employee {
    
    private int pers_nr;
    private String name;
    private String vorname;
    private LocalDate get_datum;
    private char geschlecht;
    private double gehalt;
    private int abt_nr;

    public Employee() {
    }

    public Employee(int pers_nr) {
        this.pers_nr = pers_nr;
    }
    
    
    
    public Employee(String line) {
        String[] tokens = line.split(";");
        this.name = tokens[0];
        this.vorname = tokens[1];
        this.get_datum = LocalDate.parse(tokens[2], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.gehalt = Double.parseDouble(tokens[3]);
        this.abt_nr = Integer.parseInt(tokens[4]);
        this.geschlecht = tokens[5].charAt(0);
    }

    public Employee(int pers_nr, String name, String vorname, LocalDate get_datum, char geschlecht, double gehalt, int abt_nr) {
        this.pers_nr = pers_nr;
        this.name = name;
        this.vorname = vorname;
        this.get_datum = get_datum;
        this.geschlecht = geschlecht;
        this.gehalt = gehalt;
        this.abt_nr = abt_nr;
    }
    
    

    public int getAbt_nr() {
        return abt_nr;
    }

    public void setAbt_nr(int abt_nr) {
        this.abt_nr = abt_nr;
    }

    

    public double getGehalt() {
        return gehalt;
    }

    public void setGehalt(double gehalt) {
        this.gehalt = gehalt;
    }

    public int getPers_nr() {
        return pers_nr;
    }

    public void setPers_nr(int pers_nr) {
        this.pers_nr = pers_nr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public LocalDate getGet_datum() {
        return get_datum;
    }

    public void setGet_datum(LocalDate get_datum) {
        this.get_datum = get_datum;
    }

    public char getGeschlecht() {
        return geschlecht;
    }

    public void setGeschlecht(char geschlecht) {
        this.geschlecht = geschlecht;
    }

    @Override
    public String toString() {
        return "Employee{" + "pers_nr=" + pers_nr + ", name=" + name + ", vorname=" 
                + vorname + ", get_datum=" + get_datum + ", geschlecht=" + geschlecht + '}';
    }

    
    
    
}
