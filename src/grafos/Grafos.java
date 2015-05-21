/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package grafos;

/**
 *
 * @author Eduard
 */
public class Grafos {

    /**c@66dc899ed
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       //TRABAJAMOS EL GRAFO
       /*
       A=0  B=1  C=2 D=3  E=4 F=5 G=6 H=7 I=8 ......
       */
      /*
        Grafo G=new Grafo(6);
        
        G.CrearArista(0, 1,7);
        G.CrearArista(0, 4,13);
        G.CrearArista(1, 2,5);
        G.CrearArista(1, 3,8);
        G.CrearArista(2, 0,9);
        G.CrearArista(2, 4,4);
        G.CrearArista(2, 5,3);
        G.CrearArista(3, 2,5);
        G.CrearArista(3, 5,3);
        G.CrearArista(5, 4,2);
        
        //System.out.println(G.MostrarMatriz());
       System.out.println(G.fordFulkerson(0,4));       
       */
        Grafo G=new Grafo(6);
        // Punto Inicial, Punto Final, Costo
        G.CrearArista(0, 1, 7);
        G.CrearArista(0, 4, 13);
        G.CrearArista(1, 3, 8);
        G.CrearArista(1, 2, 5);
        G.CrearArista(2, 0, 9);
        G.CrearArista(2, 4, 4);
        G.CrearArista(2, 5, 3);
        G.CrearArista(3, 2, 5);
        G.CrearArista(3, 5, 3);
        G.CrearArista(5, 4, 2);
        //System.out.println(G.MostrarMatriz());
        //System.out.println(G.verMatriz(G.FloyWarshall().get(0)));
        //System.out.println(G.Dijkstra(1));
        System.out.println(G.recorrido(G.Profundidad()));
        //System.out.println(G.recorrido(G.Anchura()));
        //System.out.println(G.verMatriz(G.KKruscal()));
        //System.out.println(G.verMatriz(G.Prim(0).getGrafo()));
       /*
       LinkedList<Integer> pila =new LinkedList<>();
       
       pila.push(3);
       pila.push(4);
       pila.push(9);
       
       for(Object E:pila){
           System.out.println(E);
       }
        
        System.out.println("-------------");
       
       Queue cola=new LinkedList();
       
       cola.add(3);
       cola.add(4);
       cola.add(9);
       //cola.add(7);
       //cola.add(2);
       
       //cola.poll();
       //cola.clear();
        System.out.println(cola.element());
        System.out.println("---------");
       for (Object E:cola){
           System.out.println(E);
       }
       */
        
    }
    
}
