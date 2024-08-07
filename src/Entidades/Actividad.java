package Entidades;

import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import java.util.ArrayList;

public class Actividad implements EstructurasDeDatos.ElementoDeNodo {
    private final String id;

    private final String nombre;

    private  int capacidad;

    private final String descripcion;
    private final String nombreEmprendimiento;

    public Actividad(String nombre, Emprendimiento emprendimientoAsociado, int capacidad, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id = emprendimientoAsociado.getNombre() + nombre;
        this.capacidad = capacidad;
        this.nombreEmprendimiento = emprendimientoAsociado.getNombre();
    }

    public boolean agregarPersona(Persona persona) {
        if (capacidad <= 0) {
            return false;
        }

        if (ManejadorDeGrafo.getGrafo().agregarConexion(persona, this)) {
            capacidad--;
            return true;
        }

        return false;
    }

    public boolean eliminarPersona(Persona persona) {
        if (ManejadorDeGrafo.getGrafo().eliminarConexion(this, persona)) {
            capacidad++;
            return true;
        }
        return false;
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

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getEmprendimientoAsociado() {
        return nombreEmprendimiento;
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

    public int getCalificacionPromedio() {
        int suma = 0;
        int cantidad = 0;
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Calificacion) {
                suma += ((Calificacion) elementoDeNodo).getPuntuacion();
                cantidad++;
            }
        }
        return cantidad != 0 ? suma / cantidad : 0;
    }

    public void agregarForo(Foro foro) {
        ManejadorDeGrafo.getGrafo().agregarConexion(foro, this);
    }

    public ArrayList<Foro> visualizarForos() {
        ArrayList<Foro> foros = new ArrayList<>();
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Foro) {
                foros.add((Foro) elementoDeNodo);
            }
        }
        return foros;
    }
    public int getForosIngresados() {
        int cantidad = 0;
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Foro) {
                cantidad++;
            }
        }
        return cantidad;
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
