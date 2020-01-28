package com.gbm.sample.visualizer.services.impl;

import com.gbm.sample.visualizer.entities.Operacion;
import com.gbm.sample.visualizer.interfaces.OperacionRepository;
import com.gbm.sample.visualizer.services.OperacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class OperacionServiceImpl implements OperacionService{
  
  @Autowired
  private OperacionRepository operacionRepository;

  @Override
  public Page<Operacion> findAllPageable(Pageable pageable) {
    return operacionRepository.findAll(pageable);
  }

  @Override
  public void clear() {
    operacionRepository.deleteAll();
  }
}