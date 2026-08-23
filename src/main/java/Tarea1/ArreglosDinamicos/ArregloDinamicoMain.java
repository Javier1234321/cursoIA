package Tarea1.ArreglosDinamicos;

public class ArregloDinamicoMain {
    public static void main(String[] args) {
        ArregloDinamico<String> array=new ArregloDinamico<>();
        array.add("Hola");
        array.add("Tonotos");
        array.add("Adios");
        array.add("Tonotos");
        System.out.println(array.toString().toString());
        array.deleteLast();
        array.deleteLast();
        System.out.println(array.toString().toString());
    }
}
