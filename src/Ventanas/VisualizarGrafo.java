package Ventanas;

import Otros.Lienzo;

import java.awt.*;

public class VisualizarGrafo extends Ventana {

    protected VisualizarGrafo(Ventana ventanaAnterior) {
        super("Grafo", 700, 700, ventanaAnterior);

        this.setMinimumSize(new Dimension(700, 700));
        this.setContentPane(new Lienzo());
    }
}
