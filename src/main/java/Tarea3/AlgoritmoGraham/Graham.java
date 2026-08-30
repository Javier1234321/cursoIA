package Tarea3.AlgoritmoGraham;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Graham {
    public boolean productoCruz(Vector v1, Vector v2) {
        return (v1.getI() * v2.getJ())
                - (v1.getJ() * v2.getI()) > 0;
    }
    public List<Punto> obtenEnvolventeConexa(List<Punto> puntos) {
        if (puntos.size() <= 2) {
            return new ArrayList<>(puntos);
        }
        Punto pivote = puntos.get(0);
        for (Punto p : puntos) {
            if (p.getY() < pivote.getY()
                    || (p.getY() == pivote.getY()
                    && p.getX() < pivote.getX())) {

                pivote = p;
            }
        }
        Punto finalPivote = pivote;
        puntos.sort((p1, p2) -> {
            if (p1 == finalPivote) {
                return -1;
            }
            if (p2 == finalPivote) {
                return 1;
            }
            Vector v1 = new Vector(finalPivote, p1);
            Vector v2 = new Vector(finalPivote, p2);
            int cruz = (v1.getI() * v2.getJ())
                    - (v1.getJ() * v2.getI());
            if (cruz > 0) {
                return -1;
            }
            if (cruz < 0) {
                return 1;
            }
            long distancia1=distancia(finalPivote, p1);
            long distancia2=distancia(finalPivote, p2);
            return Long.compare(distancia1, distancia2);
        });
        Stack<Punto> stack = new Stack<>();
        stack.push(puntos.get(0));
        stack.push(puntos.get(1));
        for (int i = 2; i < puntos.size(); i++) {
            Punto nuevo = puntos.get(i);
            while (stack.size() >= 2) {
                Punto actual = stack.pop();
                Punto anterior = stack.peek();
                Vector v1 = new Vector(anterior, actual);
                Vector v2 = new Vector(actual, nuevo);
                if (productoCruz(v1, v2)) {
                    stack.push(actual);
                    break;
                }
            }
            stack.push(nuevo);
        }
        return new ArrayList<>(stack);
    }
    private long distancia(Punto a,Punto b){
        long dx=b.getX()-a.getX();
        long dy=b.getY()-a.getY();
        return dx*dx+dy*dy;
    }
}