import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class MyMouseListener implements MouseListener {

	private int TILING_FACTOR = 30;
	private int x, y;
	public void mouseClicked(MouseEvent e) {
		x = (int)e.getX() / TILING_FACTOR;
		y = (int)e.getY() / TILING_FACTOR;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseReleased (MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
