/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalpoo;

import Controlador.ControladorLogin;
import Controlador.ControladorPrincipal;
import Modelo.LoginPersistencia;
import Vista.FrmLogin;
import Vista.FrmPrincipal;

/**
 *
 * @author F.JR
 */
public class ProyectoFinalPOO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //FrmPrincipal frmP = new FrmPrincipal();
        //ControladorPrincipal ctrlP = new ControladorPrincipal(frmP);
        FrmLogin frmLogin = new FrmLogin();
        LoginPersistencia loginP = new LoginPersistencia();
        ControladorLogin ctrlLog = new ControladorLogin(frmLogin, loginP);
    }
    
}
