// class for the keyboard listener - this detects key presses and runs the corresponding code

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class PlayerController implements KeyListener {
  
  //reference to items effected by keyboard press
  private Player player;
    
    PlayerController(Player p) {
      player = p;
    }
    
    public void keyTyped(KeyEvent e) {  
      
      if(e.getKeyChar() == 'a' ){   
        player.move(player.speed,0);  
        System.out.println("pressed");
      } else if(e.getKeyChar() == 'd' ){
        player.move(player.speed,0);
      } 
    }

      public void keyPressed(KeyEvent e) {
      }   
      
      public void keyReleased(KeyEvent e) {
      }
  } //end of keyboard listener