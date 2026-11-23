package Practica2Avance4;

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

    public static void grafica(double[][][] familiasPuntos){
        grafica(familiasPuntos, "Graficador");
    }

    public static void grafica(double[][][] familiasPuntos, String titulo){

        double[] escala = new double[]{1.0, 1.0};
        double[] centro = new double[]{0.0, 0.0};

        JFrame jf = new JFrame(titulo != null ? titulo : "Graficador");

        JPanel jp = new JPanel(){

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

                if (titulo != null && !titulo.isEmpty()) {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.setFont(g2d.getFont().deriveFont(Font.BOLD, 14f));
                    g2d.drawString(titulo, 10, 20);
                }

                contadorColor = 0;
                // TAMAÑO DEL PUNTO: Cambia este valor si lo quieres más grande o pequeño
                int tamanoPunto = 4;

                for(int i = 0; i < familiasPuntos.length; i++){
                    g2d.setColor(siguienteColor());

                    for(int j = 0; j < familiasPuntos[i].length; j++){
                        int[] punto = puntoPantalla(
                                familiasPuntos[i][j][0],
                                familiasPuntos[i][j][1],
                                centro[0], centro[1],
                                escala[0], escala[1],
                                this.getWidth(), this.getHeight());

                        // Se resta la mitad del tamaño para que el punto se dibuje desde su centro
                        g2d.fillRect(punto[0] - (tamanoPunto / 2), punto[1] - (tamanoPunto / 2), tamanoPunto, tamanoPunto);
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
                }else{
                    escala[0] /= 1.1f;
                    escala[1] /= 1.1f;
                }

                jp.repaint();
            }
        });

        jf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Ajustamos el paso dependiendo del nivel de zoom actual
                double paso = 0.1 / escala[0];

                switch (e.getKeyChar()){
                    case 'a': centro[0] -= paso; break;
                    case 'd': centro[0] += paso; break;
                    case 'w': centro[1] += paso; break;
                    case 's': centro[1] -= paso; break;
                }
                jp.repaint();
            }
        });

        jf.add(jp);
        jf.setSize(800, 800); // Lo hice un poco más grande para que se aprecie mejor el mapa
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    static int[] puntoPantalla(double x, double y,
                               double centroX, double centroY,
                               double escalaX, double escalaY,
                               int ancho, int alto){

        int[] punto = new int[2];

        // Fórmula corregida para que el centro (WASD) funcione como coordenada real
        punto[0] = (int) Math.round(ancho / 2.0 + (x - centroX) * escalaX * (ancho / 2.0));
        punto[1] = (int) Math.round(alto / 2.0 - (y - centroY) * escalaY * (alto / 2.0));

        return punto;
    }
}