package juan.estevez.videojuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public Timer t;
    public Rectangle2D nave;
    public int xNave = 235;
    public boolean derechaNave = false, izquierdaNave = false;

    public JuegoPanel(String nombre) {
        this.setBackground(Color.red);
        this.setFocusable(true);
        this.setBounds(300, 100, 500, 500);
        this.setPreferredSize(new Dimension(500, 500));
        this.addKeyListener(this);

        t = new Timer(10, (ActionEvent e) -> {
            if (derechaNave && xNave <= 470) {
                xNave += 5;
            }
            if (izquierdaNave && xNave >= 0) {
                xNave -= 5;
            }
            repaint();
        });

        t.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        nave = new Rectangle2D.Double(xNave, 440, 30, 60);
        g2.fill(nave);
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
