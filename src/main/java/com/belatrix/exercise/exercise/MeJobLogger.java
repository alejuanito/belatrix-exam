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
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    public static void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {                
        if(!validateConfiguration(messageText, message, warning, error)){
            return;
        }        
        
        writeLogConsole(messageText);
        writeLogFile(messageText);
        writeLogBD(message, warning, error);
    }
    
    
    /**
     * validate the correct configuration
     * @param messageText
     * @param message 
     * @param warning
     * @param error
     * @return
     * @throws Exception 
     */
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
    
    /**
     * connect to database
     * @throws Exception 
     */
    public static void connectDB() throws Exception{
        connection = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbParams.get("userName"));
        connectionProps.put("password", dbParams.get("password"));        
        connection = DriverManager.getConnection("jdbc:"+dbParams.get("dbms")+":mem:"+dbParams.get("serverName"), connectionProps);        
    }
    
    /**
     * disconnect to database
     * @throws Exception 
     */
    public static void disConnectDB() throws Exception{
        connection.close();          
    }
    
    /**
     * identify the level log
     * @param message
     * @param warning
     * @param error
     * @return 
     */
    public static int levelLog(boolean message, boolean warning, boolean error){
        if (error && logError) {
            return 2;
        }else if(warning && logWarning){
            return 3;
        }else if(message && logMessage){
            return 1;
        }else{
            return 0;
        }
    } 
    
    /**
     * write log in the console
     * @param messageText
     * @return boolean
     * @throws Exception 
     */
    public static boolean writeLogConsole(String messageText) throws Exception{    
        messageText = messageText.trim(); 
        if(!logToConsole){
            return logToConsole;
        }
        ConsoleHandler ch = new ConsoleHandler();
        logger.addHandler(ch);
        logger.log(Level.INFO, messageText);    
        return logToConsole;
    }
    
    /**
     * write log in the database
     * @param message
     * @param warning
     * @param error
     * @throws Exception 
     */
    public static boolean writeLogBD(boolean message, boolean warning, boolean error) throws Exception{
        if(!logToDatabase){
            return logToDatabase;
        }
        connectDB();    
        Statement stmt = connection.createStatement();
        int t = levelLog(message,  warning,  error);
        stmt.executeUpdate("CREATE TABLE Log_Values(id boolean , name varchar(255))");
        stmt.executeUpdate("insert into Log_Values (id, name) values ('" + message + "', " + String.valueOf(t)
                + ")");
        ResultSet rs = stmt.executeQuery("select * from Log_Values ");
        while (rs.next()) {
            System.out.println("DATA: "+ rs.getString("id") + "|" + rs.getString("name") );
        }
        disConnectDB();
        return logToDatabase;
    }
    
    /**
     * write log in the file
     * @param messageText
     * @throws Exception 
     */
    public static boolean writeLogFile(String messageText) throws Exception{
        messageText = messageText.trim(); 
        if(!logToFile){
            return logToFile;
        }
        File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
        logger.addHandler(fh);
        logger.log(Level.INFO, messageText);
        return logToFile;
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
