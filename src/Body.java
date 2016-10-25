//properties of all drawable bodies within the game
import java.awt.*;

public class Body {
	
	//position related values
		protected double pos_x; //position
		protected double pos_y;
					
		//state values
		protected boolean visible; //is body visible
		protected double mass; //mass of the body
		
		protected Color body_color;
		
		Body (double x, double y, double m, Color c, boolean vis_b) {
			this.pos_x = x;
			this.pos_y = y;
			this.mass = m;
			this.body_color = c;
			this.visible = vis_b;
		}
		
		//functions which set values
		public void set_pos (char d, double p) { //sets position
			
			if (d == 'X' || d == 'x') {
				pos_x = p;
			} else if (d == 'Y' || d == 'y') {
				pos_y = p;
			} else {
				pos_x = 0;
				pos_y = 0;
			}
			
		}
		
		public void set_visible (boolean b) {
			visible = b;
		}
			
		public void set_mass (double m) {
			mass = m;
		}
		
		public void set_color (Color c) {
			this.body_color = c;
		}
		
		//functions which return the state of values
		public boolean is_visible () { 
			return visible;
		}
		
		public double get_mass () {
			return mass;
		}
		
		public double get_pos (char d) {
			
			if (d == 'X' || d == 'x') {
				return pos_x;
			} else if (d == 'Y' || d == 'y') {
				return pos_y;
			} else {
				return 0.0;
			}
			
		}

		public Color get_color () {
			return this.body_color;
		}
}
