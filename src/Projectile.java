//what the Ships shoot
import java.awt.*;

public class Projectile extends Body {

	
	private double spawn_x;
	private double spawn_y;
	private double vel_x;
	private double vel_y;
	private double age; //currently traveled distance
	private final double MAX_AGE = 3000; //distance missile can travel before dying
	private final double VEL_MULTIPLIER = 3.0; //how much faster the projectile is compared to the ship
	
	
	Projectile (double x, double y, double vx, double vy, double m, Color c, boolean vis_b) {
		
		super (x, y, m, c, vis_b);
		this.spawn_x = x;
		this.spawn_y = y;
		this.vel_x = vx * VEL_MULTIPLIER;
		this.vel_y = vy * VEL_MULTIPLIER;
		
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
	
 	private void age () {
 		
 		if (this.get_pos('x') >= Main.WIDTH) {
			spawn_x = 0;
		} else if (this.get_pos('x') <= 0) {
			spawn_x = Main.WIDTH;
		} else if (this.get_pos('y') >= Main.HEIGHT) {
			spawn_y = 0;
		} else if (this.get_pos('y') <= 0) {
			spawn_y = Main.HEIGHT;
		}
 		
 		age = age + Math.pow(Math.pow(spawn_x - pos_x, 2.0) + Math.pow(spawn_y - pos_y, 2.0), 0.5);

 	}
 	
 	public void step () {
 		age ();
 		pos_x = pos_x + vel_x;
 		pos_y = pos_y + vel_y;
 		if (age >= MAX_AGE) {
 			visible = false;
 		}
 	}
 	
 	
	public void set_vel (char d, double v) { //sets velocity
		
		if (d == 'X' || d == 'x') {
			vel_x = v;
		} else if (d == 'Y' || d == 'y') {
			vel_y = v;
		} else if (d == 'D' || d == 'd') {
			vel_x = 0.0;
			vel_y = 0.0;
		}
		
	}

}
