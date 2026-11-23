package Practica2Avance2;

/**
 * Implementación del algoritmo Linde-Buzo-Gray (LBG) para
 * cuantización vectorial / clustering.
 *
 * Se apoya en KMedias.distanciaEuclidiana2 para mantener consistencia
 * (distancia al cuadrado).
 */
public class LBG {

    private static final double EPSILON      = 0.01;   // factor de splitting
    private static final double TOLERANCIA   = 1e-3;   // convergencia (delta de centroides)
    private static final int    MAX_VUELTAS  = 50;     // límite de iteraciones de Lloyd por split

    private static int[]    regionPorPunto = null;
    private static double[][] centroides   = null;

    public static double[][] centroides() {
        if (centroides != null) return centroides;
        throw new RuntimeException(
                "Error: debe llamar al método lbg o puntosEnRegiones antes de centroides()");
    }

    // ------------------------------------------------------------------
    // API principal, análoga a KMedias
    // ------------------------------------------------------------------
    public static double[][][] puntosEnRegiones(int numRegiones, double[][] puntos) {
        centroides = lbg(numRegiones, puntos);

        int numPuntos = puntos.length;
        int dimension = puntos[0].length;

        int[] contadorPorRegion = new int[numRegiones];
        for (int regionDePunto : regionPorPunto) contadorPorRegion[regionDePunto]++;

        double[][][] regiones = new double[numRegiones][][];
        for (int r = 0; r < numRegiones; r++) {
            regiones[r] = new double[contadorPorRegion[r]][dimension];
        }

        for (int i = 0; i < numPuntos; i++) {
            int r = regionPorPunto[i];
            contadorPorRegion[r]--;
            int pos = contadorPorRegion[r];
            System.arraycopy(puntos[i], 0, regiones[r][pos], 0, dimension);
        }
        return regiones;
    }

    /**
     * Entrena el codebook LBG.
     * Acepta cualquier tamaño (no sólo potencias de 2):
     *  - Mientras size*2 <= objetivo, hace "split completo" (todos ×2).
     *  - Al final hace "split selectivo" del centroide de mayor distorsión.
     */
    public static double[][] lbg(int numCentroides, double[][] puntos) {
        int numPuntos = puntos.length;
        int dimension = puntos[0].length;

        // 1) Centroide global (media de todos los puntos)
        double[][] codebook = new double[1][dimension];
        for (double[] p : puntos)
            for (int d = 0; d < dimension; d++) codebook[0][d] += p[d];
        for (int d = 0; d < dimension; d++) codebook[0][d] /= (double) numPuntos;

        // 2) Refinamiento inicial (Lloyd) sobre el único codeword
        codebook = refinar(codebook, puntos);

        // 3) Bucle de splitting
        while (codebook.length < numCentroides) {
            int objetivoDouble = codebook.length * 2;

            if (objetivoDouble <= numCentroides) {
                // Split completo
                double[][] nuevo = new double[objetivoDouble][dimension];
                for (int i = 0; i < codebook.length; i++) {
                    for (int d = 0; d < dimension; d++) {
                        nuevo[2 * i][d]     = perturbar(codebook[i][d], +1);
                        nuevo[2 * i + 1][d] = perturbar(codebook[i][d], -1);
                    }
                }
                codebook = nuevo;
            } else {
                // Split selectivo hasta alcanzar el tamaño exacto
                int faltan = numCentroides - codebook.length;
                codebook = splitSelectivo(codebook, puntos, faltan);
            }
            codebook = refinar(codebook, puntos);
        }

        // 4) Dejar regionPorPunto consistente con el codebook final
        asignarRegiones(codebook, puntos);
        return codebook;
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------
    private static double perturbar(double valor, int signo) {
        double delta = Math.abs(valor) * EPSILON;
        if (delta < 1e-9) delta = EPSILON;          // evita splits degenerados
        return valor + signo * delta;
    }

    /** Divide sólo los 'faltan' centroides con mayor distorsión promedio. */
    private static double[][] splitSelectivo(double[][] codebook,
                                             double[][] puntos,
                                             int faltan) {
        int k = codebook.length;
        int dimension = puntos[0].length;

        double[] distorsion = new double[k];
        int[]    cuenta     = new int[k];

        for (double[] p : puntos) {
            int    mejor    = 0;
            double mejorD   = KMedias.distanciaEuclidiana2(p, codebook[0]);
            for (int c = 1; c < k; c++) {
                double d = KMedias.distanciaEuclidiana2(p, codebook[c]);
                if (d < mejorD) { mejorD = d; mejor = c; }
            }
            distorsion[mejor] += mejorD;
            cuenta[mejor]++;
        }

        Integer[] indices = new Integer[k];
        for (int i = 0; i < k; i++) indices[i] = i;
        java.util.Arrays.sort(indices, (a, b) -> {
            double da = cuenta[a] > 0 ? distorsion[a] / cuenta[a] : 0;
            double db = cuenta[b] > 0 ? distorsion[b] / cuenta[b] : 0;
            return Double.compare(db, da);
        });

        java.util.Set<Integer> aDividir = new java.util.HashSet<>();
        for (int i = 0; i < faltan; i++) aDividir.add(indices[i]);

        double[][] nuevo = new double[k + faltan][dimension];
        int pos = 0;
        for (int i = 0; i < k; i++) {
            if (aDividir.contains(i)) {
                for (int d = 0; d < dimension; d++)
                    nuevo[pos][d] = perturbar(codebook[i][d], +1);
                pos++;
                for (int d = 0; d < dimension; d++)
                    nuevo[pos][d] = perturbar(codebook[i][d], -1);
                pos++;
            } else {
                System.arraycopy(codebook[i], 0, nuevo[pos], 0, dimension);
                pos++;
            }
        }
        return nuevo;
    }

    /** Iteraciones de Lloyd hasta que el codebook deja de moverse. */
    private static double[][] refinar(double[][] codebook, double[][] puntos) {
        double[][] actual = codebook;
        for (int it = 0; it < MAX_VUELTAS; it++) {
            double[][] nuevos = generacion(actual, puntos);
            double delta = 0;
            for (int c = 0; c < actual.length; c++)
                delta += KMedias.distanciaEuclidiana2(actual[c], nuevos[c]);
            actual = nuevos;
            if (delta < TOLERANCIA) break;
        }
        return actual;
    }

    /** Un paso de Lloyd: asignar y recalcular centroides. */
    private static double[][] generacion(double[][] codebook, double[][] puntos) {
        int k = codebook.length;
        int dimension = puntos[0].length;

        int[] region = new int[puntos.length];
        for (int i = 0; i < puntos.length; i++) {
            double mejorD = Double.POSITIVE_INFINITY;
            for (int c = 0; c < k; c++) {
                double d = KMedias.distanciaEuclidiana2(puntos[i], codebook[c]);
                if (d < mejorD) { mejorD = d; region[i] = c; }
            }
        }

        double[][] nuevos = new double[k][dimension];
        int[] cuenta = new int[k];
        for (int i = 0; i < puntos.length; i++) {
            int r = region[i];
            for (int d = 0; d < dimension; d++) nuevos[r][d] += puntos[i][d];
            cuenta[r]++;
        }
        for (int c = 0; c < k; c++) {
            if (cuenta[c] > 0) {
                for (int d = 0; d < dimension; d++)
                    nuevos[c][d] /= (double) cuenta[c];
            } else {
                System.arraycopy(codebook[c], 0, nuevos[c], 0, dimension);
            }
        }
        return nuevos;
    }

    private static void asignarRegiones(double[][] codebook, double[][] puntos) {
        int k = codebook.length;
        regionPorPunto = new int[puntos.length];
        for (int i = 0; i < puntos.length; i++) {
            double mejorD = Double.POSITIVE_INFINITY;
            for (int c = 0; c < k; c++) {
                double d = KMedias.distanciaEuclidiana2(puntos[i], codebook[c]);
                if (d < mejorD) { mejorD = d; regionPorPunto[i] = c; }
            }
        }
    }

    // ------------------------------------------------------------------
    // main de prueba (equivalente al de KMedias)
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        int numPuntos    = 100_000;
        int dimension    = 2;
        int numCentroides = 16;   // potencia de 2 -> LBG clásico

        double[][] puntos = new double[numPuntos][dimension];
        for (int i = 0; i < numPuntos; i++)
            for (int j = 0; j < dimension; j++)
                puntos[i][j] = Math.random() * 2 - 1;

        double[][][] regiones = LBG.puntosEnRegiones(numCentroides, puntos);

        System.out.println("Centroides LBG:");
        for (double[] c : LBG.centroides())
            System.out.println("  " + java.util.Arrays.toString(c));

        Graficador.grafica(regiones);
    }
}
