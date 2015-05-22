/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

/**
 *
 * @author juangocc
 */
public class Producto {
    private String nombre;
    private String precio;
    private String tamanio;

    public Producto(String nombre, String precio, String tamanio) {
        this.nombre = nombre;
        this.precio = precio;
        this.tamanio = tamanio;
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

    /**
     * @return the precio
     */
    public String getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(String precio) {
        this.precio = precio;
    }

    /**
     * @return the tamanio
     */
    public String getTamanio() {
        return tamanio;
    }

    /**
     * @param tamanio the tamanio to set
     */
    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
    
}
