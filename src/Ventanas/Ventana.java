package Ventanas;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class Ventana extends JFrame {
    protected final Ventana ventanaAnterior;

    protected Ventana(String titulo, int altura, int ancho, Ventana ventanaAnterior) {
        this.ventanaAnterior = ventanaAnterior;
        this.setTitle(titulo);
        this.setSize(ancho, altura);

        this.setLocationRelativeTo(null);

        setWindowClosing();
    }

    public void setWindowClosing() {
        // Establece la acción que se debe hacer cuando se cierra la ventana
        this.addWindowListener(new WindowAdapter() {
            @Override
            public  void windowClosing(WindowEvent e){
                if(ventanaAnterior != null){
                    ventanaAnterior.setVisible(true);
                }
                dispose();
            }
        });
    }
}
