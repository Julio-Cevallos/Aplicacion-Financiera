package com.example.menuprobando;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo1.Categoria;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AdmiCategoria extends AppCompatActivity {

    private Spinner spinnerTipoCategoria;
    private ListView listViewIngresos;
    private ListView listViewGastos;
    private TextView textViewMensaje;

    private ArrayList<Categoria> categoriasIngresos = new ArrayList<>();
    private ArrayList<Categoria> categoriasGastos = new ArrayList<>();
    private ArrayAdapter<Categoria> adapterIngresos;
    private ArrayAdapter<Categoria> adapterGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admi_categorias);

        // Inicializar vistas
        spinnerTipoCategoria = findViewById(R.id.spinner_tipo_categoria);
        listViewIngresos = findViewById(R.id.list_view_ingresos);
        listViewGastos = findViewById(R.id.list_view_gastos);
        textViewMensaje = findViewById(R.id.text_view_mensaje);

        // Inicializar listas de categorías con valores predeterminados
        instanciarDatosPredeterminados();

        // Inicializar adaptadores
        adapterIngresos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriasIngresos);
        adapterGastos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriasGastos);

        listViewIngresos.setAdapter(adapterIngresos);
        listViewGastos.setAdapter(adapterGastos);

        // Cargar datos desde el archivo, si existen
        cargarDatos();

        // Configurar el botón registrar categoría
        Button buttonRegistrarCategoria = findViewById(R.id.button_registrar_categoria);
        buttonRegistrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoRegistrarCategoria();
            }
        });

        // Configurar las acciones de eliminar categoría desde la lista
        listViewIngresos.setOnItemClickListener((parent, view, position, id) -> eliminarCategoria("ingreso", position));
        listViewGastos.setOnItemClickListener((parent, view, position, id) -> eliminarCategoria("gasto", position));
    }

    private void mostrarDialogoRegistrarCategoria() {
        // Crear un AlertDialog para agregar nueva categoría
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialogo_agregar_categoria, null);

        final Spinner spinnerTipo = dialogView.findViewById(R.id.spinner_dialogo_tipo_categoria);
        final EditText editTextNombre = dialogView.findViewById(R.id.edit_text_dialogo_nombre_categoria);

        builder.setView(dialogView)
                .setTitle("Registrar Categoría")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tipo = spinnerTipo.getSelectedItem().toString().toLowerCase();
                        String nombre = editTextNombre.getText().toString().trim().toLowerCase();

                        if (nombre.isEmpty()) {
                            textViewMensaje.setText("El nombre de la categoría no puede estar vacío.");
                            return;
                        }

                        if (categoriaExiste(nombre, tipo)) {
                            textViewMensaje.setText("La categoría ya existe.");
                            return;
                        }

                        Categoria categoria = new Categoria(nombre, tipo);
                        if (tipo.equals("ingreso")) {
                            categoriasIngresos.add(categoria);
                            adapterIngresos.notifyDataSetChanged();
                        } else if (tipo.equals("gasto")) {
                            categoriasGastos.add(categoria);
                            adapterGastos.notifyDataSetChanged();
                        }

                        textViewMensaje.setText("Categoría agregada correctamente.");
                        guardarDatos();
                    }
                })
                .setNegativeButton("Regresar", null)
                .show();
    }

    private boolean categoriaExiste(String nombreCategoria, String tipoCategoria) {
        ArrayList<Categoria> listaCategorias;
        if (tipoCategoria.equals("ingreso")) {
            listaCategorias = categoriasIngresos;
        } else if (tipoCategoria.equals("gasto")) {
            listaCategorias = categoriasGastos;
        } else {
            return false;
        }

        for (Categoria categoria : listaCategorias) {
            if (categoria.getNombreCategoria().equalsIgnoreCase(nombreCategoria)) {
                return true;
            }
        }
        return false;
    }

    private void eliminarCategoria(final String tipoCategoria, final int position) {
        // Crear un AlertDialog para confirmar la eliminación
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmar Eliminación")
                .setMessage("¿Está seguro de que desea eliminar esta categoría?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Eliminar la categoría y actualizar la vista
                        if (tipoCategoria.equals("ingreso")) {
                            categoriasIngresos.remove(position);
                            adapterIngresos.notifyDataSetChanged();
                        } else if (tipoCategoria.equals("gasto")) {
                            categoriasGastos.remove(position);
                            adapterGastos.notifyDataSetChanged();
                        }
                        textViewMensaje.setText("Categoría eliminada correctamente.");
                        guardarDatos();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void instanciarDatosPredeterminados() {
        if (categoriasIngresos.isEmpty()) {
            categoriasIngresos.add(new Categoria("Salario", "ingreso"));
            categoriasIngresos.add(new Categoria("Alquiler", "ingreso"));
        }

        if (categoriasGastos.isEmpty()) {
            categoriasGastos.add(new Categoria("Transporte", "gasto"));
            categoriasGastos.add(new Categoria("Mascota", "gasto"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        guardarDatos();
    }

    private void guardarDatos() {
        try (ObjectOutputStream oosIngresos = new ObjectOutputStream(openFileOutput("categorias_ingresos.dat", MODE_PRIVATE));
             ObjectOutputStream oosGastos = new ObjectOutputStream(openFileOutput("categorias_gastos.dat", MODE_PRIVATE))) {

            oosIngresos.writeObject(categoriasIngresos);
            oosGastos.writeObject(categoriasGastos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarDatos() {
        try (ObjectInputStream oisIngresos = new ObjectInputStream(openFileInput("categorias_ingresos.dat"));
             ObjectInputStream oisGastos = new ObjectInputStream(openFileInput("categorias_gastos.dat"))) {

            categoriasIngresos.clear();
            categoriasGastos.clear();

            categoriasIngresos.addAll((ArrayList<Categoria>) oisIngresos.readObject());
            categoriasGastos.addAll((ArrayList<Categoria>) oisGastos.readObject());

            if (categoriasIngresos.isEmpty() || categoriasGastos.isEmpty()) {
                instanciarDatosPredeterminados();
            }

            adapterIngresos.notifyDataSetChanged();
            adapterGastos.notifyDataSetChanged();

        } catch (FileNotFoundException e) {
            instanciarDatosPredeterminados();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



}
