package Tarea2.DFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Grafo<T> {

    private HashMap<T, List<T>> grafo = new HashMap<>();
    public void agregarArista(T origen, T destino) {
        if (!grafo.containsKey(origen)) {
            grafo.put(origen, new ArrayList<>());
        }
        if (!grafo.containsKey(destino)) {
            grafo.put(destino, new ArrayList<>());
        }
        grafo.get(origen).add(destino);
        grafo.get(destino).add(origen);
    }
    public void DFS(T inicio) {
        Stack<T> pila = new Stack<>();
        List<T> visitados = new ArrayList<>();
        pila.push(inicio);
        while (!pila.isEmpty()) {
            T actual = pila.pop();
            if (!visitados.contains(actual)) {
                System.out.print(actual + " ");
                visitados.add(actual);
                for (T vecino : grafo.get(actual)) {
                    if (!visitados.contains(vecino)) {
                        pila.push(vecino);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Grafo<Integer> grafo = new Grafo();
        grafo.agregarArista(1, 2);
        grafo.agregarArista(1, 3);
        grafo.agregarArista(2, 4);
        grafo.agregarArista(2, 5);
        grafo.agregarArista(3, 6);
        grafo.DFS(1);
    }
}