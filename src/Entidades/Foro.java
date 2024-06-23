package Entidades;

import EstructurasDeDatos.ElementoDeNodo;


public class Foro implements ElementoDeNodo {
    private final String id;
    private Actividad actividad;


    public Foro(Emprendimiento ea, Actividad actividad) {
        this.id = actividad.getNombre()+ea.getNombre();
        this.actividad = actividad;

    }

    public Actividad getActividad() {
        return actividad;
    }

    @Override
    public String getIdentificador() {
        return id;
    }
}
