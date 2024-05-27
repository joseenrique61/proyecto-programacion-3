package Entidades;

import EstructurasDeDatos.ElementoDeNodo;

public class Calificacion implements ElementoDeNodo, Comparable<Calificacion> {
    private final String id;

    private final int puntuacion;

    private final String comentario;

    public Calificacion(int puntuacion, String comentario, Persona autor, Actividad actividadCalificada) {
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.id = autor.getIdentificador() + actividadCalificada.getIdentificador();
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    @Override
    public String getIdentificador() {
        return id;
    }

    @Override
    public int compareTo(Calificacion a) {
        return Integer.compare(this.puntuacion,a.puntuacion);
    }
}
