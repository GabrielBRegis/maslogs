import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class LogSnapshot {

    String name;
    LocalDate date;
    LocalTime time;
    ArrayList<LogLine> loglines;

    public LogSnapshot(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.loglines = new ArrayList<LogLine>();
    }

    public MeanLogLine snapShotMaxLog(){
        String name = this.getName();
        LocalDate date = this.getDate();
        LocalTime time = this.getTime();
        double r = 0;
        double b = 0;
        double swpd = 0;
        double livre = 0;
        double buffer = 0;
        double cache = 0;
        double si = 0;
        double so = 0;
        double bi = 0;
        double bo = 0;
        double in = 0;
        double cs = 0;
        double us = 0;
        double sy = 0;
        double id = 0;
        double wa = 0;
        double st = 0;

        for(LogLine line : this.getLoglines()){
            r = r <= line.getR() ? line.getR() : r;
            b = b <= line.getB() ? line.getB() : b;
            swpd = swpd <= line.getSwpd() ? line.getSwpd() : swpd;
            livre = livre <= line.getLivre() ? line.getLivre() : livre;
            buffer = buffer <= line.getBuffer() ? line.getBuffer() : buffer;
            cache = cache <= line.getCache() ? line.getCache() : cache;
            si = si <= line.getSi() ? line.getSi() : si;
            so = so <= line.getSo() ? line.getSo() : so;

            bi = bi <= line.getBi() ? line.getBi() : bi;
            bo = bo <= line.getBo() ? line.getBo() : bo;
            in = in <= line.getIn() ? line.getIn() : in;
            cs = cs <= line.getCs() ? line.getCs() : cs;
            us = us <= line.getUs() ? line.getUs() : us;
            sy = sy <= line.getSy() ? line.getSy() : sy;
            id = id <= line.getId() ? line.getId() : id;
            wa = wa <= line.getWa() ? line.getWa() : wa;
            st = st <= line.getSt() ? line.getSt() : st;
        }

        MeanLogLine mean = new MeanLogLine(r, b, swpd, livre, buffer, cache, si, so, bi, bo, in, cs, us, sy, id, wa, st, name, date, time);
        return mean;
    }

    public MeanLogLine snapShotMeanLog(){

        String name = this.getName();
        LocalDate date = this.getDate();
        LocalTime time = this.getTime();
        double r = 0;
        double b = 0;
        double swpd = 0;
        double livre = 0;
        double buffer = 0;
        double cache = 0;
        double si = 0;
        double so = 0;
        double bi = 0;
        double bo = 0;
        double in = 0;
        double cs = 0;
        double us = 0;
        double sy = 0;
        double id = 0;
        double wa = 0;
        double st = 0;

        for(LogLine line : this.getLoglines()){
            r += line.getR();
            b += line.getB();
            swpd += line.getSwpd();
            livre += line.getLivre();
            buffer += line.getBuffer();
            cache += line.getCache();
            si += line.getSi();
            so += line.getSo();
            bi += line.getBi();
            bo += line.getBo();
            in += line.getIn();
            cs += line.getCs();
            us += line.getUs();
            sy += line.getSy();
            id += line.getId();
            wa += line.getWa();
            st += line.getSt();
        }

        r /= this.getLoglines().size();
        b /= this.getLoglines().size();
        swpd /= this.getLoglines().size();
        livre /= this.getLoglines().size();
        buffer /= this.getLoglines().size();
        cache /= this.getLoglines().size();
        si /= this.getLoglines().size();
        so /= this.getLoglines().size();
        bi /= this.getLoglines().size();
        bo /= this.getLoglines().size();
        in /= this.getLoglines().size();
        cs /= this.getLoglines().size();
        us /= this.getLoglines().size();
        sy /= this.getLoglines().size();
        id /= this.getLoglines().size();
        wa /= this.getLoglines().size();
        st /= this.getLoglines().size();

        MeanLogLine mean = new MeanLogLine(r, b, swpd, livre, buffer, cache, si, so, bi, bo, in, cs, us, sy, id, wa, st, name, date, time);
        return mean;
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

    public ArrayList<LogLine> getLoglines() {
        return loglines;
    }

    public void setLoglines(ArrayList<LogLine> loglines) {
        this.loglines = loglines;
    }
}
