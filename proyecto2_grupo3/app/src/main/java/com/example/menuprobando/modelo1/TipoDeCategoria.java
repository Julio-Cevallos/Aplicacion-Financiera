package com.example.menuprobando.modelo1;

import java.io.Serializable;

public class TipoDeCategoria implements Serializable {
  private int codigo;
  private String fechaInicio;
  private Categoria categoria;
  private int valor;
  private String fechaFin;
  private String repeticion;
  private String descripcion;

  //constructor
  public TipoDeCategoria(int codigo, String fechaInicio, Categoria categoria, int valor, String fechaFin, String repeticion,String descripcion) {
    this.codigo = codigo;
    this.fechaInicio = fechaInicio;
    this.categoria = categoria;
    this.valor = valor;
    this.fechaFin = fechaFin;
    this.repeticion = repeticion;
    this.descripcion=descripcion;
  }



  //creacion de getters y setter

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public int getCodigo() {
    return codigo;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  public String getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(String fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }

  public int getValor() {
    return valor;
  }

  public void setValor(int valor) {
    this.valor = valor;
  }

  public String getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(String fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getRepeticion() {
    return repeticion;
  }

  public void setRepeticion(String repeticion) {
    this.repeticion = repeticion;
  }

  //metodo to String
  @Override
  public String toString() {
    return String.format("Código: %-8s | Fecha inicio: %-12s | Categoría: %-20s | Valor Neto: %-10d | Descripción: %-20s | Fecha fin: %-12s | Repetición: %-10s",
            codigo, fechaInicio, categoria.getNombreCategoria(), valor, descripcion, fechaFin, repeticion);
  }



}