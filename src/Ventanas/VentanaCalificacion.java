package Ventanas;


import Entidades.Actividad;
import Entidades.Calificacion;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class VentanaCalificacion extends Ventana {

    private JButton btnElegirActividad;
    private JPanel panel1;
    private JList<String> listVerActividades;
    private JTabbedPane tabbedPane1;
    private JTextArea txtVerComentarios;
    private JTextArea txtVerTodo;
    private JButton btnBuscar;
    private JButton btnOrdenarMayor;
    DefaultListModel<String> modelo = new DefaultListModel<>();
    DefaultListModel<String> modeloSeguidas = new DefaultListModel<>();

    protected VentanaCalificacion(Ventana ventana) {
        super("Calificación", 500, 500, ventana);
        this.setContentPane(panel1);

        btnElegirActividad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarComentarios();
            }
        });

        mostrarActividades();

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = listVerActividades.getSelectedIndex();
                if (selectedIndex != -1) {
                    String actividadInfo = modelo.get(selectedIndex);
                    Actividad actividad = obtenerActividadDeInfo(actividadInfo);
                    if (actividad != null) {
                        mostrarComentariosDeActividad(actividad);
                    } else {
                        txtVerComentarios.setText("No se encontró la actividad seleccionada en el grafo.");
                    }
                } else {
                    txtVerComentarios.setText("Seleccione una actividad antes de buscar.");
                }
            }
        });
        btnOrdenarMayor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Actividad> actividadesOrdenadas = ordenarActividadesPorCalificacion();
                mostrarActividadesOrdenadasEnTextArea(actividadesOrdenadas);
            }
        });
    }

    private void mostrarActividades() {
        modelo.clear();
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                String actividad = obtenerInfoActividad((Actividad) nodo);
                modelo.addElement(actividad);
            }
        }
        listVerActividades.setModel(modelo);
    }

    private String obtenerInfoActividad(Actividad actividad) {
        return "-ANFITRION: " + actividad.getEmprendimientoAsociado() +
                "    -ACTIVIDAD: " + actividad.getNombre() +
                "    -CAPACIDAD: " + actividad.getCapacidad() +
                "    -DESCRIPCIÓN: " + actividad.getDescripcion();
    }

    private Actividad obtenerActividadDeInfo(String actividadInfo) {
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                Actividad actividad = (Actividad) nodo;
                String infoActividad = obtenerInfoActividad(actividad);
                if (infoActividad.equals(actividadInfo)) {
                    return actividad;
                }
            }
        }
        return null;
    }

    private void mostrarComentariosDeActividad(Actividad actividad) {
        txtVerComentarios.setText("");
        StringBuilder comentarios = new StringBuilder();

        if (actividad != null) {
            for (ElementoDeNodo conexion : ManejadorDeGrafo.getGrafo().getConexiones(actividad)) {
                if (conexion instanceof Calificacion) {
                    Calificacion calificacion = (Calificacion) conexion;
                    comentarios.append("Actividad: ").append(actividad.getNombre())
                            .append("\nCalificación: ").append(calificacion.getPuntuacion())
                            .append("\nComentario: ").append(calificacion.getComentario())
                            .append("\n\n");
                }
            }
        } else {
            comentarios.append("No se encontraron calificaciones para esta actividad.");
        }

        txtVerComentarios.setText(comentarios.toString());
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
    private List<Actividad> ordenarActividadesPorCalificacion() {
        List<Actividad> actividades = obtenerListaActividades();
        Collections.sort(actividades, Comparator.comparingInt(Actividad::getCalificacionPromedio).reversed());
        return actividades;
    }

    private List<Actividad> obtenerListaActividades() {
        List<Actividad> actividades = new ArrayList<>();
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                actividades.add((Actividad) nodo);
            }
        }
        return actividades;
    }

    private void mostrarActividadesOrdenadasEnTextArea(List<Actividad> actividades) {
        txtVerTodo.setText("");
        StringBuilder actividadesTexto = new StringBuilder();

        for (Actividad actividad : actividades) {
            actividadesTexto.append(obtenerInfoActividad(actividad))
                    .append(" - Calificación Promedio: ").append(actividad.getCalificacionPromedio())
                    .append("\n");
        }

        txtVerTodo.setText(actividadesTexto.toString());
    }
}

