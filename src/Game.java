import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

class Game extends JPanel {
  
  Player player = new Player(100,100,100,25,1,false,"sprite");
  
  PlayerController playerController = new PlayerController(player);
  
  public void gameLoop() {
    boolean quit = false;
    
    while (!quit) {
      player.move();
      this.repaint();
    }
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setDoubleBuffered(true);
    player.draw(g);
  }
  
}
