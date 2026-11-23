package Practica2;

public class VectorizadorEstilo {
    public static double[] vectorizarOracion(String oracion) {
        String[] palabras = oracion.trim().split("\\s+");
        int totalLetras = 0;
        int totalPuntuacion = 0;

        for (char c : oracion.toCharArray()) {
            if (Character.isLetter(c)) totalLetras++;
            if (",.;:!?¿¡".indexOf(c) >= 0) totalPuntuacion++;
        }

        double promedioLetras = palabras.length > 0 ? (double) totalLetras / palabras.length : 0;
        double palabrasPorOracion = palabras.length;
        double ratioPuntuacion = (double) totalPuntuacion / (oracion.length() + 1);

        return new double[]{promedioLetras, palabrasPorOracion, ratioPuntuacion};
    }
}