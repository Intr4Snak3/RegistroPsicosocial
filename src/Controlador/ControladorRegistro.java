/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Registro;
import Modelo.RegistroPersistencia;
import Vista.FrmAgregarRegistro;
import Vista.FrmInformeRegistros;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Intr4Snak3
 */
public class ControladorRegistro implements ActionListener {

    private Registro registro;
    private RegistroPersistencia registroP;
    private FrmAgregarRegistro frmAgregarRegistro;
    private FrmInformeRegistros frmInformeRegistros;

    private ArrayList<Registro> arrayRegistro;

    public ControladorRegistro(Registro registro, RegistroPersistencia registroP,
            FrmAgregarRegistro frmAgregarRegistro,
            FrmInformeRegistros frmInformeRegistros, ArrayList arrayRegistro) {
        this.registro = registro;
        this.registroP = registroP;
        this.frmAgregarRegistro = frmAgregarRegistro;
        this.frmInformeRegistros = frmInformeRegistros;

        this.frmAgregarRegistro.btnGuardar.addActionListener(this);
        this.frmInformeRegistros.btnBuscarFuncionario.addActionListener(this);

        this.arrayRegistro = arrayRegistro;

        llenarTablaRegistro();
    }

    public void llenarTablaRegistro() {
        if (registroP.todosLosRegistros(arrayRegistro)) {
            DefaultTableModel modeloRegistro = (DefaultTableModel) frmInformeRegistros.tblRegistros.getModel();
            int contFilas = modeloRegistro.getRowCount();

            for (int i = contFilas - 1; i >= 0; i--) {
                modeloRegistro.removeRow(i);
            }

            Object[] fila = new Object[6];

            for (int i = 0; i < arrayRegistro.size(); i++) {

                fila[0] = arrayRegistro.get(i).getId_registro();
                fila[1] = arrayRegistro.get(i).getFuncionario_a_cargo();
                fila[2] = arrayRegistro.get(i).getFechaRegistro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                fila[3] = arrayRegistro.get(i).getAlumno();
                fila[4] = arrayRegistro.get(i).getDescripcion();
                fila[5] = arrayRegistro.get(i).getNombre_estado();
                modeloRegistro.addRow(fila);

            }
        }
    }

    public void limpiarRegistro() {
        frmAgregarRegistro.txtAreaDescripcion.setText("");
        //frmAgregarRegistro.txtFecha.setText("");
        frmAgregarRegistro.txtFuncionario.setText("");
        frmAgregarRegistro.txtRunAlumno.setText("");
        frmAgregarRegistro.comboEstado.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == frmAgregarRegistro.btnGuardar) {
            LocalDate hoy = LocalDate.now();
            LocalTime ahora = LocalTime.now();
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            LocalDateTime fecha = LocalDateTime.of(hoy, ahora);
            //String strFecha = fecha.format(formatoFecha);

            //LocalDateTime fecha_new = LocalDateTime.parse(strFecha, formatoFecha);
            //String fechaFormateada = fecha.format(formatoFecha);
            registro.setDescripcion(frmAgregarRegistro.txtAreaDescripcion.getText());
            registro.setFechaRegistro(fecha);
            registro.setFuncionario_a_cargo(frmAgregarRegistro.txtFuncionario.getText());
            registro.setAlumno(frmAgregarRegistro.txtRunAlumno.getText());
            registro.setId_estado(frmAgregarRegistro.comboEstado.getSelectedIndex());

            if (registroP.guardarRegistro(registro)) {
                JOptionPane.showMessageDialog(null, "Registro Guardado");
                limpiarRegistro();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Guardar",
                        "Error", JOptionPane.ERROR_MESSAGE);
                limpiarRegistro();
            }

        }

        if (ae.getSource() == frmInformeRegistros.btnBuscarFuncionario) {
            registro.setFuncionario_a_cargo(frmInformeRegistros.txtRunFuncionario.getText());
            //registro.setAlumno(frmInformeRegistros.txtRunAlumno.getText());
            if (registroP.buscarRegistro(registro)) {

                DefaultTableModel modelo = (DefaultTableModel) frmInformeRegistros.tblRegistros.getModel();

                int cuentaFilas = modelo.getRowCount();

                for (int i = cuentaFilas - 1; i >= 0; i--) {
                    modelo.removeRow(i);
                }
                Object[] fila = new Object[6];

                //for (int i = 0; i < arrayRegistro.size(); i++) {
                    fila[0] = registro.getId_registro();
                    fila[1] = registro.getFuncionario_a_cargo();
                    fila[2] = registro.getFechaRegistro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    fila[3] = registro.getAlumno();
                    fila[4] = registro.getDescripcion();
                    fila[5] = registro.getNombre_estado();
                    modelo.addRow(fila);
                //}
            }
        }

        if (ae.getSource() == frmInformeRegistros.btnBuscarAlumno) {
            //registro.setFuncionario_a_cargo(frmInformeRegistros.txtRunFuncionario.getText());
            registro.setAlumno(frmInformeRegistros.txtRunAlumno.getText());
            if (registroP.buscarRegistro(registro)) {

                DefaultTableModel modelo = (DefaultTableModel) frmInformeRegistros.tblRegistros.getModel();

                int cuentaFilas = modelo.getRowCount();

                for (int i = cuentaFilas - 1; i >= 0; i--) {
                    modelo.removeRow(i);
                }
                Object[] fila = new Object[6];

                //for (int i = 0; i < arrayRegistro.size(); i++) {
                    fila[0] = registro.getId_registro();
                    fila[1] = registro.getFuncionario_a_cargo();
                    fila[2] = registro.getFechaRegistro().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    fila[3] = registro.getAlumno();
                    fila[4] = registro.getDescripcion();
                    fila[5] = registro.getNombre_estado();
                    modelo.addRow(fila);
                //}

            }
        }

    }

}
