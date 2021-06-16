import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Graphics;

/**
 * TODO save method
 */

class Biome {

	private String SAVE_PATH = "../saves/";
	private String SAVE_EXTENSION = ".map";
	private int X_BUFFER_SIZE = 1920 / 30; // => 60
	private int Y_BUFFER_SIZE = 1080 / 30; // => 36
	private int GROUND_SCALE = 15;
	private int UNDERGROUND_SCALE = 5;
	private String TYPE;

	private Block[][] mapBuffer = new Block[Y_BUFFER_SIZE][X_BUFFER_SIZE];
	private String outlineBlock, fillBlock;
	private String undergroundBlock = "stone";
	double[] seedGround;
	double[] groundOutline;

	Biome(String type) {
		this.TYPE = type;
		switch (type) {
			case "plains":
				outlineBlock = "grass";
				fillBlock = "dirt";
				break;
			case "desert":
				outlineBlock = "sand";
				fillBlock = "sandstone";
				break;
			case "tundra":
				outlineBlock = "snow";
				fillBlock = "dirt";
				break;
		}
	}

	public int getFirstHeight() {
		return (-1 * (int) Math.round(groundOutline[0]) + (int) (Y_BUFFER_SIZE / (1.5))) * 30;
	}

	public int getLastHeight() {
		return (-1 * (int) Math.round(groundOutline[30]) + (int) (Y_BUFFER_SIZE / (1.5))) * 30;
	}

	public void removeBlock(int x, int y) {
		if (mapBuffer[y][x] != null) {
			mapBuffer[y][x] = null;
		}
	}

	public void addBlock(int x, int y, String type) {
		if (mapBuffer[y][x] == null) {
			mapBuffer[y][x] = new Block(x, y, type);
		}
	}

	public Block[][] getMapBuffer() {
		return mapBuffer;
	}

	public String getType() {
		return TYPE;
	}

	public void saveBiome() {
		try {
			File saveFile = new File(SAVE_PATH + TYPE + SAVE_EXTENSION);
			PrintWriter output = new PrintWriter(saveFile);
			for (int i = 0; i < mapBuffer.length; i++) {
				for (int j = 0; j < mapBuffer[i].length; j++) {
					if (mapBuffer[i][j] == null) {
					} else {
						switch (mapBuffer[i][j].getType()) {
							case "dirt":
								output.print("1");
								break;
							case "grass":
								output.print("2");
								break;
							case "leaf":
								output.print("3");
								break;
							case "sand":
								output.print("4");
								break;
							case "snow":
								output.print("5");
								break;
							case "stone":
								output.print("6");
								break;
							case "wood":
								output.print("7");
								break;
							case "sands":
								output.print("8");
								break;
							default:
								output.print(" ");
								break;
						}
					}
				}
				output.println();
			}
			output.close();
		} catch (IOException e) {
			System.out.println("Err: Didn't Save File");
			// e.printStackTrace();
		}
	}

	public void loadBiome() {
		try {
			Scanner input = new Scanner(new File(SAVE_PATH + TYPE + SAVE_EXTENSION));
			for (int i = 0; input.hasNext(); i++) {
				String line = input.nextLine();
				for (int j = 0; j < line.length(); j++) {
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

	public void generateBiome() {
		Noise noiseGenerator = new Noise(X_BUFFER_SIZE, Y_BUFFER_SIZE / 2);

		seedGround = noiseGenerator.makeSeed(1);
		double[] seedUnderground = noiseGenerator.makeSeed(1);
		double[] seedFill = noiseGenerator.makeSeed(2);

		groundOutline = noiseGenerator.perlinNoise1D(seedGround, 2, 2.0);
		double[] undergroundOutline = noiseGenerator.perlinNoise1D(seedUnderground, 2, 2.0);

		for (int i = 0; i < X_BUFFER_SIZE; i++) {
			groundOutline[i] *= GROUND_SCALE;
			undergroundOutline[i] *= UNDERGROUND_SCALE;
		}

		for (int i = 0; i < Y_BUFFER_SIZE; i++) {
			for (int j = 0; j < X_BUFFER_SIZE; j++) {
				if (i == -1 * (int) Math.round(groundOutline[j]) + (int) (Y_BUFFER_SIZE / (1.5))) {
					mapBuffer[i][j] = new Block(j, i, outlineBlock);
				} else if ((i > -1 * (int) Math.round(groundOutline[j]) + (int) (Y_BUFFER_SIZE / (1.5)))
						&& (i <= -1 * (int) Math.round(undergroundOutline[j]) + (int) (Y_BUFFER_SIZE / (1.5)))) {
					mapBuffer[i][j] = new Block(j, i, fillBlock);
				} else if (i > -1 * (int) Math.round(undergroundOutline[j]) + (int) (Y_BUFFER_SIZE / (1.5))) {
					mapBuffer[i][j] = new Block(j, i, "stone");
				}
			}
		}
	}

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
