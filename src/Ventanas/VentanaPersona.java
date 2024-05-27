package Ventanas;

import Entidades.Actividad;
import Entidades.Persona;

import javax.swing.*;

public class VentanaPersona extends Ventana {
    private JPanel panel1;
    private JTextArea taActividades;
    private JButton btnUnirse;
    private JTextArea taActividadSeguida;
    private JComboBox<Actividad> cbActividad;
    private JTable tbActividades;

    protected VentanaPersona(Ventana inicioDeSesion, Persona persona) {
        super(persona.getNombre(), 1000, 1000, inicioDeSesion);
        setContentPane(panel1);


    }
}
