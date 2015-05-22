/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import java.util.LinkedList;

/**
 *
 * @author juangocc
 */
public class Usuario {

    private String direccion;
    private String telefono;
    private String nombre;
    LinkedList<Producto> listaPedido;

    public Usuario(String nombre, String direccion, String telefono) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombre = nombre;
        listaPedido = new LinkedList<>();
    }

    public void agregarProductoAlPedido(Producto producto) {
        listaPedido.add(producto);
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
