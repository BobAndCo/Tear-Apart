// class for the keyboard listener - this detects key presses and runs the corresponding code

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The key board listener for the player class
 * @author Khush Negandhi
 * @version 1.0, May 2021 
 * 
 */

class PlayerController implements KeyListener {

  // reference to items effected by keyboard press
  Player player;
  //The type of block selected
  private String selectedBlock = "grass";

  /**
   * Creates a player controller 
   * @param p The player
   */
  PlayerController(Player p) {
    player = p;
  }

  /**
   * Finds the key that is pressed
   * @param e The key event
   */
  public void keyTyped(KeyEvent e) {

    //the key
    char key = e.getKeyChar();

    //Checks which key is pressed 
    if (key == 'a') {
      //moves the player left
      player.moveLeft();
    } else if (key == 'd') {
      //moves the player right
      player.moveRight();
    } else if (key == 'j') {
      //Jumps
      player.setJumping(true);
      player.setFalling(false);
      player.jump();
    } else if (key == '1') {
      //Selects the grass block
      selectedBlock = "grass";
    } else if (key == '2') {
      //Selects the dirt block
      selectedBlock = "dirt";
    } else if (key == '3') {
      //Selects the stone block
      selectedBlock = "stone";
    } else if (key == '4') {
      //Selects the sand block
      selectedBlock = "sand";
    } /*
       * else if (key == '5') { selectedBlock = "sands"; }
       */else if (key == '6') {
         //Selects the snow block
      selectedBlock = "snow";
    } else if (key == '7') {
      //Selects the wood block
      selectedBlock = "wood";
    } else if (key == '8') {
      //Selects the leaf block
      selectedBlock = "leaf";
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
