package Ventanas;


import Entidades.Actividad;
import Entidades.Calificacion;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaCalificacion extends Ventana{

    private JButton btnElegirActividad;
    private JPanel panel1;
    private JList listVerActividades;
    private JTabbedPane tabbedPane1;
    private JTextArea txtVerComentarios;
    private JTextArea txtVerTodo;
    private JButton btnBuscar;
    DefaultListModel<String> modelo = new DefaultListModel<>();
    DefaultListModel<String> modeloSeguidas = new DefaultListModel<>();

    protected VentanaCalificacion(Ventana ventana) {
        super("Calificación", 400, 400, ventana);
        this.setContentPane(panel1);

        btnElegirActividad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarComentarios();
            }
        });

    }
    private void mostrarActividades() {
        modelo.clear();
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                String actividad = "-ANFITRION: " + ((Actividad) nodo).getEmprendimientoAsociado() +
                        "    -ACTIVIDAD: " + ((Actividad) nodo).getNombre() +
                        "    -CAPACIDAD: " + ((Actividad) nodo).getCapacidad() +
                        "    -DESCRIPCIÓN: " + ((Actividad) nodo).getDescripcion();
                modelo.addElement(actividad);
            }
        }
        listVerActividades.setModel(modelo);
    }
    private void mostrarComentarios() {
        txtVerTodo.setText("");
        StringBuilder comentarios = new StringBuilder();

        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                Actividad actividad = (Actividad) nodo;
                for (ElementoDeNodo conexion : ManejadorDeGrafo.getGrafo().getConexiones(actividad)) {
                    if (conexion instanceof Calificacion) {
                        Calificacion calificacion = (Calificacion) conexion;
                        comentarios.append("Actividad: ").append(actividad.getNombre())
                                .append("\nCalificación: ").append(calificacion.getPuntuacion())
                                .append("\nComentario: ").append(calificacion.getComentario())
                                .append("\n\n");
                    }
                }
            }
        }

        txtVerTodo.setText(comentarios.toString());
    }
}
