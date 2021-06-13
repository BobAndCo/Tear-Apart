import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;

class Player{

  String SPRITE_PATH = "../sprites/";
  String SPRITE_EXTENSION = ".jpg";

  public int x, y;
  public int health;
  public int damage;
  public int speed;
  public int jumpHeight;

  public boolean isDead;
  public boolean falling = true;
  public boolean jumping = false;

  public int dx, dy;
  public Rectangle rectangle; 

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

    this.isDead = dead;

    this.spriteName = spriteName;

  }

  public void attack(Mob mob) {

    mob.takeDamage(this.damage);

  }

  public void move() {
    this.x = this.x + this.dx;
    this.y = this.y + this.dy;
    this.dx = 0;
    this.dy = 1;
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
    g.drawImage(this.sprite, this.x, this.y, null);
    g.drawRect(this.x, this.y, 30, 30);
  }

}