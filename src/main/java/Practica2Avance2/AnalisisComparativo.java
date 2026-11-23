package Practica2Avance2;

public class AnalisisComparativo {

    public static void main(String[] args) {

        int numPuntos     = 100_000;
        int numRegiones   = 16;     // potencia de 2 para LBG
        int dimension     = 2;

        // --- Puntos aleatorios en [-1,1]^2 ---
        double[][] puntos = new double[numPuntos][dimension];
        for (int i = 0; i < numPuntos; i++)
            for (int j = 0; j < dimension; j++)
                puntos[i][j] = Math.random() * 2 - 1;

        // --- KMedias ---
        long t0 = System.currentTimeMillis();
        double[][][] regionesKM = KMedias.puntosEnRegiones(numRegiones, puntos);
        double[][]  cKM         = KMedias.centroides();
        long tKM = System.currentTimeMillis() - t0;

        // --- LBG ---
        t0 = System.currentTimeMillis();
        double[][][] regionesLBG = LBG.puntosEnRegiones(numRegiones, puntos);
        double[][]  cLBG         = LBG.centroides();
        long tLBG = System.currentTimeMillis() - t0;

        // --- Métricas ---
        double dKM  = distorsionTotal(cKM,  puntos);
        double dLBG = distorsionTotal(cLBG, puntos);

        System.out.println("=== Comparación en 100 000 puntos aleatorios ===");
        System.out.printf("KMedias : %5d ms | Distorsión total = %.4f%n", tKM,  dKM);
        System.out.printf("LBG     : %5d ms | Distorsión total = %.4f%n", tLBG, dLBG);
        System.out.printf("Diferencia relativa de distorsión: %.2f%%%n",
                100.0 * Math.abs(dKM - dLBG) / Math.min(dKM, dLBG));

        // --- Gráficas (se abren dos ventanas) ---
        Graficador.grafica(regionesKM, "K_Medias");
        Graficador.grafica(regionesLBG,"LBG");
    }

    /** Suma de distancias (al cuadrado) del punto a su centroide más cercano. */
    public static double distorsionTotal(double[][] centroides, double[][] puntos) {
        double total = 0;
        for (double[] p : puntos) {
            double mejor = Double.POSITIVE_INFINITY;
            for (double[] c : centroides) {
                double d = KMedias.distanciaEuclidiana2(p, c);
                if (d < mejor) mejor = d;
            }
            total += mejor;
        }
        return total;
    }
}