package Entidades;

import EstructurasDeDatos.ElementoDeNodo;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;

public class Publicacion  implements ElementoDeNodo {
    private Persona autor;
    private String comentario;
    private Foro foro;

    private LocalDateTime fecha_hora;


    public Publicacion(Persona autor, String comentario, Foro foro) {
        this.autor = autor;
        this.comentario = comentario;
        this.foro = foro;
        this.fecha_hora=LocalDateTime.now();
    }

    public Persona getAutor() {
        return autor;
    }

    public String getComentario() {
        return comentario;
    }

    public Foro getForo() {
        return foro;
    }

    public LocalDateTime getFecha_hora() {
        return fecha_hora;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  "Autor=" + autor + " \nComentario='" + comentario +"\n"+ "\nFecha y Hora='" + fecha_hora.format(formatter) + "\n";
    }

    @Override
    public String getIdentificador() {
        return foro.getIdentificador()+autor.getIdentificador();
}
}