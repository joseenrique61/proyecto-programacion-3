package Entidades;

public class Calificacion implements Comparable<Calificacion> {
    private int puntuacion;
    private String comentario;
    private Persona autor;

    public Calificacion(int puntuacion, String comentario, Persona autor) {
        this.puntuacion = puntuacion;
        this.comentario = comentario;
        this.autor = autor;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public Persona getAutor() {
        return autor;
    }

    @Override
    public int compareTo(Calificacion a) {
        return Integer.compare(this.puntuacion,a.puntuacion);
    }
}
