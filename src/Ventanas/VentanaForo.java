package Ventanas;

import Entidades.*;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaForo extends Ventana {
    private JPanel panel1;
    private JLabel NombreForoLabel;
    private JTextArea txtPublicaciones;
    private JTextField txtComentarioForo;
    private JButton btnPublicar;
    private final Foro foro;
    private final Persona persona;

    protected VentanaForo(Ventana ventana, Foro foro, Persona persona) {
        super("Foro: " + foro.getActividad().getNombre(), 400, 400, ventana);
        this.setContentPane(panel1);

        NombreForoLabel.setText("Foro: " + foro.getActividad().getNombre() + " - " + foro.getActividad().getEmprendimientoAsociado());

        this.foro = foro;
        this.persona = persona;

        txtPublicaciones.setText(foro.getComentarios());

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

        if (foro == null) {
            JOptionPane.showMessageDialog(null, "El foro no está disponible.");
            return;
        }

        txtComentarioForo.setText("");

        Publicacion publicacion = new Publicacion(persona, comentario, foro);
        ManejadorDeGrafo.getGrafo().agregarElemento(publicacion);
        ManejadorDeGrafo.getGrafo().agregarConexion(publicacion, persona);
        foro.agregarComentario(publicacion);
        JOptionPane.showMessageDialog(null, "Publicación subida exitosamente.");

        txtPublicaciones.setText(foro.getComentarios());
    }
}
