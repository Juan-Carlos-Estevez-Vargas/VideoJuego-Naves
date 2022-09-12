package juan.estevez.videojuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Juan Carlos Estevez Vargas.
 */
public class JuegoPanel extends JPanel implements KeyListener {

    /**
     * Variables globales.
     */
    public Timer t, enemigas;
    public Rectangle2D nave;
    public int xNave = 235, enemigasIni = 20, enemigasAumentoX = 0, enemigasAumentoY = 0, moviEnemigas = 10;
    public boolean derechaNave = false, izquierdaNave = false, derechaEnemigas = true, izquierdaEnemigas = false;
    public Graphics2D g2;
    public Rectangle2D[][] navesEnemigas = new Rectangle2D[2][8];

    /**
     * Constructor de clase.
     *
     * @param nombre del usuario que está jugando.
     */
    public JuegoPanel(String nombre) {
        this.setBackground(Color.red);
        this.setFocusable(true);
        this.setBounds(300, 100, 500, 500);
        this.setPreferredSize(new Dimension(500, 500));
        this.addKeyListener(this);

        timerNave();
        t.start();

        timerEnemigas();
        enemigas.start();
    }

    /**
     * Timer de la nave principal (movimiento).
     */
    private void timerNave() {
        t = new Timer(10, (ActionEvent e) -> {
            if (derechaNave && xNave <= 470) {
                xNave += 5;
            }
            if (izquierdaNave && xNave >= 0) {
                xNave -= 5;
            }
            repaint();
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        nave = new Rectangle2D.Double(xNave, 440, 30, 60);
        g2.fill(nave);

        inicializarEnemigas();
    }

    /**
     * Timer de las naves enemigas (movimiento).
     */
    private void timerEnemigas() {
        enemigas = new Timer(60, (ActionEvent e) -> {
            if (derechaEnemigas) {
                moviEnemigas += 5;
                if (moviEnemigas >= 70) {
                    derechaEnemigas = false;
                    izquierdaEnemigas = true;
                }
            }
            if (izquierdaEnemigas) {
                moviEnemigas -= 5;
                if (moviEnemigas <= 5) {
                    derechaEnemigas = true;
                    izquierdaEnemigas = false;
                }
            }
            repaint();
        });
    }

    /**
     * Se encarga de construir las naves enemigas en el panel.
     */
    private void inicializarEnemigas() {
        enemigasAumentoX = 0;
        enemigasAumentoY = 0;

        for (int filas = 0; filas < 2; filas++) {
            for (int columnas = 0; columnas < 8; columnas++) {
                navesEnemigas[filas][columnas] = new Rectangle2D.Double(enemigasIni + enemigasAumentoX + moviEnemigas, 10 + enemigasAumentoY, 35, 50);
                enemigasAumentoX += 50;
                g2.fill(navesEnemigas[filas][columnas]);
            }
            enemigasAumentoX = 0;
            enemigasAumentoY = 65;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            derechaNave = true;
            izquierdaNave = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            derechaNave = false;
            izquierdaNave = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            System.out.println("Disparar");
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            System.out.println("Recargando");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
