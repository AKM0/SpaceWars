//background stars
import java.util.*;

public class Star {
	
	//these are all used eventually
	private double star_x;
	private double star_y;
	private double star_rad;
	
	public Star (int screen_width, int screen_height) {
		
		Random rand = new Random ();
		double dist = 0; //distribution, used to determine radius of star
	
		this.star_x = rand.nextInt (screen_width) + 1; //randomly generates position of stars
		this.star_y = rand.nextInt (screen_height) + 1; 

		dist = rand.nextDouble(); //random number 
		
		if (dist <= 0.76) { //24% of stars radius = 2, rest stars radius = 1
			this.star_rad = 1.0;
		} else if (dist > 0.76) {
			this.star_rad = 2.0;
		} else {
			this.star_rad = 1.0;
		}
		
	}
	
	public double get_radius () {
		return (int) star_rad;
	}
	
	public double get_pos (char d) {
		
		if (d == 'X' || d == 'x') {
			return star_x;
		} else if (d == 'Y' || d == 'y') {
			return star_y;
		} else {
			return 0.0;
		}
		
	}

}
