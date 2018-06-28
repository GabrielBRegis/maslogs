import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LogController {

    ArrayList<LogSnapshot> logSnapshots;
    LogSnapshot currentSnapshot;

    public TimeStamp getLogTimeStamp() {
        return logTimeStamp;
    }

    public void setLogTimeStamp(TimeStamp logTimeStamp) {
        this.logTimeStamp = logTimeStamp;
    }

    TimeStamp logTimeStamp;
    int index;
    boolean dataCorrupted;

    public LogController(Stream<String> log){

        logSnapshots = new ArrayList<LogSnapshot>();
        currentSnapshot = null;
        index = 0;
        logTimeStamp = null;
        dataCorrupted = false;
        log.forEach(line -> chooseMethod(line));
    }

    public void chooseMethod(String line){
        if(index == 0){
            createHeader(line);
        }else{
            lineHandler(line);
        }
    }

    public void lineHandler(String line){
        if(!isHeader(line) && !isTimestamp(line)){

            if(index % 40 == 0){
                logSnapshots.add(currentSnapshot);
                currentSnapshot = new LogSnapshot(logTimeStamp.getName(), logTimeStamp.getDate(), currentSnapshot.getTime().plusMinutes(10));
            }

            List<String> logLineStrings = Arrays.asList(line.split("\\s+"));
            double r = Double.parseDouble(logLineStrings.get(1));
            double b = Double.parseDouble(logLineStrings.get(2));
            double swpd = Double.parseDouble(logLineStrings.get(3));
            double livre = Double.parseDouble(logLineStrings.get(4));
            double buffer = Double.parseDouble(logLineStrings.get(5));
            double cache = Double.parseDouble(logLineStrings.get(6));
            double si = Double.parseDouble(logLineStrings.get(7));
            double so = Double.parseDouble(logLineStrings.get(8));
            double bi = Double.parseDouble(logLineStrings.get(9));
            double bo = Double.parseDouble(logLineStrings.get(10));
            double in = Double.parseDouble(logLineStrings.get(11));
            double cs = Double.parseDouble(logLineStrings.get(12));
            double us = Double.parseDouble(logLineStrings.get(13));
            double sy = Double.parseDouble(logLineStrings.get(14));
            double id = Double.parseDouble(logLineStrings.get(15));
            double wa = 0;

            if(logLineStrings.size()<16){
                dataCorrupted = true;
                return;
            }

            if(logLineStrings.size()>16){
                wa = Double.parseDouble(logLineStrings.get(16));
            }

            double st = 0;

            if(logLineStrings.size()>17){
                st = Double.parseDouble(logLineStrings.get(17));
            }

            LogLine logLine = new LogLine(r, b, swpd, livre, buffer, cache, si, so, bi, bo, in, cs, us, sy, id, wa, st);
            currentSnapshot.getLoglines().add(logLine);
            index++;
        }
    }

    public void createHeader(String line){
        List<String> header = Arrays.asList(line.split("\\s+"));
        String name = header.get(0);
        System.out.println(header);
        LocalDate date = LocalDate.parse(header.get(1));
        LocalTime time = LocalTime.parse(header.get(2));
        logTimeStamp = new TimeStamp(name, date, time);
        index++;
        currentSnapshot = new LogSnapshot(logTimeStamp.getName(), logTimeStamp.getDate(), logTimeStamp.getTime());
    }

    public boolean isTimestamp(String line){
        String dateRegex = "\\d{4}-\\d{2}-\\d{2}";
        boolean teste = line.matches(dateRegex);
        return line.matches(dateRegex);
    }

    public boolean isHeader(String line){
        String headerRegex = ".*[a-zA-Z]+.*";
        boolean teste = line.matches(headerRegex);
        return line.matches(headerRegex) && !isTimestamp(line);
    }

    public boolean isBadLog(){
        int lastIndex = this.getLogSnapshots().size() - 1;
        return this.getLogSnapshots().size() != 143 || this.getLogSnapshots().get(lastIndex).getLoglines().size() < 40 || dataCorrupted;
    }

    public LogSnapshot getLastSnapshot(){
        int lastSnapshotIndex = this.getLogSnapshots().size() - 1;
        return this.getLogSnapshots().get(lastSnapshotIndex);
    }

    public ArrayList<LogSnapshot> getLogSnapshots() {
        return logSnapshots;
    }

    public void setLogSnapshots(ArrayList<LogSnapshot> logSnapshots) {
        this.logSnapshots = logSnapshots;
    }
}
