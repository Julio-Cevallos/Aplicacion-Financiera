package com.example.menuprobando.modelo1;
import java.util.Scanner;


  public class Gasto extends TipoDeCategoria{

    public Gasto(int codigo, String fechaInicio, Categoria categoria, int valor, String fechaFin, String repeticion,String descripcion){
      super(codigo,fechaInicio,categoria,valor,fechaFin,repeticion,descripcion);
    }

    public String toString() {
      return "Categoria: " + getCategoria()+"\n Descripcion: "+getDescripcion()  + ", Valor: " + getValor();
    }
    public void finalizarGasto() {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Ingrese la fecha de finalización (dd-mm-yyyy): ");
      String fechaFin = scanner.nextLine();

      if (fechaFin.compareTo(this.getFechaInicio()) <= 0) {
        System.out.println("Error: La fecha de finalización debe ser mayor que la fecha de inicio.");
        return;
      }

      this.setFechaFin(fechaFin);
      System.out.println("Gasto finalizado correctamente.");
    }
}