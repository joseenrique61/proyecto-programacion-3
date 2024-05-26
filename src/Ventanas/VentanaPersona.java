package Ventanas;

import Entidades.Emprendimiento;
import Entidades.Persona;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeUsuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaPersona extends Ventana {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextArea txtMostrarEmprendimientos;
    private JButton SEGUIRButton;
    private JTextArea txtEmprendimientosSeguidos;
    private JComboBox cbmEmprendimientos;
    private List<String> listEmprendimientos ;

    protected VentanaPersona(Ventana inicioDeSesion, Persona persona) {
        super(persona.getNombre(), 1000, 1000, inicioDeSesion);
        listEmprendimientos = new ArrayList<>();
        setContentPane(panel1);
        txtMostrarEmprendimientos.setText(getEmprendimientos());
        agregarEmprendimientosAlComboBox();
        SEGUIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seguidos = cbmEmprendimientos.getSelectedItem().toString();
                txtEmprendimientosSeguidos.setText(agregar(seguidos));
            }
        });
    }
    public String getEmprendimientos(){
        StringBuilder emprendimientos = new StringBuilder();
        for (ElementoDeNodo elemento : ManejadorDeUsuarios.getGrafo().getValues()) {
            if (elemento instanceof Emprendimiento) {
                Emprendimiento emprendimiento = (Emprendimiento) elemento;
                emprendimientos.append(emprendimiento.getIdentificador()).append("\n");
            }
        }
        return emprendimientos.length() > 0 ? emprendimientos.toString() : "NO HAY EMPRENDIMIENTOS";
    }
    private void agregarEmprendimientosAlComboBox() {
        for (ElementoDeNodo elemento : ManejadorDeUsuarios.getGrafo().getValues()) {
            if (elemento instanceof Emprendimiento) {
                cbmEmprendimientos.addItem(elemento.getIdentificador());
            }
        }
    }
    private String agregar(String emprendi) {
        listEmprendimientos.add(emprendi);
        return String.join("\n", listEmprendimientos);
    }

}
