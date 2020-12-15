/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.FrmLogin;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Intr4Snak3
 */
public class LoginPersistencia extends Conexion {

    
    public boolean validarLogin() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        String usuario = FrmLogin.txtUsuario.getText();
        char clave[] = FrmLogin.jpassClave.getPassword();

        String clavedef = new String(clave);

        String sql = "SELECT * FROM user WHERE user_name=? AND user_password=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, clavedef);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

}
