import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Rectangle;

class Game extends JPanel {
  
  //Creates a player type object
  Player player;
  
  //2D array for the biom specfic mobs
  Mob[][] mobs;
  
  //Scene manager of sorts
  boolean play = false;
  boolean mainMenuLoop = true;
  
  //Makes an Array list of Biome type objects for dynamic number of biomes if wanted
  ArrayList<Biome> b;
  
  //Listeners for the  different keys and scenes
  //These are for the main game scene
  MyMouseListener playerMouse = new MyMouseListener();
  PlayerController playerController;
  //This one is for the Menu Scene 
  MenuKeyListener menuKey;
  
  //To indicate what biome we are currently in 
  private int currentBiome;
  //To indicate the max number of biomes wanted
  private int maxBiomes = 10;
  
  Game() {
    
    //Initiates the player object
    player = new Player(300, 300, 100, 25, 10, 1, false, "player");
    
    //Initiates the 2D Mob array
    mobs = new Mob[maxBiomes][5];
    
    //Initiates the biome array list
    b = new ArrayList<Biome>();
    
    //Adds the biomes to the array list
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
    
    //Adds the mobs to the 2D array
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
    
    //generates the first biome(also the current one)
    this.currentBiome = 0;
    for (int i = 0; i < b.size(); i++) {
      b.get(i).generateBiome();
    }
    
    //Initiates player Controllers 
    playerController = new PlayerController(player);
    menuKey = new MenuKeyListener();
    
    this.addKeyListener(playerController);
    this.addKeyListener(menuKey);
    
    this.addMouseListener(playerMouse);
    
    this.setFocusable(true);
    this.requestFocusInWindow();
    
  }
  /**
   * Animates the screen meaning redraws things on the screen
   */
  public void animate() {
    boolean quit = false;
    
    while (!quit) {
      this.repaint();
    }
  }
  
  /**
   * Draws the background 
   * @param g Is the graphics interface
   */
  public void drawBackground(Graphics g) {
    try {
      File BACKGROUND_IMAGE = new File("../sprites/background.jpeg");
      g.drawImage(ImageIO.read(BACKGROUND_IMAGE), 0, 0, this);
    } catch (Exception e) {
      System.out.println("Error: No Background Image Found");
    }
  }
  
  /**
   * Is the main drawing and checking function of the game. Firstly is draws 
   * everything, then it checks for collisions and other things 
   * @param g Is the graphics interface
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    setDoubleBuffered(true);
    
    // this.drawBackground(g);
    
    if (mainMenuLoop) {
      /* Play Button*/
      g.setColor(new Color(10,10,10));
      g.drawRect(720, 310, 480, 50);
      g.drawString("New World", 720+(480/2), 310+(50/2));
      
      /* Exit Button*/
      g.setColor(new Color(10,10,10));
      g.drawRect(720, 610, 480, 50);
      g.drawString("Exit", 720+(480/2), 610+(50/2));
      
	/* Determine which block to highlight */
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
      
	/* Select from the menu */
      if (menuKey.getEntered()) {
        if (menuKey.getKeyPressed() == 0) {
          play = true;
          mainMenuLoop = false;
        } else {
          mainMenuLoop = false;
        }
      }
      
    } else if (play) {
      
      /*This is to check if the player is out of bounds and if yes changes the biomes
       * and the player x and y position accordingly
       */
      if (player.x > 1890 && currentBiome != this.maxBiomes - 1) {
        System.out.println("New Biome");
        player.x = 0;
        currentBiome += 1;
        player.y = b.get(currentBiome).getFirstHeight() - 30;
      } else if (player.x < 0 && currentBiome != 0) {
        player.x = 1890;
        currentBiome -= 1;
        player.y = b.get(currentBiome).getLastHeight() - 30;
      } else if (player.x < 0 && currentBiome == 0) {
        player.x = 0;
      } else if (player.x > 1420 && currentBiome == this.maxBiomes - 1) {
        player.x = 1890;
      }
      
      //Renders the biome
      b.get(currentBiome).renderBiome(g);
      
      //Moves the player
      player.move();
      //Draws the player
      player.draw(g);
      
      //Checks to see with mouse button the player pressed and accordingly places or destroys blocks
      if (playerMouse.getButtonPressed().equals("left")) {
        //Removes the block
        b.get(currentBiome).removeBlock(playerMouse.getX(), playerMouse.getY());
      } else if (playerMouse.getButtonPressed().equals("right")) {
        //Adds a block
        b.get(currentBiome).addBlock(playerMouse.getX(), playerMouse.getY(), playerController.getSelectedBlock());
      }
      
      //Cycles through all the mobs in the array
      for (int i = 0; i < mobs[currentBiome].length; i++) {
        // System.out.println(i);
        //Path finds the mob to the player
        mobs[currentBiome][i].pathFinding(player);
        //Moves the mobs
        mobs[currentBiome][i].move();
        //Draws the mobs
        mobs[currentBiome][i].draw(g);
        //Checks if the player and mob collide
        if (mobs[currentBiome][i].collides(player.getRect())) {
          // System.out.println("Ouch! " + i);
          //Player Attacks the mob
          player.attack(mobs[currentBiome][i]);
          //Mob Attacks the player
          mobs[currentBiome][i].attack(player);
        }
        //If the mobs health goes down too low, it dies
        if (mobs[currentBiome][i].health <= 0) {
          mobs[currentBiome][i].setDead(true);
        }
      }
      
      //Gets the "blueprint" of all the blocks 
      Block[][] blocks = b.get(currentBiome).getMapBuffer();
      
      //Cycles through all the blocks 
      for (int i = 0; i < blocks.length; i++) {
        for (int j = 0; j < blocks[i].length; j++) {
          //Skips the "null" block
          if (blocks[i][j] != null) {
            //Gets a Rectangle for the block for the collision detection
            Rectangle blockRectangle = blocks[i][j].getRect();
            //Checks if the block collides with the player 
            if (player.collides(blockRectangle)) {
              //Doesn't let the player fall
              player.setFalling(false);
              //Lets the player jump
              player.setJumping(false);
            }
            //Cycles through the mobs 
            for (int k = 0; k < mobs[currentBiome].length; k++) {
              //Checks if the mob collided with the block
              if (mobs[currentBiome][k].collides(blockRectangle)) {
                mobs[currentBiome][k].setFalling(false);
              }
            }
          }
        }
      }
    } else { //Exits the codes
      System.exit(0); 
    }
    //Repaints everything with updated stuff
    this.repaint();
  }
}
