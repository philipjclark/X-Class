package SpringPackage;

import java.awt.Color;
import java.util.ArrayList;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.PlotFrame;

/**
 * This is the bungee app.  It is the first of the two main parts of this project: the bungee and the spring oscillator.  It creates a dynamic array list of particles (whose size is specified by the user)
 * and drops the bungee with a mass attached at the bottom.  Spring physics (a kx up and a kx + mg down) is then used to calculate the x and y accelerations of the spring.  The x and y velocities and positions are then set accordingly.
 * The other major quantitative part of the app is the manner in which it uses a sorting algorithim to calculate the lowest point reached by the bungee.
 * @author Philip Clark 
 *
 * @method The doStep contains the body of the particle simulations program, running every 1/10 of a second.
 * In it, the each of the spring's force up and down is calculated and the y acceleration, x acceleration are set accordingly, allowing the x and y velocities to
 * be set, in turn allowing the x and y positions to be set.  The do step then adds the necessary points to 
 * the various graphs, adds a trial to mark the particles path, and increments times based on a specified delta.  It also calculates the bungee's lowest point.
 * @method reset resets the initial values of the particle and the frame states
 * @method initialize Reads in the "x" and "y" value from the Control Panel and sets them as the x and y coordinates, velocities, accelerations, etc of the spring
 * Makes sure the frame is visible, and adds the particle to the DisplayFrame 
 * BONUSES: 2-D Option: by breaking up the forces acting on each particle into x and y components via more complicated physics and coding, the bungee has the option of moving now in 2 dimensions
 * Slinky Fall: The Slinky Fall options can be used in two ways: in conjunction with the 1-D bungee drop and the separate control panel option.  By simply setting the control panel boolena "slinky fall" to true, one can watch the graphic
 * model of a slinky in free fall.  If one also uses the 1-D bungee drop and sets the angle theta to 90, one can watch the bungee be in free fall until it reaches the point at which the kx force begins to pull upwards.
 */

public class Spring_2D_Bungee extends AbstractSimulation
{
	static PlotFrame frame = new PlotFrame("x", "y", "Frame");
	static PlotFrame position = new PlotFrame("time", "y-position", "Y Position Graph");
	static PlotFrame x_position = new PlotFrame("time", "y-position", "X Position Graph");
	ArrayList <Particle> masses = new ArrayList<Particle>(); //array list of springs
	static int x1 = 0; //x position to set frame location on screen
	static int y1 = 30; //y position to set frame location on screen
	double time_counter = 0; //keeps track of time
	boolean bottom = true; //boolean variable to find lowest point of bungee
	double lowest_point; //double variable to find lowest point of bungee

	/**  The doStep contains the body of the particle simulations program, running every 1/10 of a second.
	 * In it, the each of the spring's force up and down is calculated and the y acceleration, x acceleration are set accordingly, allowing the x and y velocities to
	 * be set, in turn allowing the x and y positions to be set.  The do step then adds the necessary points to 
	 * the various graphs, adds a trial to mark the particles path, and increments times based on a specified delta.  It also calculates the bungee's lowest point.
	 **/
	protected void doStep() {
		for (int w = 0; w < 40; w++) { //speeds up do step
			double g = control.getDouble("g"); //sets acceleration of gravity to a variable
			double timestep = control.getDouble("timestep"); //sets the value of the timestep to a variable
			time_counter += timestep; //increases timestep
			double k = control.getDouble("Spring constant"); //sets spring constant variable

			if(control.getBoolean("slinky fall")==true) //runs if the slinkly fall optionis picked by user
			{
				double delta_below = Particle.getDist(masses.get(0), masses.get(1)); //lets to the top mass not be fixed so the entire slinky can free fall
				double deltaXbot = masses.get(0).getX() - masses.get(1).getX();
				double deltaYbot = masses.get(0).getY() - masses.get(1).getY();
				double xCompBelow = deltaXbot - control.getDouble("Rest Length")*deltaXbot/delta_below; //multiplies the cos and hyp. of the change in the particles position to get its x component
				double yCompBelow = deltaYbot - control.getDouble("Rest Length")*deltaYbot/delta_below; //multiplies the sin and hyp. of the change in the particles position to get its y component

				double xSum = (xCompBelow)*k; //uses F = kx formula to get the sum of the force sin the x direction
				double xAcc = xSum/masses.get(0).getMass(); //gets value for the x acc
				double ySum = -(yCompBelow)*k + masses.get(0).getMass()*g;  //uses F = kx formula and adds the weight mg to get the sum of the force sin the y direction
				double yAcc = ySum/masses.get(0).getMass();  //gets value for the y acc
				masses.get(0).set_xacc(xAcc);  //sets the x acc
				masses.get(0).set_yacc(yAcc);  //sets the y acc

			}
			for (int i = 1; i < masses.size()-1; i++) //for loop for all of the masses that are not the first or last (ie have a spring above them and below them)
			{
				double delta_above = Particle.getDist(masses.get(i), masses.get(i-1)); 
				double delta_below = Particle.getDist(masses.get(i), masses.get(i+1));

				double deltaXtop = masses.get(i-1).getX() - masses.get(i).getX(); 
				if(Math.abs(deltaXtop) < .0000000001)
				{
					deltaXtop = 0;
				}
				double deltaYtop = masses.get(i-1).getY() - masses.get(i).getY();
				double xCompAbove = deltaXtop - control.getDouble("Rest Length")*deltaXtop/delta_above; 
				double yCompAbove = deltaYtop - control.getDouble("Rest Length")*deltaYtop/delta_above; 

				double deltaYbot = masses.get(i).getY() - masses.get(i+1).getY();
				double deltaXbot = masses.get(i).getX() - masses.get(i+1).getX();
				double xCompBelow = deltaXbot - control.getDouble("Rest Length")*deltaXbot/delta_below;
				double yCompBelow = deltaYbot - control.getDouble("Rest Length")*deltaYbot/delta_below;

				double xSum = (xCompAbove - xCompBelow)*k; 
				double xAcc = xSum/control.getDouble("Mass"); 
				double ySum = (yCompAbove - yCompBelow)*k + control.getDouble("Mass")*g; 
				double yAcc = ySum/control.getDouble("Mass");
				masses.get(i).set_xacc(xAcc);
				masses.get(i).set_yacc(yAcc);
			}

			int last = masses.size()-1; //last is the index value for the last mass in the spring
			//the next 10 lines serve the same purpose as the for loop above but only use a spring in the up direction since it is the last mass
			double delta_above = Particle.getDist(masses.get(last-1),masses.get(last))-control.getDouble("Rest Length");
			double deltaXtop = masses.get(last-1).getX() - masses.get(last).getX(); 
			if(Math.abs(deltaXtop) < .0000000001)
			{
				deltaXtop = 0;
			}
			double deltaYtop = masses.get(last-1).getY() - masses.get(last).getY();
			double xCompAbove = deltaXtop - control.getDouble("Rest Length")*deltaYtop/delta_above; 
			double yCompAbove = deltaYtop - control.getDouble("Rest Length")*deltaXtop/delta_above; 
			double xSum = (xCompAbove)*k; 
			double xAcc = xSum/control.getDouble("Mass of weight"); 
			double ySum = (yCompAbove)*k + control.getDouble("Mass of weight")*g; ; 
			double yAcc = ySum/control.getDouble("Mass of weight");
			masses.get(last).set_xacc(xAcc);
			masses.get(last).set_yacc(yAcc);

			//gets velocities and positions based on accelerations
			for (int m = 0; m < masses.size(); m++) 
			{
				masses.get(m).set_yvel(masses.get(m).get_yvel()+(masses.get(m).get_yacc()*timestep)); //riemann for y vek
				masses.get(m).setY(masses.get(m).getY()+(masses.get(m).get_yvel()*timestep)); //riemann for x vel
				masses.get(m).set_xvel(masses.get(m).get_xvel()+(masses.get(m).get_xacc()*timestep)); //riemann for y pos 
				masses.get(m).setX(masses.get(m).getX()+(masses.get(m).get_xvel()*timestep)); //riemann for x pos
			}
			
			//finds the lowest point the bungee reaches
			if(lowest_point > masses.get(last).getY() && bottom == true && control.getBoolean("slinky fall") == false) {
				lowest_point = masses.get(last).getY(); //part of the sorting algorithim to find the lowest point
				System.out.println("The lowest point reached is " + lowest_point + " meters above ground."); //prints that lowest point
			}
			for (int i = 0; i < masses.size(); i++)
			{
				if(masses.get(i).getY()<=0 && control.getBoolean("slinky fall") == true) //makes sure slinky does not fall past the ground
				{
					masses.get(i).setY(0);
					masses.get(i).setX(0);
				}
				if(control.getBoolean("slinky fall") == true) //makes sure slinky fall stays in 1-D
				{
					masses.get(i).setX(0);
				}
				if(Math.abs(masses.get(i).getX()) < .00000001)
				{
					masses.get(i).setX(0); //keeps slinky in x position
				}
			}
			
				
			x_position.append(1, time_counter, masses.get(last).getX()); //apends point to x time graph
			position.append(1, time_counter, masses.get(last).getY()); //appends point to y time graph
		}
	}

	/**
	 * initialize reads in the "x" and "y" value from the Control Panel and sets them as the x and y coordinates, velocities, accelerations, etc of the spring
	 * It also places the spring/bungee according the necessary 2-D or 1-D x and y coordinates
	 **/
	public void initialize() 
	{
		//set graphs to autoscale function
		frame.setVisible(true);
		frame.setPreferredMinMax(-control.getInt("Number of masses"), control.getInt("Number of masses"), 0, control.getDouble("y_top")+10);
		position.setVisible(true);
		position.setAutoscaleY(true); //autoscale frames
		position.setAutoscaleX(true);
		x_position.setVisible(true);
		x_position.setAutoscaleY(true);
		x_position.setAutoscaleX(true);
		
		lowest_point = control.getDouble("y_top");
		double height_last = control.getDouble("y_top"); //keeps track of the last height of the placed particle
		double first = 0; //sees whether the spring being placed is the first one
		double x_tally = -Math.cos(Math.toRadians(control.getDouble("theta"))); //keeps track of past x sum
		double y_tally = (control.getDouble("y_top")) - Math.sin(Math.toRadians(control.getDouble("theta")))*control.getDouble("Initial length"); //keeps track of past y sum
		for (int i = 0; i < control.getInt("Number of masses")+1; i++) 
		{
			if(i>0)
			{
				height_last = masses.get(i-1).getY(); //gets last height
			}
			masses.add(i, new Particle());
			if(i<control.getInt("Number of masses"))
			{
				masses.get(i).setMass(control.getDouble("Mass")); //sets mass of the ith mass
				masses.get(i).setXY(x_tally + Math.cos(Math.toRadians(control.getDouble("theta")))*control.getDouble("Initial length"), y_tally + Math.sin(Math.toRadians(control.getDouble("theta")))*control.getDouble("Initial length")); //sets positon of particle
				x_tally += Math.cos(Math.toRadians(control.getDouble("theta")))*control.getDouble("Initial length"); //adds the new x position of the i mass to the x tally of positions 
				y_tally += Math.sin(Math.toRadians(control.getDouble("theta")))*control.getDouble("Initial length"); //adds the new y position of the i mass to the y tally of positions 
				//	masses.get(i).setXY(k, height_last+.5*((first*left*masses.get(i).getMass()*control.getDouble("g"))/control.getDouble("Spring constant")));
			//	left --;
				masses.get(i).pixRadius = 5;
			}

			first = 1;
			if(i==control.getInt("Number of masses")) //runs through all the masses
			{
				masses.get(i).setMass(control.getDouble("Mass of weight")); //sets mass of weight
				masses.get(i).pixRadius = 8;
				masses.get(i).setXY(x_tally + Math.cos(Math.toRadians(control.getDouble("theta")))*control.getDouble("Initial length"), y_tally + Math.sin(Math.toRadians(control.getDouble("theta")))*control.getDouble("Initial length"));
			}
			masses.get(i).setAlpha(control.getDouble("alpha")); //sets air resistance constant

			frame.addDrawable(masses.get(i)); //adds new mass to the frame

		}
		if(control.getBoolean("slinky fall") == true) //checks if slinky fall is true
		{
			frame.removeDrawable(masses.get(masses.size()-1)); //gets rid of weight if the slinky is placed in free fall
			position.setVisible(false);
			x_position.setVisible(false);
		}
		else
		{
			double x = 0; //counter
			for (int i = 0; i < control.getInt("Number of masses"); i++) {
				x += (((control.getInt("Number of masses")-i)*control.getDouble("Mass") + control.getDouble("Mass of weight"))*control.getDouble("g"))/control.getDouble("Spring constant"); //finds equilibrium
			}
			masses.get(masses.size()-1).color = Color.black;
			System.out.println("The spring's bottom point y at equilibrium is " + (control.getDouble("y_top")+x) + " meters above ground."); //prints equilibrium
		}
	

	}

	public void reset()//Reset values
	{	
		time_counter = 0;
		control.setValue("timestep", .005); //sets timestep value
		control.setValue("y_top", 30); //y value for the topmost spring
		control.setValue("Rest Length", 0); //rest length of the slinky (easiest to just leave at 0)
		control.setValue("Initial length", .2); //spacing of springs
		control.setValue("Spring constant", 40); //k value
		control.setValue("Number of masses", 20); //number of springs
		control.setValue("Mass of weight", 1); //mass of weight at the bottom of the bungee
		control.setValue("Mass", .1); //mass of each spring
		control.setValue("slinky fall", false); //boolean to put slinky in free fall
		control.setValue("g", -9.81); //value for gravity acc
		control.setValue("alpha", 0); //value for air resistance
		control.setValue("theta", -90); //angle at which the initial position of the bungee is set
		frame.clearDrawables();
		masses.clear();
		masses.removeAll(masses);
	}	

	public static void main(String[] args) 
	{
		frame.setLocation(x1, y1); //sets the location of the window to the upper-left hand corner
		position.setLocation(x1+360, y1);
		x_position.setLocation(x1+720, y1);

		SimulationControl.createApp(new Spring_2D_Bungee());
	}
}
