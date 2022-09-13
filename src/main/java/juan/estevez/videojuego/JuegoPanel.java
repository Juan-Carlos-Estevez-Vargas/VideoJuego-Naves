package juan.estevez.videojuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
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
    public int xNave = 235, enemigasIni = 20, enemigasAumentoX = 0, contadorCaer = 0;
    public int moviEnemigas = 10, enemigasAumentoY = 0, aleatorio1, aleatorio2;
    public int disparadas = 0, contadorRecargar = 0;
    public boolean derechaNave = false, izquierdaNave = false, derechaEnemigas = true;
    public boolean izquierdaEnemigas = false, segundaFila = false, caer = false;
    public Graphics2D g2;
    public Rectangle2D[][] navesEnemigas = new Rectangle2D[2][8];
    public int[][] yEnemigas = new int[2][8];
    public Ellipse2D[] disparos =  new Ellipse2D[20];
    public int[] xDisparos = new int[20];
    public int[] yDisparos = new int[20];

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

        inicializarDisparos();
        inicializarAlturaEnemigas();

        timerNave();
        t.start();

        timerEnemigas();
        enemigas.start();
    }

    /**
     * Inicializa la altura de las naves enemigas en el tablero.
     */
    private void inicializarAlturaEnemigas() {
        for (int fila = 0; fila < 2; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                yEnemigas[fila][columna] = 20;
                if (segundaFila) {
                    yEnemigas[fila][columna] = 80;
                }
            }
            segundaFila = true;
        }
    }

    /**
     * Se encarga de hacer que las naves ataquen, es decir, que bajen una por una
     * a intentar destruir la nave principal, esto se hace de manera aleatorio.
     */
    private void atacar() {
        if (caer == false) {
            while (yEnemigas[aleatorio1][aleatorio2] >= 550) {
                aleatorio1 = (int) (Math.random() * 2);
                aleatorio2 = (int) (Math.random() * 8);
            }
        }
        yEnemigas[aleatorio1][aleatorio2] += 10;

        if (yEnemigas[aleatorio1][aleatorio2] >= 550) {
            caer = false;
            contadorCaer = 0;
        }
    }
    
    /**
     * Inicializa las balas de los disparos en la posición donde está
     * la nave (las balas salen de la nave).
     */
    private void inicializarDisparos() {
        for (int i = 0; i < 20; i++) {
            yDisparos[i] = 440;
        }
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
            
            // Movimiento de las balas hacia arriba
            for (int i = 0; i < disparadas; i++) {
                yDisparos[i]-=3;
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
        
        /**
         * Dibujando las balas en el panel.
         */
        for (int i = 0; i < disparadas; i++) {
            disparos[i] = new Ellipse2D.Double(xDisparos[i], yDisparos[i], 10, 20);
            g2.fill(disparos[i]);
        }

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
                    contadorCaer += 1;
                }
            }
            if (izquierdaEnemigas) {
                moviEnemigas -= 5;
                if (moviEnemigas <= 5) {
                    derechaEnemigas = true;
                    izquierdaEnemigas = false;
                }
            }

            if (contadorCaer >= 3) {
                atacar();
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
                navesEnemigas[filas][columnas] = new Rectangle2D.Double(enemigasIni + enemigasAumentoX + moviEnemigas, yEnemigas[filas][columnas], 35, 50);
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
            if (contadorRecargar<20){
                xDisparos[disparadas] = (int) nave.getCenterX();
                disparadas++;
                contadorRecargar++;
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            System.out.println("Recargando");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
