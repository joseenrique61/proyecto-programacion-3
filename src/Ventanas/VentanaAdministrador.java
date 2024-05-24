package Ventanas;

import javax.swing.*;

public class VentanaAdministrador extends Ventana {
    private JPanel panel1;

    protected VentanaAdministrador(Ventana inicioDeSesion) {
        super("Administrador", 100, 100, inicioDeSesion);
        setContentPane(panel1);
    }
}
