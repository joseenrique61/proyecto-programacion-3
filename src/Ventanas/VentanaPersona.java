package Ventanas;

import Entidades.Actividad;
import Entidades.Emprendimiento;
import Entidades.Persona;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeUsuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPersona extends Ventana {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextArea txtMostrarEmprendimientos;
    private JButton SEGUIRButton;
    private JTextArea txtActividades;
    private JTextField textField1;
    private JButton UNIRSEButton;
    private JTextArea txtActividadSeguida;
    private JComboBox cbmEmprendimientos;

    protected VentanaPersona(Ventana inicioDeSesion, Persona persona) {
        super(persona.getNombre(), 1000, 1000, inicioDeSesion);
        setContentPane(panel1);
        txtMostrarEmprendimientos.setText(get);
        SEGUIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seguidos = cbmEmprendimientos.getSelectedItem().toString();
                txtActividades.setText(agregar(seguidos));
            }
        });
    }
    public String get(){
        StringBuilder actividades = new StringBuilder();
        for (ElementoDeNodo elemento : ManejadorDeUsuarios.getGrafo().getValues()) {
            if (elemento instanceof Actividad) {
                Actividad actividad = (Actividad) elemento;
                actividades.append(actividad.getIdentificador()).append("\n");
            }
        }
        return actividades.length() > 0 ? actividades.toString() : "NO HAY EMPRENDIMIENTOS";
    }
    /*private void agregarEmprendimientosAlComboBox() {
        for (ElementoDeNodo elemento : ManejadorDeUsuarios.getGrafo().getValues()) {
            if (elemento instanceof Emprendimiento) {
                cbmEmprendimientos.addItem(elemento.getIdentificador());
            }
        }
    }
    private String agregar(String emprendi) {
        listEmprendimientos.add(emprendi);
        return String.join("\n", listEmprendimientos);
    }*/

}
