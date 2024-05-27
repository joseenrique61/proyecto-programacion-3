package Entidades;

import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import java.util.ArrayList;

public class Actividad implements EstructurasDeDatos.ElementoDeNodo {
    private final String id;

    private final String nombre;

    private final int capacidad;

    private final String descripcion;

    public Actividad(String nombre, Emprendimiento emprendimientoAsociado, int capacidad, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = emprendimientoAsociado.getNombre() + nombre;
        this.capacidad = capacidad;
    }

    public boolean agregarPersona(Persona persona) {
        if (persona == null) {
            return false;
        }
        ManejadorDeGrafo.getGrafo().agregarConexion(persona, this);
        return true;
    }

    public boolean eliminarPersona(Persona persona) {
        return ManejadorDeGrafo.getGrafo().eliminarConexion(this, persona);
    }

    public ArrayList<Persona> visualizarPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Persona) {
                personas.add((Persona) elementoDeNodo);
            }
        }
        return personas;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String getIdentificador() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getPersonasInscritas() {
        int cantidad = 0;
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Persona) {
                cantidad++;
            }
        }
        return cantidad;
    }

    public float getCalificacionPromedio() {
        float suma = 0;
        float cantidad = 0;
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Calificacion) {
                suma += ((Calificacion) elementoDeNodo).getPuntuacion();
                cantidad++;
            }
        }
        return cantidad != 0 ? suma / cantidad : 0;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String toStringLargo() {
        StringBuilder personas = new StringBuilder();
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Persona) {
                personas.append(elementoDeNodo);
            }
        }
        return "Nombre: " + nombre +
                "\n Capacidad: " + capacidad +
                "\n Descripción: " + descripcion +
                "\n Calificación promedio: " + getCalificacionPromedio() +
                "\n Personas inscritas: " + personas;
    }
}
