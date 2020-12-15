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
public interface IUsuarioPersistencia {
    //public boolean registrarPersona(Personas persona);
    public boolean registrarUsuario(Usuario usuario);
    public boolean editar(Usuario usuario);
    public boolean eliminar(Usuario usuario);
    public boolean buscar(Usuario usuario);
}
