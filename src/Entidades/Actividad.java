package Entidades;

public class Actividad implements EstructurasDeDatos.ElementoDeNodo {
    private final String nombre;

    private final String id;

    public Actividad(String nombre, Emprendimiento emprendimientoAsociado) {
        this.nombre = nombre;
        this.id = emprendimientoAsociado.getNombre() + nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String getIdentificador() {
        return id;
    }
}
