package Entidades;

import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeUsuarios;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Emprendimiento extends Usuario implements ElementoDeNodo {
    private final String nombre;
    private String ubicacion;
    private ArrayList<Calificacion> listacalificacion;
    public String getNombre() {
        return nombre;
    }

    public Emprendimiento(String usuario, String contrasena, String nombre,String ubicacion) {
        super(usuario, contrasena);
        listacalificacion=new ArrayList<>();
        this.nombre = nombre;
        this.ubicacion = nombre;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void addActividad(Actividad actividad,Emprendimiento emprendimiento){
        ManejadorDeUsuarios.getGrafo().agregarElemento(actividad);
        ManejadorDeUsuarios.getGrafo().agregarConexion(actividad,emprendimiento);
    }
    public void deleteActividad(Actividad actividad){
        ManejadorDeUsuarios.getGrafo().eliminarElemento(actividad);
    }
    public String getCalificacion() {
        return "";
    }
    public String CalificacionesPorPuntaje() {
        StringBuilder respuesta = new StringBuilder();
        if (listacalificacion.isEmpty()){
            JOptionPane.showMessageDialog(null,"No hay elementos en la lista");

        }
        else {
            Collections.sort(listacalificacion);
            respuesta.append("Lista de calificaciones ordenadas:\n");
            for (Calificacion calificacion : listacalificacion) {
                respuesta.append("Nombre:").append(calificacion.getAutor()).append("\n");
                respuesta.append("Comentario:").append(calificacion.getComentario()).append("\n");
                respuesta.append("Puntuacion:").append(calificacion.getPuntuacion()).append("\n");
                respuesta.append("-------------------\n");
            }
        }
        return respuesta.toString();
    }

    @Override
    public String getIdentificador() {
        return nombre;
    }
}
