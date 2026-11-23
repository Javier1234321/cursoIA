package Practica2Avance3;

import java.util.ArrayList;
import java.util.List;

public class Parrafo implements Vectorizable {
    private final String texto;
    public Parrafo(String t) { this.texto = t; }
    @Override public double[] vector() { return Vectorizador.vectorizar(texto); }
    @Override public String getContenido() { return texto; }

    public static Parrafo[] dividirTexto(String texto) {
        List<Parrafo> out = new ArrayList<>();
        for (String p : texto.split("\\n\\s*\\n")) {
            String s = p.trim();
            if (s.length() > 3) out.add(new Parrafo(s));
        }
        if (out.isEmpty() && texto.trim().length() > 3)
            out.add(new Parrafo(texto.trim()));
        return out.toArray(Parrafo[]::new);
    }
}
