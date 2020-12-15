/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alumno;
import Modelo.AlumnoPersistencia;
import Modelo.Apoderado;
import Modelo.Personas;
import Modelo.Registro;
import Modelo.RegistroPersistencia;
import Modelo.Usuario;
import Modelo.UsuarioPersistencia;
import Vista.*;
import com.sun.jmx.defaults.JmxProperties;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import sun.reflect.generics.visitor.Reifier;

/**
 *
 * @author Intr4Snak3
 */
public class ControladorPrincipal implements ActionListener {

    FrmPrincipal frmPrincipal;
    FrmAgregarAlumno frmAgregarAlumno;
    FrmListaAlumnos frmListaAlumno;
    FrmEditarAlumno frmEditarAlumno;
    FrmAcercaDe frmNosotros;
    FrmAgregarRegistro frmRegistrar;
    FrmInformeRegistros frmListaRegistro;
    FrmAgregarUsuario frmAgregarUsuario;
    FrmListadoUsuarios frmListaUsuarios;
    FrmEditarUsuario frmEditarUsuario;

    public ControladorPrincipal(FrmPrincipal frmPrincipal) {
        this.frmPrincipal = frmPrincipal;
        this.frmPrincipal.setTitle("Menú Principal");
        this.frmPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);//para que parta maximizado
//this.mp.setLocationRelativeTo(null);
        this.frmPrincipal.setVisible(true);
        this.frmPrincipal.jmAgregarAlumno.addActionListener(this);
        this.frmPrincipal.jmListadoAlumnos.addActionListener(this);
        this.frmPrincipal.jmAcercaDe.addActionListener(this);
        this.frmPrincipal.jmAgregarRegistro.addActionListener(this);
        this.frmPrincipal.jmInformeRegistro.addActionListener(this);
        this.frmPrincipal.jmAgregarUsuario.addActionListener(this);
        this.frmPrincipal.jmListadoUsuarios.addActionListener(this);
        frmAgregarAlumno = new FrmAgregarAlumno();
        frmListaAlumno = new FrmListaAlumnos();
        frmEditarAlumno = new FrmEditarAlumno();

        frmRegistrar = new FrmAgregarRegistro();
        frmListaRegistro = new FrmInformeRegistros();

        frmAgregarUsuario = new FrmAgregarUsuario();
        frmListaUsuarios = new FrmListadoUsuarios();

        frmEditarUsuario = new FrmEditarUsuario();

        frmNosotros = new FrmAcercaDe();

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frmPrincipal.jmAgregarAlumno) {
            if (frmAgregarAlumno.isShowing()) {

            } else {
                FrmPrincipal.jdpPrincipal.add(frmAgregarAlumno);
                frmAgregarAlumno.show();
                Alumno alumno = new Alumno();
                Apoderado apoderado = new Apoderado();
                AlumnoPersistencia alumnoP = new AlumnoPersistencia();
                ArrayList<Alumno> arrayAlumno = new ArrayList<>();
                ControladorAlumno ctrl = new ControladorAlumno(frmAgregarAlumno, apoderado, frmListaAlumno, frmEditarAlumno, alumno, alumnoP, arrayAlumno);
            }

        }

        if (ae.getSource() == frmPrincipal.jmListadoAlumnos) {
            if (frmListaAlumno.isShowing()) {

            } else {
                FrmPrincipal.jdpPrincipal.add(frmListaAlumno);
                frmListaAlumno.show();
                Alumno alumno = new Alumno();
                Apoderado apoderado = new Apoderado();
                AlumnoPersistencia alumnoP = new AlumnoPersistencia();
                ArrayList<Alumno> arrayAlumno = new ArrayList<>();
                ControladorAlumno ctrl = new ControladorAlumno(frmAgregarAlumno, apoderado, frmListaAlumno, frmEditarAlumno, alumno, alumnoP, arrayAlumno);
            }
        }

        if (ae.getSource() == frmPrincipal.jmAcercaDe) {
            if (frmNosotros.isShowing()) {

            } else {
                FrmPrincipal.jdpPrincipal.add(frmNosotros);
                frmNosotros.show();
                //ControladorAlumno ctrl = new ControladorAlumno(frmNosotros);
            }
        }

        if (ae.getSource() == frmPrincipal.jmAgregarRegistro) {
            if (frmRegistrar.isShowing()) {

            } else {
                FrmPrincipal.jdpPrincipal.add(frmRegistrar);
                frmRegistrar.show();
                Registro registro = new Registro();
                RegistroPersistencia registroP = new RegistroPersistencia();
                ArrayList<Registro> arrayRegistro = new ArrayList<>();

                ControladorRegistro ctrl = new ControladorRegistro(registro, registroP, frmRegistrar, frmListaRegistro, arrayRegistro);

            }
        }

        if (ae.getSource() == frmPrincipal.jmInformeRegistro) {
            if (frmListaRegistro.isShowing()) {

            } else {
                FrmPrincipal.jdpPrincipal.add(frmListaRegistro);
                frmListaRegistro.show();
                Registro registro = new Registro();
                RegistroPersistencia registroP = new RegistroPersistencia();
                ArrayList<Registro> arrayRegistro = new ArrayList<>();
                ControladorRegistro ctrl = new ControladorRegistro(registro, registroP, frmRegistrar, frmListaRegistro, arrayRegistro);
            }
        }

        if (ae.getSource() == frmPrincipal.jmAgregarUsuario) {
            if (frmAgregarUsuario.isShowing()) {

            } else {
                FrmPrincipal.jdpPrincipal.add(frmAgregarUsuario);
                frmAgregarUsuario.show();
                Usuario usuario = new Usuario();
                UsuarioPersistencia usuarioP = new UsuarioPersistencia();
                ArrayList<Usuario> arrayUsuarios = new ArrayList<>();
                ControladorUsuario ctrlU = new ControladorUsuario(usuario, usuarioP, frmAgregarUsuario, frmListaUsuarios, frmEditarUsuario, arrayUsuarios);
            }
        }

        if (ae.getSource() == frmPrincipal.jmListadoUsuarios) {
            if (frmListaUsuarios.isShowing()) {

            } else {
                FrmPrincipal.jdpPrincipal.add(frmListaUsuarios);
                frmListaUsuarios.show();
                Usuario usuario = new Usuario();
                UsuarioPersistencia usuarioP = new UsuarioPersistencia();
                ArrayList<Usuario> usuarios = new ArrayList<>();
                ControladorUsuario ctrlUsuario = new ControladorUsuario(usuario, usuarioP, frmAgregarUsuario, frmListaUsuarios, frmEditarUsuario, usuarios);
            }
            /*if (frmEditarUsuario.isShowing()) {
                
            }else{
                FrmPrincipal.jdpPrincipal.add(frmEditarUsuario);
                frmEditarUsuario.show();
                Usuario usuario = new Usuario();
                UsuarioPersistencia usuarioP = new UsuarioPersistencia();
                ArrayList<Usuario> usuarios = new ArrayList<>();
                ControladorUsuario ctrlUsuario = new ControladorUsuario(usuario, usuarioP, frmAgregarUsuario, frmListaUsuarios, frmEditarUsuario, usuarios);
            
            }*/
        }
    }

}
