package Practica2Avance3;

public class Texto implements Vectorizable {
    private final String texto;
    public Texto(String t) { this.texto = t; }
    @Override public double[] vector() { return Vectorizador.vectorizar(texto); }
    @Override public String getContenido() { return texto; }

    public Palabra[] palabras() { return Palabra.dividirTexto(texto); }
    public Oracion[] oraciones() { return Oracion.dividirTexto(texto); }
    public Parrafo[] parrafos() { return Parrafo.dividirTexto(texto); }
}
