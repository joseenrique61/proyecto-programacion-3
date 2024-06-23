package Entidades;

import EstructurasDeDatos.ElementoDeNodo;

public class Publicacion  implements ElementoDeNodo {
    private Persona autor;
    private String comentario;
    private Foro foro;

    public Publicacion(Persona autor, String comentario, Foro foro) {
        this.autor = autor;
        this.comentario = comentario;
        this.foro = foro;
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

    @Override
    public String toString() {
        return  "Autor=" + autor + " \nComentario='" + comentario +"\n";
    }

    @Override
    public String getIdentificador() {
        return foro.getIdentificador()+autor.getIdentificador();
    }
}