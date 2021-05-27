import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;

class Game extends JPanel {
  
	Player player = new Player(100,100,100,25,1,false,"sprite");
	PlayerController playerController = new PlayerController(player);
  
	public void gameLoop() {
		boolean quit = false;
    
		while (!quit) {
			this.repaint();
		}
	}

	public void drawBackground(Graphics g) {
		try {
			File BACKGROUND_IMAGE = new File("../sprites/background.jpeg");
			g.drawImage(ImageIO.read(BACKGROUND_IMAGE), 0, 0, this);
		} catch(Exception e) {
			System.out.println("Error: No Background Image Found");
		};
	}
  
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setDoubleBuffered(true);
		player.move();
	}
  
}
