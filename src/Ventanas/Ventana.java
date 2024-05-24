package Ventanas;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Ventana extends JFrame {
    private final Ventana inicioDeSesion;

    protected Ventana(String titulo, int altura, int ancho, Ventana inicioDeSesion) {
        this.inicioDeSesion = inicioDeSesion;
        this.setTitle(titulo);
        this.setSize(ancho, altura);

        this.setLocationRelativeTo(null);

        setWindowClosing();
    }

    public void setWindowClosing() {
        // Establece la acción que se debe hacer cuando se cierra la ventana
        WindowAdapter exitListener = new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                // Muestra la ventana anterior
                inicioDeSesion.setVisible(true);
            }
        };
        this.addWindowListener(exitListener);
    }
}