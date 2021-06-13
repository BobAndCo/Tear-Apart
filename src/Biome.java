import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.awt.Graphics;

/**
 * TODO save method
 * TODO generate method
 */

class Biome {

	String SAVE_PATH            = "../saves/";
	String SAVE_EXTENSION       = ".map";
	int    X_BUFFER_SIZE        = 1920/30; // => 60
	int    Y_BUFFER_SIZE        = 1080/30; // => 36
	int    GROUND_SCALE         = 12;
	int    UNDERGROUND_SCALE    = 5;
	String TYPE;

	Block[][] mapBuffer = new Block[Y_BUFFER_SIZE][X_BUFFER_SIZE];
	String outlineBlock, fillBlock;
	String undergroundBlock = "stone";
	double[] seedGround;
	double[] groundOutline;

	Biome(String type) {
		this.TYPE = type;
		switch(type) {
			case "plains": outlineBlock = "grass"; fillBlock = "dirt"     ; break;
			case "desert": outlineBlock = "sand" ; fillBlock = "sandstone"; break;
			case "tundra": outlineBlock = "snow" ; fillBlock = "dirt"     ; break;
		}
	}

	public int[] getHeight() {
		int[] arr = new int[groundOutline.length];
		for (int i=0; i < groundOutline.length; i++) {
			arr[i] = -1*(int)Math.round(groundOutline[i]) + Y_BUFFER_SIZE/2;
		}
		return arr;
	}

	public void saveBiome() throws Exception {
		try {
			File saveFile = new File(SAVE_PATH + TYPE + SAVE_EXTENSION);
			if (saveFile.createNewFile()) {
				System.out.println("Save File Created");
			}
			PrintWriter output = new PrintWriter(saveFile);
			for (int i=0; i < mapBuffer.length; i++) {
				for (int j=0; j < mapBuffer[i].length; j++) {
					switch(mapBuffer[i][j].getType()) {
					case "dirt"      : output.print("1"); break;
					case "grass"     : output.print("2"); break;
					case "leaf"      : output.print("3"); break;
					case "sand"      : output.print("4"); break;
					case "snow"      : output.print("5"); break;
					case "stone"     : output.print("6"); break;
					case "wood"      : output.print("7"); break;
					case "sandstone" : output.print("8"); break;
					}
				}
				output.println();
			}
		output.close();
		} catch (IOException e) {
			System.out.println("Error Writing to Save File");
			e.printStackTrace();
		}
	}

	public String[] loadBiome(String type) throws Exception {

		String[] biomeBuffer = new String[Y_BUFFER_SIZE];
		Scanner input = new Scanner(new File(SAVE_PATH + type + SAVE_EXTENSION));

		for (int i=0; input.hasNext(); i++) {
			biomeBuffer[i] = input.nextLine();
		}

		return biomeBuffer;
	}

	public void generateBiome() {
		Noise noiseGenerator = new Noise(X_BUFFER_SIZE, Y_BUFFER_SIZE / 2);

		seedGround      = noiseGenerator.makeSeed(1);
		double[] seedUnderground = noiseGenerator.makeSeed(1);
		double[] seedFill        = noiseGenerator.makeSeed(2);

		groundOutline      = noiseGenerator.perlinNoise1D(seedGround     , 2, 2.0);
		double[] undergroundOutline = noiseGenerator.perlinNoise1D(seedUnderground, 2, 2.0);

		for (int i=0; i < X_BUFFER_SIZE; i++) {
			groundOutline[i]      *= GROUND_SCALE;
			undergroundOutline[i] *= UNDERGROUND_SCALE;
		}

		for (int i=0; i < Y_BUFFER_SIZE; i++) {
			for (int j=0; j < X_BUFFER_SIZE; j++) {
				if (i == -1*(int)Math.round(groundOutline[j]) + Y_BUFFER_SIZE/2) {
					mapBuffer[i][j] = new Block(j, i, outlineBlock);
				} else if ((i >  -1*(int)Math.round(groundOutline[j])      + Y_BUFFER_SIZE/2) &&
					     (i <= -1*(int)Math.round(undergroundOutline[j]) + Y_BUFFER_SIZE/2)) {
					mapBuffer[i][j] = new Block(j, i, fillBlock);
				} else if (i > -1*(int)Math.round(undergroundOutline[j]) + Y_BUFFER_SIZE/2) {
					mapBuffer[i][j] = new Block(j, i, "stone");
				}
			}
		}
	}

	public void renderBiome(Graphics g) {
		for (int i=0; i < mapBuffer.length; i++) {
			for (int j=0; j < mapBuffer[i].length; j++) {
				if (mapBuffer[i][j] != null) {
					mapBuffer[i][j].draw(g);
				}
			}
		}
	}

}
