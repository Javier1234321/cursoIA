package Tarea1.Pilas;

public class Pila<T>{
    Node<T> ultimo=null;
    int contador=0;

    public void push(Node<T> node){
        Node<T> nodoNuevo= node;
        nodoNuevo.setSiguiente(ultimo);
        ultimo=nodoNuevo;
        contador++;
    }

    public Node<T> pop(){
        if(contador==0){
            return null;
        }
        Node<T> nodoPop=ultimo;
        ultimo=ultimo.getSiguiente();
        return nodoPop;
    }

    public void imprimir(){
        Node<T> aux=ultimo;
        while(aux!=null){
            System.out.println("|"+aux+"|");
            aux=aux.getSiguiente();
        }

    }
}
