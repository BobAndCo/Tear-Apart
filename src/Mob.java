import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;

abstract class Mob {

  String SPRITE_PATH = "../sprites/";
  String SPRITE_EXTENSION = ".png";

  public int x, y;
  public int health;
  public int damage;
  public int speed;

  private boolean isDead;
  private boolean falling = true;

  public int dx, dy;
  private Rectangle mobRect;

  public BufferedImage sprite;
  String spriteName;

  Mob(int x, int y, int health, int speed, int damage, boolean dead, String spriteName) {

    this.x = x;
    this.y = y;
    this.health = health;
    this.speed = speed;
    this.damage = damage;
    this.dx = 0;
    this.dy = 1;

    this.sprite = loadSprite(SPRITE_PATH + spriteName + SPRITE_EXTENSION);
    this.mobRect = new Rectangle(x, y, 30, 30);

    this.isDead = dead;

    this.spriteName = spriteName;

  }

  public boolean collides(Rectangle rect) {
    if (x < rect.x + 30 && x + 30 > rect.x && y < rect.y + 30 && y + 30 > rect.y) {
      return true;
    }
    return false;
  }

  public Rectangle getRect() {
    return mobRect;
  }

  public boolean getFalling() {
    return falling;
  }

  public void setFalling(boolean fall) {
    this.falling = fall;
  }

  public boolean getDead() {
    return isDead;
  }

  public void setDead(boolean deathValue) {
    this.isDead = deathValue;
  }

  public void pathFinding(Player player) {

    if (player.x - this.x >= 0 && player.x - this.x <= 100) {
      this.moveRight();
      this.move();
    } else if (player.x - this.x <= 0 && player.x - this.x >= -100) {
      this.moveLeft();
      this.move();
    }
    try {
      Thread.sleep(10);
    } catch (Exception exc) {
      System.out.println("Thread Error");
    }

  }

  public void attack(Player player) {

    player.takeDamage(this.damage);

  }

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

  public void moveRight() {
    this.dx = this.speed;
  }

  public void moveLeft() {
    this.dx = -this.speed;
  }

  public void stay() {
    this.dx = 0;
  }

  public void takeDamage(int damageTaken) {
    this.health -= damageTaken;
  }

  public void Dead() {
    if (this.health <= 0) {
      this.isDead = true;
    }
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
    if (!isDead) {
      g.drawImage(this.sprite, this.x, this.y, null);
    }
  }

}

class Zombie extends Mob {

  Zombie(int x, int y) {
    super(x, y, 1, 3, 10, false, "snow");
  }

}

class Creeper extends Mob {

  Creeper(int x, int y) {
    super(x, y, 1, 5, 10, false, "wood");
  }

}

class Frozen extends Mob {

  Frozen(int x, int y) {
    super(x, y, 1, 1, 10, false, "dirt");
  }

}