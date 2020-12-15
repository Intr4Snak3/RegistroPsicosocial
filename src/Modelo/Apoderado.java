/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Intr4Snak3
 */
public class Apoderado extends Personas {

    //private int id_apoderado;
    private String parentesco;
    private String nivel_estudios;
    private String actividad;
    private String direccion;
    private int id_ciudad;

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getNivel_estudios() {
        return nivel_estudios;
    }

    public void setNivel_estudios(String nivel_estudios) {
        this.nivel_estudios = nivel_estudios;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getId_ciudad() {
        return id_ciudad;
    }

    public void setId_ciudad(int id_ciudad) {
        this.id_ciudad = id_ciudad;
    }

}
