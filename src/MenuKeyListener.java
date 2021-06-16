import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * [MenuKeyListener.java]
 * @author Kian Dianati
 * @version 1.0 Build 15 Jun 2021
 * KeyListener for menus
 */

class MenuKeyListener implements KeyListener {

	/* variables */
	private int keyPressed = 0;
	private boolean entered = false;

	/**
	 * keyTyped
	 * change keyPressed based the pressed key
	 * @pararm e KeyEvent
	 */
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

	/**
	 * getKeyPressed
	 * getter for keyPressed
	 */
	public int getKeyPressed() {
		return keyPressed;
	}

	/**
	 * getEntered
	 * getter for entered
	 */
	public boolean getEntered() {
		return entered;
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}
}
