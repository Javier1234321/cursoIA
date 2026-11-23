package Practica2Avance3;

import Practica2Avance2.Graficador;

public class KMedias {


    private static int[] regionPorPunto = null;
    private static double[][] centroides = null;

    public static double[][] centroides(){
        if(KMedias.centroides != null){
            return centroides;
        }
        throw new RuntimeException("Error: debe llamar al método kMedias o puntosEnRegiones antes de centroides()");
    }

    public static double[][][] puntosEnRegiones(int numRegiones, double[][] puntos){
        centroides = kMedias(numRegiones, puntos);

        int numPuntos = puntos.length;
        int dimension = puntos[0].length;

        //Maña utilizando variables estáticas (no es buena práctica pero debemos seguir adelante)
        int[] contadorPorRegion = new int[numRegiones];
        for(int i = 0; i < regionPorPunto.length; i++){
            int regionDePunto = regionPorPunto[i];
            contadorPorRegion[regionDePunto] ++;
        }

        //Creamos los arreglos en los que retornaremos las regiones ya separadas
        double[][][] regiones = new double[numRegiones][][];
        for(int indiceRegion = 0; indiceRegion < numRegiones; indiceRegion++){
            regiones[indiceRegion] = new double[contadorPorRegion[indiceRegion]][dimension];
        }

        //Maña, vamos a usar el mismo contador que creamos antes para ir llenando los arreglos
        for(int indicePunto = 0; indicePunto < numPuntos; indicePunto++){
            //El índice de la región para este punto está guardado en "regionPorPunto"
            int indiceRegion = regionPorPunto[indicePunto];
            //El arreglo inició siendo de tamaño "contadorRegion[indiceRegion]" cuando ese valor
            //era máximo. Ahora lo que vamos a ir haciendo será decrecer el valor del contador
            //cada vez que agreguemos un punto a su debida región.
            //Comenzamos restando porque el índice n-1 será el primero que quepa en un arreglo de tamaño n
            contadorPorRegion[indiceRegion]--;
            int contadorRegion = contadorPorRegion[indiceRegion];

            for(int indiceDimension = 0; indiceDimension < dimension; indiceDimension++){
                regiones[indiceRegion][contadorRegion][indiceDimension] = puntos[indicePunto][indiceDimension];
            }
        }

        return regiones;
    }

    public static double[][] kMedias(int numCentroides, double[][] puntos){
        int dimension = puntos[0].length;

        double[][] centroides = new double[numCentroides][dimension];

        //elegimos los centroides al "azar" (los primeros num-centroides)
        for(int i = 0; i < numCentroides; i++){
            //esta línea es opcional porque arriba ya lo creamos
            //centroides[i] = new double[ejes.length];

            for(int j = 0; j < dimension; j++){
                centroides[i][j] = puntos[i][j];
            }
        }

        double delta = 0;
        int numVueltas = 20;

        do{
            double[][] nuevosCentroides = generacionKMedias(centroides, puntos);

            //Medimos cuánto se movieron todos los centroides de una generación a otra
            for(int indiceCentroide = 0; indiceCentroide < numCentroides; indiceCentroide++){
                delta += distanciaEuclidiana2(centroides[indiceCentroide], nuevosCentroides[indiceCentroide]);
            }

            centroides = nuevosCentroides;
            numVueltas --;

        }while(delta > 0.001 && numVueltas > 0);

        return centroides;
    }

    //Función para iterar de una generación a otra de kMedias
    private static double[][] generacionKMedias(double[][] viejosCentroides, double[][] puntos){
        int numCentroides = viejosCentroides.length;
        int numPuntos = puntos.length;
        int dimension = puntos[0].length;

        //Para todos los puntos encontrar su estado más cercano
        regionPorPunto = new int[numPuntos];
        for(int indicePunto = 0; indicePunto < numPuntos; indicePunto++){
            double[] punto = new double[dimension];
            for(int indiceEje = 0; indiceEje < dimension; indiceEje++){
                punto[indiceEje] = puntos[indicePunto][indiceEje];
            }

            //Encontrar su distancia euclidiana a todos los centroides
            //nos quedamos con aquel centroide cuya distancia sea menor
            double mejorDistancia = Double.POSITIVE_INFINITY;
            for(int indiceCentroide = 0; indiceCentroide < numCentroides; indiceCentroide++){
                double distancia = distanciaEuclidiana2(punto, viejosCentroides[indiceCentroide]);
                if(distancia < mejorDistancia){
                    mejorDistancia = distancia;
                    regionPorPunto[indicePunto] = indiceCentroide;
                }
            }
        }

        //Para cada región obtenemos su nuevo centroide
        double[][] nuevosCentroides = new double[numCentroides][dimension];
        for(int indiceCentroide = 0; indiceCentroide < numCentroides; indiceCentroide++){

            int contador = 0;
            for(int indicePunto = 0; indicePunto < numPuntos; indicePunto++){
                //Si en el arreglo de las regiones, encontramos que -éste- es el centroide
                //lo acumulamos
                if(regionPorPunto[indicePunto] == indiceCentroide){
                    for(int indiceEje = 0; indiceEje < dimension; indiceEje++){
                        nuevosCentroides[indiceCentroide][indiceEje] += puntos[indicePunto][indiceEje];
                    }
                    contador ++;
                }
            }

            for(int indiceEje = 0; indiceEje < dimension; indiceEje++){
                nuevosCentroides[indiceCentroide][indiceEje] /= (double) contador;
            }
        }

        return nuevosCentroides;
    }

    public static double distanciaEuclidiana2(double[] punto1, double[] punto2){
        double acumulador = 0;
        for(int i = 0; i < punto1.length; i++){
            double deltaEje = punto1[i] - punto2[i];
            deltaEje *= deltaEje;
            acumulador += deltaEje;
        }
        //return Math.sqrt(acumulador);
        return acumulador;
    }


}
