package Entidades;

import EstructurasDeDatos.ElementoDeNodo;

public class Persona extends Usuario implements ElementoDeNodo {
    private final String cedula;

    private final String nombre;

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona(String usuario, String contrasena, String cedula, String nombre) {
        super(usuario, contrasena);
        this.cedula = cedula;
        this.nombre = nombre;
    }

    @Override
    public String getIdentificador() {
        return cedula;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", cédula: " + cedula;
    }
}
