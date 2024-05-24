package Ventanas;

import javax.swing.*;

public class RegistrarUsuario extends Ventana {
    private JPanel panel1;
    private JTextField txtUsuario;
    private JTextField textField1;
    private JRadioButton rbPersona;
    private JRadioButton rbEmprendimiento;
    private JPanel pPersona;
    private JPanel pEmprendimiento;

    protected RegistrarUsuario(Ventana inicioDeSesion) {
        super("Registrar usuario", 400, 400, inicioDeSesion);
        setContentPane(panel1);
    }
}
