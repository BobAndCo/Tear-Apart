import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;

class Block {

	protected int x, y;
	protected int durability;
	BufferedImage sprite;
	String spriteName;
	boolean broken;

	Block(int x, int y, int durability) {
		this.x          = x;
		this.y          = y;
		this.durability = durability;
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

class Dirt extends Block {

	Dirt(int x, int y, int durability) {
		super(x, y, durability);
	}

	public void draw(Graphics g) {
		try {
			sprite = ImageIO.read(new File(spriteName));
		} catch(Exception e) {
			System.out.println("Error: Sprite Does Not Exist");
		};
		g.drawImage(sprite, x, y, null);
	}
}
