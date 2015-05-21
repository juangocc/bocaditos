/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import grafos.Grafo;
import java.awt.geom.Line2D;
import java.util.LinkedList;

/**
 *
 * @author juangocc
 */
public class MapaCiudad {

    LinkedList<Interseccion> listaIntersecciones;
    Via[][] matrizVias;
    int numVias;
    Grafo grafo;

    public MapaCiudad() {
        listaIntersecciones = new LinkedList<>();
        matrizVias = new Via[0][0];
    }

    public void crearInterseccion(String nombre, int posX, int posY) {
        listaIntersecciones.add(new Interseccion(nombre, posX - 3, posY - 3, 6, 6));
        redimensionarMatrizVias();
    }

    public void redimensionarMatrizVias() {
        Via[][] matrizViasAux = new Via[matrizVias.length + 1][matrizVias.length + 1];
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                matrizViasAux[i][j] = matrizVias[i][j];
            }
        }
        matrizVias = matrizViasAux;
    }

    public void crearVia(String nombreInterseccionInicial, String nombreInterseccionFinal, String direccion, int peso) {
        int interseccionInicial = -1;
        int interseccionFinal = -1;
        for (int i = 0; i < listaIntersecciones.size(); i++) {
            if (listaIntersecciones.get(i).getNombre().equals(nombreInterseccionInicial)) {
                interseccionInicial = i;
            }
            if (listaIntersecciones.get(i).getNombre().equals(nombreInterseccionFinal)) {
                interseccionFinal = i;
            }
        }

        if (interseccionInicial != -1 && interseccionFinal != -1) {
            numVias++;
            matrizVias[interseccionInicial][interseccionFinal] = new Via("V" + numVias, direccion, peso);
        }
    }

    public void crearCasa(String tipo, int posX, int posY) {
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    Interseccion intsInicial = listaIntersecciones.get(i);
                    Interseccion intsFinal = listaIntersecciones.get(j);
                    int posX1 = intsInicial.getPosX();
                    int posY1 = intsInicial.getPosY();
                    int posX2 = intsFinal.getPosX();
                    int posY2 = intsFinal.getPosY();
                    Line2D linea = new Line2D.Double(posX1, posY1, posX2, posY2);
                    double dist = linea.ptLineDist(posX, posY);
                    if (dist <= 1) {
                        if (tipo.equals("Casa")) {
                            via.crearCasa(posX, posY);
                        } else if (tipo.equals("Puesto")) {
                            via.crearPuesto(posX, posY);
                        }
                    }
                }
            }
        }
    }

    public Casa getCasa(String ubicacionCasa) {
        String nombreVia = ubicacionCasa.split("-")[0];
        String nombreCasa = ubicacionCasa.split("-")[1];
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    if (via.getNombre().equals(nombreVia)) {
                        LinkedList<Casa> listaCasas = via.getListaCasas();
                        for (Casa casaL : listaCasas) {
                            if (casaL.getNombre().equals(nombreCasa)) {
                                return casaL;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public LinkedList<Casa> getCasas() {
        LinkedList<Casa> listaCasas = new LinkedList<>();
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    listaCasas.addAll(via.getListaCasas());
                }
            }
        }
        return listaCasas;
    }

    public LinkedList<Via> getVias() {
        LinkedList<Via> listaVias = new LinkedList<>();
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    listaVias.add(via);
                }
            }
        }
        return listaVias;
    }

    public boolean cambiarSentidoVia(String nombreVia, String sentido) {
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    if (via.getNombre().equals(nombreVia)) {
                        via.setSentido(sentido);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean girarSentidoVia(String nombreVia) {
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    if (via.getNombre().equals(nombreVia)) {
                        matrizVias[i][j] = matrizVias[j][i];
                        matrizVias[j][i] = via;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Via getVia(String nombreVia) {
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    if (via.getNombre().equals(nombreVia)) {
                        return via;
                    }
                }
            }
        }
        return null;
    }

    public boolean solicitudPedido(String ubicacionCasa) {
        LinkedList<Interseccion> ruta = getRutaOptima(ubicacionCasa);
        Interseccion intLast = listaIntersecciones.getLast();
        Camion camion = null;
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    LinkedList<PuestoComidaRapida> listaPuestos = via.getListaPuestos();
                    for (PuestoComidaRapida puesto : listaPuestos) {
                        camion = puesto.getCamionDisponible();
                        if (camion != null) {
                            camion.asignarRuta(ruta);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public LinkedList<Interseccion> getRutaOptima(String ubicacionCasa) {
        LinkedList<Interseccion> rutaOptima = new LinkedList<>();
        //-----------------
        int[][] grafoPesos = new int[listaIntersecciones.size()][listaIntersecciones.size()];
        int numAristas = 0;
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    grafoPesos[i][j] = via.getPeso();
                    numAristas++;
                }
            }
        }
        grafo = new Grafo(grafoPesos, listaIntersecciones.size(), numAristas);
        //-----------
        //System.out.println(G.MostrarMatriz());
        //System.out.println(G.verMatriz(G.FloyWarshall().get(0)));
        //System.out.println(G.Dijkstra(1));
        System.out.println(grafo.recorrido(grafo.Profundidad()));
        //System.out.println(G.recorrido(G.Anchura()));
        //System.out.println(G.verMatriz(G.KKruscal()));
        //System.out.println(G.verMatriz(G.Prim(0).getGrafo()));
        //-----------temporal
        rutaOptima = listaIntersecciones;
        //-------------
        return rutaOptima;
    }

    public void desplazarCamiones() {
        //recorro todas las vias
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    // obtengo todos los puestos
                    LinkedList<PuestoComidaRapida> listaPuestos = via.getListaPuestos();

                    for (PuestoComidaRapida puesto : listaPuestos) {
                        // obtengo todos sus comiones
                        LinkedList<Camion> listaCamiones = puesto.getListaCamiones();
                        for (Camion camion : listaCamiones) {
                            // desplazo los camiones que tienen estado no disponible
                            if (!camion.getEstado().equals("Disponible") && !camion.getEstado().equals("Varado")) {
                                camion.desplazarCamionPedido();
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean registrarUsuario(String ubicacionCasa, Usuario nuevoUsuario) {
        String nombreVia = ubicacionCasa.split("-")[0];
        String nombreCasa = ubicacionCasa.split("-")[1];
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    if (via.getNombre().equals(nombreVia)) {
                        LinkedList<Casa> listaCasas = via.getListaCasas();
                        for (Casa casa : listaCasas) {
                            if (casa.getNombre().equals(nombreCasa)) {
                                casa.setUser(nuevoUsuario);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
