package com.belatrix.exercise.exercise;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import com.belatrix.exercise.exercise.model.LogRequest;
import com.belatrix.exercise.exercise.service.LogService;
import com.belatrix.exercise.exercise.service.impl.LogServiceImpl;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExerciseApplicationTests {

    @Autowired
    LogService logService;
    @Autowired
    LogServiceImpl logServiceImpl;
    LogRequest logRequest;

        
    @Before
    public void before(){

    }
        
    @Test(expected = Exception.class)
	public void testInvalidMessageConfigurationException() throws Exception {
        logService.registerLog(LogRequest.builder().level("INFO").message("").build());
	}
        
    @Test(expected = Exception.class)
	public void testInvalidLogConfigurationException() throws Exception {
        logService.registerLog(LogRequest.builder().level("ERRORCITO").message("ERROR MUY GRAVE").build());
	}
        
    @Test
	public void testValidateLog() throws Exception {
        boolean valid =  logServiceImpl.validateLog(LogRequest.builder().level("INFO").message("Ocurrio un error").toDatabase(true).build());
        assertTrue(valid);
	}
        
    @Test
    public void testWriteToLog() throws Exception{
        LogRequest logRequest = LogRequest.builder().level("ERROR").message("ERROR GRAVE").toConsole(true).toDatabase(true).toFile(true).build();
        boolean valid = logService.registerLog(logRequest);
        assertTrue(valid);
    }

    @Test
    public void testWriteToDataBase() throws Exception{
        LogRequest logRequest = LogRequest.builder().level("ERROR").message("ERROR GRAVE").toDatabase(true).build();
        boolean valid = logService.registerLog(logRequest);
        assertTrue(valid);
    }

    @Test
    public void testWriteToFile() throws Exception{
        LogRequest logRequest = LogRequest.builder().level("ERROR").message("ERROR GRAVE").toFile(true).build();
        boolean valid = logService.registerLog(logRequest);
        assertTrue(valid);
    }

    @Test
    public void testWriteToConsole() throws Exception{
        logRequest = LogRequest.builder().level("ERROR").message("ERROR GRAVE").toConsole(true).build();
        boolean valid = logService.registerLog(logRequest);
        assertTrue(valid);
    }


        
        

}
