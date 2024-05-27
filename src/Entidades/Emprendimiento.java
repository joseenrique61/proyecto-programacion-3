package Entidades;

import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Emprendimiento extends Usuario implements ElementoDeNodo {
    private final String nombre;
    private final String ubicacion;

    public String getNombre() {
        return nombre;
    }

    public Emprendimiento(String usuario, String contrasena, String nombre, String ubicacion) {
        super(usuario, contrasena);
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public boolean addActividad(Actividad actividad){
        if (!ManejadorDeGrafo.getGrafo().agregarElemento(actividad)) {
            JOptionPane.showMessageDialog(null, "Ya existe una actividad con este nombre.");
            return false;
        }
        ManejadorDeGrafo.getGrafo().agregarConexion(actividad, this);
        return true;
    }

    public ArrayList<Actividad> getActividades() {
        ArrayList<Actividad> actividades = new ArrayList<>();
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Actividad) {
                actividades.add((Actividad) elementoDeNodo);
            }
        }
        return actividades;
    }

    public void deleteActividad(Actividad actividad){
        ManejadorDeGrafo.getGrafo().eliminarElemento(actividad);
    }

    public int getCantidadPersonasEnActividades() {
        Set<Persona> personas = new HashSet<>();
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Actividad) {
                personas.addAll(((Actividad)elementoDeNodo).visualizarPersonas());
            }
        }
        return personas.size();
    }

    public float getCalificacion() {
        float sumaCalificacion = 0;
        float cantidad = 0;
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getConexiones(this)) {
            if (elementoDeNodo instanceof Actividad) {
                sumaCalificacion += ((Actividad)elementoDeNodo).getCalificacionPromedio();
                cantidad++;
            }
        }
        return cantidad != 0 ? sumaCalificacion / cantidad : 0;
    }

    @Override
    public String getIdentificador() {
        return nombre;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", ubicación: " + ubicacion;
    }
}
