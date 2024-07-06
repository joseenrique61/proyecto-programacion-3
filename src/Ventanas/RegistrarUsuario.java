package Ventanas;

import Entidades.Emprendimiento;
import Entidades.Persona;
import Entidades.Usuario;
import EstructurasDeDatos.ElementoDeNodo;
import Servicios.ManejadorDeGrafo;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class RegistrarUsuario extends Ventana {
    private JPanel panel1;
    private JTextField txtUsuario;
    private JTextField txtContrasenia;
    private JRadioButton rbPersona;
    private JRadioButton rbEmprendimiento;
    private JPanel pPersona;
    private JPanel pEmprendimiento;
    private JTextField txtNombrePersona;
    private JTextField txtCedula;
    private JButton btnRegistrar;
    private JTextField txtNombreEmprendimiento;
    private JTextField txtUbicacion;

    protected RegistrarUsuario(Ventana inicioDeSesion) {
        super("Registrar usuario", 400, 400, inicioDeSesion);
        setContentPane(panel1);

        rbEmprendimiento.addActionListener(e -> {
            if(rbEmprendimiento.isSelected()){
                pEmprendimiento.setVisible(true);
                pPersona.setVisible(false);
            }
        });
        rbPersona.addActionListener(e -> {
            if(rbPersona.isSelected()){
                pPersona.setVisible(true);
                pEmprendimiento.setVisible(false);
            }
        });

        btnRegistrar.addActionListener(e -> {
            String usuario = txtUsuario.getText();
            String contrasena = txtContrasenia.getText();

            if (!comprobarUsuarioLibre()) {
                JOptionPane.showMessageDialog(null, "Este usuario ya existe.");
                return;
            }

            if (rbPersona.isSelected()) {
                String nombre = txtNombrePersona.getText();
                String cedula = txtCedula.getText();

                try {
                    Long.parseLong(cedula);
                }
                catch (NumberFormatException exception) {
                    JOptionPane.showMessageDialog(null, "La cédula es inválida.");
                    return;
                }

                if (!ManejadorDeGrafo.getGrafo().agregarElemento(new Persona(usuario,contrasena,cedula,nombre))) {
                    JOptionPane.showMessageDialog(null, "Ya existe una persona con esta cédula.");
                    return;
                }
            }
            else {
                String nombre = txtNombreEmprendimiento.getText();
                String ubicacion = txtUbicacion.getText();
                if (!ManejadorDeGrafo.getGrafo().agregarElemento(new Emprendimiento(usuario,contrasena,nombre,ubicacion))) {
                    JOptionPane.showMessageDialog(null, "Ya existe un emprendimiento con este nombre.");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Usuario registrado");

            if (ventanaAnterior instanceof VentanaAdministrador ventanaAdministrador) {
                ventanaAdministrador.actualizar();
            }
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
    }

    private boolean comprobarUsuarioLibre() {
        for (ElementoDeNodo elementoDeNodo : ManejadorDeGrafo.getGrafo().getValues()) {
            if (elementoDeNodo instanceof Usuario) {
                if (Objects.equals(((Usuario) elementoDeNodo).getUsuario(), txtUsuario.getText())) {
                    return false;
                }
            }
        }
        return true;
    }
}
