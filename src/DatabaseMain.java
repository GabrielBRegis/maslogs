import javax.xml.crypto.Data;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


public class DatabaseMain {

    public static void insertLine(String line, Statement statement) throws SQLException {
        List<String> logLineStrings = Arrays.asList(line.split("\\s+"));
        UUID uuid = UUID.randomUUID();
        String sql = "insert into logDataMean values(" + "'" + uuid.toString() + "'" + logLineToSqlValues(logLineStrings) +")";
        try{
            statement.executeUpdate(sql);
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public static void insertLineMax(String line, Statement statement) throws SQLException {
        List<String> logLineStrings = Arrays.asList(line.split("\\s+"));
        UUID uuid = UUID.randomUUID();
        String sql = "insert into logDataMax values(" + "'" + uuid.toString() + "'" + logLineToSqlValues(logLineStrings) +")";
        try{
            statement.executeUpdate(sql);
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public static String logLineToSqlValues(List<String> lines){
        String sqlValues = "";

        for(String line : lines){

            if(line.matches(".*\\d+.*")){
                Double value = Double.parseDouble(line);
                sqlValues +=", " + value;
            }else{
                sqlValues +=", '" + line + "'";
            }

        }

        return sqlValues;

    }

    public static LocalDate createData(Statement statement, boolean isMean) throws SQLException {
        LocalDate lastSync = LocalDate.parse("1990-01-01");

        ResultSet rs = statement.executeQuery("select * from lastSync");
        String dateString = isMean ? "dateMean" : "dateMax";
        while(rs.next())
        {
            lastSync = LocalDate.parse(rs.getString(dateString));
        }

        LocalDate mostRecentLog = lastSync;
        String filesPath = "logsOut";
        filesPath = isMean ? filesPath + "/mean" : filesPath + "/max";
        File folder = new File(filesPath);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles){

            String date = file.getName().replaceAll("[^-?0-9]+", "").replaceAll("-", "");
            String newDate = date.substring(0,4) + "-" + date.substring(4,6) + "-" + date.substring(6, date.length());
            LocalDate logDate = LocalDate.parse(newDate);

            if(logDate.isBefore(lastSync) || logDate.isEqual(lastSync)){
                continue;
            }

            try (Stream<String> stream = Files.lines(Paths.get(file.getPath()))) {
                stream.forEach(line -> {
                    try {
                        if(isMean){
                            DatabaseMain.insertLine(line, statement);
                        }else{
                            DatabaseMain.insertLineMax(line, statement);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(mostRecentLog.isBefore(logDate)){
                mostRecentLog = logDate;
            }
        }
        return mostRecentLog;
    }

    public static void main(String[] args) throws IOException {
        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:sqlite:logs.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("create table if not exists lastSync (id int, dateMean string, dateMax string)");
            statement.executeUpdate("create table if not exists logDataMean (id string, serverName string, logYear int, logMonth int, logDay int" +
                    ", logMinute int, r double, b double, swpd double, free double, buff double, cache double, si double, so double, bi double, bo double, inVal double, cs double," +
                    "us double, sy double, idVal double, wa double, st double)");
            statement.executeUpdate("create table if not exists logDataMax (id string, serverName string, logYear int, logMonth int, logDay int" +
                    ", logMinute int, r double, b double, swpd double, free double, buff double, cache double, si double, so double, bi double, bo double, inVal double, cs double," +
                    "us double, sy double, idVal double, wa double, st double)");

            LocalDate lastSyncMean = DatabaseMain.createData(statement, true);
            LocalDate lastSyncMax = DatabaseMain.createData(statement, false);

            statement.executeUpdate("update lastSync set dateMean = " + "'" + lastSyncMean.toString() + "'" + ", dateMax = " + "'" + lastSyncMax.toString() + "'");


        }catch (SQLException e){
            System.err.println(e.getMessage());
        }finally {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                System.err.println(e);
            }
        }
    }
}

//statement.executeUpdate("create table logData (id string, serverName string, logYear int, logMonth int, logDay int" +
//        ", logMinute int, r double, b double, swpd double, free double, buff double, cache double, si double, so double, bi double, bo double, inVal double, cs double," +
//        "us double, sy double, idVal double, wa double, st double)");
