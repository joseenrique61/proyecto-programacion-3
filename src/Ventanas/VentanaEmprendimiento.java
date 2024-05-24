package Ventanas;

import Entidades.Emprendimiento;

import javax.swing.*;

public class VentanaEmprendimiento extends Ventana {
    private JPanel panel1;

    protected VentanaEmprendimiento(Ventana inicioDeSesion, Emprendimiento emprendimiento) {
        super(emprendimiento.getNombre(), 100, 100, inicioDeSesion);
    }
}
