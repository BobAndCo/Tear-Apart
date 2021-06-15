import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;

class Player {

  String SPRITE_PATH = "../sprites/";
  String SPRITE_EXTENSION = ".jpg";

  public int x, y;
  public int health;
  public int damage;
  public int speed;
  public int jumpHeight;

  private boolean isDead;

  private boolean falling = true;
  private boolean jumping = false;
  private boolean movingRight = true;
  private boolean movingLeft = true;

  private double dx, dy;
  private Rectangle playerRect;

  public BufferedImage sprite;
  String spriteName;

  Player(int x, int y, int health, int speed, int jumpHeight, int damage, boolean dead, String spriteName) {

    this.x = x;
    this.y = y;

    this.health = health;
    this.damage = damage;

    this.speed = speed;
    this.jumpHeight = jumpHeight;

    this.dx = 0;
    this.dy = 1;

    this.sprite = loadSprite(SPRITE_PATH + spriteName + SPRITE_EXTENSION);
    this.playerRect = new Rectangle(x, y, 30, 30);

    this.isDead = dead;

    this.spriteName = spriteName;

  }

  public Rectangle getRect() {
    return playerRect;
  }

  public boolean collides(Rectangle rect) {
    if (x < rect.x + 30 && x + 30 > rect.x && y < rect.y + 30 && y + 30 > rect.y) {
      return true;
    }
    return false;
  }

  public boolean getDead() {
    return isDead;
  }

  public void setDead(boolean deathValue) {
    this.isDead = deathValue;
  }

  public boolean getFalling() {
    return falling;
  }

  public void setFalling(boolean fall) {
    this.falling = fall;
  }

  public boolean getJumping() {
    return jumping;
  }

  public void setMoveRight(boolean right) {
    this.movingRight = right;
  }

  public boolean getMoveRight() {
    return movingRight;
  }

  public void setMoveLeft(boolean left) {
    this.movingLeft = left;
  }

  public boolean getMoveLeft() {
    return movingLeft;
  }

  public void setJumping(boolean jump) {
    this.jumping = jump;
  }

  public void attack(Mob mob) {

    mob.takeDamage(this.damage);

  }

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

  public void moveRight() {
    this.dx = this.speed;
  }

  public void moveLeft() {
    this.dx = -this.speed;
  }

  public void jump() {
    if (this.jumping && !this.falling) {
      this.dy = -this.jumpHeight;
      this.jumping = false;
      this.falling = true;
    }
  }

  public void takeDamage(int damageTaken) {
    this.health -= damageTaken;
  }

  public BufferedImage loadSprite(String spriteName) {
    try {
      return sprite = ImageIO.read(new File(spriteName));
    } catch (Exception e) {
      System.out.println("Error: Sprite Does Not Exist");
    }
    return null;
  }

  public void draw(Graphics g) {
    if (!this.isDead) {
      g.drawImage(this.sprite, this.x, this.y, null);
    }
  }

}