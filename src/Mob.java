import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * This class is an abstract class to represent each basic mob and its
 * properties, this is the building block upon which all the Mobs are built
 * 
 * @author Khush Negandhi
 * @version 1.0, May 2021
 * 
 */

abstract class Mob {

  // Sprite path
  String SPRITE_PATH = "../sprites/";
  String SPRITE_EXTENSION = ".png";

  // The coordinates
  public int x, y;
  // The health
  public int health;
  // The damage
  public int damage;
  // The speed
  public int speed;

  // To see if the mob is dead
  private boolean isDead;
  // To see if the mob is falling
  private boolean falling = true;

  // Change in x,y corrdinates respectively
  public int dx, dy;
  // Rectangle collision mesh
  private Rectangle mobRect;

  // Sprite
  public BufferedImage sprite;
  // Sprite name
  String spriteName;

  /**
   * Creates a mob object with the specified information
   * 
   * @param x          The x coordinate of the mob
   * @param y          The x coordinate of the mob
   * @param health     The health of the mob
   * @param speed      The speed of the mob
   * @param damage     The attack power of the mob
   * @param dead       The state of the mob(dead or alive)
   * @param spriteName The name of the sprite used by the mob
   */
  Mob(int x, int y, int health, int speed, int damage, boolean dead, String spriteName) {

    this.x = x;
    this.y = y;
    this.health = health;
    this.speed = speed;
    this.damage = damage;
    this.dx = 0;
    // Gravity
    this.dy = 1;

    // Finds the sprite and associates it with the mob
    this.sprite = loadSprite(SPRITE_PATH + spriteName + SPRITE_EXTENSION);
    // Makes the mob rectangle
    this.mobRect = new Rectangle(x, y, 30, 30);

    this.isDead = dead;

    this.spriteName = spriteName;

  }

  /**
   * Checks if the mob collided with another Rectangle
   * 
   * @param Rectangle the rectangle body of teh object we want to check the mob
   *                  collided with
   * @return if the mob collided with a Rectangle or not
   */
  public boolean collides(Rectangle rect) {
    if (x < rect.x + 30 && x + 30 > rect.x && y < rect.y + 30 && y + 30 > rect.y) {
      return true;
    }
    return false;
  }

  /**
   * Returns the rectangle of the mob
   * 
   * @return the rectangle of the mob
   */
  public Rectangle getRect() {
    return mobRect;
  }

  /**
   * If the mob is falling or not
   * 
   * @return falling state
   */
  public boolean getFalling() {
    return falling;
  }

  /**
   * Setter to set the falling state of the mob
   * 
   * @param fall sets the falling state
   */
  public void setFalling(boolean fall) {
    this.falling = fall;
  }

  /**
   * If the mob is dead or not
   * 
   * @return death state
   */
  public boolean getDead() {
    return isDead;
  }

  /**
   * Setter to set the death state of the mob
   * 
   * @param deathValue sets the falling state
   */
  public void setDead(boolean deathValue) {
    this.isDead = deathValue;
  }

  /**
   * Finds a path to the player
   * 
   * @param player Is the player
   */
  public void pathFinding(Player player) {

    // Checks if the player is in range of the mob and if so then moves accordingly
    if (player.x - this.x >= 0 && player.x - this.x <= 100) {
      this.moveRight();
      this.move();
    } else if (player.x - this.x <= 0 && player.x - this.x >= -100) {
      this.moveLeft();
      this.move();
    }
    // Sleeps so it doesn't go crazy
    try {
      Thread.sleep(10);
    } catch (Exception exc) {
      System.out.println("Thread Error");
    }

  }

  /**
   * Attacks the player
   * 
   * @param player Is the player
   */
  public void attack(Player player) {

    player.takeDamage(this.damage);

  }

  /**
   * Moves the mob
   */
  public void move() {
    this.x = this.x + (int) this.dx;
    this.y = this.y + (int) this.dy;
    this.mobRect.x = this.x;
    this.mobRect.y = this.y;
    if (falling) {
      this.dx = 0;
      this.dy = 1;
    } else {
      this.dx = 0;
      this.dy = 0;
    }
  }

  /**
   * Sets the mobs velocity in the x direction
   */
  public void moveRight() {
    this.dx = this.speed;
  }

  /**
   * Sets the mobs velocity in the y direction
   */
  public void moveLeft() {
    this.dx = -this.speed;
  }

  /**
   * Stays still
   */
  public void stay() {
    this.dx = 0;
  }

  /**
   * Allows the mob to take damage
   * 
   * @param damageTaken The amount of damage inflicted
   */
  public void takeDamage(int damageTaken) {
    this.health -= damageTaken;
  }

  /**
   * Sets the mob to dead
   */
  public void Dead() {
    if (this.health <= 0) {
      this.isDead = true;
    }
  }

  /**
   * Allows the mob to take damage
   * 
   * @param damageTaken The amount of damage inflicted
   * @return BufferedImage The image or sprite of the mob
   */
  public BufferedImage loadSprite(String spriteName) {
    try {
      return sprite = ImageIO.read(new File(spriteName));
    } catch (Exception e) {
      System.out.println("Error: Sprite Does Not Exist");
    }
    return null;
  }

  /**
   * Draws the mob
   */
  public void draw(Graphics g) {
    // Checks if the mob is dead or not
    if (!isDead) {
      g.drawImage(this.sprite, this.x, this.y, null);
    }
  }

}

class Zombie extends Mob {

  Zombie(int x, int y) {
    super(x, y, 1, 3, 1, false, "zombie");
  }

}

class Creeper extends Mob {

  Creeper(int x, int y) {
    super(x, y, 1, 5, 1, false, "creeper");
  }

}

class Frozen extends Mob {

  Frozen(int x, int y) {
    super(x, y, 1, 1, 1, false, "drowned");
  }

}