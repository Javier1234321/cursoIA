package Practica2Avance3;

import Vectorizacion.UtilsText;
import java.util.ArrayList;
import java.util.List;

public class ComparadorQuijote {

    private static final String RUTA_QUIJOTE =
            "C:/Users/javie/Inteligencia_Artificial/cursoIA/src/main/java/Practica2Avance3/donquijote.txt";

    // Rutas para los nuevos archivos de texto.
    // Ajústalas si guardaste los .txt en otra carpeta.
    private static final String RUTA_TEXTO1 =
            "C:/Users/javie/Inteligencia_Artificial/cursoIA/src/main/java/Practica2Avance3/texto1.txt";
    private static final String RUTA_TEXTO2 =
            "C:/Users/javie/Inteligencia_Artificial/cursoIA/src/main/java/Practica2Avance3/texto2.txt";

    public static void main(String[] args) {

        // ---------- 1) Vectorizar las oraciones del Quijote ----------
        Texto quijote = new Texto(UtilsText.leeTexto(RUTA_QUIJOTE));
        Oracion[] oracionesQuijote = quijote.oraciones();
        System.out.println("Oraciones del Quijote: " + oracionesQuijote.length);

        double[][] vectoresQuijote = new double[oracionesQuijote.length][];
        for (int i = 0; i < oracionesQuijote.length; i++)
            vectoresQuijote[i] = oracionesQuijote[i].vector();

        // ---------- 2) Construir el cuantizador con 32 centroides ----------
        long t0 = System.currentTimeMillis();
        CuantizadorVectorial cuantizador = new CuantizadorVectorial(32, vectoresQuijote);
        long t = System.currentTimeMillis() - t0;
        System.out.println("Cuantizador construido: " + cuantizador.size()
                + " centroides en " + t + " ms  (dim = "
                + vectoresQuijote[0].length + ")");

        // ---------- 3) Leer los textos desde los archivos y evaluarlos ----------
        System.out.println("\nLeyendo textos desde archivos externos...");
        String texto1 = UtilsText.leeTexto(RUTA_TEXTO1);
        String texto2 = UtilsText.leeTexto(RUTA_TEXTO2);

        double d1 = evaluar(cuantizador, "TEXTO 1 (texto1.txt)", texto1);
        double d2 = evaluar(cuantizador, "TEXTO 2 (texto2.txt)", texto2);

        // ---------- 4) Conclusión ----------
        System.out.println("\n==================================================");
        System.out.printf("Distorsión media TEXTO 1 = %.6f%n", d1);
        System.out.printf("Distorsión media TEXTO 2 = %.6f%n", d2);

        String ganador = (d1 < d2) ? "TEXTO 1" : "TEXTO 2";
        System.out.println("→ El texto MÁS SEMEJANTE al Quijote es el " + ganador);
    }

    /** Devuelve la distorsión media del texto respecto al cuantizador. */
    private static double evaluar(CuantizadorVectorial c, String nombre, String texto) {
        System.out.println("\n=== " + nombre + " ===");
        Texto txt = new Texto(texto);

        // --- Nivel oración (el que pide el ejercicio) ---
        Oracion[] oraciones = txt.oraciones();
        System.out.println("Oraciones detectadas: " + oraciones.length);

        // Evitar división por cero si el texto está vacío
        if (oraciones.length == 0) {
            System.out.println("  [ADVERTENCIA] El texto no contiene oraciones válidas.");
            return Double.POSITIVE_INFINITY;
        }

        double suma = 0;
        for (int i = 0; i < oraciones.length; i++) {
            double[] v = oraciones[i].vector();
            int idx = c.cuantizar(v);
            double d = c.distorsion(v);
            suma += d;
            String p = oraciones[i].getContenido();
            if (p.length() > 90) p = p.substring(0, 90) + "...";
            System.out.printf("  [%d] → centroide #%2d | dist=%.6f | \"%s\"%n",
                    i, idx, d, p);
        }
        double mediaOracion = suma / oraciones.length;
        System.out.printf("  Distorsión media (oración): %.6f%n", mediaOracion);

        // --- Cross-check: cláusulas (más muestras, misma métrica) ---
        List<String> clausulas = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char ch = texto.charAt(i);
            sb.append(ch);
            if (",.;:!?¿¡".indexOf(ch) >= 0) {
                String s = sb.toString().trim();
                if (s.length() > 3) clausulas.add(s);
                sb.setLength(0);
            }
        }
        String resto = sb.toString().trim();
        if (resto.length() > 3) clausulas.add(resto);

        if (!clausulas.isEmpty()) {
            double sumaC = 0;
            for (String cl : clausulas) sumaC += c.distorsion(Vectorizador.vectorizar(cl));
            System.out.printf("  Distorsión media (cláusulas): %.6f   (%d cláusulas)%n",
                    sumaC / clausulas.size(), clausulas.size());
        }

        return mediaOracion;
    }
}