package Ventanas;

import Entidades.*;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaAdministrador extends Ventana {
    private JTabbedPane panel1;
    private JTable tbPersonas;
    private JTable tbEmprendimientos;
    private JButton btnRegistrar;
    private JComboBox<Usuario> cbUsuario;
    private JTextArea taUsuario;
    private JButton btnEliminar;
    private JButton btnBuscarComentarios;
    private JButton btnEliminarComentario;
    private JList<String> list1;
    private JPanel panelComentarios;
    DefaultListModel<String> comentariosModel = new DefaultListModel<>();


    protected VentanaAdministrador(Ventana inicioDeSesion) {
        super("Administrador", 500, 500, inicioDeSesion);
        setContentPane(panel1);

        actualizar();

        btnRegistrar.addActionListener(e -> {
            setVisible(false);

            RegistrarUsuario registrarUsuario = new RegistrarUsuario(this);
            registrarUsuario.setVisible(true);
        });

        cbUsuario.addActionListener(e -> {
            Usuario usuario = (Usuario) cbUsuario.getSelectedItem();
            taUsuario.setText(usuario.toString());
        });

        btnEliminar.addActionListener(e -> {
            Usuario usuario = (Usuario) cbUsuario.getSelectedItem();
            if (usuario instanceof Persona persona) {
                ManejadorDeGrafo.getGrafo().eliminarElemento(persona);
            }
            else if (usuario instanceof Emprendimiento emprendimiento) {
                ManejadorDeGrafo.getGrafo().eliminarElemento(emprendimiento);
            }

            JOptionPane.showMessageDialog(null, "Usuario eliminado.");
            taUsuario.setText("");

            actualizar();
        });
        btnBuscarComentarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarComentarios();
            }
        });
        btnEliminarComentario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarComentarioSeleccionado();
            }
        });
    }

    private void setTableEmprendimiento() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Nombre");
        defaultTableModel.addColumn("Ubicación");
        defaultTableModel.addColumn("Usuario");

        defaultTableModel.addRow(new Object[] {"Nombre", "Ubicación", "Usuario"});

        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (elementoDeNodo instanceof Emprendimiento emprendimiento) {
                defaultTableModel.addRow(new Object[] {emprendimiento.getNombre(), emprendimiento.getUbicacion(), emprendimiento.getUsuario()});
            }
        }

        tbEmprendimientos.setModel(defaultTableModel);
    }

    private void setTablePersonas() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Nombre");
        defaultTableModel.addColumn("Cédula");
        defaultTableModel.addColumn("Usuario");

        defaultTableModel.addRow(new Object[] {"Nombre", "Cédula", "Usuario"});

        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (elementoDeNodo instanceof Persona persona) {
                defaultTableModel.addRow(new Object[] {persona.getNombre(), persona.getCedula(), persona.getUsuario()});
            }
        }

        tbPersonas.setModel(defaultTableModel);
    }

    private void setComboBoxUsuarios() {
        DefaultComboBoxModel<Usuario> usuarios = new DefaultComboBoxModel<>();
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (elementoDeNodo instanceof Usuario usuario) {
                usuarios.addElement(usuario);
            }
        }
        cbUsuario.setModel(usuarios);
    }

    public void actualizar() {
        setTablePersonas();
        setTableEmprendimiento();
        setComboBoxUsuarios();
    }
    private void mostrarComentarios() {
        comentariosModel.clear();
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                Actividad actividad = (Actividad) nodo;
                for (ElementoDeNodo conexion : ManejadorDeGrafo.getGrafo().getConexiones(actividad)) {
                    if (conexion instanceof Calificacion) {
                        Calificacion calificacion = (Calificacion) conexion;
                        String comentario = "Actividad: " + actividad.getNombre()
                                + ", Calificación: " + calificacion.getPuntuacion()
                                + ", Comentario: " + calificacion.getComentario();
                        comentariosModel.addElement(comentario);
                    }
                }
            }
        }
        list1.setModel(comentariosModel);
    }
    private void eliminarComentarioSeleccionado() {
        int selectedIndex = list1.getSelectedIndex();
        if (selectedIndex != -1) {
            String comentarioSeleccionado = comentariosModel.get(selectedIndex);

            for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
                if (nodo instanceof Actividad) {
                    Actividad actividad = (Actividad) nodo;
                    Calificacion calificacionAEliminar = null;
                    for (ElementoDeNodo conexion : ManejadorDeGrafo.getGrafo().getConexiones(actividad)) {
                        if (conexion instanceof Calificacion) {
                            Calificacion calificacion = (Calificacion) conexion;
                            String comentario = "Actividad: " + actividad.getNombre()
                                    + ", Calificación: " + calificacion.getPuntuacion()
                                    + ", Comentario: " + calificacion.getComentario();
                            if (comentario.equals(comentarioSeleccionado)) {
                                calificacionAEliminar = calificacion;
                                break;
                            }
                        }
                    }

                    if (calificacionAEliminar != null) {
                        ManejadorDeGrafo.getGrafo().eliminarElemento(calificacionAEliminar);
                        break;
                    }
                }
            }

            comentariosModel.remove(selectedIndex);
            JOptionPane.showMessageDialog(null, "Comentario eliminado.");
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un comentario para eliminar.");
        }
    }




}
