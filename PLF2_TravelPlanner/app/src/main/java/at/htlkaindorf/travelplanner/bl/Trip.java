package at.htlkaindorf.travelplanner.bl;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Trip implements Serializable {
    private String city;
    private String country;
    private String countryCode;
    private LocalDate startDate;
    private int duration;

    public Trip(String city, String country, String countryCode, LocalDate startDate, int duration) {
        this.city = city;
        this.country = country;
        this.countryCode = countryCode;
        this.startDate = startDate;
        this.duration = duration;
    }

    public Trip(String line){
        String[] tokens = line.split(" - ");
        this.city = tokens[0].trim();
        this.country = tokens[1].trim();
        this.countryCode = tokens[2].trim();
        this.startDate = LocalDate.parse(tokens[3].trim(), DateTimeFormatter.ofPattern("d.M.yyyy"));
        this.duration = Integer.parseInt(tokens[4].trim());
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return duration == trip.duration &&
                Objects.equals(city, trip.city) &&
                Objects.equals(country, trip.country) &&
                Objects.equals(countryCode, trip.countryCode) &&
                Objects.equals(startDate, trip.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, countryCode, startDate, duration);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", startDate=" + startDate +
                ", duration=" + duration +
                '}';
    }

}
