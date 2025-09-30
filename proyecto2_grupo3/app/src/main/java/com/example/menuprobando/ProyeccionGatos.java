package com.example.menuprobando;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo1.Gasto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class ProyeccionGatos extends AppCompatActivity {

    private TextView mes1;
    private TextView mes2;
    private TextView mes3;
    private TextView mes4;
    private TextView proyeccion;
    private ArrayList<Gasto> gastos = new ArrayList<>();
    private ArrayAdapter<Gasto> adapterGastosM1;
    private ArrayAdapter<Gasto> adapterGastosM2;
    private ArrayAdapter<Gasto> adapterGastosM3;
    private ListView mesLista1;
    private ListView mesLista2;
    private ListView mesLista3;
    public  void crearValores() {
        CategoriaManager categoriaManager = new CategoriaManager(this);
        gastos = categoriaManager.obtenerGastos();






    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proyeccion_gastos);
        mes1 = findViewById(R.id.mes1);
        mes2 = findViewById(R.id.mes2);
        mes3 = findViewById(R.id.mes3);
        mes4 = findViewById(R.id.mes4);
        proyeccion = findViewById(R.id.proyeccion);
        crearValores();

        mesLista1 = findViewById(R.id.mesLista1);
        mesLista2 = findViewById(R.id.mesLista2);
        mesLista3 = findViewById(R.id.mesLista3);
        Button buttonProyeccion = findViewById(R.id.buttonProyeccion);

        buttonProyeccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proyectarGastosSiguienteMes();



            }
        });

    }

    private int ReporteGastosPorMesActual(int o) {
        LocalDate fechaActual1 = LocalDate.now();
        LocalDate fechaActual= fechaActual1.minusMonths(o).withDayOfMonth(1);
        int mesActual = fechaActual.getMonthValue();
        int totalGastosMes = 0;
        ArrayList<Gasto> gastoMes = new ArrayList<>();



        // Filtrar ingresos por el mes actual
        for (Gasto gasto : gastos) {
            LocalDate fechaInicio = LocalDate.parse(gasto.getFechaInicio(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int mesGasto = fechaInicio.getMonthValue();
            int mese = 0;
            if(gasto.getRepeticion().equalsIgnoreCase("mensual")){
                LocalDate fechaFinal = LocalDate.parse(gasto.getFechaFin(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int yearD = fechaFinal.getYear() - fechaInicio.getYear();
                int mesD = fechaFinal.getMonthValue() - fechaInicio.getMonthValue();
                mese = yearD*12+mesD;
                if(mese>=12){
                    mese=12-mesGasto;
                }

            }else{
                mese=0;
            }
            for(int i= 0;i<mese;i++){

                if (mesGasto+i == mesActual) {

                    totalGastosMes += gasto.getValor();
                    gastoMes.add(gasto);
                }
            }


        }

        System.out.println("\nTotal de Ingresos para el Mes: " + totalGastosMes);
        if(o == 2){
            mes1.setText(obtenerNombreMes(mesActual));
            adapterGastosM1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastoMes);
            mesLista1.setAdapter(adapterGastosM1);
        }
        if(o == 1){
            mes2.setText(obtenerNombreMes(mesActual));
            adapterGastosM2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastoMes);
            mesLista2.setAdapter(adapterGastosM2);
        }
        if(o == 0){
            mes3.setText(obtenerNombreMes(mesActual));
            adapterGastosM3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastoMes);
            mesLista3.setAdapter(adapterGastosM3);
        }

        return totalGastosMes;
    }
    private String obtenerNombreMes(int numeroMes) {
        return LocalDate.of(1, numeroMes, 1).getMonth().toString();
    }
    private void proyectarGastosSiguienteMes() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaInicio = fechaActual.minusMonths(3).withDayOfMonth(1);
        LocalDate fechaFin = fechaActual.minusMonths(1).withDayOfMonth(fechaActual.minusMonths(1).lengthOfMonth());

        int x =ReporteGastosPorMesActual(0);
        int y =ReporteGastosPorMesActual(1);
        int z =ReporteGastosPorMesActual(2);
        int  a= x+y+z;
        double promedioGastos=a/3.0;
        // Imprimir la proyección de gastos para el siguiente mes
        LocalDate siguienteMes = fechaActual.withDayOfMonth(1).plusMonths(1);
        System.out.println("\nProyección de Gastos para el Mes de " + obtenerNombreMes(siguienteMes.getMonthValue())
                + " " + siguienteMes.getYear());
        System.out.println("Promedio de gastos de los últimos tres meses: " + promedioGastos);
        mes4.setText(obtenerNombreMes(siguienteMes.getMonthValue()));

        proyeccion.setText("\nLa proyeccion de ingresos para el proximo mes es: "+ promedioGastos);
    }
}
