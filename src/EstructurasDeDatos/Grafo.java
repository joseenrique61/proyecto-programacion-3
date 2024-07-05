package EstructurasDeDatos;

import Entidades.Publicacion;

import java.util.*;

public class Grafo {
    private final Map<String, Nodo> grafo = new Hashtable<>();

    public boolean agregarElemento(ElementoDeNodo elementoDeNodo) {
        if (grafo.get(elementoDeNodo.getIdentificador()) != null) {
            return false;
        }

        grafo.put(elementoDeNodo.getIdentificador(), new Nodo(elementoDeNodo));
        return true;
    }

    public ElementoDeNodo getElemento(String identificador) {
        if (grafo.get(identificador) == null) {
            return null;
        }
        return grafo.get(identificador).getNodo();
    }

    public ArrayList<ElementoDeNodo> getValues() {
        ArrayList<ElementoDeNodo> values = new ArrayList<>();
        for (Nodo nodo : grafo.values()) {
            values.add(nodo.getNodo());
        }
        return values;
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

    public boolean agregarConexion(ElementoDeNodo elemento1, ElementoDeNodo elemento2) {
        if (grafo.get(elemento1.getIdentificador()) == null || grafo.get(elemento2.getIdentificador()) == null) {
            return false;
        }

        if (grafo.get(elemento1.getIdentificador()).getConexiones().contains(elemento2)) {
            return false;
        }

        grafo.get(elemento1.getIdentificador()).getConexiones().add(elemento2);
        grafo.get(elemento2.getIdentificador()).getConexiones().add(elemento1);
        return true;
    }

    public ArrayList<ElementoDeNodo> getConexiones(ElementoDeNodo elementoDeNodo) {
        if (grafo.get(elementoDeNodo.getIdentificador()) == null) {
            return null;
        }

        return grafo.get(elementoDeNodo.getIdentificador()).getConexiones();
    }

    public boolean eliminarConexion(ElementoDeNodo elemento1, ElementoDeNodo elemento2) {
        if (grafo.get(elemento1.getIdentificador()) == null || grafo.get(elemento2.getIdentificador()) == null) {
            return false;
        }

        if (!grafo.get(elemento1.getIdentificador()).getConexiones().contains(elemento2)) {
            return false;
        }

        grafo.get(elemento1.getIdentificador()).getConexiones().remove(elemento2);
        grafo.get(elemento2.getIdentificador()).getConexiones().remove(elemento1);
        return true;
    }

    public List<Publicacion> obtenerPublicacionesOrdenadasPorFecha() {
        List<Publicacion> publicaciones = new ArrayList<>();
        for (Nodo nodo : grafo.values()) {
            ElementoDeNodo elemento = nodo.getNodo();
            if (elemento instanceof Publicacion) {
                publicaciones.add((Publicacion) elemento);
            }
        }
        Collections.sort(publicaciones, Comparator.comparing(Publicacion::getFecha_hora));
        return publicaciones;
    }
}
