package at.htlkaindorf.exa_q2_201_zodiacsign.bl;

import java.time.MonthDay;
import java.util.Objects;

public class ZodiacSign {
    private String name;
    private MonthDay startDate;
    private int drawableld;

    public ZodiacSign(String name, MonthDay startDate, int drawableld) {
        this.name = name;
        this.startDate = startDate;
        this.drawableld = drawableld;
    }

    public int getIdFromDrawable(){
        return drawableld;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MonthDay getStartDate() {
        return startDate;
    }

    public void setStartDate(MonthDay startDate) {
        this.startDate = startDate;
    }


    public void setDrawableld(int drawableld) {
        this.drawableld = drawableld;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZodiacSign that = (ZodiacSign) o;
        return name == that.name &&
                drawableld == that.drawableld &&
                Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, drawableld);
    }
}
