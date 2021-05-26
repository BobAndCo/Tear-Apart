import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;

class Block {

	String SPRITE_PATH      = "../sprites/";
	String SPRITE_EXTENSION = ".jpeg";

	protected int x, y;
	protected int durability;
	BufferedImage sprite;
	String spriteName;
	boolean broken;

	Block(int x, int y, int durability, String type) {
		this.x          = x;
		this.y          = y;
		this.durability = durability;
		this.spriteName = SPRITE_PATH + type + SPRITE_EXTENSION;
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
