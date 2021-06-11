import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

class Game extends JPanel {

  Player player;

  Mob[] mobs;

  Game() {

    player = new Player(300, 300, 100, 25, 10, 1, false, "dirt");

    mobs = new Mob[5];

    for (int i = 0; i < mobs.length; i++) {
      mobs[i] = new Zombie(100 + (50 * i), 100 + (75 * i));
    }

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

    for (int i = 0; i < mobs.length; i++) {
      mobs[i].pathFinding(player);
      mobs[i].move();
      mobs[i].draw(g);
    }

    this.repaint();
  }

}
