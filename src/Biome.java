import java.io.File;
import java.util.Scanner;
import java.awt.Graphics;

class Biome {
	
	String MAP_PATH             = "../maps/";
	String MAP_EXTENSION        = ".map";
	int    MAP_TYPES            = 4;
	int    X_BUFFER_SIZE        = 1920/30;
	int    Y_BUFFER_SIZE        = 1080/30;
	int    X_PREFAB_BUFFER_SIZE = X_BUFFER_SIZE/MAP_TYPES;
	int    Y_PREFAB_BUFFER_SIZE = Y_BUFFER_SIZE/MAP_TYPES;

	String[] mapBuffer = new String[Y_BUFFER_SIZE];

	Biome(String type) {
		try {
			this.mapBuffer = loadBiome(type);
		} catch (Exception e) {
			System.out.println("Error: Map File Not Found");
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

	public void renderBiome(Graphics g) {
		for (int i=0; i<mapBuffer.length; i++) {
			for (int j=0; j<mapBuffer[i].length(); j++) {
				switch (mapBuffer[i].charAt(j)) {
					case '1': (new Block(j, i, "grass")).draw(g); break;
					case '2': (new Block(j, i, "dirt" )).draw(g); break;
					case '3': (new Block(j, i, "stone")).draw(g); break;
					case '4': (new Block(j, i, "sand" )).draw(g); break;
					case '5': (new Block(j, i, "snow" )).draw(g); break;
					case '6': (new Block(j, i, "wood" )).draw(g); break;
					case '7': (new Block(j, i, "leaf" )).draw(g); break;
				}
			}
		}
	}
}
