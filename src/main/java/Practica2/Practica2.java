package Practica2;


import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import Practica2Avance4.ArbolDecision;
import Vectorizacion.UtilsText;
import Practica2Avance3.Vectorizador;

public class Practica2 {

    // Ruta correcta según la estructura del proyecto
    private static final String DIRECTORIO =
            Paths.get("src", "main", "java", "Practica2")
                    .toAbsolutePath().normalize().toString()
                    + java.io.File.separator;

    public static void main(String[] args) {

        List<double[]> datosLenguaje = new ArrayList<>();
        List<String> etiquetasLenguaje = new ArrayList<>();

        System.out.println("Cargando textos para el Árbol de Decisión...");

        // ==========================================
        // FASE 1: CARGA DE DATOS PARA IDENTIFICAR IDIOMA
        // ==========================================

        cargarDatosLenguaje("espannol", "Español",
                datosLenguaje, etiquetasLenguaje);

        cargarDatosLenguaje("frances", "Frances",
                datosLenguaje, etiquetasLenguaje);

        cargarDatosLenguaje("ingles", "Ingles",
                datosLenguaje, etiquetasLenguaje);

        // Comprobar que se cargaron los 9 documentos
        if (datosLenguaje.size() != 9) {
            System.out.println("ERROR: No se pudieron cargar los 9 documentos.");
            System.out.println("Directorio utilizado: " + DIRECTORIO);
            return;
        }

        ArbolDecision arbolLenguaje = new ArbolDecision();

        arbolLenguaje.entrenar(
                datosLenguaje.toArray(new double[0][]),
                etiquetasLenguaje.toArray(new String[0])
        );

        System.out.println(
                "¡Árbol de Decisión entrenado con "
                        + datosLenguaje.size() + " documentos!"
        );

        // ==========================================
        // FASE 2: ENTRENAMIENTO DE AUTORES (LBG)
        // ==========================================

        System.out.println("\nEntrenando Cuantizadores Vectoriales para Autores (LBG)...");

        // Español
        CuantizadorLBG autorEsp1 =
                entrenarAutor("espannol_01.txt", "espannol_02.txt");

        CuantizadorLBG autorEsp2 =
                entrenarAutor("espannol_03.txt");

        // Francés
        CuantizadorLBG autorFra1 =
                entrenarAutor("frances_01.txt", "frances_02.txt");

        CuantizadorLBG autorFra2 =
                entrenarAutor("frances_03.txt");

        // Inglés
        CuantizadorLBG autorIng1 =
                entrenarAutor("ingles_01.txt", "ingles_02.txt");

        CuantizadorLBG autorIng2 =
                entrenarAutor("ingles_03.txt");

        System.out.println("¡Cuantizadores de Autores entrenados!");

        // ==========================================
        // FASE 3: PRUEBA
        // ==========================================

        // Cambia este archivo por el que te proporcione el profesor
        String archivoPrueba = "espannol_01.txt";

        String rutaTextoPrueba = DIRECTORIO + archivoPrueba;

        System.out.println(
                "\n=== EVALUANDO TEXTO DE PRUEBA: "
                        + archivoPrueba + " ==="
        );

        String textoPrueba = UtilsText.leeTexto(rutaTextoPrueba);

        // Comprobar que el texto de prueba se pudo leer
        if (textoPrueba == null || textoPrueba.trim().isEmpty()) {
            System.out.println("ERROR: No se pudo leer el texto de prueba.");
            return;
        }

        // ==========================================
        // 1. PREDECIR LENGUAJE
        // ==========================================

        double[] vectorLenguaje = Vectorizador.vectorizar(textoPrueba);

        String lenguajePredicho = arbolLenguaje.predecir(vectorLenguaje);

        System.out.println(
                "Lenguaje detectado por el Árbol: " + lenguajePredicho
        );

        // ==========================================
        // 2. PREDECIR AUTOR
        // ==========================================

        double distorsionAutor1 = 0;
        double distorsionAutor2 = 0;

        if (lenguajePredicho.equals("Español")) {

            distorsionAutor1 =
                    calcularDistorsionTexto(textoPrueba, autorEsp1);

            distorsionAutor2 =
                    calcularDistorsionTexto(textoPrueba, autorEsp2);

        } else if (lenguajePredicho.equals("Frances")) {

            distorsionAutor1 =
                    calcularDistorsionTexto(textoPrueba, autorFra1);

            distorsionAutor2 =
                    calcularDistorsionTexto(textoPrueba, autorFra2);

        } else if (lenguajePredicho.equals("Ingles")) {

            distorsionAutor1 =
                    calcularDistorsionTexto(textoPrueba, autorIng1);

            distorsionAutor2 =
                    calcularDistorsionTexto(textoPrueba, autorIng2);

        } else {
            System.out.println("ERROR: No se pudo identificar el idioma.");
            return;
        }

        System.out.println(
                "Distorsión Autor 1 (Textos 01 y 02): "
                        + distorsionAutor1
        );

        System.out.println(
                "Distorsión Autor 2 (Texto 03): "
                        + distorsionAutor2
        );

        if (distorsionAutor1 < distorsionAutor2) {

            System.out.println("-> El AUTOR del texto es el AUTOR 1.");

        } else if (distorsionAutor2 < distorsionAutor1) {

            System.out.println("-> El AUTOR del texto es el AUTOR 2.");

        } else {

            System.out.println("-> No se pudo determinar el autor.");
        }
    }

    // ==========================================
    // CARGAR DOCUMENTOS PARA IDENTIFICAR IDIOMA
    // ==========================================

    private static void cargarDatosLenguaje(
            String prefijo,
            String etiqueta,
            List<double[]> X,
            List<String> Y) {

        for (int i = 1; i <= 3; i++) {

            String archivo = DIRECTORIO
                    + prefijo + "_0" + i + ".txt";

            try {

                if (!Files.exists(Paths.get(archivo))) {
                    System.out.println("NO EXISTE: " + archivo);
                    continue;
                }

                String texto = UtilsText.leeTexto(archivo);

                if (texto == null || texto.trim().isEmpty()) {
                    System.out.println("ARCHIVO VACÍO: " + archivo);
                    continue;
                }

                X.add(Vectorizador.vectorizar(texto));
                Y.add(etiqueta);

                System.out.println("Cargado: " + prefijo + "_0" + i + ".txt");

            } catch (Exception e) {

                System.out.println("Error leyendo: " + archivo);
                e.printStackTrace();
            }
        }
    }

    // ==========================================
    // ENTRENAMIENTO DE CUANTIZADOR LBG POR AUTOR
    // ==========================================

    private static CuantizadorLBG entrenarAutor(String... archivos) {

        List<double[]> vectoresOraciones = new ArrayList<>();

        for (String archivo : archivos) {

            try {

                String ruta = DIRECTORIO + archivo;

                if (!Files.exists(Paths.get(ruta))) {
                    throw new RuntimeException("No existe el archivo: " + ruta);
                }

                String texto = UtilsText.leeTexto(ruta);

                if (texto == null || texto.trim().isEmpty()) {
                    throw new RuntimeException("Archivo vacío: " + archivo);
                }

                String[] oraciones = texto.split("[.!?]+");

                for (String oracion : oraciones) {

                    if (oracion.trim().length() > 10) {

                        vectoresOraciones.add(
                                VectorizadorEstilo.vectorizarOracion(oracion)
                        );
                    }
                }

            } catch (Exception e) {

                System.out.println("Error leyendo autor: " + archivo);
                e.printStackTrace();
            }
        }

        // Evitar entrenar LBG con datos vacíos
        if (vectoresOraciones.isEmpty()) {

            throw new IllegalStateException(
                    "No hay oraciones disponibles para entrenar el cuantizador."
            );
        }

        return new CuantizadorLBG(
                16,
                vectoresOraciones.toArray(new double[0][])
        );
    }

    // ==========================================
    // CALCULAR DISTORSIÓN DEL TEXTO
    // ==========================================

    private static double calcularDistorsionTexto(
            String texto,
            CuantizadorLBG cuantizador) {

        String[] oraciones = texto.split("[.!?]+");

        double distorsionTotal = 0;
        int contador = 0;

        for (String oracion : oraciones) {

            if (oracion.trim().length() > 10) {

                double[] v =
                        VectorizadorEstilo.vectorizarOracion(oracion);

                distorsionTotal += cuantizador.distorsion(v);

                contador++;
            }
        }

        return contador > 0
                ? distorsionTotal / contador
                : Double.MAX_VALUE;
    }
}
