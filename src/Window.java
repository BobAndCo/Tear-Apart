import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

class Window extends JFrame { 

 JFrame frame;

 int ROWS    = 500;
 int COLUMNS = 500;
     
 Window() { 
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
     
 public static void main(String[] args) { 
  new Window();
 }
}
