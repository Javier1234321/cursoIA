package Tarea6;

import java.util.HashMap;

public class Diccionarios {

    protected static HashMap<Character, Integer> contadorCaracteres(String texto){
        HashMap<Character,Integer> diccionario = new HashMap<>();
        for (int i = 0; i < texto.length(); i++){
            if (diccionario.containsKey(texto.charAt(i))){
                diccionario.put(
                        texto.charAt(i),
                        diccionario.get(texto.charAt(i)) + 1
                );
            }
            else {
                diccionario.put(texto.charAt(i), 1);
            }
        }
        return diccionario;
    }
    protected static int contadorPalabras(String texto){
        boolean primerCaracter = false;
        int contador = 0;
        for (int i = 0; i < texto.length(); i++){
            if (Character.isAlphabetic(texto.charAt(i)) && !primerCaracter){
                contador++;
                primerCaracter = true;
            }
            if (Character.isSpaceChar(texto.charAt(i))){
                primerCaracter = false;
            }
        }
        return contador;
    }
    protected static int contadorPalabrasUnicas(String texto){
        HashMap<String,Integer> contadorPalabras = new HashMap<>();
        String palabra = "";
        for (int i = 0; i < texto.length(); i++){
            if (Character.isAlphabetic(texto.charAt(i))){
                palabra += texto.charAt(i);
            }
            else if (Character.isSpaceChar(texto.charAt(i)) && !palabra.isEmpty()){
                contadorPalabras.put(
                        palabra,
                        contadorPalabras.getOrDefault(palabra, 0) + 1
                );
                palabra = "";
            }
        }
        if (!palabra.isEmpty()){
            contadorPalabras.put(
                    palabra,
                    contadorPalabras.getOrDefault(palabra, 0) + 1
            );
        }
        return contadorPalabras.size();
    }
    protected static int contadorCaracteresUnicos(String texto){
        HashMap<Character,Integer> contadorCaracteres=contadorCaracteres(texto);
        return contadorCaracteres.size();
    }

    protected static HashMap<String, Integer> contadorPalabrasHashMap(String texto){
        HashMap<String, Integer> diccionario = new HashMap<>();
        String palabra = "";
        for (int i = 0; i < texto.length(); i++){
            if (Character.isAlphabetic(texto.charAt(i))){
                palabra += texto.charAt(i);
            }
            else if (Character.isSpaceChar(texto.charAt(i)) && !palabra.isEmpty()){
                if (diccionario.containsKey(palabra)){
                    diccionario.put(
                            palabra,
                            diccionario.get(palabra) + 1
                    );
                }
                else {
                    diccionario.put(palabra, 1);
                }
                palabra = "";
            }
        }
        if (!palabra.isEmpty()){
            if (diccionario.containsKey(palabra)){
                diccionario.put(
                        palabra,
                        diccionario.get(palabra) + 1
                );
            }
            else {
                diccionario.put(palabra, 1);
            }
        }
        return diccionario;
    }

}
