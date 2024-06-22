package Ventanas;

import Entidades.Actividad;
import Entidades.Emprendimiento;
import Entidades.Persona;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class VentanaEmprendimiento extends Ventana {
    private JPanel panel1;
    private JTextField txtDescripicion;
    private JButton btnCrearActividad;
    private JTextField txtNombreActividad;
    private JTextField txtCantidadPersonasEnActividades;
    private JTextField txtCalificacionPromedioEmprendimiento;
    private JTextField txtCedula;
    private JButton btnAsignar;
    private JButton btnEliminarPersona;
    private JButton btnBuscarInformacionSobreActividad;
    private JTextArea taInformacionActividad;
    private JComboBox<Actividad> cbActividadEliminar;
    private JButton btnEliminarActividad;
    private JComboBox<Actividad> cbActividadEliminarPersona;
    private JComboBox<Persona> cbPersonaEliminar;
    private JComboBox<Actividad> cbActividadInformacion;
    private JTextArea taInformacionActividadEliminar;
    private JSpinner spCapacidad;
    private JTable tbActividades;
    private JComboBox<Actividad> cbActividadAsginar;
    private JButton btnEmprendimientoCalificacion;
    private JButton btn;

    private final Emprendimiento emprendimiento;


    protected VentanaEmprendimiento(Ventana ventana, Emprendimiento emprendimiento) {
        super(emprendimiento.getNombre(), 550, 600, ventana);
        setContentPane(panel1);
        this.emprendimiento = emprendimiento;
        setTable();
        setComboBoxesDeActividades();
        setEstadisticas();

        btnCrearActividad.addActionListener(e -> {
            if (Objects.equals(txtNombreActividad.getText(), "") || Objects.equals(txtDescripicion.getText(), "")) {
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.");
                return;
            }

            try {
                if (!emprendimiento.addActividad(new Actividad(txtNombreActividad.getText(), emprendimiento, Integer.parseInt(spCapacidad.getValue().toString()), txtDescripicion.getText()))) {
                    return;
                }
            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "La cantidad no es válida.");
                return;
            }

            JOptionPane.showMessageDialog(null, "La actividad ha sido creada.");
            setTable();
            setComboBoxesDeActividades();

            txtNombreActividad.setText("");
            txtDescripicion.setText("");
            spCapacidad.setValue(0);
        });

        btnBuscarInformacionSobreActividad.addActionListener(e -> {
            Actividad actividad = (Actividad) cbActividadInformacion.getSelectedItem();
            if (actividad == null) {
                return;
            }
            taInformacionActividad.setText(actividad.toStringLargo());
        });

        btnAsignar.addActionListener(e -> {
            if (Objects.equals(txtCedula.getText(), "")) {
                JOptionPane.showMessageDialog(null, "La cédula debe estar llena.");
                return;
            }

            Actividad actividad = (Actividad) cbActividadAsginar.getSelectedItem();
            if (actividad == null) {
                return;
            }

            if (!actividad.agregarPersona((Persona) ManejadorDeGrafo.getGrafo().getElemento(txtCedula.getText()))) {
                JOptionPane.showMessageDialog(null, "No se ha encontrado la cédula.");
                return;
            }

            JOptionPane.showMessageDialog(null, "Persona agregada.");
            txtCedula.setText("");
        });

        btnEliminarPersona.addActionListener(e -> {
            Actividad actividad = (Actividad) cbActividadEliminarPersona.getSelectedItem();
            Persona persona = (Persona) cbPersonaEliminar.getSelectedItem();
            if (actividad != null && persona != null) {
                if (actividad.eliminarPersona(persona)) {
                    JOptionPane.showMessageDialog(null, "Persona eliminada.");
                    setComboBoxPersonas(); // Actualiza el ComboBox de personas
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar la persona.");
                }
            }
        });

        cbActividadEliminar.addActionListener(e -> {
            Actividad actividad = (Actividad) cbActividadEliminar.getSelectedItem();
            if (actividad == null) {
                return;
            }
            taInformacionActividadEliminar.setText(actividad.toStringLargo());
        });

        cbActividadEliminarPersona.addActionListener(e -> {
            setComboBoxPersonas();
        });

        btnEliminarActividad.addActionListener(e -> {
            Actividad actividad = (Actividad) cbActividadEliminar.getSelectedItem();
            emprendimiento.deleteActividad(actividad);
            JOptionPane.showMessageDialog(null, "La actividad ha sido eliminada");
            taInformacionActividadEliminar.setText("");
            setTable();
            setComboBoxesDeActividades();
        });
        btnEmprendimientoCalificacion.addActionListener(e ->  {
            this.setVisible(false);

            VentanaCalificacion ventanaCalificacion = new VentanaCalificacion(this);
            ventanaCalificacion.setVisible(true);
        });
    }

    private void setEstadisticas() {
        txtCantidadPersonasEnActividades.setText(Integer.toString(emprendimiento.getCantidadPersonasEnActividades()));
        txtCalificacionPromedioEmprendimiento.setText(Float.toString(emprendimiento.getCalificacion()));
    }

    private void setTable() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("Nombre");
        defaultTableModel.addColumn("Capacidad");
        defaultTableModel.addColumn("Descripción");
        defaultTableModel.addColumn("Personas inscritas");
        defaultTableModel.addColumn("Calificación promedio");

        defaultTableModel.addRow(new Object[]{"Nombre", "Capacidad", "Descripción", "Personas inscritas", "Calificación promedio"});

        for (Actividad actividad : emprendimiento.getActividades()) {
            defaultTableModel.addRow(new Object[]{actividad.getNombre(), actividad.getCapacidad(), actividad.getDescripcion(), actividad.getPersonasInscritas(), actividad.getCalificacionPromedio()});
        }

        tbActividades.setModel(defaultTableModel);
    }

    private void setComboBoxPersonas() {
        DefaultComboBoxModel<Persona> personasModel = new DefaultComboBoxModel<>();
        Actividad actividad = (Actividad) cbActividadEliminarPersona.getSelectedItem();
        if (actividad != null) {
            for (Persona persona : actividad.visualizarPersonas()) {
                personasModel.addElement(persona);
            }
        }
        cbPersonaEliminar.setModel(personasModel);
    }

    private void setComboBoxesDeActividades() {
        DefaultComboBoxModel<Actividad> actividadesModel = new DefaultComboBoxModel<>();
        for (Actividad actividad : emprendimiento.getActividades()) {
            actividadesModel.addElement(actividad);
        }

        cbActividadEliminar.setModel(actividadesModel);
        cbActividadInformacion.setModel(actividadesModel);
        cbActividadEliminarPersona.setModel(actividadesModel);
        cbActividadAsginar.setModel(actividadesModel);
        setComboBoxPersonas();
    }



}
