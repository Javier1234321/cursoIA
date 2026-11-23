package Practica2Avance4;

import java.util.*;

public class ArbolDecision {

    private final int minElementos = 5;
    private final int maxProfundidad = 10;
    private final double purezaAceptacion = 0.95;

    private Nodo raiz;
    private String[] nombresClases; // Para traducir los índices a texto

    public void entrenar(double[][] X, String[] y) {
        // Mapeamos los nombres de los países a enteros (0, 1, 2...) para mayor velocidad
        Map<String, Integer> mapaClases = new HashMap<>();
        List<String> listaClases = new ArrayList<>();
        int[] yInt = new int[y.length];

        for (int i = 0; i < y.length; i++) {
            if (!mapaClases.containsKey(y[i])) {
                mapaClases.put(y[i], listaClases.size());
                listaClases.add(y[i]);
            }
            yInt[i] = mapaClases.get(y[i]);
        }
        this.nombresClases = listaClases.toArray(new String[0]);

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < X.length; i++) indices.add(i);

        raiz = construirArbol(X, yInt, indices, 0);
    }

    private Nodo construirArbol(double[][] X, int[] y, List<Integer> indices, int profundidad) {
        Nodo nodo = new Nodo();
        int total = indices.size();
        int numClases = nombresClases.length;

        // 1. Contar clases
        int[] conteoClases = new int[numClases];
        for (int idx : indices) conteoClases[y[idx]]++;

        int claseMayoritaria = 0;
        int maxConteo = 0;
        for (int c = 0; c < numClases; c++) {
            if (conteoClases[c] > maxConteo) {
                maxConteo = conteoClases[c];
                claseMayoritaria = c;
            }
        }
        double pureza = (double) maxConteo / total;

        // 2. Condiciones de parada
        if (profundidad >= maxProfundidad || total < minElementos || pureza >= purezaAceptacion) {
            nodo.clasePredicha = nombresClases[claseMayoritaria];
            return nodo;
        }

        // 3. Búsqueda de la mejor división (Optimizada)
        double mejorGini = Double.MAX_VALUE;
        int mejorAtributo = -1;
        double mejorUmbral = 0;
        int numAtributos = X[0].length;

        for (int atributo = 0; atributo < numAtributos; atributo++) {
            final int attr = atributo;
            indices.sort(Comparator.comparingDouble(i -> X[i][attr]));

            int[] conteoIzq = new int[numClases];
            int[] conteoDer = conteoClases.clone();
            int izqSize = 0;

            double sumSqIzq = 0;
            double sumSqDer = 0;
            for (int c = 0; c < numClases; c++) {
                sumSqDer += (double) conteoDer[c] * conteoDer[c];
            }

            for (int i = 0; i < total - 1; i++) {
                int idx = indices.get(i);
                int clase = y[idx];

                // Actualización O(1) del cálculo de Gini
                sumSqIzq += 2 * conteoIzq[clase] + 1;
                conteoIzq[clase]++;
                izqSize++;

                sumSqDer -= 2 * conteoDer[clase] - 1;
                conteoDer[clase]--;
                int derSize = total - izqSize;

                double valActual = X[idx][attr];
                double valSiguiente = X[indices.get(i + 1)][attr];

                if (valActual == valSiguiente) continue;

                double umbral = (valActual + valSiguiente) / 2.0;

                double giniIzq = 1.0 - (sumSqIzq / ((double) izqSize * izqSize));
                double giniDer = 1.0 - (sumSqDer / ((double) derSize * derSize));
                double giniPonderado = (izqSize * giniIzq + derSize * giniDer) / total;

                if (giniPonderado < mejorGini) {
                    mejorGini = giniPonderado;
                    mejorAtributo = atributo;
                    mejorUmbral = umbral;
                }
            }
        }

        if (mejorAtributo == -1) {
            nodo.clasePredicha = nombresClases[claseMayoritaria];
            return nodo;
        }

        // 4. Dividir y crear hijos
        nodo.indiceAtributo = mejorAtributo;
        nodo.umbral = mejorUmbral;

        List<Integer> izq = new ArrayList<>();
        List<Integer> der = new ArrayList<>();
        for (int idx : indices) {
            if (X[idx][mejorAtributo] <= mejorUmbral) izq.add(idx);
            else der.add(idx);
        }

        nodo.izquierda = construirArbol(X, y, izq, profundidad + 1);
        nodo.derecha = construirArbol(X, y, der, profundidad + 1);

        return nodo;
    }

    public String predecir(double[] x) {
        return predecirRecursivo(raiz, x);
    }

    private String predecirRecursivo(Nodo nodo, double[] x) {
        if (nodo.esHoja()) return nodo.clasePredicha;
        if (x[nodo.indiceAtributo] <= nodo.umbral) return predecirRecursivo(nodo.izquierda, x);
        else return predecirRecursivo(nodo.derecha, x);
    }
}