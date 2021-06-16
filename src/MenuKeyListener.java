import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MenuKeyListener implements KeyListener {

	private int keyPressed = 0;
	private boolean entered = false;

	MenuKeyListener() {
	}

	public void keyTyped(KeyEvent e) {
		char key = e.getKeyChar();
		if (key == 'w') {
			keyPressed = 0;
			System.out.println("w");
		} else if (key == 's') {
			keyPressed = 1;
			System.out.println("s");
		} else if (key == 'e') {
			entered = true;
			System.out.println("e");
		}
	}

	public int getKeyPressed() {
		return keyPressed;
	}

	public boolean getEntered() {
		return entered;
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}
