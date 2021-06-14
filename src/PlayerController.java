// class for the keyboard listener - this detects key presses and runs the corresponding code

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class PlayerController implements KeyListener {

  // reference to items effected by keyboard press
  Player player;

  PlayerController(Player p) {
    player = p;
  }

  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == 'a') {
      player.moveLeft();
    } else if (e.getKeyChar() == 'd') {
      player.moveRight();
    } else if (e.getKeyChar() == 'j') {
      player.setJumping(true);
      player.setFalling(false);
      player.jump();
    }
  }

  public void keyPressed(KeyEvent e) {

  }

  public void keyReleased(KeyEvent e) {

  }

} // end of keyboard listener
