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
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLogger {

    private static boolean logToFile;
    private static boolean logToConsole;
    private static boolean logMessage;
    private static boolean logWarning;
    private static boolean logError;
    private static boolean logToDatabase;
    private boolean initialized; //Esta variable nunca es usada y deberia retirarse
    private static Map dbParams;
    private static  Logger logger;

    public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
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
        
        messageText.trim(); /**messageText puede ser nulo y generará un nullpointer que será capturado por el Exception pero
         * seria bueno poder capturar el error y poder mandar un mensaje de validación
         * 
         */
        if (messageText == null || messageText.length() == 0) {
            
            return;
        }
        if (!logToConsole && !logToFile && !logToDatabase) {
            throw new Exception("Invalid configuration");
        }
        if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
            throw new Exception("Error or Warning or Message must be specified");
        }
        /**La conexión a la bd esta en el mismo metodo
         * Los parámetros de conexión estan harcodeados
         */
        Connection connection = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbParams.get("userName"));
        connectionProps.put("password", dbParams.get("password"));
        /**
         * Se esta creando la conexión sin tener la certeza de que se va a grabar en la bd
         * Se configuró la conexión a una bd H2 en memoria
         */
        connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://"
                + dbParams.get("serverName")
                + ":" + dbParams.get("portNumber") + "/", connectionProps);
        int t = 0;
        /**
         * en este bloque de código existen muchos ifs y el nivel del log es excluyente quiere decir que 
         * solo deberia ser error, warning o message y en ese orden
         */
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
        /**
         * Se instancia file sin tener la certeza de que el parámetro logFile sea true
         */
        File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");
        /**
         * Se instancia ConsoleHandler sn tener la certeza de que el parámetro logError sea true
         */
        ConsoleHandler ch = new ConsoleHandler();
        
        /**
         * En este bloque de código existen muchos ifs ademas la variable l nunca es utilizada
         */
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
        /**
         * En este bloque recien se verifica las formas donde se registrá el log ademas de tener muchos ifs y hacer
         * el código no tan limpio
         * La lógica de los tipos de log estan desordenadas
         */
        if (logToFile) {
            logger.addHandler(fh);
            logger.log(Level.INFO, messageText);
        }
        if (logToConsole) {
            logger.addHandler(ch);
            logger.log(Level.INFO, messageText);
        }
        if (logToDatabase) {
            stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(t)
                    + ")");
            /**
             * La sintaxis de inserción es incorrecta
             * finalizada la inserción no se cierra la conexión,
             * 
             */
        }
    }
}
