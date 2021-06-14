import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.util.ArrayList;

class Game extends JPanel {

  Player player;

  ArrayList<Mob> mobs;

  Biome b = new Biome("plains");

  MyMouseListener playerMouse = new MyMouseListener();

  Game() {

    player = new Player(300, 300, 100, 25, 10, 1, false, "player");
    this.addMouseListener(playerMouse);

    mobs = new ArrayList<Mob>();

    for (int i = 0; i < 5; i++) {
      mobs.add(new Zombie(200 + (50 * i), 100 + (75 * i)));
    }

    b.generateBiome();

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

    // this.drawBackground(g);

    b.addBlock(playerMouse.getX(), playerMouse.getY(), "dirt");
    b.renderBiome(g);

    player.move();
    player.draw(g);

    for (int i = 0; i < b.getHeight().length; i++) {

    }

    for (int i = 0; i < mobs.size(); i++) {
      mobs.get(i).draw(g);
      mobs.get(i).move();
      mobs.get(i).pathFinding(player);
      if (player.collides(mobs.get(i).mobRect)) {
        System.out.println("Ouch! " + i);
        player.attack(mobs.get(i));
        mobs.get(i).attack(player);
      }
      if (mobs.get(i).health <= 0) {
        mobs.get(i).setDead(true);
        mobs.remove(i);
      }
      if (player.health <= 0) {
        player.setDead(true);
      }
    }
    this.repaint();
  }

}
