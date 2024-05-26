package Ventanas;

import Entidades.Emprendimiento;

import javax.swing.*;

public class VentanaEmprendimiento extends Ventana {
    private JPanel panel1;
    private JPanel PrincipalEmprendimiento;
    private JTabbedPane tabbedPane1;
    private JTextField txtFieldCapacidadActividades;
    private JTextField txtFieldDescripicion;
    private JButton btncrearActividad;
    private JTextField textFieldNombreActividad;
    private JTextField txtFieldNombreActividadParaBorrar;
    private JButton btnEliminar;
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

    protected VentanaEmprendimiento(Ventana inicioDeSesion, Emprendimiento emprendimiento) {
        super(emprendimiento.getNombre(), 1000, 1000, inicioDeSesion);
        setContentPane(panel1);

    }



}
