//class for the properties of a ship
import java.awt.*;
import java.util.*;

public class Ship extends Body {

	//position related values
	private double vel_x; //velocity/speed
	private double vel_y;
	private double acc_x; //acceleration
	private double acc_y;
	private boolean alive; //has ship been destroyed?
	private double heading; //direction of the front of the ship due to rotation, in angle from 0PI
	private double DEFAULT_ACCELERATION = 0.0045; //default acceleration
	private final double DEFAULT_VELOCITY = 50; //default acceleration
	private final double MAX_VEL = 10; //ship should not travel faster than max vel
	public ArrayList <Projectile> missiles = new ArrayList<Projectile>();
	
	public Ship (double x, double y, double m, Color c, boolean vis_b) {
		
		super (x, y, m, c, vis_b);
		this.alive = true;
		this.vel_x = DEFAULT_VELOCITY;
		this.vel_y = DEFAULT_VELOCITY;
		this.acc_x = DEFAULT_ACCELERATION;
		this.acc_y = DEFAULT_ACCELERATION;
		this.heading = 0;
		
	}
	
	public Ship (double x, double y, double vx, double yx, double m, Color c, boolean vis_b) {
		
		super (x, y, m, c, vis_b);
		this.alive = true;
		this.vel_x = vx;
		this.vel_y = yx;
		this.acc_x = DEFAULT_ACCELERATION;
		this.acc_y = DEFAULT_ACCELERATION;
		this.body_color = c;
		this.heading = 0;
		
	}
	
	public Ship (double x, double y, double vx, double vy, double ax, double ay, double h, double m, Color c, boolean vis_b) {
		
		super (x, y, m, c, vis_b);
		this.alive = true;
		this.vel_x = vx;
		this.vel_y = vy;
		this.acc_x = ax;
		this.acc_y = ay;
		this.DEFAULT_ACCELERATION = ax;
		this.body_color = c;
		this.heading = h;
		
	}
	
	public void set_vel (char d, double v) { //sets velocity
		
		if (d == 'X' || d == 'x') {
			vel_x = v;
		} else if (d == 'Y' || d == 'y') {
			vel_y = v;
		} else if (d == 'D' || d == 'd') {
			vel_x = DEFAULT_VELOCITY;
			vel_y = DEFAULT_VELOCITY;
		}
		
	}
	
	public void set_acc (char d, double a) { //sets acceleration
		
		if (d == 'X' || d == 'x') {
			acc_x = a;
		} else if (d == 'Y' || d == 'y') {
			acc_y = a;
		} else if (d == 'D' || d == 'd') {
			acc_x = DEFAULT_ACCELERATION;
			acc_y = DEFAULT_ACCELERATION;
		}
		
	}
	
	public void set_alive (boolean b) {
		alive = b;
	}
	
	public void set_heading (double h) {
		heading = h;
	}
	
	public void set_color (Color c) {
		this.body_color = c;
	}
	
	//functions which return the state of values
	public boolean is_alive () {
		return alive;
	}
	
	public double get_heading () {
		return heading;
	}
	
	public double get_vel (char d) {
		
		if (d == 'X' || d == 'x') {
			return vel_x;
		} else if (d == 'Y' || d == 'y') {
			return vel_y;
		} else {
			return 0.0;
		}
		
	}
	
	public double get_acc (char d) {
		
		if (d == 'X' || d == 'x') {
			return acc_x;
		} else if (d == 'Y' || d == 'y') {
			return acc_y;
		} else {
			return 0.0;
		}
		
	}
	
	public double get_dft_acc () {
		return DEFAULT_ACCELERATION;
	}
	
	public Color get_color () {
		return this.body_color;
	}

	public void calculate_pos (Input keyboard, Attractor attr, int screen_width, int screen_height) {
	
		double gamma = Math.abs (Math.pow (1.0 - ((Math.pow (this.vel_x, 2.0) + Math.pow (this.vel_y, 2.0))/Math.pow (MAX_VEL, 2.0)), 2.0)); //ship accelerates less as it approaches max speed
		
		
		//changes velocity
//		if (keyboard.move_right) {
//			this.set_vel('x', get_vel ('x') + this.acc_x);
//		} else if (keyboard.move_left) {
//			this.set_vel('x', get_vel ('x') + this.acc_x);
//		} else if (keyboard.move_down) {
//			this.set_vel('y', get_vel ('y') + this.acc_y);
//		} else if (keyboard.move_up) {
//			this.set_vel('y', get_vel ('y') + this.acc_y);
//		} else {
//			this.set_vel('x', get_vel ('x') + gamma * attr.get_acc_to_attractor ('x', this));
//			this.set_vel('y', get_vel ('y') + gamma * attr.get_acc_to_attractor ('y', this));
//		}
		
		if (keyboard.move_right || keyboard.move_left) { //sets velocity
			
			if (keyboard.move_right) {
				this.acc_x = gamma * (this.get_dft_acc () + attr.get_acc_to_attractor ('x', this));
			} else if (keyboard.move_left) {
				this.acc_x = gamma *  ((-1 *this.get_dft_acc ()) + attr.get_acc_to_attractor ('x', this));
			} 
			
			this.acc_y = gamma * attr.get_acc_to_attractor ('y', this);
			this.set_vel ('y', this.get_vel ('y') + acc_y);
			this.set_vel('x', this.get_vel ('x') + acc_x); 
			
		} else if  (keyboard.move_up || keyboard.move_down) {
			
			if (keyboard.move_up) {
				this.acc_y = gamma * ((-1 * this.get_dft_acc ()) + attr.get_acc_to_attractor ('y', this));
			} else if (keyboard.move_down) {
				this.acc_y = gamma * (this.get_dft_acc () + attr.get_acc_to_attractor ('y', this));
			} 
			
			this.acc_x = gamma * attr.get_acc_to_attractor ('x', this);
			this.set_vel ('x', this.get_vel ('x') + acc_x);
			this.set_vel ('y', this.get_vel ('y') + acc_y);
			
		} else {
			this.acc_y = gamma * attr.get_acc_to_attractor ('y', this);
			this.acc_x = gamma * attr.get_acc_to_attractor ('x', this);
			this.set_vel ('x', this.get_vel ('x') + acc_x); 
			this.set_vel ('y', this.get_vel ('y') + acc_y);
		}
		
		//ensures that ship cannot go off screen forever
		if (this.get_pos('x') >= screen_width) {
			this.set_pos('x', 0);
		} else if (this.get_pos('x') <= 0) {
			this.set_pos('x', screen_width);
		} else if (this.get_pos('y') >= screen_height) {
			this.set_pos('y', 0);
		} else if (this.get_pos('y') <= 0) {
			this.set_pos('y', screen_height);
		} 
		
		this.set_pos('x', get_pos ('x') + get_vel ('x'));
		this.set_pos('y', get_pos ('y') + get_vel ('y')); //'negative' (left, backwards) movement automatically handled
		

	} 

	public void fire_projectile (Input keyboard) {
		if (keyboard.fire) {
			Projectile missile = new Projectile (this.pos_x, this.pos_y, this.vel_x, this.vel_y, 1.0, this.body_color, true);
			missiles.add(missile);
		}
	}
	
	public void manage_missiles () {
		ArrayList <Integer> r = new ArrayList<Integer> ();
		for (int k = 0; k < missiles.size(); k++) {
			if (missiles.get(k).visible == false) {
				r.add(k);
			}
			missiles.get(k).step();
		}
		for (int i = 0; i < r.size(); i++) {
			missiles.remove(r.get(i));
		}
	}

}
