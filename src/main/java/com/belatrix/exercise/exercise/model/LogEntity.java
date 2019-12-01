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

@Entity
@Table(name = "log")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogEntity {

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "message")
  private String message;

  @Column(name = "level")
  private String level;

  @Column(name = "create_date")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createDate;


}
