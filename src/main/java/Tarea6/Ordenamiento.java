package Tarea6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Ordenamiento {

    public static LinkedHashMap<Character, Integer> mergeSort(String texto) {
        HashMap<Character, Integer> mapa =
                Diccionarios.contadorCaracteres(texto);
        ArrayList<Map.Entry<Character, Integer>> lista =
                new ArrayList<>(mapa.entrySet());
        mergeSort(lista);
        LinkedHashMap<Character, Integer> resultado =
                new LinkedHashMap<>();
        for (Map.Entry<Character, Integer> entrada : lista) {
            resultado.put(entrada.getKey(), entrada.getValue());
        }
        return resultado;
    }

    public static void mergeSort(
            ArrayList<Map.Entry<Character, Integer>> lista) {
        if (lista.size() < 2) {
            return;
        }
        int mitad = lista.size() / 2;
        ArrayList<Map.Entry<Character, Integer>> izquierda =
                new ArrayList<>(lista.subList(0, mitad));
        ArrayList<Map.Entry<Character, Integer>> derecha =
                new ArrayList<>(lista.subList(mitad, lista.size()));
        mergeSort(izquierda);
        mergeSort(derecha);
        mezclar(lista, izquierda, derecha);
    }

    public static void mezclar(
            ArrayList<Map.Entry<Character, Integer>> lista,
            ArrayList<Map.Entry<Character, Integer>> izquierda,
            ArrayList<Map.Entry<Character, Integer>> derecha) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).getValue() >= derecha.get(j).getValue()) {
                lista.set(k, izquierda.get(i));
                i++;
            }
            else {
                lista.set(k, derecha.get(j));
                j++;
            }
            k++;
        }
        while (i < izquierda.size()) {
            lista.set(k, izquierda.get(i));
            i++;
            k++;
        }
        while (j < derecha.size()) {
            lista.set(k, derecha.get(j));
            j++;
            k++;
        }
    }
    public static LinkedHashMap<String, Integer> mergeSortPalabras(
            String texto) {
        HashMap<String, Integer> mapa =
                Diccionarios.contadorPalabrasHashMap(texto);
        ArrayList<Map.Entry<String, Integer>> lista =
                new ArrayList<>(mapa.entrySet());
        mergeSortPalabras(lista);
        LinkedHashMap<String, Integer> resultado =
                new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entrada : lista) {
            resultado.put(entrada.getKey(), entrada.getValue());
        }
        return resultado;
    }
    public static void mergeSortPalabras(
            ArrayList<Map.Entry<String, Integer>> lista) {
        if (lista.size() < 2) {
            return;
        }
        int mitad = lista.size() / 2;
        ArrayList<Map.Entry<String, Integer>> izquierda =
                new ArrayList<>(lista.subList(0, mitad));
        ArrayList<Map.Entry<String, Integer>> derecha =
                new ArrayList<>(lista.subList(mitad, lista.size()));
        mergeSortPalabras(izquierda);
        mergeSortPalabras(derecha);
        mezclarPalabras(lista, izquierda, derecha);
    }
    private static void mezclarPalabras(
            ArrayList<Map.Entry<String, Integer>> lista,
            ArrayList<Map.Entry<String, Integer>> izquierda,
            ArrayList<Map.Entry<String, Integer>> derecha) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).getValue() >= derecha.get(j).getValue()) {
                lista.set(k, izquierda.get(i));
                i++;
            }
            else {
                lista.set(k, derecha.get(j));
                j++;
            }
            k++;
        }
        while (i < izquierda.size()) {
            lista.set(k, izquierda.get(i));
            i++;
            k++;
        }
        while (j < derecha.size()) {
            lista.set(k, derecha.get(j));
            j++;
            k++;
        }
    }
    public static LinkedHashMap<String, Integer> mergeSortPalabrasAlfabetico(
            String texto) {
        HashMap<String, Integer> mapa =
                Diccionarios.contadorPalabrasHashMap(texto);
        ArrayList<Map.Entry<String, Integer>> lista =
                new ArrayList<>(mapa.entrySet());
        mergeSortPalabrasAlfabetico(lista);
        LinkedHashMap<String, Integer> resultado =
                new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entrada : lista) {
            resultado.put(entrada.getKey(), entrada.getValue());
        }
        return resultado;
    }

    public static void mergeSortPalabrasAlfabetico(
            ArrayList<Map.Entry<String, Integer>> lista) {
        if (lista.size() < 2) {
            return;
        }
        int mitad = lista.size() / 2;
        ArrayList<Map.Entry<String, Integer>> izquierda =
                new ArrayList<>(lista.subList(0, mitad));
        ArrayList<Map.Entry<String, Integer>> derecha =
                new ArrayList<>(lista.subList(mitad, lista.size()));
        mergeSortPalabrasAlfabetico(izquierda);
        mergeSortPalabrasAlfabetico(derecha);
        mezclarPalabrasAlfabetico(lista, izquierda, derecha);
    }
    private static void mezclarPalabrasAlfabetico(
            ArrayList<Map.Entry<String, Integer>> lista,
            ArrayList<Map.Entry<String, Integer>> izquierda,
            ArrayList<Map.Entry<String, Integer>> derecha) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < izquierda.size() && j < derecha.size()) {
            if (izquierda.get(i).getKey()
                    .compareToIgnoreCase(derecha.get(j).getKey()) <= 0) {
                lista.set(k, izquierda.get(i));
                i++;
            }
            else {
                lista.set(k, derecha.get(j));
                j++;
            }
            k++;
        }
        while (i < izquierda.size()) {
            lista.set(k, izquierda.get(i));
            i++;
            k++;
        }
        while (j < derecha.size()) {
            lista.set(k, derecha.get(j));
            j++;
            k++;
        }
    }
}