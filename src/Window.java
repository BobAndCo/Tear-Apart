import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

/**
 * [Window.java]
 * @author Kian Dianati
 * @version 1.0 Build 15 Jun 2021
 */

class Window extends JFrame { 

 JFrame frame;

 /* Constants */
 int ROWS    = 500;
 int COLUMNS = 500;
     
 /* Constructor */
 Window() { 
	 /* Boiler Plate */
  super("Terrapart!");
  this.frame = this;
                   
  this.setSize(COLUMNS, ROWS);    
  this.setLocationRelativeTo(null);
  this.setResizable(true);
  
  JPanel mainPanel = new JPanel();
  mainPanel.setLayout(new BorderLayout());
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                                       
  this.add(new Game());
  
  this.setFocusable(false);
  this.setVisible(true);
 }
     
 /**
  * main
  * main method
  * @param args
  */
 public static void main(String[] args) { 
  new Window();
 }
}
