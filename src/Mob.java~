import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;

abstract class Mob{

    protected int x,y;
    protected int health;
    protected int damage;
    protected int speed;
    protected boolean dead = false;

    private BufferedImage sprite;
    String spriteName;
    
    Mob(int x, int y, int health, int speed, int damage, boolean dead, String spriteName){

        this.x = x;
        this.y = y;
        this.health = health;
        this.speed = speed;
        this.damage = damage;

        this.isDead = dead;

        this.spriteName = spriteName;

    }

    public void attack(){}

    public void move(){}

    public void takeDamage(){}

    public void Dead(){
        if (this.health <= 0){
            this.isDead = true;
        }
    }

    public void draw(Graphics g) {
		try {
			sprite = ImageIO.read(new File(spriteName));
		} catch(Exception e) {
			System.out.println("Error: Sprite Does Not Exist");
		};
		g.drawImage(sprite,x,y,null);
	}

}

class Player extends Mob{

    protected int x,y;
    protected int health;
    protected int speed;
    protected int damage;
    protected boolean dead;

    private BufferedImage sprite;
    String spriteName;

    Player(int x, int y, int health, int speed, int damage, boolean dead, String spriteName){
        super(x, y, health, speed, damage, dead, spriteName);
    }

    public void attack(Mob attackedMob){

        attackedMob.health -= this.damage;

    }

    public void move(int dx, int dy){

        this.x += dx;
        this.y = dy;

    }

    public void takeDamage(int damageAmount){

        this.health -= damageAmount;

    } 

    public void gainHealth(int increaseHealth){

        this.health += increaseHealth;

    }

}

class Zombie extends Mob{

    protected int x,y;
    protected int health;
    protected int speed;
    protected int damage;
    protected boolean dead;

    private BufferedImage sprite;
    String spriteName;

    Player(int x, int y, int health, int speed, int damage, boolean dead, String spriteName){
        super(x, y, health, speed, damage, dead, spriteName);
    }

    public void attack(Mob attackedMob){

        attackedMob.health -= this.damage;

    }

    public void move(int dx, int dy){

        this.x += dx;
        this.y = dy;

    }

    public void takeDamage(int damageAmount){

        this.health -= damageAmount;

    } 

}