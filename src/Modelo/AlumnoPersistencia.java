/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.ControladorAlumno;
import Vista.FrmEditarAlumno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import java.util.Date;

/**
 *
 * @author emaur
 */
public class AlumnoPersistencia extends Conexion {

    public void consultarCurso(JComboBox jcomboCurso) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM curso "
                + "ORDER BY id_curso ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            jcomboCurso.addItem("Seleccione un Curso");

            while (rs.next()) {
                jcomboCurso.addItem(rs.getInt("id_curso") + ".- " + rs.getString("nombre_curso"));
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

    public void consultarRegion(JComboBox comboBoxRegion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM region "
                + " ORDER BY id_region ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            comboBoxRegion.addItem("Selecciona una Region");

            while (rs.next()) {
                comboBoxRegion.addItem(rs.getInt("id_region") + ".-" + rs.getString("nombre_region"));
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

    public void consultarCiudad(JComboBox comboBoxCiudad) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM ciudad "
                + " ORDER BY id_ciudad ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            comboBoxCiudad.addItem("Selecciona una Ciudad");

            while (rs.next()) {
                comboBoxCiudad.addItem(rs.getInt("id_ciudad") + ".-" + rs.getString("nombre_ciudad"));
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

    public boolean registrarAlumno(Alumno alumno, Apoderado apoderado) {
        PreparedStatement psRegistrar = null;
        Connection con = getConexion();
        ResultSet rs = null;
        //Apoderado apoderado= new Apoderado();

        String sql = "INSERT INTO personas (run, nombres, apellidoP, apellidoM)"
                + "VALUES (?, ?, ?, ?)";
        String sql2 = "INSERT INTO apoderado (run_fk, direccion, ciudad_fk, parentesco, nivel_estudios, actividad)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        String sql3 = "INSERT INTO alumno (run_fk, curso_fk, apoderado_fk, fecha_nacimiento)"
                + "VALUES (?, ?, ?, ?)";
        String sql4 = "SELECT * FROM apoderado";

        try {
            psRegistrar = con.prepareStatement(sql);
            psRegistrar.setString(1, alumno.getRun());
            psRegistrar.setString(2, alumno.getNombre());
            psRegistrar.setString(3, alumno.getApellidoP());
            psRegistrar.setString(4, alumno.getApellidoM());
            psRegistrar.execute();
            psRegistrar = con.prepareStatement(sql);
            psRegistrar.setString(1, apoderado.getRun());
            psRegistrar.setString(2, apoderado.getNombre());
            psRegistrar.setString(3, apoderado.getApellidoP());
            psRegistrar.setString(4, apoderado.getApellidoM());
            psRegistrar.execute();
            psRegistrar = con.prepareStatement(sql2);
            psRegistrar.setString(1, apoderado.getRun());
            psRegistrar.setString(2, apoderado.getDireccion());
            psRegistrar.setInt(3, apoderado.getId_ciudad());
            psRegistrar.setString(4, apoderado.getParentesco());
            psRegistrar.setString(5, apoderado.getNivel_estudios());
            psRegistrar.setString(6, apoderado.getActividad());
            psRegistrar.execute();
            psRegistrar = con.prepareStatement(sql4);
            rs = psRegistrar.executeQuery();
            while (rs.next()) {
                alumno.setApoderado_fk(rs.getInt("id_apoderado"));
            }

            psRegistrar = con.prepareStatement(sql3);
            psRegistrar.setString(1, alumno.getRun());
            psRegistrar.setInt(2, alumno.getId_curso());
            psRegistrar.setInt(3, alumno.getApoderado_fk());
            java.util.Date fecha = alumno.getFechaNac();
            java.sql.Date fechasql = new java.sql.Date(fecha.getTime());
            psRegistrar.setDate(4, fechasql);
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

    public boolean todosLosAlumnos(ArrayList<Alumno> arrayAlumnos) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connect = getConexion();
        arrayAlumnos.removeAll(arrayAlumnos);

        String sql = "SELECT * FROM personas p\n"
                + "INNER JOIN alumno a\n"
                + "ON p.run = a.run_fk\n"
                + "INNER JOIN curso c\n"
                + "ON a.curso_fk = c.id_curso\n"
                + "INNER JOIN apoderado ap\n"
                + "ON ap.id_apoderado = a.apoderado_fk\n"
                + "INNER JOIN personas per\n"
                + "ON ap.run_fk = per.run";

        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            Alumno alumno;
            while (rs.next()) {
                alumno = new Alumno();
                alumno.setRun(rs.getString("p.run"));
                alumno.setNombre(rs.getString("p.nombres"));
                alumno.setApellidoP(rs.getString("p.apellidoP"));
                alumno.setApellidoM(rs.getString("p.apellidoM"));
                alumno.setApoderado_fk(rs.getInt("apoderado_fk"));
                alumno.setFechaNac(rs.getDate("fecha_nacimiento"));
                alumno.setCurso(rs.getString("nombre_curso"));
                alumno.setNombreApoderado(rs.getString("per.nombres") + " "
                        + rs.getString("per.apellidoP") + " " + rs.getString("per.apellidoM"));
                arrayAlumnos.add(alumno);
            }
            if (arrayAlumnos.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return false;

    }

    public boolean registrarPersona(Personas persona) {
        PreparedStatement psRegistrar = null;
        Connection con = getConexion();

        String sql = "INSERT INTO personas (run, nombres, apellidoP, "
                + "apellidoM) VALUES(?,?,?,?)";

        try {
            psRegistrar = con.prepareStatement(sql);
            psRegistrar.setString(1, persona.getRun());
            psRegistrar.setString(2, persona.getNombre());
            psRegistrar.setString(3, persona.getApellidoP());
            psRegistrar.setString(4, persona.getApellidoM());
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

    public boolean editar(Alumno alumno, Apoderado apoderado) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "UPDATE personas SET nombres=?, "
                + "apellidoP=?, apellidoM=? WHERE run=?";
        String sql2 = "UPDATE alumno SET curso_fk=? "
                + "WHERE run_fk=?";
        
        String sql3 = "UPDATE personas SET nombres=?, "
                + "apellidoP=?, apellidoM=? WHERE run=?";

        try {
            ps = con.prepareStatement(sql);            
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellidoP());
            ps.setString(3, alumno.getApellidoM());
            ps.setString(4, alumno.getRun());
            ps.execute();
            ps = con.prepareStatement(sql2);
            ps.setInt(1, alumno.getId_curso());           
            ps.setString(2, alumno.getRun());
            ps.execute();
            ps = con.prepareStatement(sql3);
            ps.setString(1, apoderado.getNombre());
            ps.setString(2, apoderado.getApellidoP());
            ps.setString(3, apoderado.getApellidoM());
            ps.setString(4, apoderado.getRun());
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

    public boolean eliminar(Alumno alumno,Apoderado apoderado) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "DELETE FROM alumno WHERE run_fk=?";
        String sql2 = "DELETE FROM personas WHERE run=?";
        String sql3 = "DELETE FROM apoderado WHERE run_fk=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, alumno.getRun());
            ps.execute();
            ps = con.prepareStatement(sql2);
            ps.setString(1, alumno.getRun());
            ps.execute();
            ps = con.prepareStatement(sql3);
            ps.setString(1, apoderado.getRun());
            ps.execute();
            ps = con.prepareStatement(sql2);
            ps.setString(1, apoderado.getRun());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AlumnoPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
        return false;
    }

    public boolean buscar(Alumno alumno) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM personas p\n"
                + "INNER JOIN alumno a\n"
                + "ON p.run = a.run_fk\n"
                + "INNER JOIN curso c\n"
                + "ON a.curso_fk = c.id_curso\n"
                + "INNER JOIN apoderado ap\n"
                + "ON ap.id_apoderado = a.apoderado_fk\n"
                + "INNER JOIN personas per\n"
                + "ON ap.run_fk = per.run "
                + "WHERE p.run LIKE (?)";
        try {            
            ps = con.prepareStatement(sql);
            ps.setString(1, alumno.getRun());
            rs = ps.executeQuery();
            if (rs.next()) {
                alumno.setRun(rs.getString("p.run"));
                alumno.setNombre(rs.getString("p.nombres"));
                alumno.setApellidoP(rs.getString("p.apellidoP"));
                alumno.setApellidoM(rs.getString("p.apellidoM"));
                alumno.setFechaNac(rs.getDate("fecha_nacimiento"));
                alumno.setCurso(rs.getString("nombre_curso"));
                alumno.setId_curso(rs.getInt("c.id_curso"));
                alumno.setNombreApoderado(rs.getString("per.nombres") + " "
                        + rs.getString("per.apellidoP") + " " + rs.getString("per.apellidoM"));
                alumno.setRunApoderado(rs.getString("per.run"));
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
    
    /*public boolean buscarApoderado(Apoderado apoderado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();
        

        String sql = "SELECT * FROM personas p\n"
                + "INNER JOIN alumno a\n"
                + "ON p.run = a.run_fk\n"
                + "INNER JOIN curso c\n"
                + "ON a.curso_fk = c.id_curso\n"
                + "INNER JOIN apoderado ap\n"
                + "ON ap.id_apoderado = a.apoderado_fk\n"
                + "INNER JOIN personas per\n"
                + "ON ap.run_fk = per.run "
                + "WHERE per.run LIKE (?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, apoderado.getRun());
            rs = ps.executeQuery();
            if (rs.next()) {
                apoderado.setNombre("per.nombres");
                apoderado.setApellidoP(rs.getString("per.apellidoP"));
                apoderado.setApellidoM(rs.getString("per.apellidoM"));
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
    }*/

}
