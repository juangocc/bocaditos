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
public class PuestoComidaRapida extends Casa {

    private LinkedList<Camion> listaCamiones;

    public PuestoComidaRapida(String nombre, int posX, int posY, int ancho, int alto) {
        super(nombre, posX, posY, ancho, alto);
        listaCamiones = new LinkedList<>();
        Camion camion = new Camion("CM1", posX, posY, 5, 5);
        camion.setEstado("Disponible");
        listaCamiones.add(camion);
    }

    public Camion getCamionDisponible() {
        Camion camionDisp = null;
        for (Camion camion : listaCamiones) {
            if (camion.getEstado().equals("Disponible")) {
                camionDisp = camion;
                break;
            }
        }
        return camionDisp;
    }

    /**
     * @return the listaCamiones
     */
    public LinkedList<Camion> getListaCamiones() {
        return listaCamiones;
    }

    /**
     * @param listaCamiones the listaCamiones to set
     */
    public void setListaCamiones(LinkedList<Camion> listaCamiones) {
        this.listaCamiones = listaCamiones;
    }

}
