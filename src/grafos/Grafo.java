/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Eduard
 */
public class Grafo {

    private int Grafo[][];
    private int Vertices = 0;
    private int Aristas = 0;

    public Grafo() {
    }

    public Grafo(int vertices) { //se crea el numero de Vertices y la matris del grafo
        this.Grafo = new int[vertices][vertices];
        this.Vertices = vertices;

    }

    public Grafo(int[][] Grafo, int NVertices, int NAristas)//para prim y Kruskal
    {
        this.Grafo = Grafo;
        this.Vertices = NVertices;
        this.Aristas = NAristas;

    }

    public int[][] getGrafo() {
        return this.Grafo;
    }

    public int getAristas() { //coseguir el numero de Aristas
        return this.Aristas;
    }

    public void agregarVertice() {
        int tem[][] = this.Grafo;
        int num = this.Vertices + 1;
        this.Vertices++;
        this.Grafo = new int[num][num];

        for (int i = 0; i < num - 1; i++) {//copia los elementos que ya estaban
            for (int j = 0; j < num - 1; j++) {
                this.Grafo[i][j] = tem[i][j];
            }
        }

    }

    public void CrearArista(int x, int y, int Costo)//Aristas con costo
    {
        if (x <= this.Vertices && y <= this.Vertices) {
            this.Grafo[x][y] = Costo;
            //this.Grafo[y][x]=Costo;//grafo no dirigido
            this.Aristas++;
        }
    }

    public void EliminarArista(int x, int y) {

        if (x <= this.Vertices && y <= this.Vertices) {
            this.Grafo[x][y] = 0;
        }

    }

    public boolean buscarArista(int x, int y) {

        if (x < this.Vertices && y < this.Vertices) {
            if (this.Grafo[x][y] != 0) {
                return true;
            }
        }
        return false;

    }

    public ArrayList<Integer> Profundidad() {
        ArrayList<Integer> vec = new ArrayList<>();
        LinkedList<Integer> pila = new LinkedList<>();

        pila.push(0);
        while (vec.size() != this.Vertices) {
            int tem = pila.remove();
            for (int j = this.Vertices - 1; j >= 0; j--) {

                if (this.Grafo[tem][j] != 0 && !vec.contains(j)) {
                    pila.push(j);
                }
            }
            vec.add(tem);
        }

        return vec;
    }

    public String recorrido(ArrayList<Integer> vec) {
        String cadena = "";

        for (int E : vec) {
            cadena += E + " ";
        }

        return cadena;
    }

    public ArrayList<Integer> Anchura() {

        ArrayList<Integer> vector = new ArrayList<>();
        Queue<Integer> cola = new LinkedList<>();
        cola.add(0);
        while (!cola.isEmpty()) {
            int tem = cola.remove();
            if (!vector.contains(tem)) {
                vector.add(tem);
            }
            for (int j = 0; j < this.Vertices; j++) {
                if (this.Grafo[tem][j] != 0 && !vector.contains(j)) {
                    cola.add(j);
                }
            }

        }
        return vector;

    }

    public String MostrarMatriz()//unicamente muestra la matriz del grafo
    {
        String letras[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m"};//para imprimir con letras
        String Res = " ";

        for (int i = 0; i < this.Vertices; i++) {
            Res += "  " + letras[i];
        }
        Res = Res + "\n";

        for (int i = 0; i < this.Vertices; i++) {
            Res += letras[i];

            for (int j = 0; j < this.Vertices; j++) {
                Res += "  " + Grafo[i][j];
            }

            Res += "\n";
        }
        return Res;

    }

    public String verMatriz(int[][] matriz)//me muestra cualquier tipo de matriz
    {
        String letras[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "ñ"};//para imprimir con letras
        String Res = " ";

        for (int i = 0; i < matriz.length; i++) {
            Res += "  " + letras[i];
        }
        Res = Res + "\n";

        for (int i = 0; i < matriz.length; i++) {
            Res += letras[i];

            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j] == Integer.MAX_VALUE) {
                    Res += "  " + "ND";
                } else {
                    Res += "  " + matriz[i][j];
                }
            }

            Res += "\n";
        }
        return Res;

    }
    private int[] VectorDijktra;
    private int[] VectorVert;

    public String Dijkstra(int Vinicial) {
        int Vaux = Vinicial;
        setVectorDijktra(new int[Vertices]);
        setVectorVert(new int[Vertices]);
        boolean[] VectorMarcados = new boolean[Vertices];

        Queue<Integer> Cola = new LinkedList();

        Cola.add(0);

        for (int i = 0; i < Vertices; i++) // marcamos todo como infinito
        {
            getVectorDijktra()[i] = Integer.MAX_VALUE;

        }

        VectorMarcados[Vinicial] = true;
        getVectorDijktra()[Vinicial] = 0;

        while (!Cola.isEmpty()) {

            int tem = Cola.remove();
            if (tem != 0) {
                for (int i = 0; i < Vertices; i++) {
                    if (getVectorDijktra()[i] == tem && !VectorMarcados[i]) /// asignar vertices con costo menor actual del vectordijktra
                    {
                        Vaux = i;
                        break;
                    }
                }
            }

            for (int i = 0; i < Vertices; i++) {
                if (Grafo[Vaux][i] != 0 && getVectorDijktra()[i] > Grafo[Vaux][i] + getVectorDijktra()[Vaux] && !VectorMarcados[i] && Vaux != i) // si el costo es menor al que ya esta
                {
                    getVectorDijktra()[i] = Grafo[Vaux][i] + getVectorDijktra()[Vaux];
                    getVectorVert()[i] = Vaux;
                } else if (Vaux == i) {
                    VectorMarcados[i] = true;
                }
            }
            for (int i = 0; i < Vertices; i++) {
                if (getVectorDijktra()[i] != 0 && getVectorDijktra()[i] != Integer.MAX_VALUE && !VectorMarcados[i])// agregar a la cola todos los costo actuales
                {
                    Cola.add(getVectorDijktra()[i]);
                }
            }

        }
        //SACAMOS RECORRIDO

        String Resultado = "";
        for (int i = 0; i < this.Vertices; i++) {
            if (getVectorDijktra()[i] == Integer.MAX_VALUE) {
                Resultado += "De: " + Vinicial + " a " + i + " ND\n";
            } else {
                Resultado += "De: " + Vinicial + " a " + i + " se llega por: " + getVectorVert()[i] + " con un costo: " + getVectorDijktra()[i] + "\n";
            }

        }

        return Resultado;

    }

    public LinkedList<int[][]> FloyWarshall() {
        int[][] Procedencia = new int[Vertices][Vertices];
        int[][] Matriz = new int[Vertices][Vertices];

        for (int i = 0; i < Vertices; i++)//Llenado de Matrices iniciales
        {
            for (int j = 0; j < Vertices; j++) {
                if (i == j) {
                    Matriz[i][j] = 0;

                } else if (Grafo[i][j] == 0) {
                    Matriz[i][j] = Integer.MAX_VALUE;

                } else {
                    Matriz[i][j] = Grafo[i][j];

                }
            }
        }

        for (int i = 0; i < Vertices; i++) {
            for (int j = 0; j < Vertices; j++) {
                Procedencia[i][j] = j;
            }
        }               // fin Llenado de Matrices iniciales

        for (int k = 0; k < Matriz.length; k++) {
            for (int i = 0; i < Matriz.length; i++) {
                for (int j = 0; j < Matriz.length; j++) {

                    if ((Matriz[i][k] != Integer.MAX_VALUE) && (Matriz[k][j] != Integer.MAX_VALUE) && (Math.min(Matriz[i][j], Matriz[i][k] + Matriz[k][j]) < Matriz[i][j])) {

                        Matriz[i][j] = Matriz[i][k] + Matriz[k][j];
                        Procedencia[i][j] = k;//Procedencia[i][k];
                    }
                }
            }
        }

        LinkedList<int[][]> Resultado = new LinkedList();
        Resultado.add(Matriz);
        Resultado.add(Procedencia);

        return Resultado;
    }

    public Grafo Prim(int Vi) {

        int[][] ArbolPrim = new int[this.Vertices][this.Vertices];
        int[][] Arbol = new int[this.Vertices][this.Vertices];

        Queue<Integer> Cola = new LinkedList<>();
        boolean[] Marcados = new boolean[Vertices];
        Marcados[Vi] = true;
        int Nmarcados = 1;
        int NAris = 0;

        for (int i = 0; i < this.Vertices; i++) {//relizo una copia del grafo origunal
            for (int j = 0; j < this.Vertices; j++) {
                ArbolPrim[i][j] = this.Grafo[i][j];
            }
        }

        while (NAris < Vertices) // != Nvertices-1
        {
            for (int i = 0; i < Vertices; i++) {
                if (Marcados[i]) // analise los pesos de las aristas de solo marcados
                {
                    for (int j = 0; j < Vertices; j++) {
                        if (ArbolPrim[i][j] != 0 && !Marcados[j])// si no son cero ni estan marcados
                        {
                            Cola.add(ArbolPrim[i][j]);
                        } else {
                            ArbolPrim[i][j] = 0; // borrar las que no se pueden seleccionar (puede no se tan necesario) pero si para evirar arista de mismo peso
                        }
                    }
                }
            }
            if (Cola.isEmpty()) {
                break;
            }
            int aux_menor_peso = Cola.poll(); // extraido arista con menor peso
            Cola.clear(); // vaciar cola porque en cada iteracion se esta volviendo a llenar (se peude marcar aristas para evitar esto)
            int aux_V_pad = Integer.MIN_VALUE; // vertices que crear arista con menor peso
            for (int i = 0; i < Vertices; i++) {
                if (Marcados[i]) // para evitar algunos casos de errores cuando ahi varios pesos iguales
                {
                    for (int j = 0; j < Vertices; j++) {
                        if (ArbolPrim[i][j] == aux_menor_peso) // puede haber error cuando un vertices tiene aristas con el mismo peso
                        {
                            aux_V_pad = j; // hallar vertice que crea a arista
                            Vi = i; // hallar Vertice inicial para contruir la arista, pueden haber errores
                            break;
                        }
                    }
                }
            }
            Arbol[Vi][aux_V_pad] = aux_menor_peso; // agregamos arista al arbol de expancion minima
            NAris++; //sumanos arista agregada
            ArbolPrim[Vi][aux_V_pad] = 0; // borramos arista que ya añadimos
            Marcados[aux_V_pad] = true; // marcamos vertice visitado
            Nmarcados++;
        }

        return new Grafo(Arbol, Vertices, NAris);

    }

    public int[][] KKruscal() {
        int[][] adyacencia = new int[this.Vertices][this.Vertices];
        int[][] Arbol = new int[Vertices][Vertices];// Devuelve la matriz de adyacencia del árbol mínimo.
        int[] pertenece = new int[Vertices];// indica a que árbol pertenece el nodo

        for (int i = 0; i < this.Vertices; i++) {
            for (int j = 0; j < this.Vertices; j++) {
                adyacencia[i][j] = this.Grafo[i][j];
            }
        }

        for (int i = 0; i < this.Vertices; i++) {
            pertenece[i] = i;
        }

        int nodoA = 0;
        int nodoB = 0;
        int arcos = 1;
        while (arcos < this.Vertices) {
            // Encontrar  el camino mínimo que no forma ciclo y guardar los nodos y la distancia.
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < this.Vertices; i++) {
                for (int j = 0; j < this.Vertices; j++) {
                    if (min > adyacencia[i][j] && adyacencia[i][j] != 0 && pertenece[i] != pertenece[j]) {
                        min = adyacencia[i][j];
                        nodoA = i;
                        nodoB = j;
                    }
                }
            }
            // Si los nodos no pertenecen al mismo árbol agrego el camino al árbol mínimo.
            if (pertenece[nodoA] != pertenece[nodoB]) {
                Arbol[nodoA][nodoB] = min;
                //Arbol[nodoB][nodoA] = min;

                // Todos los nodos del árbol del nodoB ahora pertenecen al árbol del nodoA.
                int temp = pertenece[nodoB];
                pertenece[nodoB] = pertenece[nodoA];
                for (int k = 0; k < this.Vertices; k++) {
                    if (pertenece[k] == temp) {
                        pertenece[k] = pertenece[nodoA];
                    }
                }
                arcos++;
            }
        }
        return Arbol;

    }

    private boolean HayCamino(int Vin, int Vfin, int[][] MiGrafo, int parent[]) {
        // vector de los vertices no marcados
        boolean[] visitado = new boolean[this.Vertices];

        // Cola para los vertices ,y marcar el que ya esta visitados
        Queue<Integer> q = new LinkedList<>();
        q.add(Vin);
        visitado[Vin] = true;
        parent[Vin] = -1;

        // 
        while (!q.isEmpty()) {
            int i = q.remove();

            for (int j = 0; j < this.Vertices; j++) {
                if (!visitado[j] && MiGrafo[i][j] > 0) {
                    q.add(j);
                    parent[j] = i;
                    visitado[j] = true;
                }
            }
        }

        // si tidavia exixte un camino retorna falso o verdadero
        return visitado[Vfin];
    }

    public int fordFulkerson(int Vin, int Vfin) {
        int u, v;
        int MiGrafo[][] = new int[this.Vertices][this.Vertices]; // operaciones sobre una copia el grafo  

        //se hace una copia del Grfo original
        for (int i = 0; i < this.Vertices; i++) {
            for (int j = 0; j < this.Vertices; j++) {
                MiGrafo[i][j] = this.Grafo[i][j];
            }

        }
        int[] padre = new int[this.Vertices];  // 
        padre[Vin] = -1;

        int FlujoMax = 0;

        // Mientras exista un camino
        while (HayCamino(Vin, Vfin, MiGrafo, padre)) {

            int FlujoArist = Integer.MAX_VALUE;
            for (v = Vfin; v != Vin; v = padre[v]) {
                u = padre[v];
                FlujoArist = Math.min(FlujoArist, MiGrafo[u][v]);
            }

            // crea residual
            // recorriendo el camino
            for (v = Vfin; v != Vin; v = padre[v]) {
                u = padre[v];
                MiGrafo[u][v] -= FlujoArist;
                MiGrafo[v][u] += FlujoArist;
            }

            // sumar flujo
            FlujoMax += FlujoArist;
        }

        return FlujoMax;
    }

    /**
     * @return the VectorDijktra
     */
    public int[] getVectorDijktra() {
        return VectorDijktra;
    }

    /**
     * @param VectorDijktra the VectorDijktra to set
     */
    public void setVectorDijktra(int[] VectorDijktra) {
        this.VectorDijktra = VectorDijktra;
    }

    /**
     * @return the VectorVert
     */
    public int[] getVectorVert() {
        return VectorVert;
    }

    /**
     * @param VectorVert the VectorVert to set
     */
    public void setVectorVert(int[] VectorVert) {
        this.VectorVert = VectorVert;
    }

}
