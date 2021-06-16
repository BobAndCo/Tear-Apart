import javax.swing.JFrame;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * [Block.java]
 * @author Kian Dianati
 * @version 1.0 Build 15 Jun 2021
 * define the block type
 */

class Block {

	/* Constants */
	private String SPRITE_PATH = "../sprites/";
	private String SPRITE_EXTENSION = ".png";
	private int BLOCK_SIDE = 30;

	/* Variables */
	protected int x, y;
	protected int durability;
	BufferedImage sprite;
	boolean broken = false;
	private String TYPE;
	private Rectangle blockRect;

	/* Constructor */
	Block(int x, int y, String type) {
		this.x = x;
		this.y = y;
		this.sprite = loadSprite(SPRITE_PATH + type + SPRITE_EXTENSION);
		this.TYPE = type;
		this.blockRect = new Rectangle(x * BLOCK_SIDE, y * BLOCK_SIDE, 30, 30);
		switch (type) {
			case "grass":
				this.durability = 10;
				break;
			case "dirt":
				this.durability = 10;
				break;
			case "stone":
				this.durability = 12;
				break;
			case "sand":
				this.durability = 07;
				break;
			case "snow":
				this.durability = 02;
				break;
			case "wood":
				this.durability = 11;
				break;
			case "leaf":
				this.durability = 01;
				break;
		}
	}

	/**
	 * getType
	 * getter for TYPE variable
	 */
	public String getType() {
		return TYPE;
	}

	/**
	 * getRect
	 * getter for the blockRect variable
	 */
	public Rectangle getRect() {
		return blockRect;
	}

	/**
	 * loadSprite
	 * load the sprite based on spriteName
	 * @param spriteName the file name of the sprite
	 * @return BufferedImage of the sprite
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
	 * draw
	 * draw the graphics based on the tiling system
	 * @param g graphics
	 */
	public void draw(Graphics g) {
		g.drawImage(sprite, x * BLOCK_SIDE, y * BLOCK_SIDE, null);
	}
}
