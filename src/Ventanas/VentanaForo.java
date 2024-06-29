package Ventanas;

import Entidades.*;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;

public class VentanaForo extends Ventana{
    private JPanel VentanitaForo;
    private JLabel NombreForoLabel;
    private JTextArea txtPublicaciones;
    private JTextField txtComentarioForo;
    private JButton btnPublicar;
    private Publicacion publicacion;
    private Foro foro;
    private Persona persona;

    private Publicacion p;

    private Actividad actividad;

    protected VentanaForo(Ventana ventana) {
        super("Foro", 400, 400, ventana);
        this.setContentPane(VentanitaForo);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.foro = null;
        this.publicacion = publicacion;
    }
    private void subirComentario() {
        String comentario = txtComentarioForo.getText();

        if (comentario == null) {
            JOptionPane.showMessageDialog(null, "Debe escribir un comentario");
            return;
        }



        if (persona == null) {
            JOptionPane.showMessageDialog(null, "Persona no inicializada.");
            return;
        }

        Publicacion publicacion1 = new Publicacion(persona, comentario, foro);
        ManejadorDeGrafo.getGrafo().agregarElemento(publicacion1);
        ManejadorDeGrafo.getGrafo().agregarConexion(publicacion1, persona);

        JOptionPane.showMessageDialog(null, "Publicacion subida exitosamente");

    }
}
