package Tarea3.AlgoritmoGraham;

public class Vector {
    private Punto p1;
    private Punto p2;
    private int i;
    private int j;
    /// P1P2 Vector
    public Vector(Punto p1, Punto p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.i=p2.getX()-p1.getX();
        this.j=p2.getY()-p1.getY();
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
