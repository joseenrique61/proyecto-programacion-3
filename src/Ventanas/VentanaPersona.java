package Ventanas;

import Entidades.*;
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
    private JComboBox<Integer> cboElegirCalificacion;
    private JTextField txtComentario;
    private JButton btnSubir;
    private JButton btnCalificacionPersona;
    private JList<String> listActividadesCalificar;
    private JButton btnIngresarAForo;
    private JComboBox<String> cboActividadesForo;
    private JList list1;
    private JButton btnEliminar;
    private JTextField txtNombreForo;
    private JTable tbActividadesSeguidas;
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel dtm2 = new DefaultTableModel();
    private final Persona persona;
    DefaultListModel<String> modelo = new DefaultListModel<>();
    DefaultListModel<String> modeloSeguidas = new DefaultListModel<>();

    protected VentanaPersona(Ventana ventana, Persona persona) {
        super("Ventana Persona", 500, 500, ventana);
        this.setContentPane(panel1);
        this.persona = persona;

        // Inicializa los modelos y las listas
        listaActividades.setModel(modelo);
        listActividadesSeguidas.setModel(modeloSeguidas);
        listActividadesCalificar.setModel(modeloSeguidas);

        mostrarActividades();
        mostrarActividadesSeguidas();
        actualizarCboActividadesForo();

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
                    if (agregarPersonaAActividad(actividadNombre, actividadEmprendimiento)) {
                        agregarActividadSeguida(actividadNombre, actividadEmprendimiento);
                        mostrarActividades();
                        actualizarCboActividadesForo();
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

        btnCalificacionPersona.addActionListener(e -> {
            this.setVisible(false);
            VentanaCalificacion ventanaCalificacion = new VentanaCalificacion(this);
            ventanaCalificacion.setVisible(true);
        });

        btnIngresarAForo.addActionListener(e -> {
            String actividadSeleccionada = (String) cboActividadesForo.getSelectedItem();
            if (actividadSeleccionada != null) {
                Actividad actividad = obtenerActividadPorNombre(actividadSeleccionada);
                if (actividad != null) {
                    this.setVisible(false);
                    VentanaForo ventanaForo = new VentanaForo(this, (Foro) ManejadorDeGrafo.getGrafo().getConexiones(actividad).get(1), persona);
                    ventanaForo.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Actividad no encontrada.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una actividad del foro.");
            }
        });

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String actividadSeleccionada = listActividadesSeguidas.getSelectedValue();
                if (actividadSeleccionada != null) {
                    String[] partes = actividadSeleccionada.split("[:\\-]");
                    String actividadNombre = partes[4].trim();
                    String actividadEmprendimiento = partes[2].trim();
                    if (aumentarCapacidad(actividadNombre, actividadEmprendimiento)) {
                        eliminarActividadSeguida();
                        mostrarActividades();
                        actualizarCboActividadesForo();
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
    }

    private void mostrarActividadesSeguidas() {
        modeloSeguidas.clear();
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getConexiones(persona)) {
            if (nodo instanceof Actividad) {
                String actividad = "-ANFITRION: " + ((Actividad) nodo).getEmprendimientoAsociado() +
                        "    -ACTIVIDAD: " + ((Actividad) nodo).getNombre() +
                        "    -CAPACIDAD: " + ((Actividad) nodo).getCapacidad() +
                        "    -DESCRIPCIÓN: " + ((Actividad) nodo).getDescripcion();
                modeloSeguidas.addElement(actividad);
            }
        }
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
    }

    private boolean agregarPersonaAActividad(String nombreActi, String emprendimientoAso) {
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad &&
                    ((Actividad) nodo).getNombre().equals(nombreActi) &&
                    ((Actividad) nodo).getEmprendimientoAsociado().equals(emprendimientoAso)) {

                if (((Actividad) nodo).getCapacidad() <= 0) {
                    JOptionPane.showMessageDialog(null, "Ya no hay espacio en esta actividad.");
                    return false;
                }
                if (!((Actividad) nodo).agregarPersona(persona)) {
                    JOptionPane.showMessageDialog(null, "Ya estás inscrito en esta actividad.");
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private void eliminarActividadSeguida() {
        DefaultListModel modeloEliminar = (DefaultListModel) listActividadesSeguidas.getModel();
        modeloEliminar.remove(listActividadesSeguidas.getSelectedIndex());
    }

    private boolean aumentarCapacidad(String nombreActi, String emprendimientoAso) {
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad &&
                    ((Actividad) nodo).getNombre().equals(nombreActi) &&
                    ((Actividad) nodo).getEmprendimientoAsociado().equals(emprendimientoAso)) {
                    ((Actividad) nodo).eliminarPersona(persona);
                return true;
            }
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////
    private void actualizarCboActividadesForo() {
        cboActividadesForo.removeAllItems();
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getConexiones(persona)) {
            if (nodo instanceof Actividad) {
                cboActividadesForo.addItem(((Actividad) nodo).getNombre());
            }
        }
    }
    private Actividad obtenerActividadPorNombre(String nombre) {
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad && ((Actividad) nodo).getNombre().equals(nombre)) {
                return (Actividad) nodo;
            }
        }
        return null;
    }
    private void subirCalificacionYComentario() {
        String actividadSeleccionadaStr = listActividadesCalificar.getSelectedValue();
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
        if (!ManejadorDeGrafo.getGrafo().agregarElemento(calificacion)) {
            JOptionPane.showMessageDialog(null, "Ya hiciste un comentario de esta actividad.");
            return;
        }
        ManejadorDeGrafo.getGrafo().agregarConexion(calificacion, actividadSeleccionada);
        ManejadorDeGrafo.getGrafo().agregarConexion(calificacion, persona);

        JOptionPane.showMessageDialog(null, "Calificación y comentario subidos exitosamente.");
        txtComentario.setText("");
    }
}
