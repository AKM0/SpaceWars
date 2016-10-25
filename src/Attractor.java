//gravitational bodies
import java.awt.*;
import java.util.Random;

public class Attractor extends Body {
	
	private final int PROB_OF_PULSE = 300; //sometimes the blazar's effects may be pronounced, chance is 1/PROP_OF_PULSE 
	private final int PULSE_EFFECT = 10; //size of effect
	private final double K = 0.05;
	private final double C = 0.85;
	private double radius;
	
	public Attractor (double x, double y, double m, double r, Color c, boolean vis_b) {
		super (x, y, m, c, vis_b);
		this.radius = r;
	}

	public void set_radius (double r) {
		radius = r;
	}

	public double get_radius () {
		return radius;
	}
	
	public int [] get_pulse () { //values for arms of attractor, done in polar
		
		Random rand = new Random ();
		int [] arm_pos = new int [2];
		double r; //radius of center
		double theta; //angle of 'arms' to 0/2PI
		
		if ((rand.nextInt (PROB_OF_PULSE) + 1) == 1) { 
			r = this.radius + PULSE_EFFECT;
		} else {
			r = (this.radius * (rand.nextDouble ())); //will cause arms to fluctuate in length
		}
		
		theta = 2.0 * Math.PI * rand.nextDouble (); 
		arm_pos [0] = (int) (r * Math.cos (theta));
		arm_pos [1] = (int) (r * Math.sin (theta));
		
		return arm_pos;
		
	}
	
	//calculates acceleration on ship to attractor depending on distance
	//calculates acceleration on ship to attractor depending on distance
	public double get_acc_to_attractor (char d, Body ship) {
			
		double acc_to_attractor;
		double a;
		double b;
		double raw; //raw acceleration value from formula
		double distance; //distance from attractor to ship
		double theta; //angle from x axis through the attractor to position of the ship
		double sign_of_pos;
		
		a = Math.abs (ship.get_pos('x') - this.get_pos('x'));
		b = Math.abs (ship.get_pos('y') - this.get_pos('y'));
		distance = Math.pow(Math.pow (a, 2) + Math.pow (b, 2), 0.5); //true distance from attractor to ship
		theta = Math.atan2(b, a);
		
		//raw = K * (this.mass/(Math.pow (distance * C, 2)));
		raw = 0.1;
		
		if (d == 'X' || d == 'x') { //acceleration in the x direction
			
			sign_of_pos = ship.get_pos('x') - this.get_pos('x');
			
			if (sign_of_pos < 0) {	
				acc_to_attractor = Math.cos (theta) * raw;
			} else {
				acc_to_attractor = (-1 * Math.cos (theta) * raw);
			}
			
		} else if (d == 'Y' || d == 'y') { //acceleration in the y direction
			
			sign_of_pos = ship.get_pos('y') - this.get_pos('y');
			
			if (sign_of_pos < 0) {	
				acc_to_attractor =  Math.sin (theta) * raw;
			} else {
				acc_to_attractor = (-1 * Math.sin (theta) * raw);
			}
			
		} else {
			acc_to_attractor = 0.0;
		}

		return acc_to_attractor;
	}
	
}
