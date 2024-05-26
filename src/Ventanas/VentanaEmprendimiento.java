package Ventanas;

import Entidades.Actividad;
import Entidades.Emprendimiento;
import Servicios.ManejadorDeActividades;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VentanaEmprendimiento extends Ventana {
    private JPanel panel1;
    private JPanel PrincipalEmprendimiento;
    private JTabbedPane tabbedPane1;
    private JTextField txtCapacidadActividades;
    private JTextField txtDescripicion;
    private JButton btncrearActividad;
    private JTextField txtNombreActividad;
    private JTextField txtFieldUsuarioSiguiendoLaActividad;
    private JTextField txtFieldCalificacionPromedioEmprendimiento;
    private JTextField txtFieldNombreUsuarioParaAsignar;
    private JTextField txtFieldUsuarioAsignarActividad;
    private JButton btnAsignar;
    private JTextField txtFieldUsuarioparaeliminardeactividad;
    private JTextField txtFieldNombreActividadEliminarUsuario;
    private JButton eliminarButton;
    private JTextField txtFieldNombreActividadBusqueda;
    private JButton buscarInformacionSobreActividadButton;
    private JTextArea txtAreaImpresionListaUsuariosPertenecientesAUnaActividad;
    private JTextArea txtMostrarActividades;
    private JComboBox comboBox1;
    private JButton button1;
    private List<Actividad> listActividades;

    protected VentanaEmprendimiento(Ventana inicioDeSesion, Emprendimiento emprendimiento) {
        super(emprendimiento.getNombre(), 1000, 1000, inicioDeSesion);
        listActividades = new ArrayList<>();
        setContentPane(panel1);

        btncrearActividad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreActividad = txtNombreActividad.getText();
                String capacidad = txtCapacidadActividades.getText();
                String descripcion = txtDescripicion.getText();
                Actividad acti = new Actividad(nombreActividad,capacidad,descripcion);
                ManejadorDeActividades.agregarActividad(acti);
                txtMostrarActividades.setText(listarActividades());
                txtMostrarActividades.setText(listarActividades());

            }
        });
    }
    private String listarActividades() {
        StringBuilder sb = new StringBuilder();
        for (Actividad actividad : ManejadorDeActividades.getActividades()) {
            sb.append(actividad.toString()).append("\n");
        }
        return sb.toString();
    }
    /*public String listar() {
        String texto = "";
        for(String j : listActividades){
            texto += j.toString();
        }
        return texto;
    }*/


}
