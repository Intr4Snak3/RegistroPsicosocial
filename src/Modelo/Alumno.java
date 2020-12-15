/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Intr4Snak3
 */
public class Alumno extends Personas {

    private int id_matricula;
    private Date fechaNac;
    private int apoderado_fk;
    private int id_curso;
    private String curso;
    private String nombreApoderado;
    private String runApoderado;

    public String getRunApoderado() {
        return runApoderado;
    }

    public void setRunApoderado(String runApoderado) {
        this.runApoderado = runApoderado;
    }

    public String getNombreApoderado() {
        return nombreApoderado;
    }

    public void setNombreApoderado(String nombreApoderado) {
        this.nombreApoderado = nombreApoderado;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getApoderado_fk() {
        return apoderado_fk;
    }

    public void setApoderado_fk(int apoderado_fk) {
        this.apoderado_fk = apoderado_fk;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getId_matricula() {
        return id_matricula;
    }

    public void setId_matricula(int id_matricula) {
        this.id_matricula = id_matricula;
    }

    public int getId_curso() {
        return id_curso;
    }

    public void setId_curso(int id_curso) {
        this.id_curso = id_curso;
    }

}
