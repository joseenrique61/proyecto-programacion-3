package Ventanas;

import Entidades.Administrador;
import Entidades.Emprendimiento;
import Entidades.Persona;
import Entidades.Usuario;
import Servicios.ManejadorDeUsuarios;

import javax.swing.*;
import java.util.Objects;

public class InicioDeSesion extends Ventana {
    private JPanel panel1;
    private JTextField txtUsuario;
    private JTextField txtContrasena;
    private JButton iniciarSesionButton;
    private JButton registrarNuevoUsuarioButton;

    public InicioDeSesion() {
        super("Inicio de sesión", 400, 400, null);
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        iniciarSesionButton.addActionListener(e -> {
            if (Objects.equals(txtUsuario.getText(), "") || Objects.equals(txtContrasena.getText(), "")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar todos los campos.");
                return;
            }

            Usuario usuario = ManejadorDeUsuarios.getUsuario(txtUsuario.getText(), txtContrasena.getText());
            if (usuario == null) {
                JOptionPane.showMessageDialog(null, "El usuario o la contraseña son incorrectos.");
                return;
            }

            abrirVentanaDeUsuario(usuario);
        });

        registrarNuevoUsuarioButton.addActionListener(e -> {
            this.setVisible(false);

            RegistrarUsuario registrarUsuario = new RegistrarUsuario(this);
            registrarUsuario.setVisible(true);
        });
    }

    private void abrirVentanaDeUsuario(Usuario usuario) {
        switch (usuario) {
            case Persona ignored:
                VentanaPersona ventanaPersona = new VentanaPersona(this, (Persona)usuario);
                ventanaPersona.setVisible(true);
                break;
            case Emprendimiento ignored:
                VentanaEmprendimiento ventanaEmprendimiento = new VentanaEmprendimiento(this, (Emprendimiento) usuario);
                ventanaEmprendimiento.setVisible(true);
                break;
            case Administrador ignored:
                VentanaAdministrador ventanaAdministrador = new VentanaAdministrador(this);
                ventanaAdministrador.setVisible(true);
                break;
            default:
                break;
        }
    }
}
