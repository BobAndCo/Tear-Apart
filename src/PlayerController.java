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
    
    char key = e.getKeyChar();
    
    if (key == 'a'){
      player.moveLeft();
    } else if (key == 'd') {
      player.moveRight();
    } else if (key == 'j') {
      player.setJumping(true);
      player.setFalling(false);
      player.jump();
    } else if (key == '1') {
      selectedBlock = "grass";
    }else if (key == '2') {
      selectedBlock = "dirt";
    }else if (key == '3') {
      selectedBlock = "stone";
    }else if (key == '4') {
     selectedBlock = "sand";
    } /*else if (key == '5') {
      selectedBlock = "sands";
    }*/else if (key == '6') {
      selectedBlock = "snow";
    }else if (key == '7') {
      selectedBlock = "wood";
    }else if (key == '8') {
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
