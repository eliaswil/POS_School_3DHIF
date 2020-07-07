package bl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Event {
    private String text;
    private LocalDate date;
    private DateTimeFormatter ddf = DateTimeFormatter.ofPattern("dd.mm.YYYY");

    public Event(String text, LocalDate date) {
        this.text = text;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s - %s %s",text,date,date.format(ddf));
    }
}
