import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * This class is an abstract class to represent each basic 
 * player and its properties
 * @author Khush Negandhi
 * @version 1.0, May 2021 
 * 
 */

class Player {
  
  //Sprite path
  String SPRITE_PATH = "../sprites/";
  String SPRITE_EXTENSION = ".jpg";
  
  //The coordinates
  public int x, y;
  //The health
  public int health;
  //The damage
  public int damage;
  //The speed
  public int speed;
  //The jump height
  public int jumpHeight;
  
  //To see if the player is dead
  private boolean isDead;
  
  //To see if the player is falling 
  private boolean falling = true;
  //To see if the player is falling 
  private boolean jumping = false;
  //Allows the player to move right
  private boolean movingRight = true;
  //Allows the player to move left
  private boolean movingLeft = true;
  
  //Change in x,y corrdinates respectively
  private double dx, dy;
  //Rectangle collision mesh 
  private Rectangle playerRect;
  
  //Sprite
  public BufferedImage sprite;
  //Sprite name
  String spriteName;
  
  /**
   * Creates a mob object with the specified information
   * @param x The x coordinate of the player
   * @param y The x coordinate of the player
   * @param health The health of the player
   * @param speed The speed of the player
   * @param jumpHeight The speed of the player
   * @param damage The attack power of the player
   * @param dead The state of the player(dead or alive)
   * @param spriteName The name of the sprite used by the player
   */
  Player(int x, int y, int health, int speed, int jumpHeight, int damage, boolean dead, String spriteName) {
    
    this.x = x;
    this.y = y;
    
    this.health = health;
    this.damage = damage;
    
    this.speed = speed;
    this.jumpHeight = jumpHeight;
    
    this.dx = 0;
    //Gravity
    this.dy = 1;
    
    //Finds the sprite and associates it with the mob
    this.sprite = loadSprite(SPRITE_PATH + spriteName + SPRITE_EXTENSION);
    //Makes the mob rectangle
    this.playerRect = new Rectangle(x, y, 30, 30);
    
    this.isDead = dead;
    
    this.spriteName = spriteName;
    
  }
  
  /**
   * Returns the rectangle of the player
   * @return the rectangle of the player
   */
  public Rectangle getRect() {
    return playerRect;
  }
  
  /**
   * Checks if the player collided with another Rectangle 
   * @param Rectangle the rectangle body of teh object we want to check the player collided with
   * @return if the player collided with a Rectangle or not
   */
  public boolean collides(Rectangle rect) {
    if (x < rect.x + 30 && x + 30 > rect.x && y < rect.y + 30 && y + 30 > rect.y) {
      return true;
    }
    return false;
  }
  
  /**
   * If the player is dead or not
   * @return death state
   */
  public boolean getDead() {
    return isDead;
  }
  
  /**
   * Setter to set the death state of the player
   * @param deathValue sets the falling state
   */
  public void setDead(boolean deathValue) {
    this.isDead = deathValue;
  }
  
  /**
   * If the player is falling or not
   * @return falling state
   */
  public boolean getFalling() {
    return falling;
  }
  
  /**
   * Setter to set the falling state of the player
   * @param fall sets the falling state
   */
  public void setFalling(boolean fall) {
    this.falling = fall;
  }
  
  /**
   * If the player is jumping or not
   * @return jump state
   */
  public boolean getJumping() {
    return jumping;
  }
  
  /**
   * Setter to set the jump state of the player
   * @param jump sets the jump state
   */
  public void setJumping(boolean jump) {
    this.jumping = jump;
  }
  
  /**
   * If the player can move right
   * @return the ability to move right state
   */
  public boolean getMoveRight() {
    return movingRight;
  }
  
  /**
   * Setter to set the move state of the player
   * @param right sets the jump state
   */
  public void setMoveRight(boolean right) {
    this.movingRight = right;
  }
  
  /**
   * If the player can move left
   * @return the ability to move left state
   */
  public boolean getMoveLeft() {
    return movingLeft;
  }
  
  /**
   * Setter to set the move state of the player
   * @param right sets the jump state
   */
  public void setMoveLeft(boolean left) {
    this.movingLeft = left;
  }
  
  /**
   * Attacks the mob
   * @param mpb Is the mpb
   */
  public void attack(Mob mob) {
    
    mob.takeDamage(this.damage);
    
  }
  
  /**
   * Moves the player 
   */
  public void move() {
    this.x = this.x + (int) this.dx;
    this.y = this.y + (int) this.dy;
    this.playerRect.x = this.x;
    this.playerRect.y = this.y;
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
   * Allows the player to jump
   */
  public void jump() {
    if (this.jumping && !this.falling) {
      this.dy = -this.jumpHeight;
      this.jumping = false;
      this.falling = true;
    }
  }
  
  /**
   * Allows the mob to take damage
   * @param damageTaken The amount of damage inflicted
   */
  public void takeDamage(int damageTaken) {
    this.health -= damageTaken;
  }
  
  /**
   * Allows the mob to take damage
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
    //Checks if the mob is dead or not
    if (!this.isDead) {
      g.drawImage(this.sprite, this.x, this.y, null);
    }
  }
  
}