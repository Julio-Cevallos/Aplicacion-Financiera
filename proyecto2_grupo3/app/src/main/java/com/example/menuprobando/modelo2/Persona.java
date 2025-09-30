package com.example.menuprobando.modelo2;

import java.io.Serializable;

public class Persona extends Identidad implements Serializable {
  public Persona(String nombre, String telefono, String email, String identificador){
    super(nombre, telefono, email, identificador);
  }

  //metodo toString()
  @Override
  public String toString() {
    return String.format(
            "Persona%n" +
                    "Nombre: %-20s%n" +
                    "Teléfono: %-15s%n" +
                    "Email: %-25s%n" +
                    "Identificador: %-10s",
            getNombre(),
            getTelefono(),
            getEmail(),
            getIdentificador()
    );
  }


}