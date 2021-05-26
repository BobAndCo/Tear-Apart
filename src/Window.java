import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

class Window extends JFrame { 

	JFrame thisFrame;
	    
	Window() { 
		super("Start Screen");
		this.thisFrame = this; //lol  
	                  
		this.setSize(400,700);    
		this.setLocationRelativeTo(null); //start the frame in the center of the screen
		this.setResizable (false);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		thisFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                                                      
		this.add(mainPanel);
		this.setVisible(true);
		this.requestFocusInWindow();
	}
	    
	public static void main(String[] args) { 
		new Window();
	}
}
