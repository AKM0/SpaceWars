//handles all input
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	
	public boolean move_left;
	public boolean move_right;
	public boolean move_up;
	public boolean move_down;
	public boolean fire;
	public boolean quit_game = false;
	
	public Input (Component source) {
		
		source.addKeyListener (this);
		
	}

	//keyTyped, keyPressed, and keyReleased
	public void keyTyped(KeyEvent e) {
	
	}

	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_W) {	move_up = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {	move_right = true;	
		} else if (e.getKeyCode() == KeyEvent.VK_A) {	move_left = true;	
		} else if (e.getKeyCode() == KeyEvent.VK_S) {	move_down = true;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) { fire = true; 
		} 

		if (e.getKeyCode() == KeyEvent.VK_Q) {	
			quit_game = true;
		}
			
	}
	
	public void keyReleased(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_W) {	move_up = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {	move_right = false;	
		} else if (e.getKeyCode() == KeyEvent.VK_A) {	move_left = false;	
		} else if (e.getKeyCode() == KeyEvent.VK_S) {	move_down = false;
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) { fire = false; 
		} 

		if (e.getKeyCode() == KeyEvent.VK_Q) {	
			quit_game = false;
		}
		
	}
	
}



