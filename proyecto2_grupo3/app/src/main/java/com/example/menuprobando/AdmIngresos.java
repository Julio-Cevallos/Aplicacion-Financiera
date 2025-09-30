package com.example.menuprobando;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo1.Categoria;
import com.example.menuprobando.modelo1.Ingreso;

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

public class AdmIngresos extends AppCompatActivity {

    private List<Categoria> categoriasIngresos;
    private List<Ingreso> ingresos;
    private ArrayAdapter<Ingreso> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admi_ingresos);

        categoriasIngresos = new ArrayList<>(); // Inicializar la lista de categorías
        ingresos = new ArrayList<>(); // Inicializar la lista de ingresos

        cargarDatos(); // Cargar los datos antes de configurar el ListView

        if (categoriasIngresos.isEmpty()) {
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
        categoriasIngresos = new ArrayList<>();

        CategoriaManager categoriaManager = new CategoriaManager(this);
        categoriasIngresos = categoriaManager.obtenerCategoriasIngresos();

        // Ejemplo de categorías iniciales
       // Categoria categoriaCasa = new Categoria("Casa", "Ingreso");
        //Categoria categoriaVivienda = new Categoria("Vivienda", "Ingreso");

       // categoriasIngresos.add(categoriaCasa);
        //categoriasIngresos.add(categoriaVivienda);


        // Aquí se pueden agregar ingresos predeterminados si se desea
    }

    private void configurarListView() {
        ListView listViewIngresos = findViewById(R.id.list_view_ingresos);

        adapter = new ArrayAdapter<Ingreso>(
                this,
                android.R.layout.simple_list_item_1,
                ingresos
        ) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                Ingreso ingreso = getItem(position);
                if (ingreso != null) {
                    ((android.widget.TextView) convertView.findViewById(android.R.id.text1))
                            .setText(ingreso.getCategoria().getNombreCategoria() + " - Info: " + ingreso.getDescripcion());
                }

                return convertView;
            }
        };

        listViewIngresos.setAdapter(adapter);

        listViewIngresos.setOnItemClickListener((parent, view, position, id) -> {
            Ingreso ingresoSeleccionado = ingresos.get(position);
            mostrarOpcionesIngreso(ingresoSeleccionado);
        });
    }

    private void configurarBotonRegistrar() {
        findViewById(R.id.boton_registrar).setOnClickListener(v -> mostrarFormularioRegistro());
    }

    private void mostrarOpcionesIngreso(Ingreso ingreso) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Opciones del Ingreso")
                .setMessage("Código: " + ingreso.getCodigo() + "\n" +
                        "Fecha de inicio: " + ingreso.getFechaInicio() + "\n" +
                        "Nombre: " + ingreso.getCategoria().getNombreCategoria() + "\n" +
                        "Valor: " + ingreso.getValor() + "\n" +
                        "Descripción: " + ingreso.getDescripcion() + "\n" +
                        "Fecha de fin: " + ingreso.getFechaFin() + "\n" +
                        "Repetición: " + ingreso.getRepeticion())
                .setPositiveButton("Finalizar Registro", (dialog, which) -> finalizarIngreso(ingreso))
                .setNeutralButton("Eliminar", (dialog, which) -> confirmarEliminarIngreso(ingreso))
                .setNegativeButton("Regresar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void guardarIngreso(EditText etCodigo, EditText etFechaInicio, EditText etValor, EditText etFechaFin, EditText etRepeticion, EditText etDescripcion, Spinner spCategorias) {
        try {
            int codigo = Integer.parseInt(etCodigo.getText().toString());
            String fechaInicio = etFechaInicio.getText().toString();
            int valor = Integer.parseInt(etValor.getText().toString());
            String fechaFin = etFechaFin.getText().toString();
            String repeticion = etRepeticion.getText().toString();
            String descripcion = etDescripcion.getText().toString();
            Categoria categoria = (Categoria) spCategorias.getSelectedItem();

            if (categoria != null) {
                Ingreso nuevoIngreso = new Ingreso(codigo, fechaInicio, categoria, valor, fechaFin, repeticion, descripcion);
                ingresos.add(nuevoIngreso);
                actualizarListView(); // Actualiza la lista después de guardar
                guardarDatos(); // Guardar datos después de registrar
                Toast.makeText(this, "Ingreso guardado exitosamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Seleccione una categoría.", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingrese valores válidos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarIngreso(Ingreso ingreso) {
        ingresos.remove(ingreso);
        Toast.makeText(this, "Ingreso eliminado.", Toast.LENGTH_SHORT).show();
        actualizarListView();
        guardarDatos(); // Guardar datos después de eliminar
    }

    private void finalizarIngreso(Ingreso ingreso) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_finalizar_ingreso, null);

        EditText etFechaInicio = dialogView.findViewById(R.id.etFechaInicio);
        EditText etFechaFin = dialogView.findViewById(R.id.etFechaFin);
        etFechaInicio.setText(ingreso.getFechaInicio());

        builder.setView(dialogView)
                .setTitle("Finalizar Ingreso")
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String fechaFin = etFechaFin.getText().toString();
                    if (validarFechaFin(ingreso.getFechaInicio(), fechaFin)) {
                        ingreso.setFechaFin(fechaFin);
                        Toast.makeText(this, "Ingreso finalizado exitosamente.", Toast.LENGTH_SHORT).show();
                        actualizarListView();
                        guardarDatos(); // Guardar datos después de finalizar
                    } else {
                        Toast.makeText(this, "La fecha de fin debe ser posterior a la fecha de inicio.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void confirmarEliminarIngreso(Ingreso ingreso) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Ingreso")
                .setMessage("¿Estás seguro de que deseas eliminar este ingreso?")
                .setPositiveButton("Eliminar", (dialog, which) -> eliminarIngreso(ingreso))
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
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_registrar_ingreso, null);

        EditText etCodigo = dialogView.findViewById(R.id.etCodigo);
        EditText etFechaInicio = dialogView.findViewById(R.id.etFechaInicio);
        EditText etValor = dialogView.findViewById(R.id.etValor);
        EditText etFechaFin = dialogView.findViewById(R.id.etFechaFin);
        EditText etRepeticion = dialogView.findViewById(R.id.etRepeticion);
        EditText etDescripcion = dialogView.findViewById(R.id.etDescripcion);
        Spinner spCategorias = dialogView.findViewById(R.id.spCategorias);

        ArrayAdapter<Categoria> adapterCategorias = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoriasIngresos);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategorias.setAdapter(adapterCategorias);

        builder.setView(dialogView)
                .setTitle("Registrar Ingreso")
                .setPositiveButton("Guardar", (dialog, which) -> guardarIngreso(
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
        try (FileOutputStream fos = openFileOutput("ingresos.dat", MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(ingresos);
        } catch (IOException e) {
            Log.e("AdmIngresos", "Error al guardar datos", e);
        }
    }

    private void cargarDatos() {
        try (FileInputStream fis = openFileInput("ingresos.dat");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            ingresos = (List<Ingreso>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdmIngresos", "Error al cargar datos", e);
            ingresos = new ArrayList<>();
        }
    }
}
