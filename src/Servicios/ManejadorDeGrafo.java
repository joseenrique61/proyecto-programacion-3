package Servicios;

import Entidades.Administrador;
import Entidades.Usuario;
import EstructurasDeDatos.ElementoDeNodo;
import EstructurasDeDatos.Grafo;

import java.util.Objects;

public class ManejadorDeGrafo {
    private static final Administrador administrador = new Administrador("admin", "admin");

    private static final Grafo grafo = new Grafo();

    public static Grafo getGrafo() {
        return grafo;
    }

    public static Usuario getUsuario(String usuario, String contrasena) {
        if (Objects.equals(administrador.getUsuario(), usuario) && Objects.equals(administrador.getContrasena(), contrasena)) {
            return administrador;
        }

        for (ElementoDeNodo elemento : grafo.getValues()) {
            if (elemento instanceof Usuario && Objects.equals(((Usuario)elemento).getUsuario(), usuario) && Objects.equals(((Usuario)elemento).getContrasena(), contrasena)) {
                return (Usuario) elemento;
            }
        }

        return null;
    }
}
