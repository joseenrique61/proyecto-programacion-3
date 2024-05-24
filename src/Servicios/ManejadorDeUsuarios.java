package Servicios;

import Entidades.Administrador;
import Entidades.Usuario;
import EstructurasDeDatos.Grafo;

import java.util.Objects;

public class ManejadorDeUsuarios {
    private static final Administrador administrador = new Administrador("admin", "admin");

    private static final Grafo grafo = new Grafo();

    public static Grafo getGrafo() {
        return grafo;
    }

    public static Usuario getUsuario(String usuario, String contrasena) {
        if (Objects.equals(administrador.getUsuario(), usuario) && Objects.equals(administrador.getContrasena(), contrasena)) {
            return administrador;
        }

        return grafo.getUsuario(usuario, contrasena);
    }
}
