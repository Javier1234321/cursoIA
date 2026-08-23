package Tarea1.ListasLigadasDobles;

public class MainListasLigadasDobles{
    public static void main(String[] args) {
        ListaLigadaDoble<Integer> listaDoble= new ListaLigadaDoble<>();
        listaDoble.addFirst(new Node<>(1));
        listaDoble.addFirst(new Node<>(2));
        listaDoble.addFirst(new Node<>(3));
        listaDoble.addFirst(new Node<>(4));
        listaDoble.addFirst(new Node<>(5));
        listaDoble.addFirst(new Node<>(6));
        listaDoble.imprimirAscendente();
        listaDoble.addLast(new Node<>(10));
        listaDoble.addLast(new Node<>(11));
        listaDoble.addLast(new Node<>(12));
        listaDoble.imprimirAscendente();
        System.out.println(listaDoble.deleteLast());
        listaDoble.imprimirDescendente();
        System.out.println(listaDoble.deteleFirst());
        listaDoble.imprimirDescendente();
    }

}
