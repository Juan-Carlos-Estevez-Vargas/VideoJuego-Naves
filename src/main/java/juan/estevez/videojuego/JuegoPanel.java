package juan.estevez.videojuego;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Juan Carlos Estevez Vargas.
 */
public class JuegoPanel extends JPanel {
    
    public JuegoPanel(String nombre) {
        this.setBackground(Color.red);
        this.setFocusable(true);
        this.setBounds(300, 100, 500, 500);
        this.setPreferredSize(new Dimension(500, 500));
    }
    
}
