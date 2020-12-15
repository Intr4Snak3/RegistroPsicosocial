/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.LoginPersistencia;
import Vista.FrmLogin;
import static Vista.FrmLogin.jpassClave;
import static Vista.FrmLogin.txtUsuario;
import Vista.FrmPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Intr4Snak3
 */
public class ControladorLogin implements ActionListener {

    private FrmLogin frmLogin;
    private LoginPersistencia loginP;

    public ControladorLogin(FrmLogin frmLogin, LoginPersistencia loginP) {
        this.frmLogin = frmLogin;
        this.loginP = loginP;

        this.frmLogin.setTitle("ACCESO");
        this.frmLogin.setVisible(true);
        this.frmLogin.btnIngresar.addActionListener(this);
    }

    /*public ControladorLogin(FrmLogin frmLogin, LoginPersistencia loginP) {
        this.frmLogin = frmLogin;
        this.loginP = loginP;
        this.frmLogin.setTitle("ACCESO");
        this.frmLogin.setVisible(true);
        this.frmLogin.btnIngresar.addActionListener(this);
    }*/

 /*public ControladorLogin(LoginPersistencia loginP) {
        this.loginP = loginP;
    }*/
    //Metodo para validar en el login
    public void ingresar() {

        if (loginP.validarLogin()) {
            this.frmLogin.dispose();

            JOptionPane.showMessageDialog(null, "Bienvenido\n"
                    + "Has ingresado satisfactoriamente al sistema", "Mensaje de bienvenida",
                    JOptionPane.INFORMATION_MESSAGE);

            FrmPrincipal frmP = new FrmPrincipal();
            ControladorPrincipal ctrlP = new ControladorPrincipal(frmP);

            frmP.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Acceso denegado:\n"
                    + "Por favor ingrese un usuario y/o contraseña correctos", "Acceso denegado",
                    JOptionPane.ERROR_MESSAGE);
            txtUsuario.setText("");
            jpassClave.setText("");
        }

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frmLogin.btnIngresar) {
            ingresar();
        }
    }
}
