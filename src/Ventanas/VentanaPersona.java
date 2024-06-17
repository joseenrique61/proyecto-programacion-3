package Ventanas;

import Entidades.Actividad;
import Entidades.Emprendimiento;
import Entidades.Persona;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPersona extends Ventana {
    private JPanel panel1;
    private JTextArea taActividades;
    private JButton btnSeguir;
    private JTextArea taActividadSeguida;
    private JTabbedPane unirse;
    private JList<String> listActividadesSeguidas;
    private JList<String> listaActividades;
    private JTable tbActividadesSeguidas;
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel dtm2 = new DefaultTableModel();
    private Persona persona;
    private Emprendimiento emprendimiento;
    DefaultListModel<String> modelo = new DefaultListModel<>();
    DefaultListModel<String> modeloSeguidas = new DefaultListModel<>();

    protected VentanaPersona(Ventana inicioDeSesion, Persona persona) {
        super(persona.getNombre(), 600, 1000, inicioDeSesion);
        setContentPane(panel1);
        this.persona = persona;
        this.emprendimiento = null;
        mostrarActividades();
        btnSeguir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actividadSeleccionada = listaActividades.getSelectedValue();
                if (actividadSeleccionada != null) {
                    String[] partes = actividadSeleccionada.split("[:\\-]");
                    String actividadNombre = partes[4].trim();
                    String actividadEmprendimiento = partes[2].trim();
                    if (actualizarCapacidad(actividadNombre, actividadEmprendimiento)) {
                        agregarActividadSeguida(actividadNombre, actividadEmprendimiento);
                        mostrarActividades();
                    }
                }
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
        listaActividades.setModel(modelo);
    }

    private void agregarActividadSeguida(String nombreActi, String emprendimientoAso) {
        boolean found = false;
        for (int i = 0; i < modeloSeguidas.getSize(); i++) {
            String actividad = modeloSeguidas.getElementAt(i);
            if (actividad.contains(nombreActi) && actividad.contains(emprendimientoAso)) {
                String[] partes = actividad.split("[:\\-]");
                int capacidad = Integer.parseInt(partes[6].trim());
                capacidad--;
                String nuevaActividad = "-ANFITRION: " + emprendimientoAso +
                        "    -ACTIVIDAD: " + nombreActi +
                        "    -CAPACIDAD: " + capacidad +
                        "    -DESCRIPCIÓN: " + partes[8].trim();
                modeloSeguidas.setElementAt(nuevaActividad, i);
                found = true;
                break;
            }
        }
        if (!found) {
            for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
                if (nodo instanceof Actividad &&
                        ((Actividad) nodo).getNombre().equals(nombreActi) &&
                        ((Actividad) nodo).getEmprendimientoAsociado().equals(emprendimientoAso)) {
                    String actividad = "-ANFITRION: " + ((Actividad) nodo).getEmprendimientoAsociado() +
                            "    -ACTIVIDAD: " + ((Actividad) nodo).getNombre() +
                            "    -CAPACIDAD: " + ((Actividad) nodo).getCapacidad() +
                            "    -DESCRIPCIÓN: " + ((Actividad) nodo).getDescripcion();
                    modeloSeguidas.addElement(actividad);
                    break;
                }
            }
        }
        listActividadesSeguidas.setModel(modeloSeguidas);
    }

    private boolean actualizarCapacidad(String nombreActi, String emprendimientoAso) {
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad &&
                    ((Actividad) nodo).getNombre().equals(nombreActi) &&
                    ((Actividad) nodo).getEmprendimientoAsociado().equals(emprendimientoAso)) {

                if (((Actividad) nodo).getCapacidad() <= 0) {
                    JOptionPane.showMessageDialog(null, "YA NO HAY CAPACIDAD PARA LA ACTIVIDAD");
                    return false;
                }
                ((Actividad) nodo).setCapacidad(((Actividad) nodo).getCapacidad() - 1);
                return true;
            }
        }
        return false;
    }
}
