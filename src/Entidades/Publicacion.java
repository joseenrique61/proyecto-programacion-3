package Entidades;

import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

public class Publicacion  implements ElementoDeNodo {
    private final String comentario;

    private final LocalDateTime fechaHora;

    private final int numeroComentario;

    private final String id;

    public Publicacion(Persona autor, String comentario, Foro foro) {
        this.numeroComentario = foro.getCantidadComentarios() + 1;

        this.id = foro.getIdentificador() + autor.getIdentificador() + numeroComentario;

        this.comentario = comentario;
        this.fechaHora = LocalDateTime.now();
    }

    public Persona getAutor() {
        return (Persona) ManejadorDeGrafo.getGrafo().getConexiones(this).get(0);
    }

    public String getComentario() {
        return comentario;
    }

    public Foro getForo() {
        return (Foro) ManejadorDeGrafo.getGrafo().getConexiones(this).get(1);
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getAutor().getNombre() + ": " + comentario + "\n" + fechaHora.format(formatter) + "\n";
    }

    @Override
    public String getIdentificador() {
        return id;
    }
}