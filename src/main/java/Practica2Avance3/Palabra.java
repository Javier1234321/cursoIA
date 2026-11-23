package Practica2Avance3;

import java.util.ArrayList;
import java.util.List;

public class Palabra implements Vectorizable {
    private final String texto;
    public Palabra(String t) { this.texto = t; }
    @Override public double[] vector() { return Vectorizador.vectorizar(texto); }
    @Override public String getContenido() { return texto; }
    public int longitud() { return texto.length(); }

    public static Palabra[] dividirTexto(String texto) {
        List<Palabra> out = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (Character.isLetter(c)) sb.append(c);
            else if (sb.length() > 0) { out.add(new Palabra(sb.toString())); sb.setLength(0); }
        }
        if (sb.length() > 0) out.add(new Palabra(sb.toString()));
        return out.toArray(Palabra[]::new);
    }
}