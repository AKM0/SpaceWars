//A rough to-be-improved SpaceWars! recreation
//Programmed by AKM
//June 24, 2016 - 
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Main extends JComponent{
	
	private static final long serialVersionUID = 1L; //makes error go away
	
	public static String TITLE = "SpaceWars!"; //title of window
	public static int WIDTH = 1920; //width of window
	public static int HEIGHT = 1080; //height of window
	public static int FRAMERATE = 60;
	public static int NUMBER_OF_STARS = 512;
	public static double SHIP_ACCELERATION = 0.10;
	public static double SHIP_MASS = 1;
	public static double ATTRACTOR_MASS = 100.0;
	public static boolean RUN = true; //used in game loop
	
	static Ship ship_1 = new Ship (100, 100, 0, 0, SHIP_ACCELERATION, SHIP_ACCELERATION, 0, SHIP_MASS, Color.GREEN, true); //(double x, double y, double vx, double vy, double ax, double 
	//ay, double h, double m, boolean vis_b)
	static Ship ship_2 = new Ship (WIDTH - 100, HEIGHT - 100, 0, 0, SHIP_ACCELERATION, SHIP_ACCELERATION, 0, SHIP_MASS, Color.RED, true);
	static Attractor center = new Attractor (WIDTH/2, HEIGHT/2, ATTRACTOR_MASS, 15, Color.CYAN, true); //(position x, position y, mass, radius, visibility)
	static Star [] stars = new Star [NUMBER_OF_STARS]; 

	public static void main (String [] args) { 
		
		JFrame window = new JFrame (TITLE); //window (includes title, icon, and exit buttons)
		JPanel canvas = (JPanel) window.getContentPane();
		Input keyboard = new Input (canvas);
		
		canvas.setPreferredSize (new Dimension (WIDTH, HEIGHT));
		canvas.setBackground (Color.BLACK);
		canvas.setIgnoreRepaint (true); //won't automatically redraw
		canvas.setFocusable(true);
		canvas.addKeyListener(new Input (canvas)); //gets input when game is in focus
		
		window.setBounds (0, 0, WIDTH, HEIGHT);
		window.pack(); //sets size of window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		//window.setResizable(false);
		
		run_game (canvas, keyboard);
		
		System.exit (0);
		
	}
	
	public static void run_game (JPanel canvas, Input keyboard) {
		
		long time; //times to be used for framerate
		long delay_time; 
		
		Graphics2D g2d = (Graphics2D) canvas.getGraphics();
		BufferedImage buffer = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D bg = (Graphics2D) buffer.getGraphics(); //(Graphics2D) canvas.getGraphics ();
		bg.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int k = 0; k < stars.length; k++) { //loop generates stars
			stars [k] = new Star (WIDTH, HEIGHT);
		}
		
		while (!keyboard.quit_game) {
			time = System.currentTimeMillis();
		
			clear (bg);
			
			update (keyboard);
			
			render_stars (bg);
			render_ships (bg);
			render_projectiles (bg);
			render_attractor (bg);
			
			g2d.drawImage(buffer, 0, 0, WIDTH, HEIGHT, canvas);
			
			delay_time = ((1000/FRAMERATE) - (time - System.currentTimeMillis()));
			if (delay_time > 0) {
				try {
					Thread.sleep(delay_time);
				} catch (InterruptedException e) {
					System.out.println ("Error, unable to delay framerate");
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void clear (Graphics2D bg) {
		
		bg.setColor(Color.BLACK);
		bg.fillRect(0, 0, WIDTH, HEIGHT);
		
	}
	
	public static void render_stars (Graphics2D bg) {
		
		bg.setColor (Color.CYAN);
	
		for (int k = 0; k < stars.length; k++) {	//populates canvas with stars
			bg.fillRect  ((int) stars [k].get_pos ('x'), (int) stars [k].get_pos ('y'), 
					(int) stars [k].get_radius(), (int) stars [k].get_radius());
		}
			
	}
	
	public static void render_ships (Graphics2D bg) { 
		
		bg.setColor (ship_1.get_color ()); 
		bg.fillOval ((int) ship_1.get_pos('x'), (int) ship_1.get_pos('y'), 5, 5);	//draws first ship
//		bg.drawString ("Ship aceleration X: " + ship_1.get_acc('x'), 0, 10);
//		bg.drawString ("Ship aceleration Y: " + ship_1.get_acc('y'), 0, 20);
//		bg.drawString ("Ship velocity X: " + ship_1.get_vel('x'), 0, 30);
//		bg.drawString ("Ship velocity Y: " + ship_1.get_vel('y'), 0, 40);
		bg.setColor (ship_2.get_color ());
		bg.fillOval ((int) ship_2.get_pos('x'), (int) ship_2.get_pos('y'), 5, 5);	//second ship
		
	}

	public static void render_projectiles (Graphics2D bg) {
		ship_1.manage_missiles();
		ship_2.manage_missiles();
		for (int i = 0; i < ship_1.missiles.size(); i++) {
			bg.setColor(ship_1.missiles.get(i).body_color);
			bg.drawRect((int)ship_1.missiles.get(i).pos_x, (int)ship_1.missiles.get(i).pos_y, 0, 0);
		}
		for (int i = 0; i < ship_2.missiles.size(); i++) {
			bg.setColor(ship_2.missiles.get(i).body_color);
			bg.drawRect((int)ship_2.missiles.get(i).pos_x, (int)ship_2.missiles.get(i).pos_y, 0, 0);
		}
	}
	
	public static void render_attractor (Graphics2D bg) {
		
		//renders attractor with blazar effect
		bg.setColor (center.get_color ());
		
		int [] a; //contains positions of arm final
		int x = (int) center.get_pos('x');
		int y = (int) center.get_pos('y');

		a = center.get_pulse();
		
		bg.drawLine (x, y, x + a [0], y - a [1]); //renders attractor with blazar effect
		bg.drawLine (x, y, x - a [0], y + a [1]);
		bg.drawLine (x, y, x - a [1], y - a [0]);
		bg.drawLine (x, y, x + a [1], y + a [0]);

		
	}

	public static void update (Input keyboard) {
		
		ship_1.fire_projectile(keyboard);
		ship_1.calculate_pos(keyboard, center, WIDTH, HEIGHT); //calculates new position of ship
		
		//ship_2.calculate_pos(keyboard, center, WIDTH, HEIGHT);
		
	}
}
