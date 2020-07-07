package io;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Company {
    private String name;
    private int noEmployees;
    private LocalDate founded;
    private double turnover;
    private String country;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public Company(String name, int noEmployees, LocalDate founded, double turnover, String country) {
        this.name = name;
        this.noEmployees = noEmployees;
        this.founded = founded;
        this.turnover = turnover;
        this.country = country;
    }

    public Company(String line) {
        String[] tokens = line.split(",");
        this.name = tokens[0];
        this.noEmployees = Integer.parseInt(tokens[1]);
        this.founded = LocalDate.parse(tokens[2], DTF);
        this.turnover = Double.parseDouble(tokens[3]);
        this.country = tokens[4];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoEmployees() {
        return noEmployees;
    }

    public void setNoEmployees(int noEmployees) {
        this.noEmployees = noEmployees;
    }

    public LocalDate getFounded() {
        return founded;
    }

    public void setFounded(LocalDate founded) {
        this.founded = founded;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", noEmployees=" + noEmployees +
                ", founded=" + founded +
                ", turnover=" + turnover +
                ", country='" + country + '\'' +
                '}';
    }
}
