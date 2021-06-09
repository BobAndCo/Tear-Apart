import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

class Game extends JPanel {

  Player player;
  Zombie zombie1;

  Game() {

    player = new Player(300, 300, 100, 25, 1, false, "dirt");
    zombie1 = new Zombie(200, 200, 100, 10, 1, false);

    PlayerController playerController = new PlayerController(player);
    this.addKeyListener(playerController);

    this.setFocusable(true);
    this.requestFocusInWindow();

  }

  public void animate() {
    boolean quit = false;

    while (!quit) {
      this.repaint();
    }

  }

  public void drawBackground(Graphics g) {
    try {
      File BACKGROUND_IMAGE = new File("../sprites/background.jpeg");
      g.drawImage(ImageIO.read(BACKGROUND_IMAGE), 0, 0, this);
    } catch (Exception e) {
      System.out.println("Error: No Background Image Found");
    }
    ;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setDoubleBuffered(true);

    player.move();
    player.draw(g);

    zombie1.pathFinding(player);
    zombie1.move();
    zombie1.draw(g);

    this.repaint();
  }

}
