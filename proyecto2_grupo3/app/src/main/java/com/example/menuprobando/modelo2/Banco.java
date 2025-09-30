package com.example.menuprobando.modelo2;

import java.io.Serializable;

public class Banco extends Identidad implements Serializable {
  private String oficialCredito;
  //constructor 
  public Banco(String nombre, String telefono, String email, String identificador, String oficialCredito){
    super(nombre, telefono, email, identificador);
    this.oficialCredito=oficialCredito;
  }

  public Banco(String identificador){
    super(identificador);
  }

  //getter y setter

  public String getOficialCredito() {
    return oficialCredito;
  }

  public void setOficialCredito(String oficialCredito) {
    this.oficialCredito = oficialCredito;
  }

  @Override
  public String toString() {
    return String.format(
            "%nNombre: %-20s%n" +
                    "Teléfono: %-15s%n" +
                    "Email: %-30s%n" +
                    "Identificador: %-20s%n" +
                    "Oficial de Crédito: %-20s",
            getNombre() != null ? getNombre() : "No disponible",
            getTelefono() != null ? getTelefono() : "No disponible",
            getEmail() != null ? getEmail() : "No disponible",
            getIdentificador() != null ? getIdentificador() : "No disponible",
            oficialCredito != null ? oficialCredito : "No disponible"
    );
  }
//tipodecategoria ahi pero ya no importa
}