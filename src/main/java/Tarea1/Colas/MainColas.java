package Tarea1.Colas;

public class MainColas {
    public static void main(String[] args) {
        Cola<Integer> cola=new Cola<>();
        cola.enqueue(new Node<>(1));
        cola.enqueue(new Node<>(2));
        cola.enqueue(new Node<>(3));
        cola.enqueue(new Node<>(4));
        cola.imprimir();
        System.out.println(cola.queue());
        System.out.println(cola.queue());
        System.out.println(cola.queue());
        cola.imprimir();
    }
}
