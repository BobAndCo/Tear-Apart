import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

class Game extends JPanel {

	Block dirt = new Dirt(10,10,10);

	public void gameLoop() {
		boolean quit = false;
		JFrame frame = new JFrame("Terrapart!");
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		while (!quit) {
			this.repaint();
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setDoubleBuffered(true);
		dirt.draw(g);
	}

}
