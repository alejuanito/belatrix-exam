package com.belatrix.exercise.exercise.repository;

import com.belatrix.exercise.exercise.model.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;


@Repository
public interface LogRepository extends JpaRepository<LogEntity, UUID> {


}
