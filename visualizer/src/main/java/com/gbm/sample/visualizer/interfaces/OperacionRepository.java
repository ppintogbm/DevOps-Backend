package com.gbm.sample.visualizer.interfaces;

import java.sql.Timestamp;

import com.gbm.sample.visualizer.entities.Operacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacionRepository extends CrudRepository<Operacion,Timestamp>{

  Page<Operacion> findAll(Pageable pageable);
  
}