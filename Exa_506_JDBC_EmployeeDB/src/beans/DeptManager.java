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
public class DeptManager {
    private String last_name;
    private String first_name;
    private LocalDate from_date;
    private LocalDate to_date;
    
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public DeptManager(String last_name, String first_name, LocalDate from_date, LocalDate to_date) {
        this.last_name = last_name;
        this.first_name = first_name;
        this.from_date = from_date;
        this.to_date = to_date;
    }

    @Override
    public String toString() {
        return String.format("<p style=\"margin-bottom: 4px;\">"
                + "<b>%s, %s</b>:\t"
                + "from <i style=\"color: red;\">%s</i> "
                + "to <i style=\"color: red;\">%s</i>"
                + "</p>", this.last_name, this.first_name, this.from_date.format(DTF), 
                this.to_date.isAfter(LocalDate.now()) ? "now" : this.to_date.format(DTF));
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

    public LocalDate getFrom_date() {
        return from_date;
    }

    public void setFrom_date(LocalDate from_date) {
        this.from_date = from_date;
    }

    public LocalDate getTo_date() {
        return to_date;
    }

    public void setTo_date(LocalDate to_date) {
        this.to_date = to_date;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.last_name);
        hash = 61 * hash + Objects.hashCode(this.first_name);
        hash = 61 * hash + Objects.hashCode(this.from_date);
        hash = 61 * hash + Objects.hashCode(this.to_date);
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
        final DeptManager other = (DeptManager) obj;
        if (!Objects.equals(this.last_name, other.last_name)) {
            return false;
        }
        if (!Objects.equals(this.first_name, other.first_name)) {
            return false;
        }
        if (!Objects.equals(this.from_date, other.from_date)) {
            return false;
        }
        if (!Objects.equals(this.to_date, other.to_date)) {
            return false;
        }
        return true;
    }
    
    
}
