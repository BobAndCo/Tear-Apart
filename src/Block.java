import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;

class Block {

	String SPRITE_PATH      = "../sprites/";
	String SPRITE_EXTENSION = ".jpeg";
	int BLOCK_SIDE          = 30;

	protected int x, y;
	protected int durability;
	BufferedImage sprite;
	boolean broken = false;

	Block(int x, int y, String type) {
		this.x          = x;
		this.y          = y;
		this.sprite     = loadSprite(SPRITE_PATH+type+SPRITE_EXTENSION);
		switch (type) {
			case "dirt"  : this.durability = 10; break;
			case "stone" : this.durability = 12; break;
			case "sand"  : this.durability = 07; break;
			case "snow"  : this.durability = 02; break;
			case "wood"  : this.durability = 11; break;
			case "leaf"  : this.durability = 01; break;
		}
	}

	public void loseDurability(int durability) {
		if (this.durability < 1) {
			broken = true;
		}
		this.durability -= durability;
	}

	public BufferedImage loadSprite(String spriteName) {
		try {
			return sprite = ImageIO.read(new File(spriteName));
		} catch(Exception e) {
			System.out.println("Error: Sprite Does Not Exist");
		};
		return null;
	}

	public void draw(Graphics g, int x, int y) {
		g.fillRect(x*BLOCK_SIDE, y*BLOCK_SIDE, BLOCK_SIDE, BLOCK_SIDE);  
	}
}
