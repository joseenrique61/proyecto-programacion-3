package Entidades;

import Servicios.ManejadorDeUsuarios;

public class Actividad implements EstructurasDeDatos.ElementoDeNodo {
    private final String nombre;

    private final String capacidad;
    private final String descripcion;

    public Actividad(String nombre, /*Emprendimiento emprendimientoAsociado*/String capacidad, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        //this.capacidad = emprendimientoAsociado.getNombre() + nombre;
        this.capacidad = capacidad;
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
        return capacidad;
    }
}
