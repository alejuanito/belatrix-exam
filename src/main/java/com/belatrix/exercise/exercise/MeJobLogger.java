/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belatrix.exercise.exercise;

/**
 *
 * @author Alejandro
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
public class MeJobLogger {

    private static boolean logToFile;
    private static boolean logToConsole;
    private static boolean logMessage;
    private static boolean logWarning;
    private static boolean logError;
    private static boolean logToDatabase;
    private static Map dbParams;
    private static  Logger logger;    
    private static Connection connection;
    
    

    public MeJobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
            boolean logMessageParam, boolean logWarningParam, boolean logErrorParam,
            Map dbParamsMap) {
        logger = Logger.getLogger("MyLog");
        logError = logErrorParam;
        logMessage = logMessageParam;
        logWarning = logWarningParam;
        logToDatabase = logToDatabaseParam;
        logToFile = logToFileParam;
        logToConsole = logToConsoleParam;
        dbParams = dbParamsMap;
    }

    public static boolean validateConfiguration(String messageText, boolean message, boolean warning, boolean error) throws Exception{
        
        if (messageText == null || messageText.length() == 0) {
            return false;
        }
        
        if (!logToConsole && !logToFile && !logToDatabase) {
            throw new Exception("Invalid configuration");
        }
        if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
            throw new Exception("Error or Warning or Message must be specified");
        }
        return true;
    }
    
    public static void connectDB() throws Exception{
        connection = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbParams.get("userName"));
        connectionProps.put("password", dbParams.get("password"));
        //jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
        
        connection = DriverManager.getConnection("jdbc:"+dbParams.get("dbms")+":mem:"+dbParams.get("serverName"), connectionProps);
        
    }
    
    public static void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
        
        
        if(!validateConfiguration(messageText, message, warning, error)){
            return;
        }        
        messageText = messageText.trim();
        connectDB();
        
        int t = 0;
        if (message && logMessage) {
            t = 1;
        }
        if (error && logError) {
            t = 2;
        }
        if (warning && logWarning) {
            t = 3;
        }
        Statement stmt = connection.createStatement();
        String l = null;
        File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
        ConsoleHandler ch = new ConsoleHandler();
        if (error && logError) {
            l = l + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date())
                    + messageText;
        }
        if (warning && logWarning) {
            l = l + "warning " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
        }
        if (message && logMessage) {
            l = l + "message " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
        }
        if (logToFile) {
            logger.addHandler(fh);
            logger.log(Level.INFO, messageText);
        }
        if (logToConsole) {
            logger.addHandler(ch);
            logger.log(Level.INFO, messageText);
        }
        if (logToDatabase) {
            
            //String CreateQuery = "CREATE TABLE Log_Values(id int primary key, name varchar(255))";
            stmt.executeUpdate("CREATE TABLE Log_Values(id boolean , name varchar(255))");
            stmt.executeUpdate("insert into Log_Values (id, name) values ('" + message + "', " + String.valueOf(t)
                    + ")");
            ResultSet rs = stmt.executeQuery("select * from Log_Values ");
            while (rs.next()) {
                System.out.println("DATA: "+ rs.getString("id") + "|" + rs.getString("name") );
            }
            connection.close();
        }
    }

    
    
    public static boolean isLogToFile() {
        return logToFile;
    }

    public static void setLogToFile(boolean logToFile) {
        MeJobLogger.logToFile = logToFile;
    }

    public static boolean isLogToConsole() {
        return logToConsole;
    }

    public static void setLogToConsole(boolean logToConsole) {
        MeJobLogger.logToConsole = logToConsole;
    }

    public static boolean isLogMessage() {
        return logMessage;
    }

    public static void setLogMessage(boolean logMessage) {
        MeJobLogger.logMessage = logMessage;
    }

    public static boolean isLogWarning() {
        return logWarning;
    }

    public static void setLogWarning(boolean logWarning) {
        MeJobLogger.logWarning = logWarning;
    }

    public static boolean isLogError() {
        return logError;
    }

    public static void setLogError(boolean logError) {
        MeJobLogger.logError = logError;
    }

    public static boolean isLogToDatabase() {
        return logToDatabase;
    }

    public static void setLogToDatabase(boolean logToDatabase) {
        MeJobLogger.logToDatabase = logToDatabase;
    }

    public static Map getDbParams() {
        return dbParams;
    }

    public static void setDbParams(Map dbParams) {
        MeJobLogger.dbParams = dbParams;
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        MeJobLogger.logger = logger;
    }
    
    
}
