package Nose;

public class Gato {
    public static class Movida{
        public final int casilla;
        public final int puntuacion;

        public Movida(int casilla, int puntuacion) {
            this.casilla = casilla;
            this.puntuacion = puntuacion;
        }
    }
    public static Movida minimax(char[] tablero, boolean juegaTache, int profundidad){
        int mejorCasilla=0;
        int mejorPuntuacion=juegaTache ? -10:10;
        char cj= juegaTache ? 'x':'o';

        //Si la profundidad es 1 entregamos solo jugadas ganadoras
        if (profundidad<=1){
            //Evaluar que jugadas gana y evalua todas las posibles jugadas
            for (int i=0;i<9;i++){
                if (tablero[i] !='_') {
                    continue;
                }
                tablero[i]=cj;
                int puntuacion=evaluaPosicion(tablero);
                if (puntuacion>mejorPuntuacion){
                    mejorPuntuacion=puntuacion;
                    mejorCasilla=i;
                }
                tablero[i]='_';
            }
            return new Movida(mejorCasilla,mejorPuntuacion);
        }
        else {
            return null;
        }
    }

    static int evaluaPosicion(char[] tablero){
        if (gana(tablero,true)){
            return 1;
        }
        else if(!gana(tablero,false)){
            return -1;
        }
        else{
            return 0;
        }
    }

    static boolean gana(char[] tablero,boolean juegaTache){
        char cj=juegaTache ? 'x':'o';
        int mapaBits=0;
        for (int i=0;i<9;i++){
            mapaBits<<=1;
            if (tablero[i]==cj) {
                mapaBits++;
            }
        }
        int[] posGanadoras=new int[]{
                0b111_000_000,
                0b000_111_000,
                0b000_000_111,
                0b100_100_100,
                0b010_010_010,
                0b001_001_001,
                0b100_010_001,
                0b001_010_100
        };
        for (int i=0; i<posGanadoras.length;i++){
            int copiaMapa=mapaBits;
            copiaMapa &=posGanadoras[i];
            if (copiaMapa == posGanadoras[i]) {
                return true;
            }

        }
        return  false;
    }
    public static void main(String[] args) {
        char[] tablero= new char[]{
                '_','_','_',
                '_','_','_',
                '_','_','_'
        };
        System.out.println(evaluaPosicion(tablero));
    }
}
