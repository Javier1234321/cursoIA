package Diccionarios;

public class UtilsTexto {
    public static int numLetras(String texto){
        int contador=0;
        for (int i=0;i<texto.length();i++){
            char c=texto.charAt(i);
            if (Character.isLetter(c)){
                contador++;
            }
        }
        return contador;
    }
    public static int[] cuantaCaracteres(String texto,Character[] caracteres){
        int[] cuenta=new int[caracteres.length];
        for (int i=0;i<texto.length();i++){
            char c=texto.charAt(i);
            int indice=indiceDe(c,caracteres);
            if (indice>0){
                cuenta[indice]++;
            }
        }
        return cuenta;
    }

    private static int indiceDe(Object c, Object[] arreglo){
        for (int i = 0; i < arreglo.length; i++) {
            if (c.equals(arreglo[i])){
                return i;
            }
        }
        return -1;
    }
}
