package Diccionarios;

import java.util.HashMap;

public class Diccionarios {

    public static HashMap<Character, Integer> contadorCaracteres(String texto){
        HashMap<Character,Integer> diccionario=new HashMap<>();
        for (int i=0;i<texto.length();i++){

            if (diccionario.containsKey(texto.charAt(i)) && texto.charAt(i)==' '){
                diccionario.put(texto.charAt(i),diccionario.get(texto.charAt(i))+1);
            }
            else {
                diccionario.put(texto.charAt(i),1);
            }
        }
        return diccionario;
    }

}
