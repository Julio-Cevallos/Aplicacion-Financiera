package com.example.menuprobando;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo2.Banco;
import com.example.menuprobando.modelo2.CuentaBancaria;
import com.example.menuprobando.modelo2.Persona;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdmiCuentasBancarias extends AppCompatActivity {

    private EditText editTextSearch;
    private EditText fechazz;
    private ListView listViewNames;
    private ArrayAdapter<String> listAdapter;
    private List<String> bancoList;
    private List<String> filteredList;
    private TextView textView;
    private Spinner spinnerAccountType;
    private Button buttonChangeToEditText;
    private Button registrarboton;
    private EditText numero;
    private EditText fechaApertura;
    private EditText fechaFinal;
    private EditText saldo;
    private EditText fecha2;
    private EditText valorInteres;
    private ArrayList<Banco> bancos = new ArrayList<>();
    private ArrayList<CuentaBancaria> cuentasBancarias = new ArrayList<>();
    private String xd;
    private String tip;
    private ListView listViewcuentasBancarias;
    private ArrayAdapter<CuentaBancaria> adapterCuentasBancarias;

    public void crearValores() {
        try (ObjectInputStream ois = new ObjectInputStream(openFileInput("bancos.dat"))) {

            bancos = (ArrayList<Banco>) ois.readObject();


        } catch (FileNotFoundException e) {
            // Archivo no encontrado, se asume que es la primera vez que se ejecuta la aplicación
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (ObjectInputStream ois = new ObjectInputStream(openFileInput("cuentasBancarias.dat"))) {

            cuentasBancarias = (ArrayList<CuentaBancaria>) ois.readObject();


        } catch (FileNotFoundException e) {
            // Archivo no encontrado, se asume que es la primera vez que se ejecuta la aplicación
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admi_cuentas_bancarias);

        // Inicialización de vistas
        listViewcuentasBancarias = findViewById(R.id.cuentas2);
        fecha2 = findViewById(R.id.fechaz2);
        fechazz = findViewById(R.id.fechazz);
        editTextSearch = findViewById(R.id.editTextSearch);
        listViewNames = findViewById(R.id.listViewNames);
        textView = findViewById(R.id.textView4);
        spinnerAccountType = findViewById(R.id.spinnerAccountType);
        buttonChangeToEditText = findViewById(R.id.buttonChangeToEditText);
        registrarboton = findViewById(R.id.registrar);
        numero = findViewById(R.id.numero);
        fechaApertura = findViewById(R.id.fechaz);
        fechaFinal = findViewById(R.id.fechaFinal);
        saldo = findViewById(R.id.saldo);
        valorInteres = findViewById(R.id.interes);
        Button buttonEliminar = findViewById(R.id.eliminar);
        Button buttonCerrar = findViewById(R.id.cerrar);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarCuentaBancaria();
                guardarDatos();
            }
        });

        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarCuentaBancaria();
                guardarDatos();

            }
        });



        // Crea los valores iniciales
        crearValores();

        // Inicializa la lista de bancos
        bancoList = new ArrayList<>();
        for (Banco banco : bancos) {
            bancoList.add(banco.getNombre());
        }

        // Crea un adaptador para la lista
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bancoList);
        listViewNames.setAdapter(listAdapter);
        adapterCuentasBancarias = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cuentasBancarias);
        listViewcuentasBancarias.setAdapter(adapterCuentasBancarias);

        // Crea y configura el adaptador para el Spinner
        List<String> accountTypes = new ArrayList<>();
        accountTypes.add("Ahorro");
        accountTypes.add("Corriente");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, accountTypes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountType.setAdapter(spinnerAdapter);

        spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();
                traer2(selectedType);
                Toast.makeText(AdmiCuentasBancarias.this, "Seleccionaste: " + selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Manejar el caso cuando no se selecciona nada si es necesario
            }
        });

        // Configura el TextWatcher para buscar en la lista
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        listViewNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtiene el banco seleccionado
                String selectedBank = listAdapter.getItem(position);
                traer(selectedBank);

                if (!selectedBank.isEmpty()) {
                    textView.setText(selectedBank); // Actualiza el TextView con el nombre del banco seleccionado
                    editTextSearch.setVisibility(View.GONE); // Oculta el EditText
                    textView.setVisibility(View.VISIBLE);
                    listViewNames.setVisibility(View.GONE);
                    buttonChangeToEditText.setVisibility(View.VISIBLE); // Muestra el botón para cambiar de nuevo al EditText
                } else {
                    Toast.makeText(AdmiCuentasBancarias.this, "Por favor, selecciona un banco", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonChangeToEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cambia de nuevo al EditText
                editTextSearch.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                listViewNames.setVisibility(View.VISIBLE);
                buttonChangeToEditText.setVisibility(View.GONE);
                editTextSearch.setText(""); // Limpia el texto del EditText si es necesario
            }
        });

        registrarboton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarCuentaBancaria();
                guardarDatos();
            }
        });
    }

    private void traer(String nombre) {
        xd = nombre;
    }

    private void traer2(String tipo) {
        tip = tipo;
    }

    // Método para filtrar la lista
    private void filter(String text) {
        filteredList = new ArrayList<>();
        for (String name : bancoList) {
            // Verifica si el nombre del banco contiene el texto ingresado (sin distinción de mayúsculas)
            if (name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(name);
            }
        }
        listAdapter.clear(); // Limpiar la lista anterior
        listAdapter.addAll(filteredList); // Agregar los nombres de los bancos filtrados
        listAdapter.notifyDataSetChanged(); // Notificar al adaptador sobre los cambios
    }

    private void registrarCuentaBancaria() {
        // Obtiene los valores de los campos de texto y los limpia
        String entidad = xd; // Nombre del banco
        String fechaAp = fechaApertura.getText().toString().trim();
        String telefono2 = numero.getText().toString().trim();
        String fechaCierre = fechaFinal.getText().toString().trim();
        String sinteres = valorInteres.getText().toString().trim();
        String saldos = saldo.getText().toString().trim();

        // Verifica que todos los campos estén completos
        if (entidad == null || entidad.isEmpty() || telefono2.isEmpty() || fechaAp.isEmpty() || fechaCierre.isEmpty() || sinteres.isEmpty() || saldos.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Inicializa la entidad bancaria
        Banco entidadBancaria = null;
        if (entidad == null) {
            Toast.makeText(this, "Entidad no especificada", Toast.LENGTH_SHORT).show();
            return;
        }
        // Busca el banco en la lista de bancos
        for (Banco banco : bancos) {
            if (entidad.equals(banco.getNombre()) || entidad.equals(banco.getIdentificador())) {
                entidadBancaria = banco;
                break;
            }
        }

        // Verifica si se encontró la entidad bancaria
        if (entidadBancaria == null) {
            Toast.makeText(this, "Entidad bancaria no encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        // Intenta convertir los valores a sus tipos respectivos
        try {
            int number = Integer.parseInt(saldos);
            int telefono = Integer.parseInt(telefono2);
            double interes = Double.parseDouble(sinteres);
            int codigoCuentaBancaria = cuentasBancarias.size() + 1;

            // Añade la nueva cuenta bancaria a la lista
            cuentasBancarias.add(new CuentaBancaria(codigoCuentaBancaria, entidadBancaria, telefono, tip, fechaAp, number, interes, fechaCierre));
            adapterCuentasBancarias.notifyDataSetChanged();

            // Muestra un mensaje de éxito
            Toast.makeText(this, "Cuenta bancaria registrada", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            // Muestra un mensaje de error si la conversión falla
            Toast.makeText(this, "Por favor, ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show();
        }
    }
    private void eliminarCuentaBancaria() {
        if (cuentasBancarias.isEmpty()) {
            new AlertDialog.Builder(this)
                    .setMessage("No se encontró ninguna cuenta bancaria")
                    .setPositiveButton("OK", null)
                    .show();
            return;
        }

        // Mostrando las cuentas bancarias en la consola para depuración
        for (int i = 0; i < cuentasBancarias.size(); i++) {
            System.out.println(cuentasBancarias.get(i).toString());
        }

        String c = fecha2.getText().toString().trim().toLowerCase();
        int codigoEliminar;
        try {
            codigoEliminar = Integer.parseInt(c);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Código inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        for (final CuentaBancaria cuentaBancaria : cuentasBancarias) {
            if (cuentaBancaria.getCodigo() == codigoEliminar) {
                new AlertDialog.Builder(this)
                        .setMessage("¿Está seguro que desea eliminar esta cuenta bancaria?")
                        .setTitle("Confirmar eliminación")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            cuentasBancarias.remove(cuentaBancaria);
                            adapterCuentasBancarias.notifyDataSetChanged(); // Notifica al adaptador sobre los cambios
                        })
                        .setNegativeButton("No", null)
                        .show();
                return;
            }
        }
        Toast.makeText(this, "No se encontró ninguna cuenta bancaria con ese código.", Toast.LENGTH_SHORT).show();
    }
    private void cerrarCuentaBancaria() {
        String c = fecha2.getText().toString().trim();
        int codigoCierre;
        try {
            codigoCierre = Integer.parseInt(c);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Código inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        for (CuentaBancaria cuenta : cuentasBancarias) {
            if (cuenta.getCodigo() == codigoCierre) {
                // Mostrar un DatePickerDialog para seleccionar la nueva fecha de cierre
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        LocalDate nuevaFechaCierre = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
                        LocalDate fechaApertura = LocalDate.parse(cuenta.getFechaApertura(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        if (nuevaFechaCierre.isAfter(fechaApertura)) {
                            cuenta.setFechaCierre(nuevaFechaCierre.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            adapterCuentasBancarias.notifyDataSetChanged(); // Actualiza la lista
                            Toast.makeText(AdmiCuentasBancarias.this, "Cuenta Bancaria cerrada correctamente.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AdmiCuentasBancarias.this, "La fecha de cierre debe ser posterior a la fecha de apertura.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());

                datePickerDialog.show();
                return;
            }
        }
        Toast.makeText(this, "No se encontró ninguna Cuenta Bancaria con ese código.", Toast.LENGTH_SHORT).show();
    }
    private void guardarDatos() {
        try (ObjectOutputStream prestamosOut = new ObjectOutputStream(openFileOutput("cuentasBancarias.dat", MODE_PRIVATE));
             ) {

            prestamosOut.writeObject(cuentasBancarias);


        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}