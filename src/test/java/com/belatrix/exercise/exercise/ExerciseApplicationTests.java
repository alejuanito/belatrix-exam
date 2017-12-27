package com.belatrix.exercise.exercise;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExerciseApplicationTests {

	MeJobLogger meJob;
        static Map map;
        
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
        
        @Value("${datasource.user}")
        public void setUser(String db) {
            map.put("userName", db);            
        }
        
        @Value("${datasource.password}")
        public void setPassword(String db) {
            map.put("password", db);            
        }
        
        @Value("${datasource.port}")
        public void setPort(String db) {
            map.put("portNumber", db);            
        }
        
        @BeforeClass
        public static void beforeClass(){
            map = new HashMap<>();
            
        }
        
        @Before
        public void before(){
            meJob = new MeJobLogger(true, true, true, true, true, true, map);
        }
        
        @Test(expected = Exception.class)
	public void testInvalidMessageConfigurationException() throws Exception {           
            MeJobLogger.validateConfiguration("message", false, false, false);
	}
        
        @Test(expected = Exception.class)
	public void testInvalidLogConfigurationException() throws Exception {    
            MeJobLogger.setLogToConsole(false);
            MeJobLogger.setLogToFile(false);
            MeJobLogger.setLogToDatabase(false);
            MeJobLogger.validateConfiguration("message", true, true, true);
	}
        
        @Test
	public void testValidateMessageNull() throws Exception {
            meJob = new MeJobLogger(true, true, true, true, true, true, map);           
            boolean valid = MeJobLogger.validateConfiguration("message", true, true, true);
            assertTrue(valid);
	}
        
        @Test
	public void testParameterBD() throws Exception {
              
            assertNotNull(map.get("dbms"));
            assertThat(map.get("dbms"), is(not("")));
            
            assertNotNull(map.get("serverName"));
            assertThat(map.get("serverName"), is(not("")));                       
            
	}
        
        @Test
	public void testParameterFolderPath() throws Exception {              
            assertNotNull(map.get("logFileFolder"));
            assertThat(map.get("logFileFolder"), is(not("")));                                               
	}
        
        @Test
        public void testLevelErrorLog(){
            int expected = 2;
            Assert.assertEquals(MeJobLogger.levelLog(false, false, true), expected);            
        }
        
        @Test
        public void testLevelWarningLog(){
            int expected = 3;
            Assert.assertEquals(MeJobLogger.levelLog(false, true, false), expected);            
        }
        
        @Test
        public void testLevelMessageLog(){
            int expected = 1;
            Assert.assertEquals(MeJobLogger.levelLog(true, false, false), expected);            
        }
        
        @Test
        public void testWriteToLog() throws Exception{
            MeJobLogger.setLogToConsole(true);
            assertTrue(MeJobLogger.writeLogConsole(""));
            
            MeJobLogger.setLogToFile(true);
            assertTrue(MeJobLogger.writeLogFile(""));
            
            MeJobLogger.setLogToDatabase(true);
            assertTrue(MeJobLogger.writeLogBD(true, true, true));
                     
        }
        
        

}
