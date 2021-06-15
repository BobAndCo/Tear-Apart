import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Rectangle;

class Game extends JPanel {
  
  Player player;
  
  ArrayList<Mob> mobs;
  Biome b = new Biome("plains");
  
  MyMouseListener playerMouse = new MyMouseListener();
  PlayerController playerController;
  
  Game() {
    
    player = new Player(300, 300, 100, 25, 10, 1, false, "player");
    
    mobs = new ArrayList<Mob>();
    
    for (int i = 0; i < 5; i++) {
      mobs.add(new Zombie(100 + (50 * i), 100 + (75 * i)));
    }
    
    b.generateBiome();
     
    playerController = new PlayerController(player);
    this.addKeyListener(playerController);
    
    this.addMouseListener(playerMouse);
    
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
    
    //this.drawBackground(g);
    
    b.renderBiome(g);
    
    b.saveBiome();
    player.move();
    player.draw(g);
    
    if (playerMouse.getButtonPressed().equals("left")) {
      b.removeBlock(playerMouse.getX(), playerMouse.getY());
    } else if (playerMouse.getButtonPressed().equals("right")) {
      b.addBlock(playerMouse.getX(), playerMouse.getY(), playerController.getSelectedBlock());
    }
    
    for (int i = 0; i < mobs.size(); i++) {
      //System.out.println(i);
      mobs.get(i).pathFinding(player);
      mobs.get(i).move();
      mobs.get(i).draw(g);
      if (mobs.get(i).mobRect.intersects(player.x, player.y, 35, 35)){
        //System.out.println("Ouch! " + i);
        player.attack(mobs.get(i));
        mobs.get(i).attack(player);
      }
      if (mobs.get(i).health <= 0){
        mobs.get(i).setDead(true); 
        mobs.remove(i);
      }
      if (player.health <= 0){
        player.setDead(true); 
      }
    }
    
    Block[][] blocks = b.getMapBuffer();
    
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks[i].length; j++) {
        if (blocks[i][j] != null) {
          Rectangle blockRectangle = blocks[i][j].getRect();
          if (player.collides(blockRectangle)) {
            player.setFalling(false);
            player.setJumping(false);
          }
          for (int k = 0; k < mobs.size(); k++) {
            if (mobs.get(k).collides(blockRectangle)) {
              mobs.get(k).setFalling(false);
            }
          }
        }
      }
    }
    
    this.repaint();
  }
  
}
