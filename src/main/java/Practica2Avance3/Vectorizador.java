package Practica2Avance3;

import java.text.Normalizer;

public class Vectorizador {

    public static final int DIM = 28;

    public static double[] vectorizar(String texto) {
        double[] v = new double[DIM];

        // NFD descompone 'á' → 'a' + combining, 'ñ' → 'n' + combining, etc.
        // Al filtrar sólo a-z / A-Z los combining se caen solos.
        String norm = Normalizer.normalize(texto, Normalizer.Form.NFD);

        int letras = 0, mayus = 0, punt = 0, total = 0;
        for (int i = 0; i < norm.length(); i++) {
            char c = norm.charAt(i);
            total++;
            if (c >= 'a' && c <= 'z')      { v[c - 'a']++; letras++; }
            else if (c >= 'A' && c <= 'Z') { v[c - 'A']++; letras++; mayus++; }
            else if (esPuntuacion(c))      punt++;
        }
        if (letras > 0) for (int i = 0; i < 26; i++) v[i] /= letras;
        v[26] = letras > 0 ? (double) mayus / letras : 0;
        v[27] = total  > 0 ? (double) punt  / total  : 0;
        return v;
    }

    private static boolean esPuntuacion(char c) {
        return ",.;:!?¿¡\"'()[]{}«»-—…".indexOf(c) >= 0;
    }
}
