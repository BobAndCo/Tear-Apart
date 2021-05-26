import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

class Game extends JPanel {
  
  public void gameLoop() {
    boolean quit = false;
    
    while (!quit) {
      this.repaint();
    }
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setDoubleBuffered(true);
  }
  
}
