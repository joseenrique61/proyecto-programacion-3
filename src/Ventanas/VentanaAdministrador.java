package Ventanas;

import Entidades.Emprendimiento;
import Entidades.Persona;
import Entidades.Usuario;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VentanaAdministrador extends Ventana {
    private JTabbedPane panel1;
    private JTable tbPersonas;
    private JTable tbEmprendimientos;
    private JButton btnRegistrar;
    private JComboBox<Usuario> cbUsuario;
    private JTextArea taUsuario;
    private JButton btnEliminar;

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
}
