package Vectorizacion;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Locale;

public class UtilsText {

    public static String leeTexto(String dirrArchivo) {
        try {
            return Files.readString(Path.of(dirrArchivo), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("No se pudo abrir el documento del archivo " + dirrArchivo);
            throw new RuntimeException("Deteniendo programa");
        }
    }

    public static String[] separarLineas(String textoCompleto) {
        ArrayList<String> acumulador = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < textoCompleto.length(); i++) {
            char c = textoCompleto.charAt(i);

            if (c != '\n') {
                sb.append(c);
            } else {
                if (!sb.isEmpty()) {
                    acumulador.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        }

        if (!sb.isEmpty()) {
            acumulador.add(sb.toString());
        }

        return acumulador.toArray(String[]::new);
    }

    public static String[] separarPorSeparador(String textoCompleto, char separador) {
        ArrayList<String> acumulador = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < textoCompleto.length(); i++) {
            char c = textoCompleto.charAt(i);

            if (c != separador) {
                sb.append(c);
            } else {
                if (!sb.isEmpty()) {
                    acumulador.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        }

        if (!sb.isEmpty()) {
            acumulador.add(sb.toString());
        }

        return acumulador.toArray(String[]::new);
    }

    public static String[] detectarPalabra(String textoCompleto) {
        ArrayList<String> acumulador = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < textoCompleto.length(); i++) {
            char c = textoCompleto.charAt(i);

            if (Character.isAlphabetic(c)) {
                sb.append(c);
            } else {
                if (!sb.isEmpty()) {
                    acumulador.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        }

        if (!sb.isEmpty()) {
            acumulador.add(sb.toString());
        }

        return acumulador.toArray(String[]::new);
    }

    public static void main(String[] args) {

        String quijote = leeTexto(
                "C:/Users/javie/Inteligencia_Artificial/cursoIA/src/main/java/Vectorizacion/donquijote.txt"
        );

        // a) Diez palabras más frecuentes
        String[] palabras = detectarPalabra(quijote);
        Map<String, Integer> frecuenciaPalabras = new HashMap<>();

        for (String palabra : palabras) {
            palabra = palabra.toLowerCase(Locale.ROOT);
            frecuenciaPalabras.put(
                    palabra, frecuenciaPalabras.getOrDefault(palabra, 0) + 1
            );
        }

        System.out.println("a) Diez palabras más frecuentes:");
        imprimirTop(frecuenciaPalabras, 10);

        // b) Número total de palabras
        System.out.println("b) Total de palabras: " + palabras.length);

        // c) Número de palabras únicas
        System.out.println("c) Palabras únicas: " + frecuenciaPalabras.size());

        // d) Número total de letras
        int totalLetras = 0;
        Set<Character> letrasUnicas = new HashSet<>();

        for (int i = 0; i < quijote.length(); i++) {
            char c = quijote.charAt(i);

            if (Character.isAlphabetic(c)) {
                totalLetras++;
                letrasUnicas.add(Character.toLowerCase(c));
            }
        }

        System.out.println("d) Total de letras: " + totalLetras);

        // e) Número de letras únicas
        System.out.println("e) Letras únicas: " + letrasUnicas.size());

        // f) Diez caracteres más frecuentes
        Map<Character, Integer> frecuenciaCaracteres = new HashMap<>();

        for (int i = 0; i < quijote.length(); i++) {
            char c = quijote.charAt(i);

            frecuenciaCaracteres.put(
                    c, frecuenciaCaracteres.getOrDefault(c, 0) + 1
            );
        }

        System.out.println("f) Diez caracteres más frecuentes:");

        frecuenciaCaracteres.entrySet().stream()
                .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(e -> System.out.println(
                        "'" + representar(String.valueOf(e.getKey())) +
                                "' = " + e.getValue()
                ));

        // g) Diez bigramas más frecuentes
        Map<String, Integer> frecuenciaBigramas = new HashMap<>();

        for (int i = 0; i < quijote.length() - 1; i++) {
            String bigrama = quijote.substring(i, i + 2);

            frecuenciaBigramas.put(
                    bigrama, frecuenciaBigramas.getOrDefault(bigrama, 0) + 1
            );
        }

        System.out.println("g) Diez bigramas más frecuentes:");

        frecuenciaBigramas.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .forEach(e -> System.out.println(
                        "'" + representar(e.getKey()) + "' = " + e.getValue()
                ));

        // h) Número de caracteres únicos
        Set<Character> caracteresUnicos = new HashSet<>();

        for (int i = 0; i < quijote.length(); i++) {
            caracteresUnicos.add(quijote.charAt(i));
        }

        System.out.println("h) Caracteres únicos: " + caracteresUnicos.size());

        // i) Número de bigramas únicos
        System.out.println("i) Bigramas únicos: " + frecuenciaBigramas.size());
    }

    public static void imprimirTop(Map<String, Integer> frecuencias, int limite) {
        frecuencias.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(limite)
                .forEach(e -> System.out.println(
                        e.getKey() + " = " + e.getValue()
                ));
    }

    public static String representar(String texto) {
        return texto.replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace(" ", "␣");
    }
}
