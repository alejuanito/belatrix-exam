package com.belatrix.exercise.exercise;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExerciseApplication {
    
        static Map map = new HashMap<>();
        
        @Value("${datasource.dbms}")
        public void setDbms(String db) {
            map.put("dbms", db);            
        }

        @Value("${datasource.serverName}")
        public void setServerName(String db) {
            map.put("serverName", db);            
        }

        @Value("${file.path}")
        public void setFilePath(String db) {
            map.put("logFileFolder", db);

        }
        
	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
            try {
                map.put("userName", "user");
                map.put("password", "pass");
                map.put("portNumber", "port");
                MeJobLogger job = new MeJobLogger(true, true, true, true, true, true, map);
                MeJobLogger.LogMessage("aa", true, true, true);
                MeJobLogger.LogMessage("bb", true, true, true);
            } catch (Exception ex) {
                Logger.getLogger(ExerciseApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}
