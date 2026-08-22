package Tarea1.Colas;

public class Cola<T> {
    private Node<T> primero=null;
    private Node<T> ultimo=null;
    int contador=0;

    public void enqueue(Node<T> nodo){
        if(contador==0){
            primero=nodo;
            ultimo=nodo;
        }
        ultimo.setSiguiente(nodo);
        ultimo=nodo;
        contador++;

    }

    public Node<T> queue(){
        if (contador==0){
            return null;
        }
        Node<T> nodoQueue=primero;
        primero=primero.getSiguiente();
        return nodoQueue;
    }

    public void imprimir(){
        Node<T> primeroAux=primero;
        while(primeroAux!=null){
            System.out.print("|"+primeroAux+"|");
            primeroAux=primeroAux.getSiguiente();
        }
        System.out.println("");
    }
}
