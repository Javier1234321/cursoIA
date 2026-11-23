package Practica2Avance3;


/**
 * Cuantizador vectorial. Está formado por los vectores de entrenamiento
 * a partir de los cuales se calculan los centroides vía LBG (entrega previa).
 */
public class CuantizadorVectorial {

    private final double[][] vectoresEntrenamiento;
    private final double[][] centroides;

    public CuantizadorVectorial(int numCentroides, double[][] vectoresEntrenamiento) {
        if (numCentroides <= 0) throw new IllegalArgumentException("numCentroides > 0");
        if (vectoresEntrenamiento == null || vectoresEntrenamiento.length == 0)
            throw new IllegalArgumentException("vectores vacíos");
        this.vectoresEntrenamiento = vectoresEntrenamiento;
        this.centroides = LBG.lbg(numCentroides, vectoresEntrenamiento);
    }

    /** Índice del centroide más cercano (esto es "cuantizar"). */
    public int cuantizar(double[] v) {
        int mejor = 0;
        double mejorD = KMedias.distanciaEuclidiana2(v, centroides[0]);
        for (int i = 1; i < centroides.length; i++) {
            double d = KMedias.distanciaEuclidiana2(v, centroides[i]);
            if (d < mejorD) { mejorD = d; mejor = i; }
        }
        return mejor;
    }

    /** Distancia al centroide más cercano (error de cuantización). */
    public double distorsion(double[] v) {
        return KMedias.distanciaEuclidiana2(v, centroides[cuantizar(v)]);
    }

    public double distorsionMedia(Vectorizable[] objetos) {
        double s = 0;
        for (Vectorizable o : objetos) s += distorsion(o.vector());
        return s / objetos.length;
    }

    public int size()                { return centroides.length; }
    public double[][] getCentroides(){ return centroides; }
    public double[][] getVectoresEntrenamiento() { return vectoresEntrenamiento; }
}
