package EstructurasDeDatos;

import Entidades.Actividad;
import Entidades.Emprendimiento;
import Entidades.Persona;
import Entidades.Usuario;

import java.util.*;

public class Grafo {
    private final Dictionary<Object, ArrayList<Object>> grafo = new Hashtable<>();

    private final ArrayList<Object> objetos = new ArrayList<>();

    public boolean agregarEmprendimiento(Emprendimiento emprendimiento) {
        if (grafo.get(emprendimiento.getNombre()) != null) {
            return false;
        }

        grafo.put(emprendimiento.getNombre(), new ArrayList<>());

        objetos.add(emprendimiento);
        return true;
    }

    public boolean agregarPersona(Persona persona) {
        if (grafo.get(persona.getCedula()) != null) {
            return false;
        }

        grafo.put(persona.getCedula(), new ArrayList<>());

        objetos.add(persona);
        return true;
    }

    public boolean agregarActividad(Actividad actividad, Emprendimiento emprendimiento) {
        if (grafo.get(emprendimiento.getNombre()) == null) {
            return false;
        }

        ArrayList<Object> actividadArray = new ArrayList<>();
        actividadArray.add(emprendimiento);

        grafo.put(actividad.getId(), actividadArray);
        grafo.get(emprendimiento.getNombre()).add(actividad);

        objetos.add(actividad);
        return true;
    }

    public Actividad getActividad(int id) {
        for (Object objeto : objetos) {
            if (objeto instanceof Actividad) {
                if (((Actividad) objeto).getId() == id) {
                    return (Actividad) objeto;
                }
            }
        }
        return null;
    }

    public Usuario getUsuario(String usuario, String contrasena) {
        for (Object object : objetos) {
            if (object instanceof Usuario && Objects.equals(((Usuario) object).getUsuario(), usuario) && Objects.equals(((Usuario) object).getUsuario(), usuario)) {
                return (Usuario) object;
            }
        }
        return null;
    }

    public Emprendimiento getEmprendimiento(String nombre) {
        for (Object objeto : objetos) {
            if (objeto instanceof Emprendimiento) {
                if (Objects.equals(((Emprendimiento) objeto).getNombre(), nombre)) {
                    return (Emprendimiento) objeto;
                }
            }
        }
        return null;
    }

    public Persona getPersona(String cedula) {
        for (Object objeto : objetos) {
            if (objeto instanceof Persona) {
                if (Objects.equals(((Persona) objeto).getCedula(), cedula)) {
                    return (Persona) objeto;
                }
            }
        }
        return null;
    }

    public boolean eliminarPersona(Persona persona) {
        if (grafo.get(persona.getCedula()) == null){
            return false;
        }

        for (Object object : grafo.get(persona)) {
            grafo.get(((Actividad)object).getId()).remove(persona);
        }
        grafo.remove(persona.getCedula());
        objetos.remove(persona);
        return true;
    }

    public boolean eliminarEmprendimiento(Emprendimiento emprendimiento) {
        if (grafo.get(emprendimiento.getNombre()) == null){
            return false;
        }

        for (Object object : grafo.get(emprendimiento)) {
            grafo.get(((Actividad)object).getId()).remove(emprendimiento);
        }
        grafo.remove(emprendimiento.getNombre());
        objetos.remove(emprendimiento);
        return true;
    }

    public boolean eliminarActividad(Actividad actividad) {
        if (grafo.get(actividad.getId()) == null){
            return false;
        }

        for (Object object : grafo.get(actividad)) {
            if (object instanceof Persona) {
                grafo.get(((Persona)object).getCedula()).remove(actividad);
            }
            else {
                grafo.get(((Emprendimiento)object).getNombre()).remove(actividad);
            }
        }
        grafo.remove(actividad.getId());
        objetos.remove(actividad);
        return true;
    }
}
