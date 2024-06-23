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

    public boolean agregarForo(Foro foro) {
        if (foro == null) {
            return false;
        }

        ManejadorDeGrafo.getGrafo().agregarConexion(foro, foro.getActividad());
        return true;
    }

    public boolean eliminarForo(Foro foro) {
        return ManejadorDeGrafo.getGrafo().eliminarConexion(this, foro);
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
