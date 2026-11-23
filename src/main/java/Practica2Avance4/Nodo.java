package Practica2Avance4;
import java.util.Map;

public class Nodo {
    // Si es hoja, guardamos la clase predicha
    public String clasePredicha;

    // Si es rama, guardamos la condición
    public int indiceAtributo;     // 0 para Latitud, 1 para Longitud
    public double umbral;          // El valor que divide los datos
    public Nodo izquierda;         // Datos <= umbral
    public Nodo derecha;           // Datos > umbral

    public boolean esHoja() {
        return clasePredicha != null;
    }
}