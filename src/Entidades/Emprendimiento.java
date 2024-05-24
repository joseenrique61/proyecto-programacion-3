package Entidades;

import EstructurasDeDatos.ElementoDeNodo;

public class Emprendimiento extends Usuario implements ElementoDeNodo {
    private final String nombre;

    public String getNombre() {
        return nombre;
    }

    public Emprendimiento(String usuario, String contrasena, String nombre) {
        super(usuario, contrasena);
        this.nombre = nombre;
    }

    @Override
    public String getIdentificador() {
        return nombre;
    }
}
