package Ventanas;

import Entidades.Emprendimiento;
import Entidades.Persona;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeUsuarios;

import javax.swing.*;

public class VentanaPersona extends Ventana {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    private JTextArea txtMostrarEmprendimientos;
    private JButton SEGUIRButton;
    private JTextArea textArea2;
    private JComboBox cbmEmprendimientos;

    protected VentanaPersona(Ventana inicioDeSesion, Persona persona) {
        super(persona.getNombre(), 1000, 1000, inicioDeSesion);
        setContentPane(panel1);
        String cbEmprendimientos = getEmprendimientos();
        txtMostrarEmprendimientos.setText(getEmprendimientos());
        agregarEmprendimientosAlComboBox();
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

}
