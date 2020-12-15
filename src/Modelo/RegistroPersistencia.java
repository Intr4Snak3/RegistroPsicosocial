/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import javafx.util.converter.LocalDateTimeStringConverter;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Intr4Snak3
 */
public class RegistroPersistencia extends Conexion {

    public boolean todosLosRegistros(ArrayList<Registro> arrayRegistro) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connect = getConexion();
        arrayRegistro.removeAll(arrayRegistro);

        String sql = "SELECT * FROM registro "
                + "INNER JOIN estado "
                + "ON estado_fk = id_estado";

        try {
            ps = connect.prepareStatement(sql);
            rs = ps.executeQuery();
            Registro registro;
            while (rs.next()) {
                registro = new Registro();

                //DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                DateTimeFormatter formatoFecha
                        = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss.S").toFormatter();
                //String fechaFormateada = rs.getDate("fecha_ingreso").format(formatoFecha);
                String stringFecha = rs.getString("dia_ingreso");
                LocalDateTime fecha = LocalDateTime.parse(stringFecha, formatoFecha);

                registro.setId_registro(rs.getInt("id_registro"));
                registro.setDescripcion(rs.getString("descripcion"));
                registro.setFechaRegistro(fecha);
                //registro.setAlumno(rs.getString("run_alumno_fk"));
                registro.setAlumno(rs.getString("run_alumno_fk"));
                registro.setFuncionario_a_cargo(rs.getString("run_usuario_fk"));
                registro.setNombre_estado(rs.getString("nombre_estado"));

                arrayRegistro.add(registro);

            }

            if (arrayRegistro.size() > 0) {
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

    public void consultarEstados(JComboBox comboEstado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM estado "
                + "ORDER BY id_estado ASC";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            comboEstado.addItem("Seleccione un estado");

            while (rs.next()) {
                comboEstado.addItem(rs.getInt("id_estado") + ".- " + rs.getString("nombre_estado"));
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

    public boolean guardarRegistro(Registro registro) {
        PreparedStatement psRegistrar = null;
        Connection con = getConexion();
        String sql = "INSERT INTO registro (descripcion, dia_ingreso, run_alumno_fk, run_usuario_fk,estado_fk)"
                + "VALUES(?,?,?,?,?)";

        //LocalDate hoy = LocalDate.now();
        //LocalTime ahora = LocalTime.now();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //LocalDateTime fecha = LocalDateTime.of(hoy, ahora);
        String fechaFormateada = registro.getFechaRegistro().format(formatoFecha);

        try {
            psRegistrar = con.prepareStatement(sql);
            psRegistrar.setString(1, registro.getDescripcion());
            psRegistrar.setString(2, fechaFormateada);
            psRegistrar.setString(3, registro.getAlumno());
            psRegistrar.setString(4, registro.getFuncionario_a_cargo());
            psRegistrar.setInt(5, registro.getId_estado());
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

    public boolean buscarRegistro(Registro registro) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = getConexion();

        String sql = "SELECT * FROM registro "
                + "INNER JOIN personas "
                + "ON personas.run = run_usuario_fk "
                + "INNER JOIN estado "
                + "ON id_estado = estado_fk "
                + "WHERE run LIKE(?)";

        String sql2 = "SELECT * FROM registro "
                + "INNER JOIN personas "
                + "ON personas.run = run_alumno_fk "
                + "INNER JOIN estado "
                + "ON id_estado = estado_fk "
                + "WHERE run LIKE(?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, registro.getFuncionario_a_cargo());
            rs = ps.executeQuery();
            if (rs.next()) {
                DateTimeFormatter formatoFecha
                        = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss.S").toFormatter();
                //String fechaFormateada = rs.getDate("fecha_ingreso").format(formatoFecha);
                String stringFecha = rs.getString("dia_ingreso");
                LocalDateTime fecha = LocalDateTime.parse(stringFecha, formatoFecha);

                registro.setId_registro(rs.getInt("id_registro"));
                registro.setDescripcion(rs.getString("descripcion"));
                registro.setFechaRegistro(fecha);
                //registro.setAlumno(rs.getString("run_alumno_fk"));
                registro.setAlumno(rs.getString("run_alumno_fk"));
                registro.setFuncionario_a_cargo(rs.getString("run_usuario_fk"));
                registro.setNombre_estado(rs.getString("nombre_estado"));

                return true;
            }
            ps = con.prepareStatement(sql2);
            ps.setString(1, registro.getAlumno());
            rs = ps.executeQuery();
            if (rs.next()) {
                DateTimeFormatter formatoFecha
                        = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss.S").toFormatter();
                //String fechaFormateada = rs.getDate("fecha_ingreso").format(formatoFecha);
                String stringFecha = rs.getString("dia_ingreso");
                LocalDateTime fecha = LocalDateTime.parse(stringFecha, formatoFecha);

                registro.setId_registro(rs.getInt("id_registro"));
                registro.setDescripcion(rs.getString("descripcion"));
                registro.setFechaRegistro(fecha);
                //registro.setAlumno(rs.getString("run_alumno_fk"));
                registro.setAlumno(rs.getString("run_alumno_fk"));
                registro.setFuncionario_a_cargo(rs.getString("run_usuario_fk"));
                registro.setNombre_estado(rs.getString("nombre_estado"));

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
