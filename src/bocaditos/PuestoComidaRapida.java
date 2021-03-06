/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author juangocc
 */
public class PuestoComidaRapida extends Nodo {

    LinkedList<Producto> abastoProductos;
    private LinkedList<Camion> listaCamiones;

    public PuestoComidaRapida(String nombre, int posX, int posY, int ancho, int alto) {
        super(nombre, posX, posY, ancho, alto);
        listaCamiones = new LinkedList<>();
        Camion camion = new Camion("CM" + (getListaCamiones().size() + 1), posX, posY + 15, 20, 20, 5, 200);
        listaCamiones.add(camion);
        abastoProductos = new LinkedList<>();
    }

    public void agregarProductoAbasto(Producto abasto) {
        abastoProductos.add(abasto);
    }

    public boolean hacerPedido(LinkedList<Producto> listaPedido) {
        for (Producto prod : listaPedido) {
            boolean ofrecenProducto = false;
            for (Producto abastoProd : abastoProductos) {
                if (abastoProd.getNombre().equals(prod.getNombre())) {
                    if (abastoProd.getTamanio() >= prod.getTamanio()) {
                        abastoProd.setTamanio(abastoProd.getTamanio() - prod.getTamanio());
                    } else {
                        JOptionPane.showMessageDialog(null, "En el puesto " + getNombre() + " No hay suficiente : " + prod.getNombre() + ", solo quedan " + abastoProd.getTamanio());
                    }
                    ofrecenProducto = true;
                    break;
                }
            }
            if (!ofrecenProducto) {
                JOptionPane.showMessageDialog(null, "En el puesto " + getNombre() + " no ofrecemos " + prod.getNombre());
            }
        }
        return true;
    }

    public void agregarCamion(Camion camion) {
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
