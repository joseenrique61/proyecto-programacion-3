package EstructurasDeDatos;

import java.util.ArrayList;

public class Nodo {
    private final ElementoDeNodo nodo;

    private final ArrayList<ElementoDeNodo> conexiones;

    protected Nodo(ElementoDeNodo nodo) {
        this.nodo = nodo;
        conexiones = new ArrayList<>();
    }

    public ElementoDeNodo getNodo() {
        return nodo;
    }

    public ArrayList<ElementoDeNodo> getConexiones() {
        return conexiones;
    }
}
