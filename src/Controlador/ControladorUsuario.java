/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Personas;
import Modelo.Usuario;
import Modelo.UsuarioPersistencia;
import Vista.FrmAgregarUsuario;
import Vista.FrmEditarUsuario;
import Vista.FrmListadoUsuarios;
import Vista.FrmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Intr4Snak3
 */
public class ControladorUsuario implements ActionListener, KeyListener {

    private Usuario usuario;
    private UsuarioPersistencia usuarioP;
    private FrmAgregarUsuario frmAgregarUsuario;
    private FrmListadoUsuarios frmListaUsuarios;

    private FrmEditarUsuario frmEditarUsuario;

    private ArrayList<Usuario> arrayUsuarios;

    public ControladorUsuario(Usuario usuario, UsuarioPersistencia usuarioP, FrmAgregarUsuario frmAgregarUsuario, FrmListadoUsuarios frmListaUsuarios, FrmEditarUsuario frmEditarUsuario, ArrayList<Usuario> arrayUsuarios) {
        this.usuario = usuario;
        this.usuarioP = usuarioP;
        this.frmAgregarUsuario = frmAgregarUsuario;
        this.frmListaUsuarios = frmListaUsuarios;

        this.frmEditarUsuario = frmEditarUsuario;

        this.frmAgregarUsuario.btnGuardar.addActionListener(this);

        this.frmListaUsuarios.btnBuscar.addActionListener(this);
        this.frmListaUsuarios.btnEditar.addActionListener(this);
        this.frmListaUsuarios.btnEliminar.addActionListener(this);
        this.frmListaUsuarios.btnSalir.addActionListener(this);

        this.frmEditarUsuario.btnGuardar.addActionListener(this);

        this.arrayUsuarios = arrayUsuarios;

        llenarTabla();
        //llenarCombo();
    }

    public void limpiar() {
        frmAgregarUsuario.txtNombre.setText("");
        frmAgregarUsuario.txtApellidoP.setText("");
        frmAgregarUsuario.txtApellidoM.setText("");
        frmAgregarUsuario.txtNombreUsuario.setText("");
        frmAgregarUsuario.txtPass.setText("");
        frmAgregarUsuario.txtRun.setText("");
        frmAgregarUsuario.txtCorreo.setText("");
        frmAgregarUsuario.comboCargo.setSelectedIndex(0);
    }

    public void llenarTabla() {
        if (usuarioP.todosLosUsuarios(arrayUsuarios)) {
            DefaultTableModel modelo = (DefaultTableModel) frmListaUsuarios.tblUsuarios.getModel();
            int cuentaFilas = modelo.getRowCount();

            for (int i = cuentaFilas - 1; i >= 0; i--) {
                modelo.removeRow(i);
            }
            Object[] fila = new Object[5];
            for (int i = 0; i < arrayUsuarios.size(); i++) {

                fila[0] = arrayUsuarios.get(i).getNombre()
                        + " " + arrayUsuarios.get(i).getApellidoP()
                        + " " + arrayUsuarios.get(i).getApellidoM();
                fila[1] = arrayUsuarios.get(i).getUser_name();
                fila[2] = arrayUsuarios.get(i).getRun();
                fila[3] = arrayUsuarios.get(i).getCargo();
                fila[4] = arrayUsuarios.get(i).getUser_email();
                modelo.addRow(fila);

            }
        }

    }

    //metodo por el momento sin usar--> se reemplaza por un metodo creado en UsuarioPersistencia
    //llamado consultarCargo
    /*public void llenarCombo() {
        if (usuarioP.todosLosUsuarios(arrayUsuarios)) {
            frmAgregarUsuario.comboCargo.removeAllItems();
            frmAgregarUsuario.comboCargo.addItem("[Seleccione una Opción]");
            for (int i = 0; i < arrayUsuarios.size(); i++) {
                frmAgregarUsuario.comboCargo.addItem(arrayUsuarios.get(i).getCargo());
            }
        }
    }*/
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frmAgregarUsuario.btnGuardar) {
            //Timestamp fechaHoy = new Timestamp();
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            Date date = new Date(timeStamp.getTime());
            usuario.setNombre(frmAgregarUsuario.txtNombre.getText());
            usuario.setApellidoP(frmAgregarUsuario.txtApellidoP.getText());
            usuario.setApellidoM(frmAgregarUsuario.txtApellidoM.getText());
            usuario.setRun(frmAgregarUsuario.txtRun.getText());
            usuario.setFecha(date);
            usuario.setUser_name(frmAgregarUsuario.txtNombreUsuario.getText());
            usuario.setId_cargo(frmAgregarUsuario.comboCargo.getSelectedIndex());
            usuario.setPassword(frmAgregarUsuario.txtPass.getText());
            usuario.setUser_email(frmAgregarUsuario.txtCorreo.getText());
            if (usuarioP.registrarUsuario(usuario)) {
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                limpiar();
            }
        }

        if (ae.getSource() == frmListaUsuarios.btnBuscar) {
            usuario.setRun(frmListaUsuarios.txtRun.getText());
            if (usuarioP.buscar(usuario)) {

                DefaultTableModel modelo = (DefaultTableModel) frmListaUsuarios.tblUsuarios.getModel();

                int cuentaFilas = modelo.getRowCount();

                for (int i = cuentaFilas - 1; i >= 0; i--) {
                    modelo.removeRow(i);
                }
                Object[] fila = new Object[5];
                fila[0] = usuario.getNombre()
                        + " " + usuario.getApellidoP()
                        + " " + usuario.getApellidoM();
                fila[1] = usuario.getUser_name();
                fila[2] = usuario.getRun();
                fila[3] = usuario.getCargo();
                fila[4] = usuario.getUser_email();

                modelo.addRow(fila);
            }
        }

        if (ae.getSource() == frmListaUsuarios.btnEditar) {
            usuario.setRun(frmListaUsuarios.txtRun.getText());
            if (usuarioP.buscar(usuario)) {
                frmEditarUsuario = new FrmEditarUsuario();
                FrmPrincipal.jdpPrincipal.add(frmEditarUsuario);
                frmEditarUsuario.show();

                frmEditarUsuario.txtNombre.setText(usuario.getNombre());
                frmEditarUsuario.txtApellidoP.setText(usuario.getApellidoP());
                frmEditarUsuario.txtApellidoM.setText(usuario.getApellidoM());
                frmEditarUsuario.txtNombreUsuario.setText(usuario.getUser_name());
                frmEditarUsuario.comboCargo.setSelectedIndex(usuario.getId_cargo());
                frmEditarUsuario.txtPass.setText(usuario.getPassword());
                frmEditarUsuario.txtCorreo.setText(usuario.getUser_email());

                frmEditarUsuario.lbNombreUsuario.setText(usuario.getNombre().toUpperCase() + " "
                        + usuario.getApellidoP().toUpperCase() + " " + usuario.getApellidoM().toUpperCase());
                frmEditarUsuario.lbRunUsuario.setText(usuario.getRun());
                
                frmEditarUsuario.btnGuardar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if (ae.getSource() == frmEditarUsuario.btnGuardar) {
                            //Usuario usuario = new Usuario();
                            //UsuarioPersistencia usuarioP = new UsuarioPersistencia();

                            usuario.setNombre(frmEditarUsuario.txtNombre.getText());
                            usuario.setApellidoP(frmEditarUsuario.txtApellidoP.getText());
                            usuario.setApellidoM(frmEditarUsuario.txtApellidoM.getText());
                            usuario.setUser_name(frmEditarUsuario.txtNombreUsuario.getText());
                            usuario.setCargo(frmEditarUsuario.comboCargo.getSelectedItem().toString());
                            usuario.setPassword(frmEditarUsuario.txtPass.getText());
                            usuario.setUser_email(frmEditarUsuario.txtCorreo.getText());

                            //ArrayList<Usuario>usuarios=new ArrayList<>();
                            //ControladorUsuario ctrlUsuario = new ControladorUsuario(usuario, usuarioP, frmAgregarUsuario, frmListaUsuarios, frmEditarUsuario, usuarios);
                            if (usuarioP.editar(usuario)) {
                                JOptionPane.showMessageDialog(null, "Usuario Modificado");
                                limpiar();
                                llenarTabla();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al Editar",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                limpiar();
                            }
                        }
                    }
                });
                frmEditarUsuario.toFront();
                frmEditarUsuario.setVisible(true);

            }

        }

        if (ae.getSource() == frmListaUsuarios.btnEliminar) {
            usuario.setRun(frmListaUsuarios.txtRun.getText());
            if (usuarioP.eliminar(usuario)) {
                JOptionPane.showMessageDialog(null, "Usuario Eliminado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error: Usuario No Eliminado");
                limpiar();
            }
            llenarTabla();
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
