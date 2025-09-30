package com.example.menuprobando;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo1.Categoria;
import com.example.menuprobando.modelo1.Gasto;
import com.example.menuprobando.modelo1.Ingreso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Reportes extends AppCompatActivity {
    private TextView textoSinContexto;
    private ListView listViewTodito;
    private TextView textoSinContexto2;
    private ListView listViewTodito2;
    private TextView textsincontext11;
    private ListView list_view_listames1;
    private TextView textView13;
    private TextView textsincontext21;
    private ListView list_view_listames2;
    private TextView textView23;
    private TextView textsincontext31;
    private ListView list_view_listames3;
    private TextView textView33;
    private TextView textsincontext41;
    private ListView list_view_listames4;
    private TextView textView43;
    private TextView textsincontext51;
    private ListView list_view_listames5;
    private TextView textView53;
    private TextView textsincontext61;
    private ListView list_view_listames6;
    private TextView textView63;
    private TextView textsincontext71;
    private ListView list_view_listames7;
    private TextView textView73;
    private TextView textsincontext81;
    private ListView list_view_listames8;
    private TextView textView83;
    private TextView textsincontext91;
    private ListView list_view_listames9;
    private TextView textView93;
    private TextView textsincontext101;
    private ListView list_view_listames10;
    private TextView textView103;
    private TextView textsincontext111;
    private ListView list_view_listames11;
    private TextView textView113;
    private TextView textsincontext121;
    private ListView list_view_listames12;
    private TextView textView123;
    private TextView textsincontext12;
    private TextView textsincontext22;
    private TextView textsincontext32;
    private TextView textsincontext42;
    private TextView textsincontext52;
    private TextView textsincontext62;
    private TextView textsincontext72;
    private TextView textsincontext82;
    private TextView textsincontext92;
    private TextView textsincontext102;
    private TextView textsincontext112;
    private TextView textsincontext122;



    private ArrayList<Ingreso> ingresos = new ArrayList<>();

   private ArrayList<Gasto> gastos = new ArrayList<>();
    private ArrayAdapter<Ingreso> adapterIngresos;
    private ArrayAdapter<Gasto> adapterGastos;


    private ArrayAdapter<Ingreso> adapterIngresosM1;
    private ArrayAdapter<Ingreso> adapterIngresosM2;
    private ArrayAdapter<Ingreso> adapterIngresosM3;
    private ArrayAdapter<Ingreso> adapterIngresosM4;
    private ArrayAdapter<Ingreso> adapterIngresosM5;
    private ArrayAdapter<Ingreso> adapterIngresosM6;
    private ArrayAdapter<Ingreso> adapterIngresosM7;
    private ArrayAdapter<Ingreso> adapterIngresosM8;
    private ArrayAdapter<Ingreso> adapterIngresosM9;
    private ArrayAdapter<Ingreso> adapterIngresosM10;
    private ArrayAdapter<Ingreso> adapterIngresosM11;
    private ArrayAdapter<Ingreso> adapterIngresosM12;
    private ArrayAdapter<Gasto> adapterGastosM1;
    private ArrayAdapter<Gasto> adapterGastosM2;
    private ArrayAdapter<Gasto> adapterGastosM3;
    private ArrayAdapter<Gasto> adapterGastosM4;
    private ArrayAdapter<Gasto> adapterGastosM5;
    private ArrayAdapter<Gasto> adapterGastosM6;
    private ArrayAdapter<Gasto> adapterGastosM7;
    private ArrayAdapter<Gasto> adapterGastosM8;
    private ArrayAdapter<Gasto> adapterGastosM9;
    private ArrayAdapter<Gasto> adapterGastosM10;
    private ArrayAdapter<Gasto> adapterGastosM11;
    private ArrayAdapter<Gasto> adapterGastosM12;
    private ArrayAdapter<Gasto> adapterGastosM;
    private  ArrayList<Categoria> categoriasIngresos = new ArrayList<>();

    String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    float[] total = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};


    public  void crearValores() {
        CategoriaManager categoriaManager = new CategoriaManager(this);
        gastos = categoriaManager.obtenerGastos();


        ingresos = categoriaManager.obtenerIngresos();



    }



    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportes);



            Switch switchOperation = findViewById(R.id.sitch);
            Button buttonPorMes = findViewById(R.id.mesActual);
            Button buttonPorano = findViewById(R.id.ano);

            final String[] operation = {"ingreso"};
            switchOperation.setOnCheckedChangeListener((buttonView, isChecked) -> {

                if (isChecked) {

                    operation[0] = "gasto";
                } else {

                    operation[0] = "ingreso";
                }
            });
        crearValores();


            buttonPorMes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(operation[0].equals("ingreso")){

                        generarReporteIngresosPorMesActual();
                    }else{
                        generarReporteGastosPorMesActual();
                    }

                }
            });

            buttonPorano.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(operation[0].equals("ingreso")){
                        generarReporteIngresosPorAnio();
                    }else{
                        generarReporteGastosPorAnio();
                    }
                }
            });

            textsincontext11 = findViewById(R.id.textsincontext11);
            list_view_listames1 = findViewById(R.id.list_view_listames1);
             textView13 = findViewById(R.id.textView13);
        textsincontext21 = findViewById(R.id.textsincontext21);
        list_view_listames2 = findViewById(R.id.list_view_listames2);
        textView23 = findViewById(R.id.textView23);
        textsincontext31 = findViewById(R.id.textsincontext31);
        list_view_listames3 = findViewById(R.id.list_view_listames3);
        textView33 = findViewById(R.id.textView33);
        textsincontext41 = findViewById(R.id.textsincontext41);
        list_view_listames4 = findViewById(R.id.list_view_listames4);
        textView43 = findViewById(R.id.textView43);
        textsincontext51 = findViewById(R.id.textsincontext51);
        list_view_listames5 = findViewById(R.id.list_view_listames5);
        textView53 = findViewById(R.id.textView53);
        textsincontext61 = findViewById(R.id.textsincontext61);
        list_view_listames6 = findViewById(R.id.list_view_listames6);
        textView63 = findViewById(R.id.textView63);
        textsincontext71 = findViewById(R.id.textsincontext71);
        list_view_listames7 = findViewById(R.id.list_view_listames7);
        textView73 = findViewById(R.id.textView73);
        textsincontext81 = findViewById(R.id.textsincontext81);
        list_view_listames8 = findViewById(R.id.list_view_listames8);
        textView83 = findViewById(R.id.textView83);
        textsincontext91 = findViewById(R.id.textsincontext91);
        list_view_listames9 = findViewById(R.id.list_view_listames9);
        textView93 = findViewById(R.id.textView93);
        textsincontext101 = findViewById(R.id.textsincontext101);
        list_view_listames10 = findViewById(R.id.list_view_listames10);
        textView103 = findViewById(R.id.textView103);
        textsincontext111 = findViewById(R.id.textsincontext111);
        list_view_listames11 = findViewById(R.id.list_view_listames11);
        textView113 = findViewById(R.id.textView113);
        textsincontext121 = findViewById(R.id.textsincontext121);
        list_view_listames12 = findViewById(R.id.list_view_listames12);
        textView123 = findViewById(R.id.textView123);
        textsincontext12 =findViewById(R.id.textsincontext12);
        textsincontext22 =findViewById(R.id.textsincontext22);
        textsincontext32 =findViewById(R.id.textsincontext32);
        textsincontext42 =findViewById(R.id.textsincontext42);
        textsincontext52 =findViewById(R.id.textsincontext52);
        textsincontext62 =findViewById(R.id.textsincontext62);
        textsincontext72 =findViewById(R.id.textsincontext72);
        textsincontext82 =findViewById(R.id.textsincontext82);
        textsincontext92 =findViewById(R.id.textsincontext92);
        textsincontext102 =findViewById(R.id.textsincontext102);
        textsincontext112 =findViewById(R.id.textsincontext112);
        textsincontext122 =findViewById(R.id.textsincontext122);



            //textoSinContexto2 = findViewById(R.id.textsincontext21);
            //listViewTodito2 = findViewById(R.id.list_view_listames2);


            //adapterIngresos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresos);
            //adapterGastos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastos);


            //listViewTodito2.setAdapter(adapterGastos);



    }



    // Clase Mes para modelar los datos de los meses

    // Adaptador para RecyclerView

    private  void generarReporteIngresosPorMesActual() {
        limpiarTodo();
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();
        ArrayList<Ingreso> ingresosMesActual = new ArrayList<>();

        int totalIngresosMes = 0;

        System.out.println("\nReporte de Ingresos para el Mes Actual:");

        // Filtrar ingresos por el mes actual
        for (Ingreso ingreso : ingresos) {
            LocalDate fechaInicio = LocalDate.parse(ingreso.getFechaInicio(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int mesIngreso = fechaInicio.getMonthValue();
            int mese = 0;
            if(ingreso.getRepeticion().equalsIgnoreCase("mensual")){
                LocalDate fechaFinal = LocalDate.parse(ingreso.getFechaFin(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int yearD = fechaFinal.getYear() - fechaInicio.getYear();
                int mesD = fechaFinal.getMonthValue() - fechaInicio.getMonthValue();
                mese = yearD*12+mesD;
                if(mese>=12){
                    mese=12-mesIngreso;
                }

            }else{
                mese=0;
            }
            for(int i= 0;i<mese;i++){

                if (mesIngreso+i == mesActual) {
                    System.out.printf("Categoría: %-20s | Valor: %d\n", ingreso.getCategoria().getNombreCategoria(),
                            ingreso.getValor());
                    totalIngresosMes += ingreso.getValor();
                    ingresosMesActual.add(ingreso);
                }
            }


        }
        textsincontext11.setText(obtenerNombreMes(mesActual));
        adapterIngresosM1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesActual);
        list_view_listames1.setAdapter(adapterIngresosM1);
        textView13.setText("\nTotal de Ingresos para el Mes Actual: "+ totalIngresosMes);






    }
    //genera por año
    private void generarReporteIngresosPorAnio() {
        limpiarTodo();

        LocalDate fechaActual = LocalDate.now();
        int anioActual = fechaActual.getYear();
        int aaamActual = fechaActual.getMonthValue();

        // Array para almacenar los ingresos por mes
        int[] ingresosPorMes = new int[12];
        ArrayList<ArrayList<Ingreso>> ingresosMes = new ArrayList<>();


        // Filtrar ingresos por el año actual y acumular los valores por mes
        for (Ingreso ingreso : ingresos) {

            try {
                LocalDate fechaInicio = LocalDate.parse(ingreso.getFechaInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int mesIngreso = fechaInicio.getMonthValue();
                int anioIngreso = fechaInicio.getYear();

                if (anioIngreso == anioActual) {
                    int valorIngreso = ingreso.getValor();
                    ingresosPorMes[mesIngreso - 1] += valorIngreso;




                    // Añadir ingresos recurrentes mensuales
                    if (ingreso.getRepeticion().equals("mensual")) {
                        LocalDate fechaFinal = LocalDate.parse(ingreso.getFechaFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        int yearDiff = fechaFinal.getYear() - fechaInicio.getYear();
                        int monthDiff = fechaFinal.getMonthValue() - fechaInicio.getMonthValue();
                        int totalMeses = yearDiff * 12 + monthDiff;

                        for (int i = 1; i <= totalMeses; i++) {
                            int mesRecurrente = mesIngreso - 1 + i;
                            if (mesRecurrente < 12) {
                                ingresosPorMes[mesRecurrente] += valorIngreso;

                            }
                        }
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error al parsear fecha: " + e.getMessage());
                // Manejar el error si la fecha no puede ser parseada
            }
        }

        // Mostrar el reporte agrupado por mes
        System.out.println("\nReporte de Ingresos para el Año Actual:");
        System.out.println("----------------------------------------");
        int cp =0;
        // Iterar sobre los meses del año actual
        for (int mes = 1; mes <= fechaActual.getMonthValue(); mes++) {
            String nombreMes = obtenerNombreMes(mes);


            System.out.println(nombreMes);
            System.out.println("------------------------------");

            int totalIngresosMes = 0;
            ArrayList<Ingreso> ingresosMesA = new ArrayList<>();

            // Filtrar ingresos por el mes actual y mostrar detalles

            for (Ingreso ingreso : ingresos) {
                LocalDate fechaInicio = LocalDate.parse(ingreso.getFechaInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int mesIngreso = fechaInicio.getMonthValue();
                int anioIngreso = fechaInicio.getYear();


                if (anioIngreso == anioActual && mesIngreso == mes) {
                    System.out.printf("%-20s | Valor: %d\n", ingreso.getCategoria().getNombreCategoria(), ingreso.getValor());
                    totalIngresosMes += ingreso.getValor();
                    ingresosMesA.add(ingreso);

                }

                // Añadir detalles de ingresos recurrentes mensuales

                if (ingreso.getRepeticion().equals("mensual")) {
                    LocalDate fechaFinal = LocalDate.parse(ingreso.getFechaFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    int yearD = fechaFinal.getYear() - fechaInicio.getYear();
                    int mesD = fechaFinal.getMonthValue() - fechaInicio.getMonthValue();
                    int totalMeses = yearD * 12 + mesD;


                    for (int i = 1; i <= totalMeses; i++) {
                        int mesRecurrente = mesIngreso + i;
                        if (anioIngreso == anioActual && mesRecurrente == mes) {
                            System.out.printf("%-20s | Valor: %d\n", ingreso.getCategoria().getNombreCategoria(), ingreso.getValor());
                            totalIngresosMes += ingreso.getValor();
                            ingresosMesA.add(ingreso);

                        }

                    }


                }

            }


            System.out.println("Total Ingresos Mes: " + totalIngresosMes);



            System.out.println();
            if(mes==1){
                textsincontext11.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames1.setAdapter(adapterIngresosM1);
                textsincontext12.setText("Total de Ingresos : "+ totalIngresosMes);



            } if(mes==2){
                textsincontext21.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames2.setAdapter(adapterIngresosM2);
                textsincontext22.setText("Total de Ingresos: "+ totalIngresosMes);
            }if(mes==3){
                textsincontext31.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames3.setAdapter(adapterIngresosM3);
                textsincontext32.setText("Total de Ingresos: "+ totalIngresosMes);
            }if(mes==4){
                textsincontext41.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames4.setAdapter(adapterIngresosM4);
                textsincontext42.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }if(mes==5){
                textsincontext51.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM5 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames5.setAdapter(adapterIngresosM5);
                textsincontext52.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }if(mes==6){
                textsincontext61.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM6 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames6.setAdapter(adapterIngresosM6);
                textsincontext62.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }if(mes==7){
                textsincontext71.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM7 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames7.setAdapter(adapterIngresosM7);
                textsincontext72.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }if(mes==8){
                textsincontext81.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM8 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames8.setAdapter(adapterIngresosM8);
                textsincontext82.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }if(mes==9){
                textsincontext91.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM9 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames9.setAdapter(adapterIngresosM9);
                textsincontext92.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }if(mes==10){
                textsincontext101.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM10 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames10.setAdapter(adapterIngresosM10);
                textsincontext102.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }if(mes==11){
                textsincontext111.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM11 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames11.setAdapter(adapterIngresosM11);
                textsincontext112.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }if(mes==12){
                textsincontext121.setText(nombreMes+"\n Categoria     Valor");
                adapterIngresosM12 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingresosMesA);
                list_view_listames12.setAdapter(adapterIngresosM12);
                textsincontext122.setText("\nTotal de Ingresos: "+ totalIngresosMes);
            }
        }


        // Calcular y mostrar el total para el año
        int totalAnual = 0;
        for (int mes = 0; mes < aaamActual; mes++) {
            totalAnual += ingresosPorMes[mes];
        }
        if(1==fechaActual.getMonthValue()){
            textView13.setText("Total anual: "+totalAnual);
        }
        if(2==fechaActual.getMonthValue()){
            textView23.setText("Total anual: "+totalAnual);
        }
        if(3==fechaActual.getMonthValue()){
            textView33.setText("Total anual: "+totalAnual);
        }
        if(4==fechaActual.getMonthValue()){
            textView43.setText("Total anual: "+totalAnual);
        }
        if(5==fechaActual.getMonthValue()){
            textView53.setText("Total anual: "+totalAnual);
        }
        if(6==fechaActual.getMonthValue()){
            textView63.setText("Total anual: "+totalAnual);
        }
        if(7==fechaActual.getMonthValue()){
            textView73.setText("Total anual: "+totalAnual);
        }
        if(8==fechaActual.getMonthValue()){
            textView83.setText("Total anual: "+totalAnual);
        }
        if(9==fechaActual.getMonthValue()){
            textView93.setText("Total anual: "+totalAnual);
        }
        if(10==fechaActual.getMonthValue()){
            textView103.setText("Total anual: "+totalAnual);
        }
        if(11==fechaActual.getMonthValue()){
            textView113.setText("Total anual: "+totalAnual);
        }
        if(12==fechaActual.getMonthValue()){
            textView123.setText("Total anual: "+totalAnual);
        }
        System.out.println("Total Anual: " + totalAnual);


    }
    // Método auxiliar para obtener el nombre del mes según su número
    private String obtenerNombreMes(int numeroMes) {
        return LocalDate.of(1, numeroMes, 1).getMonth().toString();
    }
    private void generarReporteGastosPorMesActual() {
        limpiarTodo();
        ArrayList<Gasto> gastosMesActual = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();
        int totalGastosMes = 0;

        System.out.println("\nReporte de Ingresos para el Mes Actual:");

        // Filtrar ingresos por el mes actual
        for (Gasto gasto : gastos) {
            LocalDate fechaInicio = LocalDate.parse(gasto.getFechaInicio(),
                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            int mesIngreso = fechaInicio.getMonthValue();
            int mese = 0;
            if(gasto.getRepeticion().equalsIgnoreCase("mensual")){
                LocalDate fechaFinal = LocalDate.parse(gasto.getFechaFin(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int yearD = fechaFinal.getYear() - fechaInicio.getYear();
                int mesD = fechaFinal.getMonthValue() - fechaInicio.getMonthValue();
                mese = yearD*12+mesD;
                if(mese>=12){
                    mese=12-mesIngreso;
                }

            }else{
                mese=0;
            }
            for(int i= 0;i<mese;i++){

                if (mesIngreso+i == mesActual) {
                    System.out.printf("Categoría: %-20s | Valor: %d\n", gasto.getCategoria().getNombreCategoria(),
                            gasto.getValor());
                    totalGastosMes += gasto.getValor();
                    gastosMesActual.add(gasto);
                }
            }


        }
        textsincontext11.setText(obtenerNombreMes(mesActual));
        adapterGastosM1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesActual);
        list_view_listames1.setAdapter(adapterGastosM1);
        textView13.setText("\nTotal de Ingresos para el Mes Actual: "+ totalGastosMes);

        System.out.println("\nTotal de Ingresos para el Mes Actual: " + totalGastosMes);
    }

    private void generarReporteGastosPorAnio() {
        limpiarTodo();
        LocalDate fechaActual = LocalDate.now();
        int anioActual = fechaActual.getYear();
        int aaamActual = fechaActual.getMonthValue();

        // Array para almacenar los ingresos por mes
        int[] gastosPorMes = new int[12];

        // Filtrar ingresos por el año actual y acumular los valores por mes
        for (Gasto gasto : gastos) {
            try {
                LocalDate fechaInicio = LocalDate.parse(gasto.getFechaInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int mesGasto = fechaInicio.getMonthValue();
                int anioGasto = fechaInicio.getYear();

                if (anioGasto == anioActual) {
                    int valorGasto = gasto.getValor();
                    gastosPorMes[mesGasto - 1] += valorGasto;

                    // Añadir ingresos recurrentes mensuales
                    if (gasto.getRepeticion().equalsIgnoreCase("mensual")) {
                        LocalDate fechaFinal = LocalDate.parse(gasto.getFechaFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        int yearDiff = fechaFinal.getYear() - fechaInicio.getYear();
                        int monthDiff = fechaFinal.getMonthValue() - fechaInicio.getMonthValue();
                        int totalMeses = yearDiff * 12 + monthDiff;

                        for (int i = 1; i <= totalMeses; i++) {
                            int mesRecurrente = mesGasto - 1 + i;
                            if (mesRecurrente < 12) {
                                gastosPorMes[mesRecurrente] += valorGasto;

                            }
                        }
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.println("Error al parsear fecha: " + e.getMessage());
                // Manejar el error si la fecha no puede ser parseada
            }
        }

        // Mostrar el reporte agrupado por mes
        System.out.println("\nReporte de Ingresos para el Año Actual:");
        System.out.println("----------------------------------------");
        int cp =0;
        // Iterar sobre los meses del año actual
        for (int mes = 1; mes <= fechaActual.getMonthValue(); mes++) {
            String nombreMes = obtenerNombreMes(mes);

            System.out.println(nombreMes);
            System.out.println("------------------------------");

            int totalGastosMes = 0;
            ArrayList<Gasto> gastosMesA = new ArrayList<>();

            // Filtrar ingresos por el mes actual y mostrar detalles

            for (Gasto gasto : gastos) {
                LocalDate fechaInicio = LocalDate.parse(gasto.getFechaInicio(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                int mesGasto = fechaInicio.getMonthValue();
                int anioGasto = fechaInicio.getYear();

                if (anioGasto == anioActual && mesGasto == mes) {
                    System.out.printf("%-20s | Valor: %d\n", gasto.getCategoria().getNombreCategoria(), gasto.getValor());
                    totalGastosMes += gasto.getValor();
                    gastosMesA.add(gasto);
                }

                // Añadir detalles de ingresos recurrentes mensuales

                if (gasto.getRepeticion().equalsIgnoreCase("mensual")) {
                    LocalDate fechaFinal = LocalDate.parse(gasto.getFechaFin(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    int yearD = fechaFinal.getYear() - fechaInicio.getYear();
                    int mesD = fechaFinal.getMonthValue() - fechaInicio.getMonthValue();
                    int totalMeses = yearD * 12 + mesD;


                    for (int i = 1; i <= totalMeses; i++) {
                        int mesRecurrente = mesGasto + i;
                        if (anioGasto == anioActual && mesRecurrente == mes) {
                            System.out.printf("%-20s | Valor: %d\n", gasto.getCategoria().getNombreCategoria(), gasto.getValor());
                            totalGastosMes += gasto.getValor();
                            gastosMesA.add(gasto);

                        }

                    }


                }

            }


            System.out.println("Total Gastos Mes: " + totalGastosMes);
            System.out.println();
            if(mes==1){
                textsincontext11.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames1.setAdapter(adapterGastosM1);
                textsincontext12.setText("Total de Gastos : "+ totalGastosMes);



            } if(mes==2){
                textsincontext21.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames2.setAdapter(adapterGastosM2);
                textsincontext22.setText("Total de Gastos: "+ totalGastosMes);
            }if(mes==3){
                textsincontext31.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames3.setAdapter(adapterGastosM3);
                textsincontext32.setText("Total de Gastos: "+ totalGastosMes);
            }if(mes==4){
                textsincontext41.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM4 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames4.setAdapter(adapterGastosM4);
                textsincontext42.setText("\nTotal de Gastos: "+ totalGastosMes);
            }if(mes==5){
                textsincontext51.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM5 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames5.setAdapter(adapterGastosM5);
                textsincontext52.setText("\nTotal de Ingresos: "+ totalGastosMes);
            }if(mes==6){
                textsincontext61.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM6 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames6.setAdapter(adapterGastosM6);
                textsincontext62.setText("\nTotal de Gastos: "+ totalGastosMes);
            }if(mes==7){
                textsincontext71.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM7 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames7.setAdapter(adapterGastosM7);
                textsincontext72.setText("\nTotal de Gastos: "+ totalGastosMes);
            }if(mes==8){
                textsincontext81.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM8 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames8.setAdapter(adapterGastosM8);
                textsincontext82.setText("\nTotal de Gastos: "+ totalGastosMes);
            }if(mes==9){
                textsincontext91.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM9 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames9.setAdapter(adapterGastosM9);
                textsincontext92.setText("\nTotal de Gastos: "+ totalGastosMes);
            }if(mes==10){
                textsincontext101.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM10 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames10.setAdapter(adapterGastosM10);
                textsincontext102.setText("\nTotal de Gastos: "+ totalGastosMes);
            }if(mes==11){
                textsincontext111.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM11 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames11.setAdapter(adapterGastosM11);
                textsincontext112.setText("\nTotal de Gastos: "+ totalGastosMes);
            }if(mes==12){
                textsincontext121.setText(nombreMes+"\n Categoria     Valor");
                adapterGastosM12 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gastosMesA);
                list_view_listames12.setAdapter(adapterGastosM12);
                textsincontext122.setText("\nTotal de Gastos: "+ totalGastosMes);
            }
        }


        // Calcular y mostrar el total para el año
        int totalAnual = 0;
        for (int mes = 0; mes < aaamActual; mes++) {
            totalAnual += gastosPorMes[mes];
        }
        System.out.println("Total Anual: " + totalAnual);
        if(1==fechaActual.getMonthValue()){
            textView13.setText("Total anual: "+totalAnual);
        }
        if(2==fechaActual.getMonthValue()){
            textView23.setText("Total anual: "+totalAnual);
        }
        if(3==fechaActual.getMonthValue()){
            textView33.setText("Total anual: "+totalAnual);
        }
        if(4==fechaActual.getMonthValue()){
            textView43.setText("Total anual: "+totalAnual);
        }
        if(5==fechaActual.getMonthValue()){
            textView53.setText("Total anual: "+totalAnual);
        }
        if(6==fechaActual.getMonthValue()){
            textView63.setText("Total anual: "+totalAnual);
        }
        if(7==fechaActual.getMonthValue()){
            textView73.setText("Total anual: "+totalAnual);
        }
        if(8==fechaActual.getMonthValue()){
            textView83.setText("Total anual: "+totalAnual);
        }
        if(9==fechaActual.getMonthValue()){
            textView93.setText("Total anual: "+totalAnual);
        }
        if(10==fechaActual.getMonthValue()){
            textView103.setText("Total anual: "+totalAnual);
        }
        if(11==fechaActual.getMonthValue()){
            textView113.setText("Total anual: "+totalAnual);
        }
        if(12==fechaActual.getMonthValue()){
            textView123.setText("Total anual: "+totalAnual);
        }
    }
    private void limpiarTodo() {
        // Limpiar los TextViews
        textsincontext11.setText("");
        textView13.setText("");
        textsincontext21.setText("");
        textView23.setText("");
        textsincontext31.setText("");
        textView33.setText("");
        textsincontext41.setText("");
        textView43.setText("");
        textsincontext51.setText("");
        textView53.setText("");
        textsincontext61.setText("");
        textView63.setText("");
        textsincontext71.setText("");
        textView73.setText("");
        textsincontext81.setText("");
        textView83.setText("");
        textsincontext91.setText("");
        textView93.setText("");
        textsincontext101.setText("");
        textView103.setText("");
        textsincontext111.setText("");
        textView113.setText("");
        textsincontext121.setText("");
        textView123.setText("");
        textsincontext12.setText("");
        textsincontext22.setText("");
        textsincontext32.setText("");
        textsincontext42.setText("");
        textsincontext52.setText("");
        textsincontext62.setText("");
        textsincontext72.setText("");
        textsincontext82.setText("");
        textsincontext92.setText("");
        textsincontext102.setText("");
        textsincontext112.setText("");
        textsincontext122.setText("");


        // Limpiar los ListViews
        list_view_listames1.setAdapter(null);
        list_view_listames2.setAdapter(null);
        list_view_listames3.setAdapter(null);
        list_view_listames4.setAdapter(null);
        list_view_listames5.setAdapter(null);
        list_view_listames6.setAdapter(null);
        list_view_listames7.setAdapter(null);
        list_view_listames8.setAdapter(null);
        list_view_listames9.setAdapter(null);
        list_view_listames10.setAdapter(null);
        list_view_listames11.setAdapter(null);
        list_view_listames12.setAdapter(null);
    }
}