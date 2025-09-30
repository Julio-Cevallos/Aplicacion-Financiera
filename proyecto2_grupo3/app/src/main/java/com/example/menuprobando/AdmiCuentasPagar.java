package com.example.menuprobando;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo2.Banco;
import com.example.menuprobando.modelo2.Cobrar;
import com.example.menuprobando.modelo2.Pagar;
import com.example.menuprobando.modelo2.Persona;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdmiCuentasPagar extends AppCompatActivity {

    private List<Persona> personasRegistradas = new ArrayList<>();
    private List<Banco> bancosRegistrados = new ArrayList<>();
    private List<Pagar> pagarRegistrados = new ArrayList<>();
    private ListView lvDeudas;
    private ArrayAdapter<Pagar> adapter;
    Pagar as;

    private Button agregarDeudaButton;

    private static final String PERSONAS_FILE = "personas.dat";
    private static final String BANCOS_FILE = "bancos.dat";
    private static final String PAGAR_FILE = "pagar.dat";

    public void traer(Pagar xd){
        as= xd;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuentas_pagar);

        lvDeudas = findViewById(R.id.lvDeudas);
        agregarDeudaButton = findViewById(R.id.btnRegistrarDeuda);

        // Cargar datos desde almacenamiento
        cargarDatos();

        // Inicializar datos predeterminados si no existen
        if (personasRegistradas.isEmpty() && bancosRegistrados.isEmpty() && pagarRegistrados.isEmpty()) {
            inicializarDatos();
        }

        agregarDeudaButton.setOnClickListener(v -> mostrarFormularioRegistro());

        lvDeudas.setOnItemClickListener((parent, view, position, id) -> {
            Pagar deudaSeleccionada = pagarRegistrados.get(position);
            mostrarDetallesDeuda(deudaSeleccionada);
        });

        adapter = new ArrayAdapter<Pagar>(this, android.R.layout.simple_list_item_2, android.R.id.text1, pagarRegistrados) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, parent, false);
                }
                Pagar pagar = pagarRegistrados.get(position);
                TextView text1 = convertView.findViewById(android.R.id.text1);
                TextView text2 = convertView.findViewById(android.R.id.text2);
                text1.setText(pagar.toSimpleString()); // Mostrar nombre y descripción
                text2.setText(""); // Vacío ya que no queremos mostrar más información aquí
                return convertView;
            }
        };
        lvDeudas.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarDatos();
    }

    private void inicializarDatos() {
        // Crear personas y bancos predeterminados
        Persona persona1 = new Persona("Juan Pérez", "123456789", "juan.perez@gmail.com", "123456789");
        Persona persona2 = new Persona("Ana Gómez", "987654321", "ana.gomez@gmail.com", "987654321");

        Banco banco1 = new Banco("Banco Nacional", "555-1234", "contacto@banco.com", "001", "Sr. López");
        Banco banco2 = new Banco("Banco Internacional", "555-5678", "info@internacional.com", "002", "Sra. Martínez");

        personasRegistradas.add(persona1);
        personasRegistradas.add(persona2);

        bancosRegistrados.add(banco1);
        bancosRegistrados.add(banco2);

        // Crear deudas predeterminadas
        Pagar deuda1 = new Pagar(5000, "Préstamo personal", "2024-08-25", 12, "2024-09-01", "2025-08-01", persona1, 5);
        Pagar deuda2 = new Pagar(20000, "Préstamo empresarial", "2024-09-01", 24, "2024-10-01", "2026-09-01", banco1, 3);

        pagarRegistrados.add(deuda1);
        pagarRegistrados.add(deuda2);

        // Actualizar el adaptador
        adapter.notifyDataSetChanged();
    }

    private void mostrarFormularioRegistro() {
        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_registrar_deuda, null);
        final Spinner spinnerTipo = viewInflated.findViewById(R.id.spinnerTipo);
        final EditText editTextCedula = viewInflated.findViewById(R.id.editTextCedula);
        final EditText editTextCantidad = viewInflated.findViewById(R.id.editTextCantidad);
        final EditText editTextDescripcion = viewInflated.findViewById(R.id.editTextDescripcion);
        final EditText editTextFechaPagar = viewInflated.findViewById(R.id.editTextFechaPagar);
        final EditText editTextCuotas = viewInflated.findViewById(R.id.editTextCuotas);
        final EditText editTextFechaInicioPago = viewInflated.findViewById(R.id.editTextFechaInicioPago);
        final EditText editTextFechaFinPago = viewInflated.findViewById(R.id.editTextFechaFinPago);
        final EditText editTextInteres = viewInflated.findViewById(R.id.editTextInteres);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipos_acreedores, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipo.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Deuda")
                .setView(viewInflated)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    try {
                        String tipoAcreedor = spinnerTipo.getSelectedItem().toString();
                        String cedula = editTextCedula.getText().toString();
                        double cantidad = Double.parseDouble(editTextCantidad.getText().toString());
                        String descripcion = editTextDescripcion.getText().toString();
                        String fechaPagar = editTextFechaPagar.getText().toString();
                        int cuotas = Integer.parseInt(editTextCuotas.getText().toString());
                        String fechaInicioPago = editTextFechaInicioPago.getText().toString();
                        String fechaFinPago = editTextFechaFinPago.getText().toString();
                        int interes = Integer.parseInt(editTextInteres.getText().toString());

                        if (tipoAcreedor.equals("Persona")) {
                            Persona persona = buscarPersona(cedula);
                            if (persona == null) {
                                mostrarFormularioRegistroPersona(cedula, cantidad, descripcion, fechaPagar, cuotas, fechaInicioPago, fechaFinPago, interes);
                            } else {
                                Pagar prestamo = new Pagar(cantidad, descripcion, fechaPagar, cuotas, fechaInicioPago, fechaFinPago, persona, interes);
                                pagarRegistrados.add(prestamo);
                                adapter.notifyDataSetChanged(); // Actualiza la lista
                            }
                        } else if (tipoAcreedor.equals("Banco")) {
                            Banco banco = buscarBanco(cedula);
                            if (banco == null) {
                                mostrarFormularioRegistroBanco(cedula, cantidad, descripcion, fechaPagar, cuotas, fechaInicioPago, fechaFinPago, interes);
                            } else {
                                Pagar prestamo = new Pagar(cantidad, descripcion, fechaPagar, cuotas, fechaInicioPago, fechaFinPago, banco, interes);
                                pagarRegistrados.add(prestamo);
                                adapter.notifyDataSetChanged(); // Actualiza la lista
                            }
                        }
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Datos inválidos. Por favor, revisa los campos ingresados.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void mostrarFormularioRegistroPersona(String cedula, double cantidad, String descripcion, String fechaPagar, int cuotas, String fechaInicioPago, String fechaFinPago, int interes) {
        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_registrar_persona, null);
        final EditText editTextNombre = viewInflated.findViewById(R.id.editTextNombre);
        final EditText editTextTelefono = viewInflated.findViewById(R.id.editTextTelefono);
        final EditText editTextEmail = viewInflated.findViewById(R.id.editTextEmail);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Persona")
                .setView(viewInflated)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nombre = editTextNombre.getText().toString();
                    String telefono = editTextTelefono.getText().toString();
                    String email = editTextEmail.getText().toString();

                    Persona nuevaPersona = new Persona(nombre, telefono, email, cedula);
                    personasRegistradas.add(nuevaPersona);

                    Pagar prestamo = new Pagar(cantidad, descripcion, fechaPagar, cuotas, fechaInicioPago, fechaFinPago, nuevaPersona, interes);
                    pagarRegistrados.add(prestamo);
                    adapter.notifyDataSetChanged(); // Actualiza la lista
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void mostrarFormularioRegistroBanco(String cedula, double cantidad, String descripcion, String fechaPagar, int cuotas, String fechaInicioPago, String fechaFinPago, int interes) {
        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_registrar_banco, null);
        final EditText editTextNombreBanco = viewInflated.findViewById(R.id.editTextNombreBanco);
        final EditText editTextTelefonoBanco = viewInflated.findViewById(R.id.editTextTelefonoBanco);
        final EditText editTextEmailBanco = viewInflated.findViewById(R.id.editTextEmailBanco);
        final EditText editTextContactoBanco = viewInflated.findViewById(R.id.editTextContactoBanco);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Banco")
                .setView(viewInflated)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nombreBanco = editTextNombreBanco.getText().toString();
                    String telefonoBanco = editTextTelefonoBanco.getText().toString();
                    String emailBanco = editTextEmailBanco.getText().toString();
                    String contactoBanco = editTextContactoBanco.getText().toString();

                    Banco nuevoBanco = new Banco(nombreBanco, telefonoBanco, emailBanco, cedula, contactoBanco);
                    bancosRegistrados.add(nuevoBanco);

                    Pagar prestamo = new Pagar(cantidad, descripcion, fechaPagar, cuotas, fechaInicioPago, fechaFinPago, nuevoBanco, interes);
                    pagarRegistrados.add(prestamo);
                    adapter.notifyDataSetChanged(); // Actualiza la lista
                })
                .setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void mostrarDetallesDeuda(Pagar deudaSeleccionada) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Detalles de la Deuda")
                .setMessage(deudaSeleccionada.toString())
                .setPositiveButton("Aceptar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private Persona buscarPersona(String cedula) {
        for (Persona persona : personasRegistradas) {
            if (persona.getIdentificador().equals(cedula)) {
                return persona;
            }
        }
        return null;
    }

    private Banco buscarBanco(String cedula) {
        for (Banco banco : bancosRegistrados) {
            if (banco.getIdentificador().equals(cedula)) {
                return banco;
            }
        }
        return null;
    }

    private void guardarDatos() {
        try {
            FileOutputStream fosPersonas = openFileOutput("personas.dat", MODE_PRIVATE);
            ObjectOutputStream oosPersonas = new ObjectOutputStream(fosPersonas);
            oosPersonas.writeObject(personasRegistradas);
            oosPersonas.close();
            fosPersonas.close();

            FileOutputStream fosBancos = openFileOutput("bancos.dat", MODE_PRIVATE);
            ObjectOutputStream oosBancos = new ObjectOutputStream(fosBancos);
            oosBancos.writeObject(bancosRegistrados);
            oosBancos.close();
            fosBancos.close();

            FileOutputStream fosPagar = openFileOutput("pagar.dat", MODE_PRIVATE);
            ObjectOutputStream oosPagar = new ObjectOutputStream(fosPagar);
            oosPagar.writeObject(pagarRegistrados);
            oosPagar.close();
            fosPagar.close();

            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar los datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }




    private void cargarDatos() {
        try {
            // Cargar personas
            FileInputStream fisPersonas = openFileInput("personas.dat");
            ObjectInputStream oisPersonas = new ObjectInputStream(fisPersonas);
            personasRegistradas = (List<Persona>) oisPersonas.readObject();
            oisPersonas.close();
            fisPersonas.close();

            // Cargar bancos
            FileInputStream fisBancos = openFileInput("bancos.dat");
            ObjectInputStream oisBancos = new ObjectInputStream(fisBancos);
            bancosRegistrados = (List<Banco>) oisBancos.readObject();
            oisBancos.close();
            fisBancos.close();

            // Cargar deudas
            FileInputStream fisPagar = openFileInput("pagar.dat");
            ObjectInputStream oisPagar = new ObjectInputStream(fisPagar);
            pagarRegistrados = (List<Pagar>) oisPagar.readObject();
            oisPagar.close();
            fisPagar.close();

        } catch (InvalidClassException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error de clase no válida: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(this, "Error al cargar datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
