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
public class Via {

    private String nombre;
    private String sentido;
    private int distancia;
    int cantFlujoCamiones;

    public Via(String nombre, String sentido, int distancia) {
        this.nombre = nombre;
        this.sentido = sentido;
        this.distancia = distancia;
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
     * @return the sentido
     */
    public String getSentido() {
        return sentido;
    }

    /**
     * @param sentido the sentido to set
     */
    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    /**
     * @return the distancia
     */
    public int getDistancia() {
        return distancia;
    }

    /**
     * @param distancia the distancia to set
     */
    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

}
