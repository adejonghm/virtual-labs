package Suma_AngExt_Polygon;

import java.util.LinkedList;

/**
 *
 * @author alsola
 */
public class grafo {

    private LinkedList<vertice> vertices;
    private LinkedList<Arista> aristas;
    private LinkedList<LinkedList<Integer>> adyacencia;
    private int cantvertices;

    public grafo(int cantVertices) {
        this.cantvertices = cantVertices;
        vertices = new LinkedList<vertice>();
        adyacencia = new LinkedList<LinkedList<Integer>>();
        aristas = new LinkedList<Arista>();
        contruir();
    }

    public int getCantVertices() {
        return cantvertices;
    }

    public void setCantVertices(int cantvertices) {
        this.cantvertices = cantvertices;
    }

    public LinkedList<Integer> AdyacentesA(int v) {
        return adyacencia.get(v);
    }

    private void contruir() {
        int X = 0;
        int Y = 0;
        for (int i = 0; i < cantvertices; i++) {
            X = 250 + (int) (Math.cos(Math.toRadians((360 / cantvertices) * i)) * 100);
            Y = 250 + (int) ((Math.sin(Math.toRadians((360 / cantvertices)) * i)) * 100);
            vertice w = new vertice(X, Y, (char) Integer.parseInt((65 + i) + "") + "");
            vertices.add(w);
            
        }
        for (int i = 0; i < cantvertices; i++) {
            LinkedList<Integer> adyacentes = new LinkedList<Integer>();
            if (i != 0 && i != cantvertices - 1) {
                adyacentes.add(i + 1);
                adyacentes.add(i - 1);

            } else if (i != cantvertices - 1) {
                adyacentes.add(i + 1);
                adyacentes.add(cantvertices - 1);
            } else {
                adyacentes.add(0);
                adyacentes.add(i - 1);
            }
            adyacencia.add(adyacentes);
        }
        for (int i = 0; i < vertices.size(); i++) {
            vertice w = vertices.get(i);
            vertice v = vertices.get(AdyacentesA(i).get(0));
            Arista p = new Arista(w, v, cantvertices);
            aristas.add(p);
        }
    }

    public LinkedList<LinkedList<Integer>> getAdyacencia() {
        return adyacencia;
    }

    public LinkedList<Arista> getAristas() {
        return aristas;
    }

    public LinkedList<vertice> getVertices() {
        return vertices;
    }

    public int[] GetX() {
        int[] x = new int[cantvertices];
        for (int i = 0; i < vertices.size(); i++) {
            x[i] = (int) vertices.get(i).x;
        }
        return x;
    }

    public int[] GetY() {
        int[] y = new int[cantvertices];
        for (int i = 0; i < vertices.size(); i++) {
            y[i] = (int) vertices.get(i).y;
        }
        return y;
    }

    public int[] GetX(int j) {
        int[] x = new int[cantvertices];
        int pos = 0;
        for (int i = 0; i < vertices.size(); i++) {
            if (i == j) {
                continue;
            }
            x[pos++] = (int) vertices.get(i).x;
        }
        return x;
    }

    public int[] GetY(int j) {
        int[] y = new int[cantvertices];
        int pos = 0;
        for (int i = 0; i < vertices.size(); i++) {
            if (i == j) {
                continue;
            }
            y[pos++] = (int) vertices.get(i).y;
        }
        return y;
    }

    public int[] GetX(int j, int k) {
        int[] x = new int[cantvertices];
        int pos = 0;
        for (int i = 0; i < vertices.size(); i++) {
            if (i == j || i == k) {
                continue;
            }
            x[pos++] = (int) vertices.get(i).x;
        }
        return x;
    }

    public int[] GetY(int j, int k) {
        int[] y = new int[cantvertices];
        int pos = 0;
        for (int i = 0; i < vertices.size(); i++) {
            if (i == j || i == k) {
                continue;
            }
            y[pos++] = (int) vertices.get(i).y;
        }
        return y;
    }
}
