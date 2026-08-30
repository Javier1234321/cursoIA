package Tarea2.AlgoritmoGraham;

import java.util.ArrayList;
import java.util.List;

public class MainGraham {
        public static void main(String[] args) {

            List<Punto> puntos = new ArrayList<>();

            puntos.add(new Punto(0, 0));
            puntos.add(new Punto(2, 1));
            puntos.add(new Punto(4, 0));
            puntos.add(new Punto(3, 3));
            puntos.add(new Punto(1, 4));
            puntos.add(new Punto(2, 2));
            puntos.add(new Punto(1, 1));

            Graham graham = new Graham();

            List<Punto> envolvente = graham.obtenEnvolventeConexa(puntos);

            System.out.println("Puntos de la envolvente:");

            for (Punto p : envolvente) {
                System.out.println(
                        "(" + p.getX() + ", " + p.getY() + ")"
                );
            }
        }
}
