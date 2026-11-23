package Practica2Avance2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Graficador {

    private final static Color[] colores = new Color[]{
            new Color(255, 0, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255),
            new Color(255, 0, 255),
            new Color(0, 255, 255),
            new Color(255, 255, 0),
            new Color(196, 196, 0),
            new Color(0, 196, 196),
            new Color(196, 0, 196),
            new Color(196, 255, 196),
            new Color(255, 196, 196),
            new Color(196, 196, 255),
    };

    private static int contadorColor = 0;

    private static Color siguienteColor(){
        Color retorno = colores[contadorColor];
        contadorColor ++;
        contadorColor %= colores.length;
        return retorno;
    }

    // ------------------------------------------------------------------
    // Sobrecarga SIN título (retrocompatible con todo tu código actual)
    // ------------------------------------------------------------------
    public static void grafica(double[][][] familiasPuntos){
        grafica(familiasPuntos, "Graficador");
    }

    // ------------------------------------------------------------------
    // Sobrecarga CON título
    // ------------------------------------------------------------------
    public static void grafica(double[][][] familiasPuntos, String titulo){

        double[] escala = new double[]{1.0, 1.0};
        double[] centro = new double[]{0.0, 0.0};

        // El título aparece en la barra de la ventana
        JFrame jf = new JFrame(titulo != null ? titulo : "Graficador");

        JPanel jp = new JPanel(){

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

                // Título dibujado dentro del panel (arriba a la izquierda)
                if (titulo != null && !titulo.isEmpty()) {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 14f));
                    g2d.drawString(titulo, 10, 20);
                }

                contadorColor = 0;
                //Para cada familia de puntos
                for(int i = 0; i < familiasPuntos.length; i++){
                    //Le asignamos un color diferente a la familia
                    g2d.setColor(siguienteColor());

                    //Para cada punto de la familia
                    for(int j = 0; j < familiasPuntos[i].length; j++){
                        //Lo pintamos en pantalla haciendo uso de sus coordenadas
                        //Pero antes tenemos que ajustarlas al tamaño de la pantalla
                        int[] punto = puntoPantalla(
                                familiasPuntos[i][j][0],
                                familiasPuntos[i][j][1],
                                centro[0], centro[1],
                                escala[0], escala[1],
                                this.getWidth(), this.getHeight());
                        g2d.fillRect(punto[0], punto[1], 1, 1);
                    }
                }
                System.out.println("Dibujo actualizado");
            }
        };

        jf.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int delta = e.getWheelRotation();

                if(delta > 0){
                    escala[0] *= 1.1f;
                    escala[1] *= 1.1f;
                    System.out.println("Escala ampliada");;
                }else{
                    escala[0] /= 1.1f;
                    escala[1] /= 1.1f;
                    System.out.println("Escala escala reducida");
                }

                jp.repaint();
            }
        });

        jf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyChar()){
                    case 'a': centro[0] += 0.1; break;
                    case 'd': centro[0] -= 0.1; break;
                    case 'w': centro[1] += 0.1; break;
                    case 's': centro[1] -= 0.1; break;
                }
                jp.repaint();
            }
        });

        jf.add(jp);
        jf.setSize(480, 480);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    static int[] puntoPantalla(double x, double y,
                               double centroX, double centroY,
                               double escalaX, double escalaY,
                               int ancho, int alto){

        int[] punto = new int[2];

        centroX *= escalaX;
        centroY *= escalaY;

        x *= escalaX;
        y *= escalaY;

        punto[0] = (int) Math.round(ancho / 2.0 + x * (ancho / 2.0) - centroX);
        punto[1] = (int) Math.round(alto / 2.0 - y * (alto / 2.0) - centroY);

        return punto;
    }

    public static void main(String[] args) {

        double[][][] familias = new double[100][][];

        for(int i = 0; i < 100; i++){
            familias[i] = new double[10000][2];

            for(int j = 0; j < 1000; j ++){
                familias[i][j][0] = Math.random() * 2 - 1;
                familias[i][j][1] = Math.random() * 2 - 1;
            }
        }

        grafica(familias, "100 familias aleatorias en [-1,1]^2");
    }
}