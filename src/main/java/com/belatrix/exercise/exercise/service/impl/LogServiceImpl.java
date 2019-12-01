/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belatrix.exercise.exercise.service.impl;

import com.belatrix.exercise.exercise.model.LogEntity;
import com.belatrix.exercise.exercise.model.LogRequest;
import com.belatrix.exercise.exercise.repository.LogRepository;
import com.belatrix.exercise.exercise.service.LogService;
import com.belatrix.exercise.exercise.util.LevelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alejandro
 */
@Service
public class LogServiceImpl implements LogService {

    private static Logger logger = Logger.getLogger("MiLog");;

    @Autowired
    private LogRepository logRepository;

    @Override
    public Boolean registerLog(LogRequest request) throws Exception{
        validateLog(request);

        if(request.getToConsole() != null && request.getToConsole()){
            registerLogToConsole(request);
        }
        if(request.getToDatabase() != null && request.getToDatabase()){
            registerLogToDataBase(request);
        }
        if(request.getToFile() != null && request.getToFile()){
            registerLogToFile(request);
        }

        return true;
    }

    public Boolean validateLog(LogRequest request) throws Exception{
        if((request.getToFile() == null || !request.getToFile()) &&
            (request.getToDatabase() == null || !request.getToDatabase()) &&
                (request.getToConsole() == null || !request.getToConsole())){
            throw new Exception("Invalid configuration");
        }
        if(!LevelEnum.contains(request.getLevel())){
            throw new Exception("Error or Warning or Message must be specified");
        }
        if(request.getMessage() == null || request.getMessage().isEmpty()){
            throw new Exception("Message must be specified");
        }
        return true;
    }

    private Boolean registerLogToDataBase(LogRequest request) {
        logRepository.save(LogEntity.builder()
                .createDate(new Date())
                .level(request.getLevel())
                .message(request.getMessage())
                .id(UUID.randomUUID())
                .build());
        return true;
    }


    private Boolean registerLogToFile(LogRequest request) throws Exception{
        String path = "src/resources";
        File logFile = new File(path);
        if (!logFile.exists()) {
            logFile.createNewFile();
        }
        FileHandler fh = new FileHandler(path);
        logger.addHandler(fh);
        logger.log(LevelEnum.getLevel(request.getLevel()), request.getMessage());
        return true;
    }


    private Boolean registerLogToConsole(LogRequest request) throws Exception{
        ConsoleHandler ch = new ConsoleHandler();
        logger.addHandler(ch);
        logger.log(LevelEnum.getLevel(request.getLevel()), request.getMessage());
        return true;
    }
}
