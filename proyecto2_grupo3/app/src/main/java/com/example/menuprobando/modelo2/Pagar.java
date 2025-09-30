package com.example.menuprobando.modelo2;

import java.io.Serializable;

public class Pagar extends TipoDeCuenta implements Serializable {
  private Identidad acreedor;
  private int interes;
  private static int codigoPago;
  private int codigo;

  //constructor
  public Pagar(double cantidad, String descripcion, String fechaPrestamo, int cuotasPago, String fechaInicio, String fechaFinal, Identidad acreedor, int interes){
    super(cantidad, descripcion, fechaPrestamo, cuotasPago, fechaInicio, fechaFinal);
    this.acreedor=acreedor;
    this.interes=interes;
    this.codigo=codigoPago++;
  }



  //getters y setters
  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public Identidad getAcreedor() {
    return acreedor;
  }

  public void setAcreedor(Identidad acreedor) {
    this.acreedor = acreedor;
  }

  public int getInteres() {
    return interes;
  }

  public void setInteres(int interes) {
    this.interes = interes;
  }

  //metodo toString()
  public String toSimpleString() {
    String nombreAcreedor = acreedor.getNombre();  // Obtener nombre del acreedor
    return nombreAcreedor + " - " + getDescripcion();
  }

  @Override
  public String toString() {
    return "Tipo De Cueta a Pagar" +
            "\nCantidad='" + getCantidad() + 
            "\nDescripcion='" + getDescripcion() + 
            "\nFecha de Prestamo='" + getFechaPrestamo() +
            "\nCuotas de Pago=" + getCuotasPago() +
            "\nFecha de Inicio='" + getFechaInicio() + 
            "\nFechaFinal='" + getFechaFinal()+
            "\nAcreedor="+ acreedor +
            "\nInteres=" + interes +
            "\nCodigo De Pago=" + codigoPago +
            "\nCodigo" + codigo;
  }
}