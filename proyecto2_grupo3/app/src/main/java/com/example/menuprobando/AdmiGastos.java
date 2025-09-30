package com.example.menuprobando;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo1.Categoria;
import com.example.menuprobando.modelo1.Gasto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdmiGastos extends AppCompatActivity {

    private List<Categoria> categoriasGastos;
    private List<Gasto> gastos;
    private ArrayAdapter<Gasto> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admi_gastos);

        categoriasGastos = new ArrayList<>(); // Inicializar la lista de categorías
        gastos = new ArrayList<>(); // Inicializar la lista de gastos

        // Cargar datos al iniciar la actividad
        cargarDatos();

        if (categoriasGastos.isEmpty()) {
            inicializarDatos();
        }

        configurarListView();
        configurarBotonRegistrar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarDatos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        guardarDatos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        guardarDatos();
    }

    private void inicializarDatos() {
        categoriasGastos = new ArrayList<>();


        // Asumiendo que CategoriaManager es una clase que maneja las categorías
        CategoriaManager categoriaManager = new CategoriaManager(this);
        categoriasGastos = categoriaManager.obtenerCategoriasGastos();

        // Ejemplo de categorías y gastos iniciales
        //Categoria categoriaComida = new Categoria("Comida", "Gasto");
        //Categoria categoriaAlquiler = new Categoria("Alquiler", "Gasto");

        //categoriasGastos.add(categoriaComida);
        //categoriasGastos.add(categoriaAlquiler);


    }



    private void configurarListView() {
        ListView listViewGastos = findViewById(R.id.list_view_gastos);

        adapter = new ArrayAdapter<Gasto>(
                this,
                android.R.layout.simple_list_item_1,
                gastos
        ) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                Gasto gasto = getItem(position);
                if (gasto != null) {
                    ((android.widget.TextView) convertView.findViewById(android.R.id.text1))
                            .setText(gasto.getCategoria().getNombreCategoria() + " - Código: " + gasto.getCodigo());
                }

                return convertView;
            }
        };

        listViewGastos.setAdapter(adapter);

        listViewGastos.setOnItemClickListener((parent, view, position, id) -> {
            Gasto gastoSeleccionado = gastos.get(position);
            mostrarOpcionesGasto(gastoSeleccionado);
        });
    }

    private void configurarBotonRegistrar() {
        findViewById(R.id.boton_registrar).setOnClickListener(v -> mostrarFormularioRegistro());
    }

    private void mostrarOpcionesGasto(Gasto gasto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Opciones del Gasto")
                .setMessage("Código: " + gasto.getCodigo() + "\n" +
                        "Fecha de inicio: " + gasto.getFechaInicio() + "\n" +
                        "Nombre: " + gasto.getCategoria().getNombreCategoria() + "\n" +
                        "Valor: " + gasto.getValor() + "\n" +
                        "Descripción: " + gasto.getDescripcion() + "\n" +
                        "Fecha de fin: " + gasto.getFechaFin() + "\n" +
                        "Repetición: " + gasto.getRepeticion())
                .setPositiveButton("Finalizar Gasto", (dialog, which) -> finalizarGasto(gasto))
                .setNeutralButton("Eliminar", (dialog, which) -> confirmarEliminarGasto(gasto))
                .setNegativeButton("Regresar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void guardarGasto(EditText etCodigo, EditText etFechaInicio, EditText etValor, EditText etFechaFin, EditText etRepeticion, EditText etDescripcion, Spinner spCategorias) {
        try {
            int codigo = Integer.parseInt(etCodigo.getText().toString());
            String fechaInicio = etFechaInicio.getText().toString();
            int valor = Integer.parseInt(etValor.getText().toString());
            String fechaFin = etFechaFin.getText().toString();
            String repeticion = etRepeticion.getText().toString();
            String descripcion = etDescripcion.getText().toString();
            Categoria categoria = (Categoria) spCategorias.getSelectedItem();

            if (categoria != null) {
                Gasto nuevoGasto = new Gasto(codigo, fechaInicio, categoria, valor, fechaFin, repeticion, descripcion);
                gastos.add(nuevoGasto);
                actualizarListView(); // Actualiza la lista después de guardar
                guardarDatos(); // Guardar datos después de registrar
                Toast.makeText(this, "Gasto guardado exitosamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Seleccione una categoría.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese valores válidos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarGasto(Gasto gasto) {
        gastos.remove(gasto);
        Toast.makeText(this, "Gasto eliminado.", Toast.LENGTH_SHORT).show();
        actualizarListView();
        guardarDatos(); // Guardar datos después de eliminar
    }

    private void finalizarGasto(Gasto gasto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_finalizar_gasto, null);

        EditText etFechaInicio = dialogView.findViewById(R.id.etFechaInicio);
        EditText etFechaFin = dialogView.findViewById(R.id.etFechaFin);
        etFechaInicio.setText(gasto.getFechaInicio());

        builder.setView(dialogView)
                .setTitle("Finalizar Gasto")
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String fechaFin = etFechaFin.getText().toString();
                    if (validarFechaFin(gasto.getFechaInicio(), fechaFin)) {
                        gasto.setFechaFin(fechaFin);
                        Toast.makeText(this, "Gasto finalizado exitosamente.", Toast.LENGTH_SHORT).show();
                        actualizarListView();
                        guardarDatos(); // Guardar datos después de finalizar
                    } else {
                        Toast.makeText(this, "La fecha de fin debe ser posterior a la fecha de inicio.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void confirmarEliminarGasto(Gasto gasto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Gasto")
                .setMessage("¿Estás seguro de que deseas eliminar este gasto?")
                .setPositiveButton("Eliminar", (dialog, which) -> eliminarGasto(gasto))
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private boolean validarFechaFin(String fechaInicio, String fechaFin) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaInicioDate = formato.parse(fechaInicio);
            Date fechaFinDate = formato.parse(fechaFin);

            return fechaFinDate != null && fechaInicioDate != null && fechaFinDate.after(fechaInicioDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void mostrarFormularioRegistro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_registrar_gasto, null);

        EditText etCodigo = dialogView.findViewById(R.id.etCodigo);
        EditText etFechaInicio = dialogView.findViewById(R.id.etFechaInicio);
        EditText etValor = dialogView.findViewById(R.id.etValor);
        EditText etFechaFin = dialogView.findViewById(R.id.etFechaFin);
        EditText etRepeticion = dialogView.findViewById(R.id.etRepeticion);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcion);
        Spinner spCategorias = dialogView.findViewById(R.id.spCategorias);

        ArrayAdapter<Categoria> adapterCategorias = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categoriasGastos
        );
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategorias.setAdapter(adapterCategorias);

        builder.setView(dialogView)
                .setTitle("Registrar Gasto")
                .setPositiveButton("Guardar", (dialog, which) -> guardarGasto(
                        etCodigo, etFechaInicio, etValor, etFechaFin, etRepeticion, etDescripcion, spCategorias))
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();

    }
    private void actualizarListView() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    private void guardarDatos() {
        try (FileOutputStream fos = openFileOutput("gastos.dat", Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gastos);
        } catch (IOException e) {
            Log.e("AdmiGastos", "Error al guardar datos", e);
        }
    }
    private void cargarDatos() {
        try (FileInputStream fis = openFileInput("gastos.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gastos = (List<Gasto>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdmiGastos", "Error al cargar datos", e);
            gastos=new ArrayList<>();
        }
    }
}
