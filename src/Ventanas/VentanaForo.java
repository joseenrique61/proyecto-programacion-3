package Ventanas;

import Entidades.*;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaForo extends Ventana {
    private JPanel VentanitaForo;
    private JLabel NombreForoLabel;
    private JTextArea txtPublicaciones;
    private JTextField txtComentarioForo;
    private JButton btnPublicar;
    private Publicacion publicacion;
    private Foro foro;
    private Persona persona;

    protected VentanaForo(Ventana ventana, String actividadNombre, String emprendimientoAsociado) {
        super("Foro: " + actividadNombre, 400, 400, ventana);
        this.setContentPane(VentanitaForo);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.foro = null;
        this.publicacion = publicacion;

        NombreForoLabel.setText("Foro: " + actividadNombre + " - " + emprendimientoAsociado);

        btnPublicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subirComentario();
            }
        });
    }

    private void subirComentario() {
        String comentario = txtComentarioForo.getText();

        if (comentario.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe escribir un comentario.");
            return;
        }

        if (persona == null) {
            JOptionPane.showMessageDialog(null, "Persona no inicializada.");
            return;
        }

        Publicacion publicacion1 = new Publicacion(persona, comentario, foro);
        ManejadorDeGrafo.getGrafo().agregarElemento(publicacion1);
        ManejadorDeGrafo.getGrafo().agregarConexion(publicacion1, persona);

        JOptionPane.showMessageDialog(null, "Publicación subida exitosamente.");
        txtComentarioForo.setText("");
    }
}
