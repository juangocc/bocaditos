/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author juangocc
 */
public class Camion extends Objeto {

    LinkedList<LinkedList<Nodo>> rutaIdaRegreso;
    LinkedList<Nodo> ruta;
    int intActual;
    int intSiguiente;
    int velocidad;
    int entregando;
    int posXInicial;
    int posYInicial;
    int capacidad;

    public Camion(String nombre, int posX, int posY, int ancho, int alto) {
        super(nombre, posX, posY, ancho, alto);
        this.velocidad = 5;
        this.posXInicial = posX;
        this.posYInicial = posY;
    }

    public void asignarRuta(LinkedList<LinkedList<Nodo>> rutaIdaRegreso) {
        this.rutaIdaRegreso = rutaIdaRegreso;
        this.ruta = rutaIdaRegreso.get(0);
        if (ruta.size() > 1) {
            intActual = ruta.size() - 1;
            intSiguiente = ruta.size() - 2;
            // validar que la ruta tenga mas de una interseccion, o si no indica que esta en el final del recorrido
            setEstado("Ocupado");
        } else {
            JOptionPane.showMessageDialog(null, "No hay ruta para llegar al destino.");
        }
    }

    public boolean desplazarCamionPedido() {
        if (getEstado().equals("Entrega")) {
            if (++entregando > 10) {// tiene un tiempo de espera de 10 mientras entrega el pedido
                entregando = 0;
                setEstado("Regresando");
            }
            return true;
        }
        double x = this.getPosX();
        double y = this.getPosY();
        double x1 = ruta.get(intActual).getPosX();
        double y1 = ruta.get(intActual).getPosY();
        double x2 = ruta.get(intSiguiente).getPosX();
        double y2 = ruta.get(intSiguiente).getPosY();
        // --------------Desplazar en X
        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            if (x2 < x1) {
                x -= velocidad;
            } else {
                x += velocidad;
            }
            // Ecuación de la Linea
            y = (((y2 - y1) / (x2 - x1)) * (x - x1)) + y1;
        } else // --------------Desplazar en Y 
        {
            if (y2 < y1) {
                y -= velocidad;
            } else {
                y += velocidad;
            }
            // Ecuación de la Linea
            x = (((x2 - x1) / (y2 - y1)) * (y - y1)) + x1;
        }
        setPosX((int) x);
        setPosY((int) y);
        // si ya a llegado a la siguiente intersección, remplazar la actual por la siguiente , y la siguiente por la siguiente de ella en la ruta
        if (ruta.get(intSiguiente).getArea().intersects(x, y, getAncho(), getAlto())) {
            intActual = intSiguiente;
            if (getEstado().equals("Ocupado")) {
                intSiguiente = intSiguiente - 1;
                if (intSiguiente < 0) {
                    // se entrega y se invierte la ruta para devolverse
                    setEstado("Entrega");
                    this.ruta = rutaIdaRegreso.get(1);
                    if (ruta.size() < 2) {
                        JOptionPane.showMessageDialog(null, "No hay ruta para llegar al destino.");
                        setEstado("Estancado");
                    } else {
                        intActual = ruta.size() - 1;
                        intSiguiente = ruta.size() - 2;
                    }
                }
            } else if (getEstado().equals("Regresando")) {
                intSiguiente = intSiguiente - 1;
                if (intSiguiente < 0) {
                    setEstado("Disponible");
                }
            }
        }
        // si ya es el final de la ruta, cambiar a estado Entrega
        return true;
    }
}
