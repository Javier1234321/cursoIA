package Vectorizacion;

public class K_Medias {
    //Arreglo de arreglos de doubles
    public void kMedias(int numeroCentroides,double[]  ... ejes){
        double[][] centroides=new double[numeroCentroides][ejes.length];
        int dimension= ejes.length;
        int numPuntos=ejes[0].length;
        for (int i = 0; i < numeroCentroides; i++) {
            //Esta linea es opcional porque arriba ua lo creamos
            //centroides[i]=new double[ejes.length];
            for (int j = 0; j < dimension; j++) {
                centroides[i][j]=ejes[i][j];
            }
        }

        // Para todos los puntos encontrar el centroide mas cercano
        for (int i = 0; i < numPuntos; i++) {
            double[] punto=new double[dimension];
            for (int j = 0; j < dimension; j++) {
                punto[j]=ejes[i][j];
            }
        }
    }
}
