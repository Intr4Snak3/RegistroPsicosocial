/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.logging.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Intr4Snak3
 */
public class UsuarioPersistencia extends Conexion implements IUsuarioPersistencia {

    public boolean todosLosUsuarios(ArrayList<Usuario> arrayUsuarios) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connect = getConexion();
        arrayUsuarios.removeAll(arrayUsuarios);

        //Se realiza la consulta a las tablas personas, user y cargo
        //Para llamar los datos necesarios
        String sql = "SELECT * FROM personas "
                + "INNER JOIN user "
                + "ON personas.run = user.run_user_fk "
                + "INNER JOIN cargo "
                + "ON cargo.id_cargo = user.cargo_fk";

        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            Usuario usuario;
            while (rs.next()) {
                //Se crea un objeto de la clase usuario                
                usuario = new Usuario();
                //Se guardan los valores de la consulta sql en el objeto usuario
                usuario.setRun(rs.getString("run"));
                usuario.setUser_name(rs.getString("user_name"));
                //usuario.setPassword(rs.getString("user_password"));
                usuario.setUser_email(rs.getString("user_email"));
                usuario.setCargo(rs.getString("nombre_cargo"));
                usuario.setNombre(rs.getString("nombres"));
                usuario.setApellidoP(rs.getString("apellidoP"));
                usuario.setApellidoM(rs.getString("apellidoM"));
                arrayUsuarios.add(usuario);
            }
            if (arrayUsuarios.size() > 0) {
                return true;
            } else {
                return false;
            }
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

    public void consultarCargos(JComboBox comboCargo) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM cargo "
                + "ORDER BY id_cargo ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            comboCargo.addItem("Seleccione un cargo");

            while (rs.next()) {
                comboCargo.addItem(rs.getInt("id_cargo") + ".- " + rs.getString("nombre_cargo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (con != null) {
                try {
                    con.close();
                    rs.close();

                    con = null;
                    rs = null;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }
    }

    @Override
    public boolean registrarUsuario(Usuario usuario) {
        PreparedStatement psRegistrar = null;
        Connection con = getConexion();
        String sql = "INSERT INTO personas (run, nombres, apellidoP, apellidoM)"
                + "VALUES(?,?,?,?)";
        String sql2 = "INSERT INTO user (user_name, user_password"
                + ", user_email, date, run_user_fk, cargo_fk)"
                + "VALUES(?,?,?,?,?,?)";

        try {
            psRegistrar = con.prepareStatement(sql);
            psRegistrar.setString(1, usuario.getRun());
            psRegistrar.setString(2, usuario.getNombre());
            psRegistrar.setString(3, usuario.getApellidoP());
            psRegistrar.setString(4, usuario.getApellidoM());
            psRegistrar.execute();
            psRegistrar = con.prepareStatement(sql2);
            psRegistrar.setString(1, usuario.getUser_name());
            psRegistrar.setString(2, usuario.getPassword());
            psRegistrar.setString(3, usuario.getUser_email());
            psRegistrar.setDate(4, usuario.getFecha());
            psRegistrar.setString(5, usuario.getRun());
            psRegistrar.setInt(6, usuario.getId_cargo());
            psRegistrar.execute();
            return true;
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

    @Override
    public boolean editar(Usuario usuario) {
        PreparedStatement ps = null;
        Connection con = getConexion();

        String sql = "UPDATE personas "
                + "SET nombres=?, apellidoP=? ,apellidoM=? "
                + "WHERE run=?";
                
        String sql2 = "UPDATE user SET user_name=?, "
                + "user_password=?, user_email=?, cargo_fk=? "
                + "WHERE run_user_fk=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellidoP());
            ps.setString(3, usuario.getApellidoM());
            ps.setString(4, usuario.getRun());
            ps.execute();
            
            ps = con.prepareStatement(sql2);
            ps.setString(1, usuario.getUser_name());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getUser_email());
            ps.setInt(4, usuario.getId_cargo());
            ps.setString(5, usuario.getRun());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println();
            }
        }
    }

    @Override
    public boolean eliminar(Usuario usuario) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        //Query para eliminar usuario de tabla user
        String sql = "DELETE FROM user WHERE run_user_fk=? ";
        //Query para eliminar usuario de tabla personas
        String sql2 = "DELETE FROM personas WHERE run=? ";
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getRun());
            ps.execute();
            ps = con.prepareStatement(sql2);
            ps.setString(1, usuario.getRun());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    @Override
    public boolean buscar(Usuario usuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM personas "
                + "INNER JOIN user "
                + "ON personas.run = user.run_user_fk "
                + "INNER JOIN cargo "
                + "ON cargo.id_cargo = user.cargo_fk "
                + "WHERE run LIKE(?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getRun());
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario.setRun(rs.getString("run"));
                usuario.setUser_name(rs.getString("user_name"));
                //usuario.setPassword(rs.getString("user_password"));
                usuario.setUser_email(rs.getString("user_email"));
                usuario.setCargo(rs.getString("nombre_cargo"));
                usuario.setNombre(rs.getString("nombres"));
                usuario.setApellidoP(rs.getString("apellidoP"));
                usuario.setApellidoM(rs.getString("apellidoM"));
                usuario.setPassword(rs.getString("user_password"));
                usuario.setId_cargo(rs.getInt("id_cargo"));                
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
