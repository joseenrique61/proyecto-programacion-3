package EstructurasDeDatos;

import Entidades.Actividad;
import Entidades.Emprendimiento;
import Entidades.Persona;
import Entidades.Usuario;

import java.util.*;

public class Grafo {
    private final Map<String, Nodo> grafo = new Hashtable<>();

    public boolean agregarPersonaOEmprendimiento(ElementoDeNodo elementoDeNodo) {
        if (grafo.get(elementoDeNodo.getIdentificador()) != null) {
            return false;
        }

        grafo.put(elementoDeNodo.getIdentificador(), new Nodo(elementoDeNodo));
        return true;
    }

    public boolean agregarActividad(Actividad actividad, Emprendimiento emprendimientoAsociado) {
        if (grafo.get(emprendimientoAsociado.getNombre()) == null) {
            return false;
        }

        ArrayList<ElementoDeNodo> actividadArray = new ArrayList<>();
        actividadArray.add(emprendimientoAsociado);

        grafo.put(actividad.getIdentificador(), new Nodo(actividad, actividadArray));
        grafo.get(emprendimientoAsociado.getNombre()).getConexiones().add(actividad);

        return true;
    }

    public Actividad getActividad(String nombre, Emprendimiento emprendimientoAsociado) {
        return (Actividad) grafo.get(emprendimientoAsociado.getNombre() + nombre).getNodo();
    }

    public Usuario getUsuario(String usuario, String contrasena) {
        for (Nodo nodo : grafo.values()) {
            if (nodo.getNodo() instanceof Usuario && Objects.equals(((Usuario) nodo.getNodo()).getUsuario(), usuario) && Objects.equals(((Usuario) nodo.getNodo()).getContrasena(), contrasena)) {
                return (Usuario) nodo.getNodo();
            }
        }
        return null;
    }

    public Emprendimiento getEmprendimiento(String nombre) {
        return (Emprendimiento) grafo.get(nombre).getNodo();
    }

    public Persona getPersona(String cedula) {
        return (Persona) grafo.get(cedula).getNodo();
    }

    public boolean eliminarElemento(ElementoDeNodo elementoDeNodo) {
        if (grafo.get(elementoDeNodo.getIdentificador()) == null){
            return false;
        }

        for (ElementoDeNodo elemento : grafo.get(elementoDeNodo.getIdentificador()).getConexiones()) {
            grafo.get(elemento.getIdentificador()).getConexiones().remove(elementoDeNodo);
        }

        grafo.remove(elementoDeNodo.getIdentificador());
        return true;
    }
}
