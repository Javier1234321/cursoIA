package Tarea2.BFS;

import java.util.*;

public class Grafo<T> {
    private final HashMap<T, List<T>> graph=new HashMap<>();
    public void addEdge(T start,T end){
        if (!graph.containsKey(start)){
            graph.put(start, new ArrayList<>());
        }

        if(!graph.containsKey(end)){
            graph.put(end,new ArrayList<>());
        }

        graph.get(start).add(end);
        graph.get(end).add(start);
    }

    public void BFS(T start){
        Queue<T> queue=new LinkedList<>();
        List<T> visited=new ArrayList<>();

        queue.add(start);
        while(!queue.isEmpty()){
            T current=queue.poll();
            if (!visited.contains(current)){
                System.out.print(current+"->");
                visited.add(current);
                for (T c: graph.get(current)){
                    if (!visited.contains(c)){
                        queue.add(c);
                    }
                }
            }
        }
    }

    public static void main() {
        Grafo grafo = new Grafo();

        grafo.addEdge(1, 2);
        grafo.addEdge(1, 3);
        grafo.addEdge(2, 4);
        grafo.addEdge(2, 5);
        grafo.addEdge(3, 6);

        grafo.BFS(1);
    }
}
