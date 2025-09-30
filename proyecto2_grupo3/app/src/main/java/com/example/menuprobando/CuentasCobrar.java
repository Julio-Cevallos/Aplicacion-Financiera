package com.example.menuprobando;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menuprobando.modelo2.Cobrar;
import com.example.menuprobando.modelo2.Persona;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class CuentasCobrar extends AppCompatActivity {

    private ArrayList<Cobrar> prestamosRegistrados = new ArrayList<>();
    private ArrayList<Persona> personasRegistradas = new ArrayList<>();
    private ListView prestamosListView;
    private Button agregarPrestamoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cuentas_cobrar);

        prestamosListView = findViewById(R.id.prestamosListView);
        agregarPrestamoButton = findViewById(R.id.agregarPrestamoButton);

        // Inicializar datos de ejemplo
        inicializarDatosDeudores();
        inicializarDatosPrestamos();

        cargarDatos();

        // Cargar préstamos registrados
        cargarPrestamos();

        agregarPrestamoButton.setOnClickListener(v -> mostrarFormularioRegistro());

        prestamosListView.setOnItemClickListener((parent, view, position, id) -> {
            Cobrar prestamoSeleccionado = prestamosRegistrados.get(position);
            mostrarDetallesPrestamo(prestamoSeleccionado);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        guardarDatos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        guardarDatos();
    }

    private void inicializarDatosDeudores() {
        // Crear instancias de Persona con datos iniciales
        Persona persona1 = new Persona("Juan Pérez", "1234567890", "juan@example.com", "001");
        Persona persona2 = new Persona("Ana Gómez", "0987654321", "ana@example.com", "002");

        // Añadir las instancias a la lista de personas registradas
        personasRegistradas.add(persona1);
        personasRegistradas.add(persona2);
    }

    private void inicializarDatosPrestamos() {
        // Crear instancias de Cobrar con datos iniciales
        Cobrar prestamo1 = new Cobrar(5000, "Préstamo de estudio", "01/01/2024", 12, "01/01/2024", "01/01/2025", personasRegistradas.get(0));
        Cobrar prestamo2 = new Cobrar(2000, "Préstamo personal", "15/02/2024", 6, "15/02/2024", "15/08/2024", personasRegistradas.get(1));

        // Añadir las instancias a la lista de préstamos registrados
        prestamosRegistrados.add(prestamo1);
        prestamosRegistrados.add(prestamo2);
    }

    private void cargarPrestamos() {
        ArrayList<String> prestamosInfo = new ArrayList<>();
        for (Cobrar prestamo : prestamosRegistrados) {
            prestamosInfo.add(prestamo.getDeudor().getNombre() + " - " + prestamo.getDescripcion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prestamosInfo);
        prestamosListView.setAdapter(adapter);
    }

    private void mostrarFormularioRegistro() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Préstamo");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_registrar_prestamo, null);
        builder.setView(view);

        final EditText cedulaEditText = view.findViewById(R.id.cedulaEditText);
        final EditText cantidadEditText = view.findViewById(R.id.cantidadEditText);
        final EditText descripcionEditText = view.findViewById(R.id.descripcionEditText);
        final EditText fechaPrestamoEditText = view.findViewById(R.id.fechaPrestamoEditText);
        final EditText cuotasEditText = view.findViewById(R.id.cuotasEditText);
        final EditText fechaInicioEditText = view.findViewById(R.id.fechaInicioEditText);
        final EditText fechaFinEditText = view.findViewById(R.id.fechaFinEditText);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String cedula = cedulaEditText.getText().toString().trim();
            String descripcion = descripcionEditText.getText().toString().trim();
            String fechaPrestamo = fechaPrestamoEditText.getText().toString().trim();
            double cantidad;
            int cuotas;
            String fechaInicio;
            String fechaFin;

            try {
                cantidad = Double.parseDouble(cantidadEditText.getText().toString().trim());
                cuotas = Integer.parseInt(cuotasEditText.getText().toString().trim());
                fechaInicio = fechaInicioEditText.getText().toString().trim();
                fechaFin = fechaFinEditText.getText().toString().trim();
            } catch (NumberFormatException e) {
                Toast.makeText(CuentasCobrar.this, "Por favor, ingrese valores válidos.", Toast.LENGTH_SHORT).show();
                return;
            }

            Persona deudor = null;
            for (Persona persona : personasRegistradas) {
                if (persona.getIdentificador().equalsIgnoreCase(cedula)) {
                    deudor = persona;
                    break;
                }
            }

            if (deudor == null) {
                // Mostrar otro diálogo para registrar al deudor
                mostrarFormularioDeudor(cedula, cantidad, descripcion, fechaPrestamo, cuotas, fechaInicio, fechaFin);
            } else {
                registrarPrestamo(cantidad, descripcion, fechaPrestamo, cuotas, fechaInicio, fechaFin, deudor);
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void mostrarFormularioDeudor(final String cedula, final double cantidad, final String descripcion, final String fechaPrestamo,
                                         final int cuotas, final String fechaInicio, final String fechaFin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrar Deudor");

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_registrar_deudor, null);
        builder.setView(view);

        final EditText nombreEditText = view.findViewById(R.id.nombreEditText);
        final EditText telefonoEditText = view.findViewById(R.id.telefonoEditText);
        final EditText emailEditText = view.findViewById(R.id.emailEditText);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = nombreEditText.getText().toString().trim();
            String telefono = telefonoEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();

            if (nombre.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
                Toast.makeText(CuentasCobrar.this, "Complete todos los campos del deudor.", Toast.LENGTH_SHORT).show();
                return;
            }

            Persona nuevoDeudor = new Persona(nombre, telefono, email, cedula);
            personasRegistradas.add(nuevoDeudor);
            registrarPrestamo(cantidad, descripcion, fechaPrestamo, cuotas, fechaInicio, fechaFin, nuevoDeudor);
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void registrarPrestamo(double cantidad, String descripcion, String fechaPrestamo, int cuotas, String fechaInicio, String fechaFin, Persona deudor) {
        Cobrar nuevoPrestamo = new Cobrar(cantidad, descripcion, fechaPrestamo, cuotas, fechaInicio, fechaFin, deudor);
        prestamosRegistrados.add(nuevoPrestamo);
        cargarPrestamos(); // Actualiza la lista mostrada
        Toast.makeText(this, "Préstamo registrado correctamente.", Toast.LENGTH_SHORT).show();
    }

    private void mostrarDetallesPrestamo(Cobrar prestamo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Detalles del Préstamo");

        // Crear un LinearLayout para el contenido del diálogo
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        // Crear y agregar TextViews con etiquetas y valores
        TextView cedulaTextView = new TextView(this);
        cedulaTextView.setText("Cédula: " + prestamo.getDeudor().getIdentificador());
        layout.addView(cedulaTextView);

        TextView cantidadTextView = new TextView(this);
        cantidadTextView.setText("Cantidad: " + prestamo.getCantidad());
        layout.addView(cantidadTextView);

        TextView descripcionTextView = new TextView(this);
        descripcionTextView.setText("Descripción: " + prestamo.getDescripcion());
        layout.addView(descripcionTextView);

        TextView fechaPrestamoTextView = new TextView(this);
        fechaPrestamoTextView.setText("Fecha del préstamo: " + prestamo.getFechaPrestamo());
        layout.addView(fechaPrestamoTextView);

        TextView cuotasTextView = new TextView(this);
        cuotasTextView.setText("Número de cuotas: " + prestamo.getCuotasPago());
        layout.addView(cuotasTextView);

        TextView fechaInicioTextView = new TextView(this);
        fechaInicioTextView.setText("Fecha de inicio: " + prestamo.getFechaInicio());
        layout.addView(fechaInicioTextView);

        TextView fechaFinTextView = new TextView(this);
        fechaFinTextView.setText("Fecha de fin: " + prestamo.getFechaFinal());
        layout.addView(fechaFinTextView);

        builder.setView(layout);

        builder.setPositiveButton("Cerrar", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void guardarDatos() {
        try (ObjectOutputStream prestamosOut = new ObjectOutputStream(openFileOutput("prestamos.dat", MODE_PRIVATE));
             ObjectOutputStream personasOut = new ObjectOutputStream(openFileOutput("personas.dat", MODE_PRIVATE))) {

            prestamosOut.writeObject(prestamosRegistrados);
            personasOut.writeObject(personasRegistradas);

        } catch (IOException e) {
            Toast.makeText(this, "Error al guardar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDatos() {
        try (ObjectInputStream prestamosIn = new ObjectInputStream(openFileInput("prestamos.dat"));
             ObjectInputStream personasIn = new ObjectInputStream(openFileInput("personas.dat"))) {

            prestamosRegistrados = (ArrayList<Cobrar>) prestamosIn.readObject();
            personasRegistradas = (ArrayList<Persona>) personasIn.readObject();

        } catch (IOException | ClassNotFoundException e) {

        }
    }
}

