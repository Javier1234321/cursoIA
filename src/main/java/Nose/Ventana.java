package Nose;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {

    static class BotonGato extends JButton{
        public final int casilla;
        public boolean marcado = false;

        BotonGato(int casilla){
            this.casilla = casilla;
        }

        public void marca(boolean tache){
            if(!this.marcado){
                this.setText((tache) ? "X" : "O");
                this.marcado = true;
            }
        }

        public void limpia(){
            this.setText("");
            this.marcado = false;
        }
    }

    private final char[] tablero = new char[9];
    private final BotonGato[] botones = new BotonGato[9];
    private boolean juegaTache = true;

    Ventana(){
        this.setSize(400, 400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 3));

        for(int i = 0; i < 9; i++){
            botones[i] = new BotonGato(i);

            botones[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BotonGato boton = (BotonGato) e.getSource();
                    if(!boton.marcado){
                        boton.marca(juegaTache);

                        int indice = boton.casilla;
                        tablero[indice] = juegaTache ? 'x' : 'o';

                        jugada();
                    }
                }
            });
            panelBotones.add(botones[i]);
        }

        JButton bJuegaTache = new JButton("X");
        bJuegaTache.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juegaTache = !juegaTache;
                bJuegaTache.setText( juegaTache ? "X" : "O");
            }
        });

        JButton bReiniciar = new JButton("Reiniciar");
        bReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reinicia();
            }
        });

        JPanel panelInferior = new JPanel();
        panelInferior.add(bJuegaTache);
        panelInferior.add(bReiniciar);

        this.setLayout(new BorderLayout());
        this.add(panelBotones, BorderLayout.CENTER);
        this.add(panelInferior, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
        reinicia();
    }

    public void jugada(){
        Gato.Movida m = Gato.minimax(tablero, !juegaTache, 9);

        if(m.casilla > -1){
            botones[m.casilla].marca(!juegaTache);
            tablero[m.casilla] = !juegaTache ? 'x' : 'o';
        }

    }

    public void reinicia(){
        for(int i = 0; i < 9; i++){
            botones[i].limpia();
            tablero[i] = '_';
        }

        if(!juegaTache){
            jugada();
        }
    }

    static void main(String[] args) {
        new Ventana();
    }
}