package com.example.menuprobando;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo2.Banco;
import com.example.menuprobando.modelo2.Cobrar;
import com.example.menuprobando.modelo2.CuentaBancaria;
import com.example.menuprobando.modelo2.Pagar;
import com.example.menuprobando.modelo2.Persona;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdmiPersonaBanco extends AppCompatActivity {
    private EditText editTextCedula;
    private EditText editTextNombre;
    private EditText editTextTelefono;
    private EditText editTextCorreo;
    private EditText editTextCedula2;
    private EditText editTextCedula4;

    private EditText editTextRuc;
    private EditText editTextNombreB;
    private EditText editTextTelefonoB;
    private EditText editTextCorreoB;
    private EditText editTextOficialC;

    private TextView textViewMensaje1;
    private ListView listViewPersonas;
    private ListView listViewBancos;
    private TextView textViewMensaje3;

    private ArrayList<Persona> personas = new ArrayList<>();
    private ArrayList<Banco> bancos = new ArrayList<>();
    private ArrayAdapter<Persona> adapterPersonas;
    private ArrayAdapter<Banco> adapterBancos;
    private ArrayList<Cobrar> prestamosRegistrados = new ArrayList<>();
    private List<Pagar> pagarRegistrados = new ArrayList<>();
    private ArrayList<CuentaBancaria> cuentasBancarias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admi_personas_bancos);

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab 1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Persona");
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab 2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Banco");
        tabHost.addTab(spec2);

        tabHost.setCurrentTab(0);

        editTextCedula = findViewById(R.id.edit_text_cedula);
        editTextNombre = findViewById(R.id.edit_text_nombre_persona);
        editTextTelefono = findViewById(R.id.edit_text_telefono);
        editTextCorreo = findViewById(R.id.edit_text_correo);
        textViewMensaje1 = findViewById(R.id.text_view_mensaje1);
        listViewPersonas = findViewById(R.id.list_view_personas);
        editTextCedula2 = findViewById(R.id.edit_text_cedula2);

        editTextRuc = findViewById(R.id.edit_text_ruc);
        editTextNombreB = findViewById(R.id.edit_text_nombre_banco);
        editTextTelefonoB = findViewById(R.id.edit_text_telefono2);
        editTextCorreoB = findViewById(R.id.edit_text_correo2);
        textViewMensaje3 = findViewById(R.id.text_view_mensaje3);
        editTextOficialC = findViewById(R.id.edit_text_oficialC);
        listViewBancos = findViewById(R.id.list_view_bancos);
        editTextCedula4 = findViewById(R.id.edit_text_cedula4);

        // Initialize adapters
        adapterPersonas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personas);
        adapterBancos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bancos);

        listViewPersonas.setAdapter(adapterPersonas);
        listViewBancos.setAdapter(adapterBancos);

        // Set button click listeners
        Button buttonAgregarPersona = findViewById(R.id.button_agregar_persona);
        Button buttonEliminarPersona = findViewById(R.id.button_eliminar_persona);

        buttonAgregarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarPersona();
                guardarDatos();
            }
        });

        buttonEliminarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarPersona();
                guardarDatos();
            }
        });

        Button buttonAgregarBanco = findViewById(R.id.button_agregar_banco);
        Button buttonEliminarBanco = findViewById(R.id.button_eliminar_banco);

        buttonAgregarBanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarBanco();
                guardarDatos();
            }
        });

        buttonEliminarBanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarBanco();
                guardarDatos();
            }
        });

        cargarDatos();

        Log.d("AdmiPersonaBanco", "Activity created successfully");
    }

    private void agregarPersona() {
        String cedula = editTextCedula.getText().toString().trim();
        String nombre = editTextNombre.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();
        String correo = editTextCorreo.getText().toString().trim();

        if (cedula.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            textViewMensaje1.setText("Por favor, complete todos los campos.");
            return;
        }

        if (personaExiste(cedula)) {
            textViewMensaje1.setText("La Persona ya existe.");
            return;
        }

        Persona persona = new Persona(nombre, telefono, correo, cedula);
        personas.add(persona);
        adapterPersonas.notifyDataSetChanged();  // Actualizar el adaptador
        guardarDatos();

        Log.d("AdmiPersonaBanco", "Persona added: " + persona.toString());
    }

    private void registrarBanco() {
        String ruc = editTextRuc.getText().toString().trim();
        String nombre = editTextNombreB.getText().toString().trim();
        String telefono = editTextTelefonoB.getText().toString().trim();
        String email = editTextCorreoB.getText().toString().trim();
        String oficialCredito = editTextOficialC.getText().toString().trim();

        if (ruc.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            textViewMensaje1.setText("Por favor, complete todos los campos.");
            return;
        }

        if (bancoExiste(ruc)) {
            textViewMensaje1.setText("El banco ya existe.");
            return;
        }

        Banco banco = new Banco(nombre, telefono, email, ruc, oficialCredito);
        bancos.add(banco);
        adapterBancos.notifyDataSetChanged();  // Actualizar el adaptador
        guardarDatos();

        Log.d("AdmiPersonaBanco", "Banco added: " + banco.toString());
    }

    private void eliminarPersona() {
        if (personas.isEmpty()) {
            textViewMensaje1.setText("No hay personas registradas.");
            return;
        }

        String cedulaEliminar = editTextCedula2.getText().toString().trim().toLowerCase();

        for (final Persona persona : personas) {
            if (persona.getIdentificador().equalsIgnoreCase(cedulaEliminar)) {
                new AlertDialog.Builder(this)
                        .setMessage("¿Está seguro que desea eliminar esta persona?")
                        .setTitle("Confirmar eliminación")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            personas.remove(persona);
                            eliminirPagarP(persona);
                            textViewMensaje1.setText("Persona eliminada correctamente.");
                            adapterPersonas.notifyDataSetChanged();  // Actualizar el adaptador
                            guardarDatos();
                        })
                        .setNegativeButton("No", (dialog, which) -> textViewMensaje1.setText("Operación cancelada."))
                        .show();
                return;
            }
        }

        textViewMensaje1.setText("No se encontró ninguna persona con esa cédula.");
    }

    private void eliminarBanco() {
        if (bancos.isEmpty()) {
            textViewMensaje3.setText("No hay bancos registrados.");
            return;
        }

        String cedulaEliminar = editTextCedula4.getText().toString().trim().toLowerCase();

        for (final Banco banco : bancos) {
            if (banco.getIdentificador().equalsIgnoreCase(cedulaEliminar)) {
                new AlertDialog.Builder(this)
                        .setMessage("¿Está seguro que desea eliminar este banco?")
                        .setTitle("Confirmar eliminación")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            bancos.remove(banco);
                            eliminirPagarB(banco);
                            textViewMensaje3.setText("Banco eliminado correctamente.");
                            adapterBancos.notifyDataSetChanged();  // Actualizar el adaptador
                            guardarDatos();
                        })
                        .setNegativeButton("No", (dialog, which) -> textViewMensaje3.setText("Operación cancelada."))
                        .show();
                return;
            }
        }

        textViewMensaje3.setText("No se encontró ningún banco con ese RUC.");
    }

    private boolean personaExiste(String cedula) {
        for (Persona persona : personas) {
            if (persona.getIdentificador().equalsIgnoreCase(cedula)) {
                return true;
            }
        }
        return false;
    }

    private boolean bancoExiste(String ruc) {
        for (Banco banco : bancos) {
            if (banco.getIdentificador().equalsIgnoreCase(ruc)) {
                return true;
            }
        }
        return false;
    }

    private void guardarDatos() {
        try (FileOutputStream fosPersonas = openFileOutput("personas.dat", MODE_PRIVATE);
             ObjectOutputStream oosPersonas = new ObjectOutputStream(fosPersonas)) {
            oosPersonas.writeObject(personas);

            FileOutputStream fosBancos = openFileOutput("bancos.dat", MODE_PRIVATE);
            ObjectOutputStream oosBancos = new ObjectOutputStream(fosBancos);
            oosBancos.writeObject(bancos);
            FileOutputStream fosPagar = openFileOutput("pagar.dat", MODE_PRIVATE);
            ObjectOutputStream oosPagar = new ObjectOutputStream(fosPagar);
            oosPagar.writeObject(pagarRegistrados);
            FileOutputStream fosPrestamos = openFileOutput("prestamos.dat", MODE_PRIVATE);
            ObjectOutputStream oosPrestamos = new ObjectOutputStream(fosPrestamos);
            oosPagar.writeObject(prestamosRegistrados);
        } catch (IOException e) {
            Log.e("AdmiPersonaBanco", "Error saving data", e);
        }
    }

    private void cargarDatos() {
        try (ObjectInputStream oisPersonas = new ObjectInputStream(openFileInput("personas.dat"))) {
            personas = (ArrayList<Persona>) oisPersonas.readObject();
            adapterPersonas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, personas);
            listViewPersonas.setAdapter(adapterPersonas);

            ObjectInputStream oisBancos = new ObjectInputStream(openFileInput("bancos.dat"));
            bancos = (ArrayList<Banco>) oisBancos.readObject();
            adapterBancos = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bancos);
            listViewBancos.setAdapter(adapterBancos);

            ObjectInputStream oisPrestamos = new ObjectInputStream(openFileInput("prestamos.dat"));
            prestamosRegistrados = (ArrayList<Cobrar>) oisPrestamos.readObject();

            ObjectInputStream oisPagar = new ObjectInputStream(openFileInput("pagar.dat"));
            pagarRegistrados = (ArrayList<Pagar>) oisPagar.readObject();
        } catch (FileNotFoundException e) {
            Log.w("AdmiPersonaBanco", "File not found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            Log.e("AdmiPersonaBanco", "Error loading data", e);
        }
    }

    private void eliminirPagarP(Persona persona) {
        pagarRegistrados.removeIf(pagar -> pagar.getAcreedor().equals(persona));
        prestamosRegistrados.removeIf(pagar -> pagar.getDeudor().equals(persona));
    }

    private void eliminirPagarB(Banco banco) {
        pagarRegistrados.removeIf(pagar -> pagar.getAcreedor().equals(banco));
    }
}

