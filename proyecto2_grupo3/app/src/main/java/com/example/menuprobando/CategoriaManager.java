package com.example.menuprobando;

import android.content.Context;

import com.example.menuprobando.modelo1.Categoria;
import com.example.menuprobando.modelo1.Gasto;
import com.example.menuprobando.modelo1.Ingreso;
import com.example.menuprobando.modelo2.Persona;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class CategoriaManager {
    private Context context;

    public CategoriaManager(Context context) {
        this.context = context;
    }

    // Obtener categorías de ingresos
    public ArrayList<Categoria> obtenerCategoriasIngresos() {
        ArrayList<Categoria> categoriasIngresos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(context.openFileInput("categorias_ingresos.dat"))) {
            categoriasIngresos = (ArrayList<Categoria>) ois.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, puedes manejar esto si es necesario
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categoriasIngresos;
    }

    // Obtener categorías de gastos
    public ArrayList<Categoria> obtenerCategoriasGastos() {
        ArrayList<Categoria> categoriasGastos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(context.openFileInput("categorias_gastos.dat"))) {
            categoriasGastos = (ArrayList<Categoria>) ois.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, puedes manejar esto si es necesario
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categoriasGastos;
    }

    // Guardar categorías de ingresos y gastos
    public void guardarCategorias(ArrayList<Categoria> categoriasIngresos, ArrayList<Categoria> categoriasGastos) {
        try (ObjectOutputStream oosIngresos = new ObjectOutputStream(context.openFileOutput("categorias_ingresos.dat", Context.MODE_PRIVATE));
             ObjectOutputStream oosGastos = new ObjectOutputStream(context.openFileOutput("categorias_gastos.dat", Context.MODE_PRIVATE))) {

            oosIngresos.writeObject(categoriasIngresos);
            oosGastos.writeObject(categoriasGastos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Agregar nueva categoría
    public void agregarCategoria(Categoria categoria, boolean esIngreso) {
        ArrayList<Categoria> categorias = esIngreso ? obtenerCategoriasIngresos() : obtenerCategoriasGastos();

        if (!categoriaExiste(categorias, categoria.getNombreCategoria())) {
            categorias.add(categoria);
            guardarCategorias(
                    esIngreso ? categorias : obtenerCategoriasIngresos(),
                    esIngreso ? obtenerCategoriasGastos() : categorias
            );
        }
    }

    // Verificar si la categoría ya existe
    private boolean categoriaExiste(ArrayList<Categoria> listaCategorias, String nombreCategoria) {
        for (Categoria categoria : listaCategorias) {
            if (categoria.getNombreCategoria().equalsIgnoreCase(nombreCategoria)) {
                return true;
            }
        }
        return false;
    }

    // Métodos para obtener ingresos y gastos
    public ArrayList<Ingreso> obtenerIngresos() {
        ArrayList<Ingreso> ingresos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(context.openFileInput("ingresos.dat"))) {
            ingresos = (ArrayList<Ingreso>) ois.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, puedes manejar esto si es necesario
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return ingresos;
    }

    public ArrayList<Gasto> obtenerGastos() {
        ArrayList<Gasto> gastos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(context.openFileInput("gastos.dat"))) {
            gastos = (ArrayList<Gasto>) ois.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, puedes manejar esto si es necesario
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gastos;
    }




    //Cosas relacionadas con personas bancos etc
    public ArrayList<Persona> obtenerPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(context.openFileInput("personas.dat"))) {
            personas = (ArrayList<Persona>) ois.readObject();
        } catch (FileNotFoundException e) {
            // El archivo no existe, puedes manejar esto si es necesario
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return personas;
    }

    // Guardar personas
    public void guardarPersonas(ArrayList<Persona> personas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(context.openFileOutput("personas.dat", Context.MODE_PRIVATE))) {
            oos.writeObject(personas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Agregar nueva persona
    public void agregarPersona(Persona persona) {
        ArrayList<Persona> personas = obtenerPersonas();

        if (!personaExiste(personas, persona.getNombre())) {
            personas.add(persona);
            guardarPersonas(personas);
        }
    }

    // Verificar si la persona ya existe
    private boolean personaExiste(ArrayList<Persona> listaPersonas, String nombrePersona) {
        for (Persona persona : listaPersonas) {
            if (persona.getNombre().equalsIgnoreCase(nombrePersona)) {
                return true;
            }
        }
        return false;
    }


}
