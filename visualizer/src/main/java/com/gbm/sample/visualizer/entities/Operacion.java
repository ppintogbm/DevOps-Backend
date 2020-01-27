package com.gbm.sample.visualizer.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Table(name = "OPER01")
public class Operacion{
  
  @Id
  @Column(name = "HORA_TRANS")
  private Timestamp horaTrans;

  @Column(name = "OPERANDO1")
  private long operando1;

  @Column(name = "OPERANDO2")
  private long operando2;

  @Column(name = "OPERADOR")
  private String operador;

  public Timestamp getHoraTrans() {
    return this.horaTrans;
  }

  public void setHoraTrans(Timestamp horaTrans) {
    this.horaTrans = horaTrans;
  }

  public long getOperando1() {
    return this.operando1;
  }

  public void setOperando1(long operando1) {
    this.operando1 = operando1;
  }

  public long getOperando2() {
    return this.operando2;
  }

  public void setOperando2(long operando2) {
    this.operando2 = operando2;
  }

  public String getOperador() {
    return this.operador;
  }

  public void setOperador(String operador) {
    this.operador = operador;
  }


}