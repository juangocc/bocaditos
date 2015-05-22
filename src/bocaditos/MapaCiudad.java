/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bocaditos;

import grafos.Grafo;
import java.util.LinkedList;
import javax.swing.JOptionPane;

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
        CasaParticular cp = new CasaParticular("C" + listaCasas.size(), posX - 3, posY - 3, 30, 30);
        listaCasas.add(cp);
        listaIntersecciones.add(cp);
        redimensionarMatrizVias();
    }

    public void crearPuesto(int posX, int posY) {
        PuestoComidaRapida pc = new PuestoComidaRapida("P" + listaPuestos.size(), posX - 3, posY - 3, 30, 30);
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
        LinkedList<LinkedList<Nodo>> rutaIdaRegreso = getRutaOptima(nombreCasa);
        if (rutaIdaRegreso != null) {

            Nodo intLast = listaIntersecciones.getLast();
            Camion camion = null;
            for (PuestoComidaRapida puesto : listaPuestos) {
                camion = puesto.getCamionDisponible();
                if (camion != null) {
                    camion.asignarRuta(rutaIdaRegreso);
                    return true;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró Ruta óptima");
        }
        return false;
    }

    public LinkedList<LinkedList<Nodo>> getRutaOptima(String nombreCasa) {
        int indiceIntersecc = -1;
        for (int i = 0; i < listaIntersecciones.size(); i++) {
            if (listaIntersecciones.get(i).getNombre().equals(nombreCasa)) {
                indiceIntersecc = i;
                break;
            }
        }
        LinkedList<LinkedList<Nodo>> rutaOptimaIdaRegreso = new LinkedList<>();
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
        String verMatriz = grafo.MostrarMatriz();

        int[][] floyMatriz = grafo.FloyWarshall().get(0);
        int[][] floyProcedencia = grafo.FloyWarshall().get(1);
        String floyWarshall = grafo.verMatriz(floyMatriz);
        String floyWarshallProcedencia = grafo.verMatriz(floyProcedencia);

        System.out.println("verMatriz : \n" + verMatriz + "\n");
        System.out.println("floyWarshall : \n" + floyWarshall + "\n");
        System.out.println("floyWarshall Procedencia: \n" + floyWarshallProcedencia + "\n");
        /*
         String profundidad = grafo.recorrido(grafo.Profundidad());
         String anchura = grafo.recorrido(grafo.Anchura());
         String kruskal = grafo.verMatriz(grafo.KKruscal());
         String prim = grafo.verMatriz(grafo.Prim(indiceIntersecc).getGrafo());
         String fulkerson = grafo.fordFulkerson(indiceIntersecc, listaIntersecciones.size() - 1) + "";
         System.out.println("Produndidad : \n" + profundidad + "\n");
         System.out.println("Anchura : \n" + anchura + "\n");
         System.out.println("Kruskal : \n" + kruskal + "\n");
         System.out.println("Prim : \n" + prim + "\n");
         System.out.println("Fulkerson : \n" + fulkerson + "\n");
         */
        //-----------------------
        /*
         System.out.println("Ruta Optima : " + rutaStr);
         //for (String intStr : rutaVec) {
         //  int indiceInters = Integer.parseInt(intStr);
         //rutaOptima.add(listaIntersecciones.get(indiceInters));
         //}
         //--   -----------
         }
         */
        //--------------------------
        int menorDistanciaAPuesto = Integer.MAX_VALUE;
        int indicePuesto = -1;
        for (int i = 0; i < floyMatriz.length; i++) {
            if (listaIntersecciones.get(i) instanceof PuestoComidaRapida && menorDistanciaAPuesto > floyMatriz[i][indiceIntersecc]) {
                menorDistanciaAPuesto = floyMatriz[i][indiceIntersecc];
                indicePuesto = i;
            }
        }
        if (indicePuesto != -1) {
            //------------------------ Ruta De Entrega
            LinkedList<Nodo> rutaOptimaIda = new LinkedList<>();
            String dijkstra = grafo.Dijkstra(indicePuesto);
            System.out.println("Dijkstra Ida: \n" + dijkstra + "\n");
            int[] VectorVert = grafo.getVectorVert();
            int procedencia = VectorVert[indiceIntersecc];
            rutaOptimaIda.add(listaIntersecciones.get(indiceIntersecc));
            String rutaStr = indiceIntersecc + "";
            while (procedencia != -1) {
                rutaOptimaIda.add(listaIntersecciones.get(procedencia));
                rutaStr += "," + procedencia;
                procedencia = VectorVert[procedencia];
            }
            System.out.println("Ruta Optima Ida: " + rutaStr);
            //-----------------------Ruta de Regreso
            LinkedList<Nodo> rutaOptimaRegreso = new LinkedList<>();
            dijkstra = grafo.Dijkstra(indiceIntersecc);
            System.out.println("Dijkstra Regreso: \n" + dijkstra + "\n");
            VectorVert = grafo.getVectorVert();
            procedencia = VectorVert[indicePuesto];
            rutaOptimaRegreso.add(listaIntersecciones.get(indicePuesto));
            rutaStr = indicePuesto + "";
            while (procedencia != -1) {
                rutaOptimaRegreso.add(listaIntersecciones.get(procedencia));
                rutaStr += "," + procedencia;
                procedencia = VectorVert[procedencia];
            }
            System.out.println("Ruta Optima Regreso: " + rutaStr);
            //-----------------------
            rutaOptimaIdaRegreso.add(rutaOptimaIda);
            rutaOptimaIdaRegreso.add(rutaOptimaRegreso);
        }
        //--------------------------
        return rutaOptimaIdaRegreso;
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
        Nodo inters = listaIntersecciones.get(indiceInters);
        if (inters instanceof CasaParticular) {
            listaCasas.remove((CasaParticular)inters);
        }else if(listaIntersecciones.get(indiceInters) instanceof PuestoComidaRapida){
             listaPuestos.remove((PuestoComidaRapida)inters);
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
