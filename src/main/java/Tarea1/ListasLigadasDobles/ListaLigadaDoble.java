package Tarea1.ListasLigadasDobles;

public class ListaLigadaDoble<T> {
    Node<T> primero=null;
    Node<T> ultimo=null;
    int contador=0;

    public void addFirst(Node<T> node){
        if (contador==0){
            primero=node;
            ultimo=node;
            node.setSiguiente(null);
            node.setAnterior(null);
            contador++;
            return;
        }
       node.setSiguiente(primero);
       node.setAnterior(null);
       primero.setAnterior(node);
       primero=node;
       contador++;
    }

    public void addLast(Node<T> node){
        if (contador==0){
            primero=node;
            ultimo=node;
            node.setSiguiente(null);
            node.setAnterior(null);
            contador++;
            return;
        }

        node.setAnterior(ultimo);
        node.setSiguiente(null);
        ultimo.setSiguiente(node);
        ultimo=node;
        contador++;
    }

    public Node<T> deteleFirst(){
        if (contador==0){
            return null;
        }
        Node<T> aux=primero;

        if (contador == 1) {
            primero = null;
            ultimo = null;
            contador--;
            return aux;
        }
        primero=primero.getSiguiente();
        primero.getAnterior().setSiguiente(null);
        primero.setAnterior(null);
        contador--;
        return aux;
    }

    public Node<T> deleteLast(){
        if (contador==0){
            return null;
        }
        Node<T> aux=ultimo;

        if (contador == 1) {
            primero = null;
            ultimo = null;
            contador--;
            return aux;
        }
        ultimo=ultimo.getAnterior();
        ultimo.getSiguiente().setAnterior(null);
        ultimo.setSiguiente(null);
        contador--;
        return aux;
    }

    public void imprimirAscendente(){
        Node<T> aux=primero;
        while(aux!=null){
            System.out.print("|"+aux+"|");
            aux=aux.getSiguiente();
        }
        System.out.println("");
    }

    public void imprimirDescendente(){
        Node<T> aux=ultimo;
        while(aux!=null){
            System.out.print("|"+aux+"|");
            aux=aux.getAnterior();
        }
        System.out.println("");
    }

}
