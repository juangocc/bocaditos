/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import grafos.Grafo;
import java.util.LinkedList;

/**
 *
 * @author juangocc
 */
public class MapaCiudad {

    LinkedList<Nodo> listaIntersecciones;
    LinkedList<CasaParticular> listaCasas;
    LinkedList<PuestoComidaRapida> listaPuestos;
    Via[][] matrizVias;
    Grafo grafo;

    public MapaCiudad() {
        listaIntersecciones = new LinkedList<>();
        matrizVias = new Via[0][0];
        listaCasas = new LinkedList<>();
        listaPuestos = new LinkedList<>();
    }

    public void crearInterseccion(String nombre, int posX, int posY) {
        listaIntersecciones.add(new Nodo(nombre, posX - 3, posY - 3, 6, 6));
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
            matrizVias[interseccionInicial][interseccionFinal] = new Via("V" + (getVias().size() + 1), direccion, peso);
        }
    }

    public void crearCasa(int posX, int posY) {
        CasaParticular cp = new CasaParticular("C" + listaCasas.size(), posX - 3, posY - 3, 15, 15);
        listaCasas.add(cp);
        listaIntersecciones.add(cp);
        redimensionarMatrizVias();
    }

    public void crearPuesto(int posX, int posY) {
        PuestoComidaRapida pc = new PuestoComidaRapida("P" + listaPuestos.size(), posX - 3, posY - 3, 10, 10);
        listaPuestos.add(pc);
        listaIntersecciones.add(pc);
        redimensionarMatrizVias();
    }

    public CasaParticular getCasa(String nombreCasa) {
        for (CasaParticular casaL : listaCasas) {
            if (casaL.getNombre().equals(nombreCasa)) {
                return casaL;
            }
        }
        return null;
    }

    public LinkedList<CasaParticular> getCasas() {
        return listaCasas;
    }

    public LinkedList<Via> getVias() {
        LinkedList<Via> listaVias = new LinkedList<>();
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    if (!listaVias.contains(via)) {
                        listaVias.add(via);
                    }
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
                        if (sentido.equals("Simple")) {
                            matrizVias[j][i] = null;
                        } else if (sentido.equals("Doble")) {
                            matrizVias[j][i] = via;
                        }

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
                        if (via.getSentido().equals("Simple")) {
                            matrizVias[i][j] = null;
                            matrizVias[j][i] = via;
                        } else if (via.getSentido().equals("Doble")) {
                            matrizVias[j][i] = via;
                        }
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

    public boolean solicitudPedido(String nombreCasa) {
        LinkedList<Nodo> ruta = getRutaOptima(nombreCasa);
        Nodo intLast = listaIntersecciones.getLast();
        Camion camion = null;
        for (PuestoComidaRapida puesto : listaPuestos) {
            camion = puesto.getCamionDisponible();
            if (camion != null) {
                camion.asignarRuta(ruta);
                return true;
            }
        }
        return false;
    }

    public LinkedList<Nodo> getRutaOptima(String nombreCasa) {
        int indiceIntersecc = -1;
        for (int i = 0; i < listaIntersecciones.size(); i++) {
            if (listaIntersecciones.get(i).getNombre().equals(nombreCasa)) {
                indiceIntersecc = i;
                break;
            }
        }
        LinkedList<Nodo> rutaOptima = new LinkedList<>();
        //-----------------
        int[][] grafoPesos = new int[listaIntersecciones.size()][listaIntersecciones.size()];
        int numAristas = 0;
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    grafoPesos[i][j] = via.getDistancia();
                    numAristas++;
                }
            }
        }
        grafo = new Grafo(grafoPesos, listaIntersecciones.size(), numAristas);
        //-----------
        String rutaStr = "";
        String verMatriz = grafo.MostrarMatriz();
        String floyMatriz = grafo.verMatriz(grafo.FloyWarshall().get(0));
        String dijkstra = grafo.Dijkstra(indiceIntersecc);
        String profundidad = grafo.recorrido(grafo.Profundidad());
        String anchura = grafo.recorrido(grafo.Anchura());
        String kruskal = grafo.verMatriz(grafo.KKruscal());
        String prim = grafo.verMatriz(grafo.Prim(indiceIntersecc).getGrafo());
        String fulkerson = grafo.fordFulkerson(indiceIntersecc, 5) + "";

        System.out.println("verMatriz : \n" + verMatriz + "\n");
        System.out.println("floyWarshall : \n" + floyMatriz + "\n");
        System.out.println("Dijkstra : \n" + dijkstra + "\n");
        System.out.println("Produndidad : \n" + profundidad + "\n");
        System.out.println("Anchura : \n" + anchura + "\n");
        System.out.println("Kruskal : \n" + kruskal + "\n");
        System.out.println("Prim : \n" + prim + "\n");
        System.out.println("Fulkerson : \n" + fulkerson + "\n");
        //-----------------------
        rutaStr = profundidad;
        String[] rutaVec = rutaStr.split(" ");
        int[] VectorDijktra = grafo.getVectorDijktra();
        int[] VectorVert = grafo.getVectorVert();

        for (String intStr : rutaVec) {
            int indiceInters = Integer.parseInt(intStr);
            rutaOptima.add(listaIntersecciones.get(indiceInters));
        }
        //-------------
        return rutaOptima;
    }

    public void desplazarCamiones() {
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

    public boolean registrarUsuario(String nombreCasa, Usuario nuevoUsuario) {
        for (CasaParticular casa : listaCasas) {
            if (casa.getNombre().equals(nombreCasa)) {
                casa.setUser(nuevoUsuario);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarVia(String nombreVia) {
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    if (via.getNombre().equals(nombreVia)) {
                        matrizVias[i][j] = null;
                        matrizVias[j][i] = null;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void imprimirMapaCiudad() {
        System.out.println("Mapa Ciudad \n");
        for (int i = 0; i < matrizVias.length; i++) {
            for (int j = 0; j < matrizVias[i].length; j++) {
                Via via = matrizVias[i][j];
                if (via != null) {
                    int peso = via.getDistancia();
                    System.out.print(" " + peso);
                } else {
                    System.out.print(" 0");
                }
            }
            System.out.println("");
        }
    }

    public void eliminarInterseccion(String nombreInterseccionInicial) {
        int indiceInters = -1;
        for (int i = 0; i < listaIntersecciones.size(); i++) {
            Nodo inters = listaIntersecciones.get(i);
            if (inters.getNombre().equals(nombreInterseccionInicial)) {
                indiceInters = i;
                break;
            }
        }
        listaIntersecciones.remove(indiceInters);
        // Elimina Columna con el vertice a eliminar
        for (int i = 0; i < matrizVias.length - 1; i++) {
            for (int j = indiceInters; j < matrizVias[i].length - 1; j++) {
                matrizVias[i][j] = matrizVias[i][j + 1];
            }
        }
        // Elimina fila con el vertice a eliminar
        for (int i = indiceInters; i < matrizVias.length - 1; i++) {
            for (int j = 0; j < matrizVias[i].length - 1; j++) {
                matrizVias[i][j] = matrizVias[i + 1][j];
            }
        }
        // Crear una nueva matriz reducida
        Via[][] matrizViasAux = new Via[listaIntersecciones.size()][listaIntersecciones.size()];
        for (int i = 0; i < matrizVias.length - 1; i++) {
            for (int j = 0; j < matrizVias[i].length - 1; j++) {
                matrizViasAux[i][j] = matrizVias[i][j];
            }
        }
        matrizVias = matrizViasAux;
    }
}
