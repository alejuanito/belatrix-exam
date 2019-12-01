/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.belatrix.exercise.exercise.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * @author Alejandro
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogRequest {

  private String message;

  private String level;

  private Boolean toDatabase;

  private Boolean toFile;

  private Boolean toConsole;


}
