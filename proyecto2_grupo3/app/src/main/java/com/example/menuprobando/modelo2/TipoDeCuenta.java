package com.example.menuprobando.modelo2;

import java.io.Serializable;

public class TipoDeCuenta  implements Serializable {
  private double cantidad;
  private String descripcion;
  private String fechaPrestamo;
  private int cuotasPago;
  private String fechaInicio;
  private String fechaFinal;
  

  //constructor

  public TipoDeCuenta(double cantidad, String descripcion, String fechaPrestamo, int cuotasPago, String fechaInicio, String fechaFinal) {
    this.cantidad = cantidad;
    this.descripcion = descripcion;
    this.fechaPrestamo = fechaPrestamo;
    this.cuotasPago = cuotasPago;
    this.fechaInicio = fechaInicio;
    this.fechaFinal = fechaFinal;
    
  }



  //getters y setters

  public double getCantidad() {
    return cantidad;
  }

  public void setCantidad(double cantidad) {
    this.cantidad = cantidad;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getFechaPrestamo() {
    return fechaPrestamo;
  }

  public void setFechaPrestamo(String fechaPrestamo) {
    this.fechaPrestamo = fechaPrestamo;
  }

  public int getCuotasPago() {
    return cuotasPago;
  }

  public void setCuotasPago(int cuotasPago) {
    this.cuotasPago = cuotasPago;
  }

  public String getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(String fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public String getFechaFinal() {
    return fechaFinal;
  }

  public void setFechaFinal(String fechaFinal) {
    this.fechaFinal = fechaFinal;
  }

  


  //metodo to string

  @Override
  public String toString() {
    return "Tipo De Cuenta" +
            "\nCantidad=" + cantidad +
            "\nDescripcion=" + descripcion +
            "\nFecha de Prestamo=" + fechaPrestamo +
            "\nCuotas de Pago=" + cuotasPago +
            "\nFecha de Inicio=" + fechaInicio +
            "\nFechaFinal=" + fechaFinal;
  }
}