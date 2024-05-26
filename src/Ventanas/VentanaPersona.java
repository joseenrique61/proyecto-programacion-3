package Ventanas;

import Entidades.Persona;

import javax.swing.*;

public class VentanaPersona extends Ventana {
    private JPanel panel1;
    private JButton button1;

    protected VentanaPersona(Ventana inicioDeSesion, Persona persona) {
        super(persona.getNombre(), 100, 100, inicioDeSesion);
        setContentPane(panel1);
    }

}
