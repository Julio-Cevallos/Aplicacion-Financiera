package com.example.menuprobando.modelo2;
public class Inversion {
  private Banco entidadBancaria;
  private String fechaApertura;
  private int cantidad;
  private double valorInteres;
  private String fechaCierre;
  private int codigo;

  // constructor
  public Inversion(Banco entidadBancaria, String fechaApertura, int cantidad, double valorInteres, String fechaCierre, int codigo) {
    this.entidadBancaria = entidadBancaria;
    this.fechaApertura = fechaApertura;
    this.cantidad = cantidad;
    this.valorInteres = valorInteres;
    this.fechaCierre = fechaCierre;
    this.codigo = codigo;
  }

  //getters y setters

  public Banco getEntidadBancaria() {
    return entidadBancaria;
  }

  public void setEntidadBancaria(Banco entidadBancaria) {
    this.entidadBancaria = entidadBancaria;
  }

  public String getFechaApertura() {
    return fechaApertura;
  }

  public void setFechaApertura(String fechaApertura) {
    this.fechaApertura = fechaApertura;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
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

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }


  //metodo toString

  @Override
  public String toString() {
    return "Inversion {" +
            "entidadBancaria=" + entidadBancaria +
            ", fechaApertura=" + fechaApertura + '\'' +
            ", cantidad=" + cantidad +
            ", valorInteres=" + valorInteres +
            ", fechaCierre=" + fechaCierre + '\'' +
            ", codigo=" + codigo +
            '}';
  }

}