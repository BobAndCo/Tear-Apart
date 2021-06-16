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
  
  Mob[][] mobs;
  boolean play = false;
  boolean mainMenuLoop = true;
  ArrayList<Biome> b;
  
  MyMouseListener playerMouse = new MyMouseListener();
  MenuKeyListener menuKey;
  PlayerController playerController;
  
  private int currentBiome;
  private int maxBiomes = 10;
  
  Game() {
    
    player = new Player(300, 300, 100, 25, 10, 1, false, "player");
    
    mobs = new Mob[maxBiomes][5];
    
    b = new ArrayList<Biome>();
    
    for (int i = 0; i < maxBiomes; i++) {
      int random = (int) (Math.random() * 3);
      if (random == 0) {
        b.add(new Biome("plains"));
      } else if (random == 1) {
        b.add(new Biome("desert"));
      } else if (random == 2) {
        b.add(new Biome("tundra"));
      }
    }
    
    for (int i = 0; i < maxBiomes; i++) {
      for (int j = 0; j < 5; j++) {
        if (b.get(i).getType().equals("plains")) {
          mobs[i][j] = new Zombie(100 * j, 0);
        } else if (b.get(i).getType().equals("desert")) {
          mobs[i][j] = new Creeper(100 * j, 0);
        } else if (b.get(i).getType().equals("tundra")) {
          mobs[i][j] = new Frozen(100 * j, 0);
        }
      }
    }
    
    this.currentBiome = 0;
    for (int i = 0; i < b.size(); i++) {
      b.get(i).generateBiome();
    }
    
    playerController = new PlayerController(player);
    menuKey = new MenuKeyListener();
    
    this.addKeyListener(playerController);
    this.addKeyListener(menuKey);
    
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
    
    // this.drawBackground(g);
    
    if (mainMenuLoop) {
      /* Play */
      g.setColor(new Color(10,10,10));
      g.drawRect(720, 310, 480, 50);
      g.drawString("New World", 720+(480/2), 310+(50/2));
      
      /* Exit */
      g.setColor(new Color(10,10,10));
      g.drawRect(720, 610, 480, 50);
      g.drawString("Exit", 720+(480/2), 610+(50/2));
      
      switch(menuKey.getKeyPressed()) {
        case 0  :
          g.setColor(new Color(200, 0, 0));
          g.drawRect(718, 308, 484, 54);
          break;
        default :
          g.setColor(new Color(200, 0, 0));
          g.drawRect(718, 608, 484, 54);
          break;
      }
      
      if (menuKey.getEntered()) {
        if (menuKey.getKeyPressed() == 0) {
          play = true;
          mainMenuLoop = false;
        } else {
          mainMenuLoop = false;
        }
      }
      
    } else if (play) {
      System.out.println(player.x);
      
      if (player.x > 1420 && currentBiome != this.maxBiomes - 1) {
        System.out.println("New Biome");
        player.x = 0;
        currentBiome += 1;
        player.y = b.get(currentBiome).getFirstHeight() - 30;
      } else if (player.x < 0 && currentBiome != 0) {
        player.x = 1420;
        currentBiome -= 1;
        player.y = b.get(currentBiome).getLastHeight() - 30;
      } else if (player.x < 0 && currentBiome == 0) {
        player.x = 0;
      } else if (player.x > 1420 && currentBiome == this.maxBiomes - 1) {
        player.x = 1450;
      }
      
      b.get(currentBiome).renderBiome(g);
      
      b.get(currentBiome).saveBiome();
      
      player.move();
      player.draw(g);
      
      if (playerMouse.getButtonPressed().equals("left")) {
        b.get(currentBiome).removeBlock(playerMouse.getX(), playerMouse.getY());
      } else if (playerMouse.getButtonPressed().equals("right")) {
        b.get(currentBiome).addBlock(playerMouse.getX(), playerMouse.getY(), playerController.getSelectedBlock());
      }
      
      for (int i = 0; i < mobs[currentBiome].length; i++) {
        // System.out.println(i);
        
        mobs[currentBiome][i].pathFinding(player);
        mobs[currentBiome][i].move();
        mobs[currentBiome][i].draw(g);
        if (mobs[currentBiome][i].collides(player.getRect())) {
          // System.out.println("Ouch! " + i);
          player.attack(mobs[currentBiome][i]);
          mobs[currentBiome][i].attack(player);
        }
        if (mobs[currentBiome][i].health <= 0) {
          mobs[currentBiome][i].setDead(true);
        }
        if (player.health <= 0) {
          player.setDead(true);
          play = false;
          mainMenuLoop = true;
        }
      }
      
      Block[][] blocks = b.get(currentBiome).getMapBuffer();
      
      for (int i = 0; i < blocks.length; i++) {
        for (int j = 0; j < blocks[i].length; j++) {
          if (blocks[i][j] != null) {
            Rectangle blockRectangle = blocks[i][j].getRect();
            if (player.collides(blockRectangle)) {
              player.setFalling(false);
              player.setJumping(false);
            }
            for (int k = 0; k < mobs[currentBiome].length; k++) {
              if (mobs[currentBiome][k].collides(blockRectangle)) {
                mobs[currentBiome][k].setFalling(false);
              }
            }
          }
        }
      }
    } else {
      System.exit(0); 
    }
    this.repaint();
  }
}
