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

    private LinkedList<Casa> listaCasas;
    private LinkedList<PuestoComidaRapida> listaPuestos;
    private String nombre;
    private String sentido;
    private int peso;

    public Via(String nombre, String sentido, int peso) {
        listaCasas = new LinkedList<>();
        listaPuestos = new LinkedList<>();
        this.nombre = nombre;
        this.sentido = sentido;
        this.peso = peso;
    }

    public void crearCasa(int posX, int posY) {
        getListaCasas().add(new Casa("C" + (getListaCasas().size() + 1), posX - 10, posY - 10, 20, 20));
    }

    public void crearPuesto(int posX, int posY) {
        getListaPuestos().add(new PuestoComidaRapida("P" + (getListaCasas().size() + 1), posX - 5, posY - 5, 10, 10));
    }

    /**
     * @return the listaCasas
     */
    public LinkedList<Casa> getListaCasas() {
        return listaCasas;
    }

    /**
     * @param listaCasas the listaCasas to set
     */
    public void setListaCasas(LinkedList<Casa> listaCasas) {
        this.listaCasas = listaCasas;
    }

    /**
     * @return the listaPuestos
     */
    public LinkedList<PuestoComidaRapida> getListaPuestos() {
        return listaPuestos;
    }

    /**
     * @param listaPuestos the listaPuestos to set
     */
    public void setListaPuestos(LinkedList<PuestoComidaRapida> listaPuestos) {
        this.listaPuestos = listaPuestos;
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
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }
}
