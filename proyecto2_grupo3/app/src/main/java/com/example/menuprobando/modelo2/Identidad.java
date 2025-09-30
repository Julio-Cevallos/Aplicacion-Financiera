package com.example.menuprobando.modelo2;

import java.io.Serializable;

public class Identidad implements Serializable {
  private String nombre;
  private String telefono;
  private String email;
  private String identificador;

  //constructor

  public Identidad(String nombre, String telefono, String email, String identificador) {
    this.nombre = nombre;
    this.telefono = telefono;
    this.email = email;
    this.identificador = identificador;
  }
  public Identidad(String identificador){
    this.identificador=identificador;
  }



  //getters  y setters

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTeléfono(String telefono) {
    this.telefono = telefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getIdentificador() {
    return identificador;
  }

  public void setIdentificador(String identificador) {
    this.identificador = identificador;
  }

  //metodo toString


  @Override
  public String toString() {
    return String.format(
            "Persona%n" +
                    "Nombre: %-20s%n" +
                    "Teléfono: %-15s%n" +
                    "Email: %-30s%n" +
                    "Identificador: %-15s",
            nombre != null ? nombre : "No disponible",
            telefono != null ? telefono : "No disponible",
            email != null ? email : "No disponible",
            identificador != null ? identificador : "No disponible"
    );
  }



}