package Practica2Avance2;
import Vectorizacion.UtilsText;

import java.util.ArrayList;

/**
 *  - Construye un vector [vocales/letras , consonantes/letras] por oración.
 *  - Grafica los vectores.
 *  - Los agrupa con KMedias y con LBG y compara.
 */
public class AnalisisQuijote {

    // >>> CAMBIA ESTA RUTA POR LA TUYA <<<
    private static final String RUTA_QUIJOTE =
            "C:/Users/javie/Inteligencia_Artificial/cursoIA/src/main/java/Vectorizacion/donquijote.txt";

    // ------------------------------------------------------------------
    // 1) Lectura + partición por punto (fin de oración)
    // ------------------------------------------------------------------
    public static String[] separarOraciones(String texto) {
        ArrayList<String> oraciones = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (c == '.') {
                String s = sb.toString().trim();
                if (!s.isEmpty()) oraciones.add(s);
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        String s = sb.toString().trim();
        if (!s.isEmpty()) oraciones.add(s);
        return oraciones.toArray(String[]::new);
    }

    // ------------------------------------------------------------------
    // 2) Vector 2D por oración
    // ------------------------------------------------------------------
    private static boolean esVocal(char c) {
        return "aeiouáéíóúüAEIOUÁÉÍÓÚÜ".indexOf(c) >= 0;
    }

    public static double[] vectorOracion(String oracion) {
        int vocales = 0, consonantes = 0, letras = 0;
        for (int i = 0; i < oracion.length(); i++) {
            char c = oracion.charAt(i);
            if (Character.isLetter(c)) {
                letras++;
                if (esVocal(c)) vocales++; else consonantes++;
            }
        }
        if (letras == 0) return null;                 // descarta oraciones vacías
        return new double[]{
                (double) vocales     / letras,
                (double) consonantes / letras
        };
    }

    public static double[][] vectorizarOraciones(String[] oraciones) {
        ArrayList<double[]> lista = new ArrayList<>();
        for (String o : oraciones) {
            double[] v = vectorOracion(o);
            if (v != null) lista.add(v);
        }
        return lista.toArray(double[][]::new);
    }

    // ------------------------------------------------------------------
    // 3) main: pipeline completo
    // ------------------------------------------------------------------
    public static void main(String[] args) {

        // --- Leer y vectorizar ---
        String texto = UtilsText.leeTexto(RUTA_QUIJOTE);
        String[] oraciones = separarOraciones(texto);
        double[][] vectores = vectorizarOraciones(oraciones);

        System.out.println("Oraciones totales: " + oraciones.length);
        System.out.println("Vectores válidos : " + vectores.length);

        // --- Gráfica de los vectores crudos ---
        Graficador.grafica(new double[][][]{ vectores });

        // --- Clustering con KMedias ---
        int numRegiones = 8;
        long t0 = System.currentTimeMillis();
        double[][][] regionesKM = KMedias.puntosEnRegiones(numRegiones, vectores);
        double[][]  cKM         = KMedias.centroides();
        long tKM = System.currentTimeMillis() - t0;

        // --- Clustering con LBG ---
        t0 = System.currentTimeMillis();
        double[][][] regionesLBG = LBG.puntosEnRegiones(numRegiones, vectores);
        double[][]  cLBG         = LBG.centroides();
        long tLBG = System.currentTimeMillis() - t0;

        // --- Comparación ---
        double dKM  = AnalisisComparativo.distorsionTotal(cKM,  vectores);
        double dLBG = AnalisisComparativo.distorsionTotal(cLBG, vectores);

        System.out.println("\n=== Comparación sobre oraciones del Quijote ===");
        System.out.printf("KMedias : %5d ms | Distorsión total = %.6f%n", tKM,  dKM);
        System.out.printf("LBG     : %5d ms | Distorsión total = %.6f%n", tLBG, dLBG);

        System.out.println("\nCentroides KMedias:");
        imprimirCentroides(cKM);
        System.out.println("\nCentroides LBG:");
        imprimirCentroides(cLBG);

        // --- Gráficas de las regiones ---
        Graficador.grafica(regionesKM,"K_Medias");
        Graficador.grafica(regionesLBG,"LBG");
    }

    private static void imprimirCentroides(double[][] centroides) {
        for (double[] c : centroides)
            System.out.printf("  [%.4f, %.4f]%n", c[0], c[1]);
    }
}