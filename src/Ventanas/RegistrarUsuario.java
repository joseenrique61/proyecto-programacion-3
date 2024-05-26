package Ventanas;

import Entidades.Emprendimiento;
import Entidades.Persona;
import Servicios.ManejadorDeUsuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegistrarUsuario extends Ventana {
    private JPanel panel1;
    private JTextField txtUsuario;
    private JTextField txtContrasenia;
    private JRadioButton rbPersona;
    private JRadioButton rbEmprendimiento;
    private JPanel pPersona;
    private JPanel pEmprendimiento;
    private JTextField txtNombrePersona;
    private JButton INGRESARButton;
    private JTextField txtCedula;
    private JButton btnRegistrarPersona;
    private JTextField txtNombreEmprendimiento;
    private JTextField txtUicacion;
    private JButton btnRegistrarEmprendimiento;
    private JButton INGRESARButton1;
    private JPanel mainPanel;

    protected RegistrarUsuario(Ventana inicioDeSesion) {
        super("Registrar usuario", 400, 400, inicioDeSesion);
        setContentPane(panel1);
        pPersona.setVisible(false);
        pEmprendimiento.setVisible(false);

        rbEmprendimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rbEmprendimiento.isSelected()){
                    pEmprendimiento.setVisible(true);
                    pPersona.setVisible(false);
                    panel1.revalidate();
                    panel1.repaint();
                }
            }
        });
        rbPersona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rbPersona.isSelected()){
                    pPersona.setVisible(true);
                    pEmprendimiento.setVisible(false);
                    panel1.revalidate();
                    panel1.repaint();
                }
            }
        });
        btnRegistrarPersona.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasenia = txtContrasenia.getText();
                String nombre = txtNombrePersona.getText();
                String cedula = txtCedula.getText();
                ManejadorDeUsuarios.getGrafo().agregarElemento(new Persona(usuario,contrasenia,nombre,cedula));
                JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                cerrarVentana();
            }
        });
        btnRegistrarEmprendimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasenia = txtContrasenia.getText();
                String nombreEm= txtNombreEmprendimiento.getText();
                String ubicacion = txtUicacion.getText();
                ManejadorDeUsuarios.getGrafo().agregarElemento(new Emprendimiento(usuario,contrasenia,nombreEm,ubicacion));
                JOptionPane.showMessageDialog(null, "USUARIO REGISTRADO");
                cerrarVentana();
            }
        });
    }
    public void cerrarVentana() {
        this.dispose();
        Ventana ventanaInvocadora = getVentanaInvocadora();
        if (ventanaInvocadora != null) {
            ventanaInvocadora.setVisible(true);
        }
    }
    private Ventana getVentanaInvocadora() {
        return inicioDeSesion;
    }




}
