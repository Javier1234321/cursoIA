package Tarea6;

public class Main {
    static void main() {
        String texto="Hola Hola como estas me llamo rosselft y soy un guia explorador";
        System.out.println(Diccionarios.contadorCaracteres(texto));
        System.out.println(Diccionarios.contadorPalabras(texto));
        System.out.println(Diccionarios.contadorPalabrasUnicas(texto));
        System.out.println(Diccionarios.contadorCaracteresUnicos(texto));
        System.out.println(Ordenamiento.mergeSort(texto));
        System.out.println(Ordenamiento.mergeSortPalabras(texto));
        System.out.println(Ordenamiento.mergeSortPalabrasAlfabetico(texto));
    }
}
