package com.example.menuprobando.modelo1;

import java.io.Serializable;

public class Categoria implements Serializable {
  private String nombreCategoria;
  private String tipoCategoria;
  private static final long serialVersionUID = 1L;


  //Constructor

  public Categoria(String nombreCategoria, String tipoCategoria) {
    this.nombreCategoria = nombreCategoria;
    this.tipoCategoria = tipoCategoria;
  }

  //getters y setters

  public String getNombreCategoria() {
    return nombreCategoria;
  }

  public void setNombreCategoria(String nombreCategoria) {
    this.nombreCategoria = nombreCategoria;
  }

  public String getTipoCategoria() {
    return tipoCategoria;
  }

  public void setTipoCategoria(String tipoCategoria) {
    this.tipoCategoria = tipoCategoria;
  }

  //metodo to string

  @Override
  public String toString() {
    return nombreCategoria ;
  }
}