/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belatrix.exercise.exercise.service;

import com.belatrix.exercise.exercise.model.LogRequest;

/**
 *
 * @author Alejandro
 */
public interface LogService {

    Boolean registerLog(LogRequest request) throws Exception;

}
