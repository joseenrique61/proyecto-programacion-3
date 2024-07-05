package Entidades;

import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;


public class Foro implements ElementoDeNodo {
    private final String id;

    private int cantidadComentarios = 0;

    public Foro(Actividad actividad) {
        this.id = "Foro" + actividad.getIdentificador();
    }

    public Actividad getActividad() {
        return (Actividad) ManejadorDeGrafo.getGrafo().getConexiones(this).get(0);
    }

    @Override
    public String getIdentificador() {
        return id;
    }

    public int getCantidadComentarios() {
        return cantidadComentarios;
    }

    public void agregarComentario(Publicacion publicacion) {
        ManejadorDeGrafo.getGrafo().agregarConexion(this, publicacion);
        cantidadComentarios++;
    }

    public String getComentarios() {
        String comentarios = "";

        for (int i = ManejadorDeGrafo.getGrafo().getConexiones(this).size() - 1; i > 0; i--) {
            comentarios += ManejadorDeGrafo.getGrafo().getConexiones(this).get(i) + "--------------\n";
        }

        return comentarios;
    }
}
