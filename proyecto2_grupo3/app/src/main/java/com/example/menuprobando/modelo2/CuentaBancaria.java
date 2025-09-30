package com.example.menuprobando.modelo2;

import java.io.Serializable;

public class CuentaBancaria implements Serializable {
  private int codigo;
  private Banco entidadBancaria;
  private int numero;
  private String tipo;
  private String fechaApertura;
  private int saldo;
  private double valorInteres;
  private String fechaCierre;
  
  

  //constructor

  public CuentaBancaria(int codigo,Banco entidadBancaria, int numero, String tipo, String fechaApertura, int saldo, double valorInteres, String fechaCierre) {
    this.codigo = codigo;
    this.entidadBancaria = entidadBancaria;
    this.numero = numero;
    this.tipo = tipo;
    this.fechaApertura = fechaApertura;
    this.saldo = saldo;
    this.valorInteres = valorInteres;
    this.fechaCierre = fechaCierre;
  }

  public CuentaBancaria(Banco entidadBancaria, int numero, String tipo, String fechaApertura,int saldo, double valorInteres, String fechaCierre) {
    this.entidadBancaria = entidadBancaria;
    this.numero = numero;
    this.tipo = tipo;
    this.fechaApertura = fechaApertura;
    this.saldo=saldo;
    this.valorInteres = valorInteres;
    this.fechaCierre = fechaCierre;
  }


  //getters setters
  

  public int getCodigo(){
    return codigo;
  }

  public void setCodigo(int codigo){
    this.codigo=codigo;
  }
  public Banco getEntidadBancaria() {
    return entidadBancaria;
  }

  public void setEntidadBancaria(Banco entidadBancaria) {
    this.entidadBancaria = entidadBancaria;
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getFechaApertura() {
    return fechaApertura;
  }

  public void setFechaApertura(String fechaApertura) {
    this.fechaApertura = fechaApertura;
  }

  public int getSaldo() {
    return saldo;
  }

  public void setSaldo(int saldo) {
    this.saldo = saldo;
  }

  public double getValorInteres() {
    return valorInteres;
  }

  public void setValorInteres(double valorInteres) {
    this.valorInteres = valorInteres;
  }

  public String getFechaCierre() {
    return fechaCierre;
  }

  public void setFechaCierre(String fechaCierre) {
    this.fechaCierre = fechaCierre;
  }


  @Override
  public String toString() {
    return "CuentaBancaria" +
            "\nCodigo:"+codigo+
            "\nEntidad Bancaria:" + entidadBancaria +
            "\nNúmero:" + numero +
            "\nTipo:" + tipo + 
            "\nFecha de Apertura:" + fechaApertura + 
            "\nsaldo:" + saldo +
            "\nvalorInteres:" + valorInteres +
            "\nfechaCierre:" + fechaCierre;
  }



}