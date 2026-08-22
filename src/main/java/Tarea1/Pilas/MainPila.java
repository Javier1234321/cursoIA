package Tarea1.Pilas;

public class MainPila {
    public static void main(String[]args) {
        Pila<Integer> pila=new Pila<>();
        pila.push(new Node<Integer>(3) );
        pila.push(new Node<Integer>(2));
        pila.push(new Node<Integer>(1));
        pila.imprimir();
        System.out.println(pila.pop());
        pila.imprimir();
        pila.push(new Node<Integer>(1));
        pila.push(new Node<Integer>(1));
        pila.push(new Node<Integer>(1));
        pila.imprimir();
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        System.out.println(pila.pop());
        pila.imprimir();

    }
}
