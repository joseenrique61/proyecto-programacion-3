package Entidades;

import Servicios.ManejadorDeUsuarios;

import javax.swing.*;
import java.util.Collections;

public class Actividad implements EstructurasDeDatos.ElementoDeNodo {
    private final String nombre;

    private final String id;

    public Actividad(String nombre, Emprendimiento emprendimientoAsociado) {
        this.nombre = nombre;
        this.id = emprendimientoAsociado.getNombre() + nombre;
    }
    public void asignarUsuarios(Persona usuario,Actividad actividad){
        ManejadorDeUsuarios.getGrafo().agregarElemento(usuario);
        ManejadorDeUsuarios.getGrafo().agregarConexion(usuario,actividad);

    }
    public void eliminarUsuarios(Persona usuario){
        ManejadorDeUsuarios.getGrafo().eliminarElemento(usuario);
    }

    public String visualizarUsuariosActividad(Actividad actividad){
        ManejadorDeUsuarios.getGrafo().getConexiones(actividad);
        return "";
    }
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getIdentificador() {
        return id;
    }
}
