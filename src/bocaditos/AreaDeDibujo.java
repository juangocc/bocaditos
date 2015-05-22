/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.util.LinkedList;
import javax.imageio.ImageIO;

/**
 *
 * @author juangocc
 */
public class AreaDeDibujo extends javax.swing.JPanel {

    private MapaCiudad mapaCiudad;
    private boolean pintarRutaDomicilio;
    Image[] listaimagenes;

    /**
     * Creates new form AreaDeDibujo
     */
    public AreaDeDibujo() {
        initComponents();
        listaimagenes = new Image[4];
        try {
            listaimagenes[0] = ImageIO.read(new File("src/images/house.png"));
            listaimagenes[1] = ImageIO.read(new File("src/images/carRight.png"));
            listaimagenes[2] = ImageIO.read(new File("src/images/carLeft.png"));
            listaimagenes[3] = ImageIO.read(new File("src/images/grocery.png"));
        } catch (Exception e) {
            System.out.println("Error Imagen : " + e);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs); //To change body of generated methods, choose Tools | Templates.
        try {
            if (getMapaCiudad() != null) {
                Graphics2D g2 = (Graphics2D) grphcs;
                LinkedList<Nodo> listaIntersecciones = getMapaCiudad().listaIntersecciones;
                Via[][] matrizVias = getMapaCiudad().matrizVias;
                for (int i = 0; i < matrizVias.length; i++) {
                    Nodo interseccionInicial = listaIntersecciones.get(i);
                    if (interseccionInicial instanceof CasaParticular) {
                        g2.drawImage(listaimagenes[0], interseccionInicial.getPosX(), interseccionInicial.getPosY(), interseccionInicial.getAncho(), interseccionInicial.getAlto(), this);
                    } else if (interseccionInicial instanceof PuestoComidaRapida) {
                        g2.drawImage(listaimagenes[3], interseccionInicial.getPosX(), interseccionInicial.getPosY(), interseccionInicial.getAncho(), interseccionInicial.getAlto(), this);
                        LinkedList<Camion> listaCamiones = ((PuestoComidaRapida) interseccionInicial).getListaCamiones();
                        for (Camion camion : listaCamiones) {
                            if (camion.direccion.equals("Derecha")) {
                                g2.drawImage(listaimagenes[1], camion.getPosX(), camion.getPosY(), camion.getAncho(), camion.getAlto(), this);
                            } else if (camion.direccion.equals("Izquierda")) {
                                g2.drawImage(listaimagenes[2], camion.getPosX(), camion.getPosY(), camion.getAncho(), camion.getAlto(), this);
                            }
                            //g2.draw(camion.getArea());
                            g2.drawString(camion.getNombre(), camion.getPosX() + 15, camion.getPosY() - 5);

                            if (pintarRutaDomicilio) {
                                pintarRuta(camion.rutaIdaRegreso, g2);
                            }
                        }
                    } else {
                        g2.draw(interseccionInicial.getArea());
                    }
                    g2.drawString(interseccionInicial.getNombre(), interseccionInicial.getPosX() - 5, interseccionInicial.getPosY() - 5);
                    for (int j = 0; j < matrizVias[i].length; j++) {
                        Via via = matrizVias[i][j];
                        if (via != null) {
                            Nodo interseccionFinal = listaIntersecciones.get(j);
                            int posX1 = interseccionInicial.getPosX();
                            int posY1 = interseccionInicial.getPosY();
                            int posX2 = interseccionFinal.getPosX();
                            int posY2 = interseccionFinal.getPosY();

                            int centX = ((posX1 + posX2) / 2);
                            int centY = ((posY1 + posY2) / 2);
                            g2.drawString(via.getNombre() + "-" + via.getDistancia(), centX, centY);

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
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("" + e);
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

    private void pintarRuta(LinkedList<LinkedList<Nodo>> rutaIdaRegreso, Graphics2D g2) {
        if (rutaIdaRegreso != null) {
            LinkedList<Nodo> rutaIda = rutaIdaRegreso.get(0);
            LinkedList<Nodo> rutaRegreso = rutaIdaRegreso.get(1);
            if (rutaIda != null && rutaIda.size() > 1) {
                g2.setColor(Color.green);
                for (int i = 0; i < rutaIda.size() - 1; i++) {
                    int posX1 = rutaIda.get(i).getPosX();
                    int posY1 = rutaIda.get(i).getPosY();
                    int posX2 = rutaIda.get(i + 1).getPosX();
                    int posY2 = rutaIda.get(i + 1).getPosY();
                    g2.drawLine(posX1, posY1, posX2, posY2);
                }
            }
            if (rutaRegreso != null && rutaRegreso.size() > 1) {
                g2.setColor(Color.blue);
                for (int i = 0; i < rutaRegreso.size() - 1; i++) {
                    int posX1 = rutaRegreso.get(i).getPosX();
                    int posY1 = rutaRegreso.get(i).getPosY();
                    int posX2 = rutaRegreso.get(i + 1).getPosX();
                    int posY2 = rutaRegreso.get(i + 1).getPosY();
                    g2.drawLine(posX1, posY1, posX2, posY2);
                }
            }
            g2.setColor(Color.black);
        }
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

    /**
     * @return the pintarRutaDomicilio
     */
    public boolean isPintarRutaDomicilio() {
        return pintarRutaDomicilio;
    }

    /**
     * @param pintarRutaDomicilio the pintarRutaDomicilio to set
     */
    public void setPintarRutaDomicilio(boolean pintarRutaDomicilio) {
        this.pintarRutaDomicilio = pintarRutaDomicilio;
    }
}
