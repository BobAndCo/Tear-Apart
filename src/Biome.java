import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Graphics;

/**
 * [Biome.java]
 * 
 * @author Kian Dianati
 * @version 1.0 Build 15 Jun 2021 Biome class, it generates a Biome, saves a
 *          Biome to a save file, loads the Biome from the save file, and
 *          renders the Biome unto the screen.
 */

class Biome {

	/* Constants */
	private String SAVE_PATH = "../saves/"; // Path to the save file
	private String SAVE_EXTENSION = ".map"; // Extension for the save file
	private int X_BUFFER_SIZE = 1920 / 30; // Columns in Buffer
	private int Y_BUFFER_SIZE = 1080 / 30; // Rows in Buffer
	private int GROUND_SCALE = 15; // Scale factor for Ground Noise
	private int UNDERGROUND_SCALE = 5; // Scale factor for Underground Noise
	private String TYPE; // Type of Biome

	/* Variables */
	// Buffer to store the map of the Biome
	private Block[][] mapBuffer = new Block[Y_BUFFER_SIZE][X_BUFFER_SIZE];
	private String outlineBlock, fillBlock; // Types of blocks used in Biome
	private String undergroundBlock = "stone"; // Use stone when underground.
	double[] seedGround; // Seed for ground
	double[] groundOutline; // Seed for outline

	/* Constructor */
	Biome(String type) {
		this.TYPE = type;
		// Determine the Blocks based on type
		switch (type) {
			case "plains": // Plains uses grass and dirt
				outlineBlock = "grass";
				fillBlock = "dirt";
				break;
			case "desert": // Desert uses sand and sandstone
				outlineBlock = "sand";
				fillBlock = "stone";
				break;
			case "tundra": // Tundra uses snow and dirt
				outlineBlock = "snow";
				fillBlock = "dirt";
				break;
		}
	}

	/**
	 * getFirstHeight This method returns the height of the right-most block of a
	 * biome
	 */
	public int getFirstHeight() {
		return (-1 * (int) Math.round(groundOutline[0]) + (int) (Y_BUFFER_SIZE / (1.5))) * 30;
	}

	/**
	 * getLastHeight This method returns the height of the left-most block of a
	 * biome
	 */
	public int getLastHeight() {
		return (-1 * (int) Math.round(groundOutline[30]) + (int) (Y_BUFFER_SIZE / (1.5))) * 30;
	}

	/**
	 * removeBlock Removes the block at the given cord
	 * 
	 * @param x The x-cord of the block to be removed
	 * @param y the y-cord of the block to be removed
	 */
	public void removeBlock(int x, int y) {
		if (mapBuffer[y][x] != null) {
			mapBuffer[y][x] = null;
		}
	}

	/**
	 * addBlock Adds a block of the given type at the given cord
	 * 
	 * @param x    The x-cord of the block to be added
	 * @param y    the y-cord of the block to be added
	 * @param type the type of the block to be added
	 */
	public void addBlock(int x, int y, String type) {
		if (mapBuffer[y][x] == null) {
			mapBuffer[y][x] = new Block(x, y, type);
		}
	}

	/**
	 * getMapBuffer Returns the mapBuffer array
	 */
	public Block[][] getMapBuffer() {
		return mapBuffer;
	}

	/**
	 * getType Returns the type of the Biome
	 */
	public String getType() {
		return TYPE;
	}

	/**
	 * saveBiome Save the biome to the appropriate file
	 */
	public void saveBiome() {
		/* Manage Errors */
		try {
			File saveFile = new File(SAVE_PATH + TYPE + SAVE_EXTENSION);
			PrintWriter output = new PrintWriter(saveFile);

			/*
			 * Iterate through the buffer and write the appropriate number for each block
			 */
			for (int i = 0; i < mapBuffer.length; i++) {
				for (int j = 0; j < mapBuffer[i].length; j++) {
					if (mapBuffer[i][j] == null) {
					} else {
						switch (mapBuffer[i][j].getType()) {
							case "dirt":
								output.print("1");
								break; // dirt is 1
							case "grass":
								output.print("2");
								break; // grass is 2
							case "leaf":
								output.print("3");
								break; // leaf is 3
							case "sand":
								output.print("4");
								break; // sand is 4
							case "snow":
								output.print("5");
								break; // snow is 5
							case "stone":
								output.print("6");
								break; // stone is 6
							case "wood":
								output.print("7");
								break; // wood is 7
							case "stone":
								output.print("8");
								break; // sandstone is 8
							default:
								output.print(" ");
								break; // null is empty string
						}
					}
				}
				output.println();
			}
			output.close();
		} catch (IOException e) {
			System.out.println("Err: Didn't Save File");
			e.printStackTrace();
		}
	}

	/**
	 * loadBiome load a biome from a save file and write it into mapBuffer
	 */
	public void loadBiome() {
		/* Check Errors */
		try {
			Scanner input = new Scanner(new File(SAVE_PATH + TYPE + SAVE_EXTENSION));
			/* Iterate throught the file */
			for (int i = 0; input.hasNext(); i++) {
				String line = input.nextLine();
				for (int j = 0; j < line.length(); j++) {
					/* Set the appropriate Block according to the read character */
					switch (line.charAt(j)) {
						case '1':
							mapBuffer[i][j] = new Block(j, i, "dirt");
							break;
						case '2':
							mapBuffer[i][j] = new Block(j, i, "grass");
							break;
						case '3':
							mapBuffer[i][j] = new Block(j, i, "leaf");
							break;
						case '4':
							mapBuffer[i][j] = new Block(j, i, "sand");
							break;
						case '5':
							mapBuffer[i][j] = new Block(j, i, "snow");
							break;
						case '6':
							mapBuffer[i][j] = new Block(j, i, "stone");
							break;
						case '7':
							mapBuffer[i][j] = new Block(j, i, "wood");
							break;
						case '8':
							mapBuffer[i][j] = new Block(j, i, "sands");
							break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error Reading Save File");
		}
	}

	/**
	 * generateBiome Generate the biome's terrain
	 */
	public void generateBiome() {
		Noise noiseGenerator = new Noise(X_BUFFER_SIZE, Y_BUFFER_SIZE / 2);

		/* Generate Seed */
		seedGround = noiseGenerator.makeSeed(1);
		double[] seedUnderground = noiseGenerator.makeSeed(1);

		/* Generate Noise */
		groundOutline = noiseGenerator.perlinNoise1D(seedGround, 2, 2.0);
		double[] undergroundOutline = noiseGenerator.perlinNoise1D(seedUnderground, 2, 2.0);

		/* Scale the Noise */
		for (int i = 0; i < X_BUFFER_SIZE; i++) {
			groundOutline[i] *= GROUND_SCALE;
			undergroundOutline[i] *= UNDERGROUND_SCALE;
		}

		/* Iterate through the buffer */
		for (int i = 0; i < Y_BUFFER_SIZE; i++) {
			for (int j = 0; j < X_BUFFER_SIZE; j++) {
				// If i is equal to the given height then set the outline block
				if (i == -1 * (int) Math.round(groundOutline[j]) + (int) (Y_BUFFER_SIZE / (1.5))) {
					mapBuffer[i][j] = new Block(j, i, outlineBlock);
				}
				// If i is smaller than the outline height and smaller than the underground
				// outline height
				// then set the fillBlock
				else if ((i > -1 * (int) Math.round(groundOutline[j]) + (int) (Y_BUFFER_SIZE / (1.5)))
						&& (i <= -1 * (int) Math.round(undergroundOutline[j]) + (int) (Y_BUFFER_SIZE / (1.5)))) {
					mapBuffer[i][j] = new Block(j, i, fillBlock);
				}
				// If i smaller than underground outline height then set to stone
				else if (i > -1 * (int) Math.round(undergroundOutline[j]) + (int) (Y_BUFFER_SIZE / (1.5))) {
					mapBuffer[i][j] = new Block(j, i, "stone");
				}
			}
		}
	}

	/**
	 * renderBiome render the biome onto the screen
	 */
	public void renderBiome(Graphics g) {
		for (int i = 0; i < mapBuffer.length; i++) {
			for (int j = 0; j < mapBuffer[i].length; j++) {
				if (mapBuffer[i][j] != null) {
					mapBuffer[i][j].draw(g);
				}
			}
		}
	}

}
