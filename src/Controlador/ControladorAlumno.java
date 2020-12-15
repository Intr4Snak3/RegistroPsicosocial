/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Alumno;
import Modelo.AlumnoPersistencia;
import Modelo.Apoderado;
import Modelo.UsuarioPersistencia;
import Vista.FrmAgregarAlumno;
import Vista.FrmEditarAlumno;
import Vista.FrmListaAlumnos;
import Vista.FrmPrincipal;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Intr4Snak3
 */
public class ControladorAlumno implements ActionListener, KeyListener {

    private Alumno alumno;
    private Apoderado apoderado;
    private AlumnoPersistencia alumnoP;
    private FrmAgregarAlumno frmAgregarAlumno;
    private FrmEditarAlumno frmEditarAlumno;
    private FrmListaAlumnos frmListaAlumnos;

    private ArrayList<Alumno> arrayAlumnos;

    SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy");

    public ControladorAlumno(FrmAgregarAlumno frmAgregarAlumno, Apoderado apoderado,
            FrmListaAlumnos frmListaAlumnos, FrmEditarAlumno frmEditarAlumno,
            Alumno alumno, AlumnoPersistencia alumnoP, ArrayList<Alumno> arrayAlumnos) {
        this.alumno = alumno;
        this.apoderado = apoderado;
        this.alumnoP = alumnoP;
        this.frmAgregarAlumno = frmAgregarAlumno;
        this.frmListaAlumnos = frmListaAlumnos;
        this.frmEditarAlumno = frmEditarAlumno;

        this.frmAgregarAlumno.btnGuardar.addActionListener(this);
        this.frmListaAlumnos.btnBuscar.addActionListener(this);
        this.frmListaAlumnos.btnEditar.addActionListener(this);
        this.frmListaAlumnos.btnEliminar.addActionListener(this);
        this.frmListaAlumnos.btnSalir.addActionListener(this);

//        this.frmEditarAlumno.btnGuardar.addActionListener(this);
        this.arrayAlumnos = arrayAlumnos;

        llenarTabla();
    }

    public String getFechaNac(JDateChooser jd) {
        if (jd.getDate() != null) {
            return fecha.format(jd.getDate());
        } else {
            return null;
        }
    }

    public java.util.Date StringDate(String fecha) {
        SimpleDateFormat formato_del_texto = new SimpleDateFormat("dd-M-yyyy");
        Date fechaN = null;
        try {
            fechaN = (Date) formato_del_texto.parse(fecha);
            return fechaN;
        } catch (ParseException ex) {
            return null;
        }
    }

    public void limpiar() {
        frmAgregarAlumno.txtNombreAlumno.setText("");
        frmAgregarAlumno.txtAlumnoApePat.setText("");
        frmAgregarAlumno.txtAlumnoApeMat.setText("");
        frmAgregarAlumno.txtRunAlumno.setText("");
        frmAgregarAlumno.txtNombreApoderado.setText("");
        frmAgregarAlumno.txtApoderadoApePat.setText("");
        frmAgregarAlumno.txtApoderadoApeMat.setText("");
        frmAgregarAlumno.txtRunApoderado.setText("");
        frmAgregarAlumno.txtCalle.setText("");
        frmAgregarAlumno.txtNumero.setText("");
        frmAgregarAlumno.txtParentesco.setText("");
        frmAgregarAlumno.txtnivelEstudios.setText("");
        frmAgregarAlumno.txtActividad.setText("");
        frmAgregarAlumno.comboBoxCiudad.setSelectedIndex(0);
        frmAgregarAlumno.jcomboCurso.setSelectedIndex(0);
        frmAgregarAlumno.jDateChooser1.setCalendar(null);
    }


    /*public ControladorAlumno(FrmListaAlumnos frmListaAlumnos, Alumno alumno, AlumnoPersistencia alumnoPersistencia, ArrayList<Alumno> arrayAlumnos) {
        this.alumno = alumno;
        this.alumnoP = alumnoP;
        this.frmListaAlumnos = frmListaAlumnos;

        this.frmListaAlumnos.btnBuscar.addActionListener(this);
        this.frmListaAlumnos.btnEditar.addActionListener(this);
        this.frmListaAlumnos.btnEliminar.addActionListener(this);
        this.frmListaAlumnos.btnSalir.addActionListener(this);

        this.arrayAlumnos = arrayAlumnos;
    }*/
    public void llenarTabla() {
        if (alumnoP.todosLosAlumnos(arrayAlumnos)) {
            DefaultTableModel modelo = (DefaultTableModel) frmListaAlumnos.tblAlumno.getModel();
            int cuentaFilas = modelo.getRowCount();

            for (int i = cuentaFilas - 1; i >= 0; i--) {
                modelo.removeRow(i);
            }
            Object[] fila = new Object[5];
            for (int i = 0; i < arrayAlumnos.size(); i++) {
                fila[0] = arrayAlumnos.get(i).getNombre()
                        + " " + arrayAlumnos.get(i).getApellidoP()
                        + " " + arrayAlumnos.get(i).getApellidoM();
                fila[1] = arrayAlumnos.get(i).getRun();
                fila[2] = arrayAlumnos.get(i).getFechaNac();
                fila[3] = arrayAlumnos.get(i).getCurso();
                fila[4] = arrayAlumnos.get(i).getNombreApoderado();

                modelo.addRow(fila);

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frmAgregarAlumno.btnGuardar) {

            alumno.setNombre(frmAgregarAlumno.txtNombreAlumno.getText());
            alumno.setApellidoP(frmAgregarAlumno.txtAlumnoApePat.getText());
            alumno.setApellidoM(frmAgregarAlumno.txtAlumnoApeMat.getText());
            alumno.setRun(frmAgregarAlumno.txtRunAlumno.getText());
            alumno.setFechaNac(frmAgregarAlumno.jDateChooser1.getDate());
            alumno.setId_curso(frmAgregarAlumno.jcomboCurso.getSelectedIndex());
            // alumno.setApoderado_fk(0);
            apoderado.setRun(frmAgregarAlumno.txtRunApoderado.getText());
            apoderado.setNombre(frmAgregarAlumno.txtNombreApoderado.getText());
            apoderado.setApellidoP(frmAgregarAlumno.txtApoderadoApePat.getText());
            apoderado.setApellidoM(frmAgregarAlumno.txtApoderadoApeMat.getText());
            apoderado.setDireccion(
                    frmAgregarAlumno.txtCalle.getText()
                    + " " + frmAgregarAlumno.txtNumero.getText());
            apoderado.setParentesco(frmAgregarAlumno.txtParentesco.getText());
            apoderado.setId_ciudad(frmAgregarAlumno.comboBoxCiudad.getSelectedIndex());
            apoderado.setNivel_estudios(frmAgregarAlumno.txtnivelEstudios.getText());
            apoderado.setActividad(frmAgregarAlumno.txtActividad.getText());

            if (alumnoP.registrarAlumno(alumno, apoderado)) {
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar", "Error", JOptionPane.ERROR_MESSAGE);
                limpiar();
            }

        }
        if (ae.getSource() == frmListaAlumnos.btnBuscar) {
            alumno.setRun(frmListaAlumnos.txtRun.getText());
            if (alumnoP.buscar(alumno)) {

                DefaultTableModel modelo = (DefaultTableModel) frmListaAlumnos.tblAlumno.getModel();

                int cuentaFilas = modelo.getRowCount();

                for (int i = cuentaFilas - 1; i >= 0; i--) {
                    modelo.removeRow(i);
                }
                Object[] fila = new Object[5];
                fila[0] = alumno.getNombre()
                        + " " + alumno.getApellidoP()
                        + " " + alumno.getApellidoM();
                fila[1] = alumno.getRun();
                fila[2] = alumno.getFechaNac();
                fila[3] = alumno.getCurso();
                fila[4] = alumno.getNombreApoderado();

                modelo.addRow(fila);
            }
        }

        if (ae.getSource() == frmListaAlumnos.btnEditar) {
            alumno.setRun(frmListaAlumnos.txtRun.getText());
            if (alumnoP.buscar(alumno)) {
                frmEditarAlumno = new FrmEditarAlumno();
                FrmPrincipal.jdpPrincipal.add(frmEditarAlumno);
                frmEditarAlumno.show();                
                
                frmEditarAlumno.txtNombreAlumno4.setText(alumno.getNombre());
                frmEditarAlumno.txtAlumnoApePat4.setText(alumno.getApellidoP());
                frmEditarAlumno.txtAlumnoApeMat4.setText(alumno.getApellidoM());
                frmEditarAlumno.jcomboeditCurso.setSelectedIndex(alumno.getId_curso());
                
                String apo = alumno.getNombreApoderado();
                String[] apoDividido = apo.split(" ");
                
                frmEditarAlumno.txtNombreApoderado4.setText(apoDividido[0]);
                frmEditarAlumno.txtApoderadoApePat4.setText(apoDividido[1]);
                frmEditarAlumno.txtApoderadoApeMat4.setText(apoDividido[2]);          
                
                
                frmEditarAlumno.btnGuardar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        if (ae.getSource() == frmEditarAlumno.btnGuardar) {
                            //Revisar                            
                            alumno.setRun(frmListaAlumnos.txtRun.getText());

                            alumno.setNombre(frmEditarAlumno.txtNombreAlumno4.getText());
                            alumno.setApellidoP(frmEditarAlumno.txtAlumnoApePat4.getText());
                            alumno.setApellidoM(frmEditarAlumno.txtAlumnoApeMat4.getText());
                            alumno.setId_curso(frmEditarAlumno.jcomboeditCurso.getSelectedIndex());
                            apoderado.setNombre(frmEditarAlumno.txtNombreApoderado4.getText());
                            apoderado.setApellidoP(frmEditarAlumno.txtApoderadoApePat4.getText());
                            apoderado.setApellidoM(frmEditarAlumno.txtApoderadoApeMat4.getText());
                            apoderado.setRun(alumno.getRunApoderado());

                            if (alumnoP.editar(alumno,apoderado)) {
                                JOptionPane.showMessageDialog(null, "Alumno Modificado");
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

            }
        }
        
        if (ae.getSource() == frmListaAlumnos.btnEliminar) {
            alumno.setRun(frmListaAlumnos.txtRun.getText());
            apoderado.setRun(alumno.getRunApoderado());
            if (alumnoP.eliminar(alumno, apoderado)) {
                JOptionPane.showMessageDialog(null, "Usuario Eliminado");
                limpiar();
                llenarTabla();
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
