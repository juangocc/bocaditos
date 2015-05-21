/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 *
 * @author juangocc
 */
public class AreaDeDibujo extends javax.swing.JPanel {

    private MapaCiudad mapaCiudad;

    /**
     * Creates new form AreaDeDibujo
     */
    public AreaDeDibujo() {
        initComponents();
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
        try {
            if (getMapaCiudad() != null) {
                Graphics2D g2 = (Graphics2D) grphcs;
                LinkedList<Interseccion> listaIntersecciones = getMapaCiudad().listaIntersecciones;
                Via[][] matrizVias = getMapaCiudad().matrizVias;
                for (int i = 0; i < matrizVias.length; i++) {
                    Interseccion interseccionInicial = listaIntersecciones.get(i);
                    g2.drawString(interseccionInicial.getNombre(), interseccionInicial.getPosX() - 5, interseccionInicial.getPosY() - 5);
                    g2.draw(interseccionInicial.getArea());
                    for (int j = 0; j < matrizVias[i].length; j++) {
                        Via via = matrizVias[i][j];
                        if (via != null) {
                            Interseccion interseccionFinal = listaIntersecciones.get(j);
                            int posX1 = interseccionInicial.getPosX();
                            int posY1 = interseccionInicial.getPosY();
                            int posX2 = interseccionFinal.getPosX();
                            int posY2 = interseccionFinal.getPosY();

                            int centX = ((posX1 + posX2) / 2);
                            int centY = ((posY1 + posY2) / 2);
                            g2.drawString(via.getNombre() + "-" + via.getPeso(), centX, centY);

                            g2.drawLine(posX1, posY1, posX2, posY2);
                            if (via.getSentido().equals("Simple")) {
                                dibujarFlecha(posX1, posY1, posX2, posY2, g2);
                            } else if (via.getSentido().equals("Doble")) {
                                dibujarFlecha(posX1, posY1, posX2, posY2, g2);
                                dibujarFlecha(posX2, posY2, posX1, posY1, g2);
                            } else if (via.getSentido().equals("Bloqueada")) {
                                // dibujar equis
                                dibujarFlecha(posX1, posY1, centX, centY, g2);
                                dibujarFlecha(posX2, posY2, centX, centY, g2);
                            }
                            LinkedList<Casa> listaCasas = via.getListaCasas();
                            for (Casa casa : listaCasas) {
                                g2.drawString(casa.getNombre(), casa.getPosX() - 5, casa.getPosY() - 5);
                                g2.draw(casa.getArea());
                            }
                            LinkedList<PuestoComidaRapida> listaPuestos = via.getListaPuestos();
                            for (PuestoComidaRapida puesto : listaPuestos) {
                                g2.drawString(puesto.getNombre(), puesto.getPosX() - 5, puesto.getPosY() - 5);
                                g2.draw(puesto.getArea());
                                LinkedList<Camion> listaCamiones = puesto.getListaCamiones();
                                for (Camion camion : listaCamiones) {
                                    g2.drawString(camion.getNombre(), camion.getPosX() - 5, camion.getPosY() - 5);
                                    g2.draw(camion.getArea());
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(""+e);
        }
    }

    public void dibujarFlecha(int posX1, int posY1, int posX2, int posY2, Graphics2D g2) {
        double arrowBody = angleOf(posX1, posY1, posX2, posY2) - 180;
        double startX = posX2;
        double startY = posY2;
        int distanceLines = 20;
        int lengthLines = 20;
        //---------------------------------
        double angleDeg = arrowBody - distanceLines - 270;
        double angleRad = angleDeg * Math.PI / 180;
        double endX = startX + lengthLines * Math.sin(angleRad);
        double endY = startY + lengthLines * Math.cos(angleRad);
        g2.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
        //----------------------------------
        angleDeg = arrowBody + distanceLines - 270;
        angleRad = angleDeg * Math.PI / 180;
        endX = startX + lengthLines * Math.sin(angleRad);
        endY = startY + lengthLines * Math.cos(angleRad);
        g2.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
    }

    public double angleOf(int startX, int startY, int endX, int endY) {
        // NOTE: Remember that most math has the Y axis as positive above the X.
        // However, for screens we have Y as positive below. For this reason, 
        // the Y values are inverted to get the expected results.
        final double deltaY = (startY - endY);
        final double deltaX = (endX - startX);
        final double result = Math.toDegrees(Math.atan2(deltaY, deltaX));
        return (result < 0) ? (360d + result) : result;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    /**
     * @return the mapaCiudad
     */
    public MapaCiudad getMapaCiudad() {
        return mapaCiudad;
    }

    /**
     * @param mapaCiudad the mapaCiudad to set
     */
    public void setMapaCiudad(MapaCiudad mapaCiudad) {
        this.mapaCiudad = mapaCiudad;
    }
}
