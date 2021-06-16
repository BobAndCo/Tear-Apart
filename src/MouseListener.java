import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class MyMouseListener implements MouseListener {

	/* Variables */
	private int TILING_FACTOR = 30;
	private int x, y;
	private String buttonPressed = "";

	/**
	 * mouseClicked
	 * get the correct cords of the mouse
	 * @param e MouseEvent
	 */
	public void mouseClicked(MouseEvent e) {
		x = (int) e.getX() / TILING_FACTOR;
		y = (int) e.getY() / TILING_FACTOR;

		if (e.getButton() == MouseEvent.BUTTON1) {
			buttonPressed = "left";
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			buttonPressed = "right";
		}
	}

	/**
	 * getButtonPressed
	 * getter for buttonPressed
	 */
	public String getButtonPressed() {
		return buttonPressed;
	}

	/**
	 * getX
	 * getter for x
	 */
	public int getX() {
		return x;
	}

	/**
	 * getY
	 * getter for y
	 */
	public int getY() {
		return y;
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
