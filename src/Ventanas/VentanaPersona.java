package Ventanas;

import Entidades.Actividad;
import Entidades.Calificacion;
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
    private JComboBox cboElegirCalificacion;
    private JTextField txtComentario;
    private JButton btnSubir;
    private JButton btnCalificacionPersona;
    private JList listActividadesCalificar;
    private JTable tbActividadesSeguidas;
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel dtm2 = new DefaultTableModel();
    private Persona persona;
    private Emprendimiento emprendimiento;
    DefaultListModel<String> modelo = new DefaultListModel<>();
    DefaultListModel<String> modeloSeguidas = new DefaultListModel<>();

    protected VentanaPersona(Ventana ventana, Persona persona) {
        super("Ventana Persona", 500, 500, null);
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.emprendimiento = null;
        this.persona = persona;
        mostrarActividades();
        for (int i = 1; i <= 10; i++) {
            cboElegirCalificacion.addItem(i);
        }
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
        btnSubir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subirCalificacionYComentario();
            }
        });
        btnCalificacionPersona.addActionListener(e ->  {
            this.setVisible(false);

            VentanaCalificacion ventanaCalificacion = new VentanaCalificacion(this);
            ventanaCalificacion.setVisible(true);
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
        listActividadesCalificar.setModel(modeloSeguidas);
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
    private void subirCalificacionYComentario() {
        String actividadSeleccionadaStr = (String) listActividadesCalificar.getSelectedValue();
        Integer calificacionSeleccionada = (Integer) cboElegirCalificacion.getSelectedItem();
        String comentario = txtComentario.getText();

        if (actividadSeleccionadaStr == null || comentario.isEmpty() || calificacionSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una actividad, una calificación y escribir un comentario.");
            return;
        }

        String[] partes = actividadSeleccionadaStr.split("[:\\-]");
        String actividadNombre = partes[4].trim();
        String actividadEmprendimiento = partes[2].trim();

        Actividad actividadSeleccionada = null;
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                Actividad actividad = (Actividad) nodo;
                if (actividad.getNombre().equals(actividadNombre) && actividad.getEmprendimientoAsociado().equals(actividadEmprendimiento)) {
                    actividadSeleccionada = actividad;
                    break;
                }
            }
        }

        if (actividadSeleccionada == null) {
            JOptionPane.showMessageDialog(null, "Actividad no encontrada.");
            return;
        }

        if (persona == null) {
            JOptionPane.showMessageDialog(null, "Persona no inicializada.");
            return;
        }

        Calificacion calificacion = new Calificacion(calificacionSeleccionada, comentario, persona, actividadSeleccionada);
        ManejadorDeGrafo.getGrafo().agregarElemento(calificacion);
        ManejadorDeGrafo.getGrafo().agregarConexion(calificacion, actividadSeleccionada);
        ManejadorDeGrafo.getGrafo().agregarConexion(calificacion, persona);

        JOptionPane.showMessageDialog(null, "Calificación y comentario subidos exitosamente.");
        txtComentario.setText("");
    }

}
