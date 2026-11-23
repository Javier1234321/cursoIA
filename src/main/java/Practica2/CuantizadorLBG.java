package Practica2;

public class CuantizadorLBG {
    private double[][] centroides;

    public CuantizadorLBG(int numCentroides, double[][] datos) {
        // Asumiendo que tu clase LBG tiene el método lbg()
        this.centroides = Practica2Avance2.LBG.lbg(numCentroides, datos);
    }

    public double distorsion(double[] vector) {
        double minDist = Double.MAX_VALUE;
        for (double[] c : centroides) {
            // Usamos la distancia euclidiana al cuadrado
            double d = 0;
            for (int i = 0; i < vector.length; i++) {
                d += Math.pow(vector[i] - c[i], 2);
            }
            if (d < minDist) minDist = d;
        }
        return minDist;
    }
}