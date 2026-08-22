package Tarea1.Colas;

public class Node<T> {
    Node<T> siguiente;
    T contenido;

    public Node(T contenido) {
        this.contenido = contenido;
    }

    public Node<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Node<T> siguiente) {
        this.siguiente = siguiente;
    }

    public T getContenido() {
        return contenido;
    }

    public void setContenido(T contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Node{" +
                "contenido=" + contenido +
                '}';
    }
}
