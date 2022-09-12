package juan.estevez.videojuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Juan Carlos Estevez Vargas.
 */
public class JuegoPanel extends JPanel implements KeyListener {

    public Timer t;

    public JuegoPanel(String nombre) {
        this.setBackground(Color.red);
        this.setFocusable(true);
        this.setBounds(300, 100, 500, 500);
        this.setPreferredSize(new Dimension(500, 500));
        this.addKeyListener(this);

        t = new Timer(10, (ActionEvent e) -> {
            int i = 0;
            System.out.println(i);
            i++;
        });

        t.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D) {
            System.out.println("Ir a la derecha");
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            System.out.println("Ir a la izquierda");
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
