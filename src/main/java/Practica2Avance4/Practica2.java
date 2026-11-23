package Practica2Avance4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Practica2 {

    private static final String RUTA_CSV = "C:/Users/javie/Inteligencia_Artificial/cursoIA/src/main/java/Practica2Avance4/DatosEntrenamiento.csv";
    // Ruta donde se guardará la matriz
    private static final String RUTA_MATRIZ = "C:/Users/javie/Inteligencia_Artificial/cursoIA/src/main/java/Practica2Avance4/MatrizConfusion.csv";

    public static void main(String[] args) {
        List<double[]> listaX = new ArrayList<>();
        List<String> listaY = new ArrayList<>();

        System.out.println("Leyendo archivo CSV...");
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_CSV))) {
            String linea;
            int numeroDeLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroDeLinea++;
                if (numeroDeLinea <= 2) continue; // Ignorar cabeceras

                String[] partes = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (partes.length >= 3) {
                    try {
                        double lat = parsearCoordenada(partes[0]);
                        double lng = parsearCoordenada(partes[1]);
                        String clase = partes[2].replace("\"", "").trim();

                        listaX.add(new double[]{lat, lng});
                        listaY.add(clase);
                    } catch (NumberFormatException e) {
                        // Ignorar líneas corruptas
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error leyendo el CSV: " + e.getMessage());
            return;
        }

        int totalLineas = listaX.size();
        System.out.println("Datos válidos leídos: " + totalLineas);

        int numPrueba = 1000;
        double[][] X_train = new double[totalLineas - numPrueba][];
        String[] y_train = new String[totalLineas - numPrueba];
        double[][] X_test = new double[numPrueba][];
        String[] y_test = new String[numPrueba];

        // Primeras 1000 -> Prueba
        for (int i = 0; i < numPrueba; i++) {
            X_test[i] = listaX.get(i);
            y_test[i] = listaY.get(i);
        }
        // El resto -> Entrenamiento
        for (int i = numPrueba; i < totalLineas; i++) {
            X_train[i - numPrueba] = listaX.get(i);
            y_train[i - numPrueba] = listaY.get(i);
        }

        System.out.println("Entrenando el Árbol de Decisión...");
        long inicio = System.currentTimeMillis();
        ArbolDecision arbol = new ArbolDecision();
        arbol.entrenar(X_train, y_train);
        long fin = System.currentTimeMillis();
        System.out.println("¡Entrenamiento terminado en " + (fin - inicio) + " ms!");

        // --- EVALUACIÓN ---
        int correctos = 0, incorrectos = 0;

        // 1. Obtener todas las clases únicas que aparecen en el conjunto de PRUEBA
        // (Solo evaluamos las que están en prueba para que la matriz no tenga filas vacías)
        Set<String> setClases = new TreeSet<>();
        for (String c : y_test) setClases.add(c);

        List<String> clasesLista = new ArrayList<>(setClases);
        Map<String, Integer> clasesIndices = new HashMap<>();
        for (int i = 0; i < clasesLista.size(); i++) {
            clasesIndices.put(clasesLista.get(i), i);
        }

        // 2. Inicializar Matriz de Confusión (N x N)
        int n = clasesLista.size();
        int[][] matrizConfusion = new int[n][n];

        for (int i = 0; i < numPrueba; i++) {
            String prediccion = arbol.predecir(X_test[i]);
            String real = y_test[i];

            if (prediccion.equals(real)) correctos++;
            else incorrectos++;

            // Llenar Matriz de Confusión
            // Si el árbol predice una clase que no estaba en las clases de prueba, la ignoramos o la agregamos
            if (clasesIndices.containsKey(real) && clasesIndices.containsKey(prediccion)) {
                int filaReal = clasesIndices.get(real);
                int colPredicha = clasesIndices.get(prediccion);
                matrizConfusion[filaReal][colPredicha]++;
            }
        }

        System.out.println("\n=== RESULTADOS ===");
        System.out.println("Clasificaciones Correctas: " + correctos);
        System.out.println("Clasificaciones Incorrectas: " + incorrectos);
        System.out.printf("Precisión (Accuracy): %.2f%%\n", (correctos * 100.0 / numPrueba));

        // 3. Guardar Matriz de Confusión en un archivo CSV para Excel
        System.out.println("\nGuardando Matriz de Confusión en " + RUTA_MATRIZ + "...");
        try (PrintWriter pw = new PrintWriter(RUTA_MATRIZ)) {
            // Cabecera (Nombres de las columnas)
            pw.print("Real \\ Prediccion,");
            for (int i = 0; i < n; i++) {
                pw.print(clasesLista.get(i) + (i < n - 1 ? "," : ""));
            }
            pw.println();

            // Filas
            for (int i = 0; i < n; i++) {
                pw.print(clasesLista.get(i) + ",");
                for (int j = 0; j < n; j++) {
                    pw.print(matrizConfusion[i][j] + (j < n - 1 ? "," : ""));
                }
                pw.println();
            }
            System.out.println("¡Matriz guardada con éxito!");
        } catch (Exception e) {
            System.err.println("Error al guardar la matriz: " + e.getMessage());
        }

        // 4. Imprimir una versión resumida en consola (Top 15 países más frecuentes en prueba)
        System.out.println("\n=== MATRIZ DE CONFUSIÓN (Vista previa en consola) ===");
        List<String> topClases = new ArrayList<>(clasesLista);
        // Ordenar por cantidad de apariciones en el conjunto de prueba
        topClases.sort((a, b) -> {
            int countA = 0, countB = 0;
            for (String s : y_test) {
                if (s.equals(a)) countA++;
                if (s.equals(b)) countB++;
            }
            return Integer.compare(countB, countA);
        });

        int limite = Math.min(15, n);
        System.out.print("Real \\ Pred\t");
        for (int i = 0; i < limite; i++) {
            String c = topClases.get(i);
            System.out.printf("%-8s\t", c.substring(0, Math.min(8, c.length())));
        }
        System.out.println();

        for (int i = 0; i < limite; i++) {
            String real = topClases.get(i);
            System.out.printf("%-12s\t", real.substring(0, Math.min(12, real.length())));
            int filaReal = clasesIndices.get(real);

            for (int j = 0; j < limite; j++) {
                String pred = topClases.get(j);
                int colPred = clasesIndices.get(pred);
                System.out.printf("%-8d\t", matrizConfusion[filaReal][colPred]);
            }
            System.out.println();
        }

        // --- PREPARAR GRÁFICA (Opcional, por si quieres ver los puntos de nuevo) ---
        Map<String, List<double[]>> puntosPorClase = new HashMap<>();
        for (int i = 0; i < numPrueba; i++) {
            String real = y_test[i];
            puntosPorClase.putIfAbsent(real, new ArrayList<>());
            puntosPorClase.get(real).add(X_test[i]);
        }

        double[][][] familias = new double[clasesLista.size()][][];
        for (int i = 0; i < clasesLista.size(); i++) {
            String clase = clasesLista.get(i);
            List<double[]> puntos = puntosPorClase.getOrDefault(clase, new ArrayList<>());
            familias[i] = new double[puntos.size()][2];
            for (int j = 0; j < puntos.size(); j++) {
                familias[i][j][0] = puntos.get(j)[1] / 180.0;
                familias[i][j][1] = puntos.get(j)[0] / 180.0;
            }
        }
        Graficador.grafica(familias, "Puntos de Prueba (1000)");
    }

    private static double parsearCoordenada(String texto) {
        texto = texto.trim().replace("\"", "");
        if (texto.chars().filter(ch -> ch == '.').count() > 1) {
            int ultimoPunto = texto.lastIndexOf('.');
            String enteros = texto.substring(0, ultimoPunto).replace(".", "");
            String decimales = texto.substring(ultimoPunto + 1);
            texto = enteros + "." + decimales;
        }
        double valor = Double.parseDouble(texto);
        if (Math.abs(valor) > 180.0) {
            valor = valor / 10.0;
        }
        return valor;
    }
}