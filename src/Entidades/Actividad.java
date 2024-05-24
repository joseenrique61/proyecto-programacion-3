package Entidades;

public class Actividad {
    private final String nombre;

    private final int id;

    public Actividad(String nombre, int id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

}
