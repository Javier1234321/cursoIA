package Practica2Avance3;

import java.util.ArrayList;
import java.util.List;

public class Oracion implements Vectorizable {
    private final String texto;
    public Oracion(String t) { this.texto = t; }
    @Override public double[] vector() { return Vectorizador.vectorizar(texto); }
    @Override public String getContenido() { return texto; }

    public static Oracion[] dividirTexto(String texto) {
        List<Oracion> out = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            sb.append(c);
            if (c == '.' || c == '!' || c == '?') {
                String s = sb.toString().trim();
                if (s.length() > 3) out.add(new Oracion(s));
                sb.setLength(0);
            }
        }
        String s = sb.toString().trim();
        if (s.length() > 3) out.add(new Oracion(s));
        return out.toArray(Oracion[]::new);
    }
}
