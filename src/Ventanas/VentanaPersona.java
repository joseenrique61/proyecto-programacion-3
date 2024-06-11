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
    private JButton btnUnirse;
    private JTextArea taActividadSeguida;
    private JComboBox<String> cbActividad;
    private JTable tbActividades;
    private JTabbedPane unirse;
    private JTable tbActividadesSeguidas;
    DefaultTableModel dtm = new DefaultTableModel();
    DefaultTableModel dtm2 = new DefaultTableModel();
    private Persona persona;
    private Emprendimiento emprendimiento;

    public void configurarTabla1() {
        dtm.addColumn("ANFITRION");
        dtm.addColumn("NOMBRE DE ACTIVIDAD");
        dtm.addColumn("CAPACIDAD");
        dtm.addColumn("DESCRIPCIÓN");
        dtm.addRow(new Object[]{"ANFITRION", "NOMBRE DE ACTIVIDAD", "CAPACIDAD", "DESCRIPCION"});
        tbActividades.setModel(dtm);
    }


    public void configurarTabla2() {
        dtm2.addColumn("ANFITRION");
        dtm2.addColumn("NOMBRE DE ACTIVIDAD");
        dtm2.addColumn("CAPACIDAD");
        dtm2.addColumn("DESCRIPCIÓN");
        dtm2.addRow(new Object[]{"ANFITRION", "NOMBRE DE ACTIVIDAD", "CAPACIDAD", "DESCRIPCION"});
        tbActividadesSeguidas.setModel(dtm2);
    }


    protected VentanaPersona(Ventana inicioDeSesion, Persona persona) {
        super(persona.getNombre(), 1000, 1000, inicioDeSesion);
        setContentPane(panel1);
        this.persona = persona;
        this.emprendimiento = null;
        configurarTabla1();
        configurarTabla2();
        mostrarActividades();
        setComboBoxActividades();


        btnUnirse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = cbActividad.getSelectedItem().toString();
                String[] partes = seleccion.split(" : ");
                String nombreActividad = partes[1];
                Actividad actividad = getInfoActividad(nombreActividad);
                if (actividad != null) {
                    dtm2.addRow(new Object[]{actividad.getEmprendimientoAsociado(), actividad.getNombre(), actividad.getCapacidad(), actividad.getDescripcion()});
                }
            }
        });
    }

    public void mostrarActividades() {
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                dtm.addRow(new Object[]{((Actividad) nodo).getEmprendimientoAsociado(), ((Actividad) nodo).getNombre(), ((Actividad) nodo).getCapacidad(), ((Actividad) nodo).getDescripcion()});
            }
        }
    }

    private void setComboBoxActividades() {
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad) {
                String actividad = ((Actividad) nodo).getEmprendimientoAsociado() + " : " + ((Actividad) nodo).getNombre();
                cbActividad.addItem(actividad);
            }
        }
    }

    private Actividad getInfoActividad(String nombre) {
        for (ElementoDeNodo nodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (nodo instanceof Actividad && ((Actividad) nodo).getNombre().equals(nombre)) {
                ((Actividad) nodo).setCapacidad(((Actividad) nodo).getCapacidad()-1);
                if(((Actividad) nodo).getCapacidad() <0){
                    JOptionPane.showMessageDialog(null,"YA NO HAY CAPACIDAD EN LA ACTIVIDAD-");
                    break;
                }
                return (Actividad) nodo;
            }
        }
        return null;
    }

}
