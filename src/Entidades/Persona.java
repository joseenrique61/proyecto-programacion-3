package Entidades;

public class Persona extends Usuario {
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
}
