// class for the keyboard listener - this detects key presses and runs the corresponding code

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class PlayerController implements KeyListener {

	// reference to items effected by keyboard press
	Player player;
	private String selectedBlock = "grass";

	PlayerController(Player p) {
		player = p;
	}

	public void keyTyped(KeyEvent e) {
		switch (e.getKeyChar()) {
			case 'a' : player.moveLeft()        ; break;
			case 'd' : player.moveRight()       ; break;
			case ' ' : player.setJumping(true)  ;
				     player.setFalling(false) ; break;
			case '1' : selectedBlock = "grass"  ; break;
			case '2' : selectedBlock = "dirt"   ; break;
			case '3' : selectedBlock = "stone"  ; break;
			case '4' : selectedBlock = "sand"   ; break;
			//case '5' : selectedBlock = "sands"  ; break;
			case '6' : selectedBlock = "snow"   ; break;
			case '7' : selectedBlock = "wood"   ; break;
			case '8' : selectedBlock = "leaf"   ; break;
		}
	}

	public String getSelectedBlock() {
		return selectedBlock;
	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {

	}

} // end of keyboard listener
