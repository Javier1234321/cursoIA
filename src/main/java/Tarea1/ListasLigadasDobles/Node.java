package Tarea1.ListasLigadasDobles;

public class Node<T> {
    private Node<T> siguiente;
    private Node<T> anterior;
    private T contenido;

    public Node(T contenido) {
        this.contenido = contenido;
    }

    public Node<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Node<T> siguiente) {
        this.siguiente = siguiente;
    }

    public Node<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(Node<T> anterior) {
        this.anterior = anterior;
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
