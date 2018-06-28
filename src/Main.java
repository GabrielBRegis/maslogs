import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        final LocalDate[] lastSync = new LocalDate[1];
        String lastSyncPath = "lastSync/lastSync.txt";
        File lastSyncFile = new File(lastSyncPath);

        if(lastSyncFile.exists()){
            Files.lines(Paths.get(lastSyncFile.getPath())).forEach(line -> lastSync[0] = LocalDate.parse(line));
        }else{
            lastSync[0] = LocalDate.ofYearDay(1990,1);
        }

        LocalDate mostRecentLog = lastSync[0];
        String filesPath = "logsIn";
        File folder = new File(filesPath);
        File[] listOfFiles = folder.listFiles();


        for (File file : listOfFiles){

            String date = file.getName().replaceAll("[^-?0-9]+", "").replaceAll("-", "");
            String newDate = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6, date.length());
            LocalDate logDate = LocalDate.parse(newDate);

            if(logDate.isBefore(lastSync[0]) || logDate.isEqual(lastSync[0])){
                continue;
            }

            try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
                LogController controller = new LogController(stream);
                if(controller.isBadLog()){
                    Path outPath = Paths.get("badLogs/" + file.getName());
                    Files.copy(file.toPath(), (new File(outPath + file.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
                }else{
                    Path outPath = Paths.get("logsOut/mean/" + file.getName() + "mean");
                    try (BufferedWriter writer = Files.newBufferedWriter(outPath);) {
                        for(LogSnapshot snapshot : controller.logSnapshots){
                            writer.write(snapshot.snapShotMeanLog().returnMeanLogLineString()+"\n");
                        }
                    }
                    outPath = Paths.get("logsOut/max/" + file.getName() + "max");
                    try (BufferedWriter writer = Files.newBufferedWriter(outPath);) {
                        for(LogSnapshot snapshot : controller.logSnapshots){
                            writer.write(snapshot.snapShotMaxLog().returnMeanLogLineString()+"\n");
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            Files.deleteIfExists(file.toPath());

            if(mostRecentLog.isBefore(logDate)){
                mostRecentLog = logDate;
            }
        }

        Path syncPath = Paths.get("lastSync/lastSync.txt");
        if(lastSyncFile.exists()){
            try (BufferedWriter writer = Files.newBufferedWriter(syncPath)) {
                writer.write(mostRecentLog + "\n");
            }
        }else{
            List<String> lines = Arrays.asList("" + mostRecentLog);
            Files.write(syncPath, lines, Charset.forName("UTF-8"));
        }
    }
}
