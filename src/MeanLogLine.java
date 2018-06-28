import java.time.LocalDate;
import java.time.LocalTime;

public class MeanLogLine extends LogLine {
    private String name;
    private LocalDate date;
    private LocalTime time;

    public MeanLogLine(double r, double b, double swpd, double livre, double buffer, double cache, double si, double so, double bi, double bo, double in, double cs, double us, double sy, double id, double wa, double st, String name, LocalDate date, LocalTime time) {
        super(r, b, swpd, livre, buffer, cache, si, so, bi, bo, in, cs, us, sy, id, wa, st);
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String returnMeanLogLineString(){
        return name + " " + date.getYear() + " " + date.getMonth() + " " + date.getDayOfMonth() + " " + time.getHour() + " " + time.getMinute() + " " + this.getR() + " " + this.getB() + " " + this.getSwpd() + " " + String.format( "%.4f",this.getLivre()).replaceAll(",", ".") + " " + this.getBuffer() + " " + this.getCache() + " " + this.getSi() + " " + this.getSo() + " " + this.getBo() + " " + this.getIn() + " " + this.getCs() + " " + this.getUs() + " " + this.getSy() + " " + this.getId() + " " + this.getWa() + " " + this.getSt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
