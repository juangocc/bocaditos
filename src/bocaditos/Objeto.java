/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author juangocc
 */
public class Objeto {

    private boolean contienePunto;
    private boolean intersectaFigura;
    private String nombre;
    private String estado;
    private String imagenUbicacion;
    private boolean estaVivo;
    private boolean estaDesplazandose;
    private Rectangle2D area;

    public Objeto(String nombre, int posX, int posY, int ancho, int alto) {
        area = new Rectangle2D.Double(posX, posY, ancho, alto);
        this.nombre = nombre;
    }

    /**
     * @return the posX
     */
    public int getPosX() {
        return (int) area.getX();
    }

    /**
     * @param posX the posX to set
     */
    public void setPosX(int posX) {
        this.area.setFrame(posX, area.getY(), area.getWidth(), area.getHeight());
    }

    /**
     * @return the posY
     */
    public int getPosY() {
        return (int) area.getY();
    }

    /**
     * @param posY the posY to set
     */
    public void setPosY(int posY) {
        this.area.setFrame(area.getX(), posY, area.getWidth(), area.getHeight());
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return (int) area.getWidth();
    }

    /**
     * @param ancho the ancho to set
     */
    public void setAncho(int ancho) {
        this.area.setFrame(area.getX(), area.getY(), ancho, area.getHeight());
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return (int) area.getHeight();
    }

    /**
     * @param alto the alto to set
     */
    public void setAlto(int alto) {
        this.area.setFrame(area.getX(), area.getY(), area.getWidth(), alto);
    }

    /**
     * @return the contienePunto
     */
    public boolean isContienePunto() {
        return contienePunto;
    }

    /**
     * @param contienePunto the contienePunto to set
     */
    public void setContienePunto(boolean contienePunto) {
        this.contienePunto = contienePunto;
    }

    /**
     * @return the intersectaFigura
     */
    public boolean isIntersectaFigura() {
        return intersectaFigura;
    }

    /**
     * @param intersectaFigura the intersectaFigura to set
     */
    public void setIntersectaFigura(boolean intersectaFigura) {
        this.intersectaFigura = intersectaFigura;
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
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the imagenUbicacion
     */
    public String getImagenUbicacion() {
        return imagenUbicacion;
    }

    /**
     * @param imagenUbicacion the imagenUbicacion to set
     */
    public void setImagenUbicacion(String imagenUbicacion) {
        this.imagenUbicacion = imagenUbicacion;
    }

    /**
     * @return the estaVivo
     */
    public boolean isEstaVivo() {
        return estaVivo;
    }

    /**
     * @param estaVivo the estaVivo to set
     */
    public void setEstaVivo(boolean estaVivo) {
        this.estaVivo = estaVivo;
    }

    /**
     * @return the estaDesplazandose
     */
    public boolean isEstaDesplazandose() {
        return estaDesplazandose;
    }

    /**
     * @param estaDesplazandose the estaDesplazandose to set
     */
    public void setEstaDesplazandose(boolean estaDesplazandose) {
        this.estaDesplazandose = estaDesplazandose;
    }

    /**
     * @return the area
     */
    public Rectangle2D getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(Rectangle2D area) {
        this.area = area;
    }

}
