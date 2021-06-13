import java.io.File;
import java.util.Scanner;
import java.awt.Graphics;

/**
 * TODO save method
 * TODO generate method
 */

class Biome {

	String MAP_PATH             = "../maps/";
	String MAP_EXTENSION        = ".map";
	int    MAP_TYPES            = 4;
	int    X_BUFFER_SIZE        = 1920/30; // => 60
	int    Y_BUFFER_SIZE        = 1080/30; // => 36
	int    GROUND_SCALE         = 12;
	int    UNDERGROUND_SCALE    = 5;

	Block[][] mapBuffer = new Block[Y_BUFFER_SIZE][X_BUFFER_SIZE];
	String outlineBlock, fillBlock;
	String undergroundBlock = "stone";

	Biome(String type) {
		switch(type) {
			case "plains": outlineBlock = "grass"; fillBlock = "dirt"     ; break;
			case "desert": outlineBlock = "sand" ; fillBlock = "sandstone"; break;
			case "tundra": outlineBlock = "snow" ; fillBlock = "dirt"     ; break;
		}
	}

	public String[] loadBiome(String type) throws Exception {

		String[] biomeBuffer = new String[Y_BUFFER_SIZE];
		Scanner input = new Scanner(new File(MAP_PATH + type + MAP_EXTENSION));

		for (int i=0; input.hasNext(); i++) {
			biomeBuffer[i] = input.nextLine();
		}

		return biomeBuffer;
	}

	public void generateTerrain() {
		Noise noiseGenerator = new Noise(X_BUFFER_SIZE, Y_BUFFER_SIZE / 2);

		double[] seedGround      = noiseGenerator.makeSeed(1);
		double[] seedUnderground = noiseGenerator.makeSeed(1);
		double[] seedFill        = noiseGenerator.makeSeed(2);

		double[] groundOutline      = noiseGenerator.perlinNoise1D(seedGround     , 2, 2.0);
		double[] undergroundOutline = noiseGenerator.perlinNoise1D(seedUnderground, 2, 2.0);
		//double[] undergroundFill    = noiseGenerator.perlinNoise2D(seedFill       , 2, 2.0);

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

	/*
	public void renderBiome(Graphics g) {
		for (int i=0; i<mapBuffer.length; i++) {
			for (int j=0; j<mapBuffer[i].length(); j++) {
				switch (mapBuffer[i].charAt(j)) {
					case '1': (new Block(j, i, "grass"    )).draw(g); break;
					case '2': (new Block(j, i, "dirt"     )).draw(g); break;
					case '3': (new Block(j, i, "stone"    )).draw(g); break;
					case '4': (new Block(j, i, "sand"     )).draw(g); break;
					case '5': (new Block(j, i, "snow"     )).draw(g); break;
					case '6': (new Block(j, i, "wood"     )).draw(g); break;
					case '7': (new Block(j, i, "leaf"     )).draw(g); break;
					case '8': (new Block(j, i, "sandstone")).draw(g); break;
				}
			}
		}
	}
	*/
}
