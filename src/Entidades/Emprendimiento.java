package Entidades;

public class Emprendimiento extends Usuario {
    private final String nombre;

    public String getNombre() {
        return nombre;
    }

    public Emprendimiento(String usuario, String contrasena, String nombre) {
        super(usuario, contrasena);
        this.nombre = nombre;
    }
}
