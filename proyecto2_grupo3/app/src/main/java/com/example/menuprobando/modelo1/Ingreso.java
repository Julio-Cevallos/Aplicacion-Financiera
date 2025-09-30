package com.example.menuprobando.modelo1;

import java.io.Serializable;
import java.util.Scanner;

public class Ingreso extends TipoDeCategoria implements Serializable {


  public Ingreso(int codigo, String fechaInicio, Categoria categoria, int valor, String fechaFin, String repeticion, String descripcion) {
    super(codigo, fechaInicio, categoria, valor, fechaFin, repeticion, descripcion);
  }


  @Override
  public String toString() {
    return "Categoria: " + getCategoria()+"\n Descripcion: "+getDescripcion()  + ", Valor: " + getValor();
  }


  public boolean finalizarIngreso(String fechaFin) {
    // Verificar que la fecha de finalización sea posterior a la fecha de inicio
    if (fechaFin.compareTo(this.getFechaInicio()) <= 0) {
      return false; // La fecha de finalización no es válida
    }

    // Si la fecha es válida, actualizar la fecha de fin
    this.setFechaFin(fechaFin);
    return true; // La fecha de finalización se ha establecido correctamente
  }

}

