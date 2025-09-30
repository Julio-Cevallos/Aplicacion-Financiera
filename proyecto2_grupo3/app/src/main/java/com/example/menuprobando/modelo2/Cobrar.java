package com.example.menuprobando.modelo2;

import java.io.Serializable;

public class Cobrar extends TipoDeCuenta implements Serializable {
    private static int codigoPrestamo = 1; // Variable estática para generar códigos del Menu 4
    private int codigo;
    private Persona deudor;

    // Constructor
    public Cobrar(double cantidad, String descripcion, String fechaPrestamo, int cuotasPago, String fechaInicio, String fechaFinal, Persona deudor) {
        super(cantidad, descripcion, fechaPrestamo, cuotasPago, fechaInicio, fechaFinal);
        this.codigo = codigoPrestamo++;
        this.deudor = deudor;
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public Persona getDeudor() {
        return deudor;
    }

    public void setDeudor(Persona deudor) {
        this.deudor = deudor;
    }

    // Método toString
    @Override
    public String toString() {
        return "Cobrar{" +
                "codigo=" + codigo +
                ", deudor=" + deudor +
                ", " + super.toString() +
                '}';
    }
}
