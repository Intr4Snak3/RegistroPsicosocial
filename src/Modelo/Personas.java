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
public abstract class Personas {
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String run;

    public Personas(String nombre, String apellidoP, String apellidoM, String run) {
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.run = run;
    }
    
    public Personas(){
        
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }
    
    
}
