package SpringPackage;

import org.opensourcephysics.controls.*;
import java.awt.Color;
import java.util.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.*;
import java.lang.Math;

/**
 *  @author Philip Clark
 * This is the Spring_Oscillator app.  It is the second part of the project's two sections.  Using spring physics and the driving sin function, it models simple and damped harmonic oscillation.
 * @method doStep the doStep method runs every 1/10 of a second and is the central function of the program.  The drive function sets of the first wave of x and y accelerations.  From there,
 * the doStep calculates each of the results left and right x and y accs from each mini spring and sets the velocities and positions accordingly.  Depending on the period, amplitude, and whether the final mass is fixed or not, the resulting wave can take many varing forms.
 * @method reset resets the initial values of the particle and the frame states
	 * @method initialize Reads in the "x" and "y" value from the Control Panel and sets them as the x and y coordinates, velocities, accelerations, etc of the spring
	 * Makes sure the frame is visible, and adds the particle to the DisplayFrame 
 * BONUSES: 1. Has the oscillator move in 2D given a inputed angle theta
 * 2. Models the different levels of instrument string oscillation.
 */
public class Spring_Oscillator extends AbstractSimulation 
{
	PlotFrame frame = new PlotFrame("x", "y", "Frame");//Plotframe 
	ArrayList <Particle> springs = new ArrayList<Particle>(); //Array List
	double time = 0; //keeps track of elapsed time
	
	/**
	 * the doStep method runs every 1/10 of a second and is the central function of the program.  The drive function sets of the first wave of x and y accelerations.  From there,
 * the doStep calculates each of the results left and right x and y accs from each mini spring and sets the velocities and positions accordingly.  Depending on the period, amplitude, and whether the final mass is fixed or not, the resulting wave can take many varing forms.
	 **/
	protected void doStep() //Dostep method
	{			
		for (int j = 0; j < 70; j++) //Speeds up do-step
		{
			double k = control.getDouble("Spring constant");
			double L = control.getDouble("Initial length") * springs.size();
			double A = control.getDouble("Amplitude");//Takes in user mag
			double T = control.getDouble("T");//Takes in user period
			int m = control.getInt("Mass Oscillator");
			double timestep = control.getDouble("timestep");//Takes timestep
			springs.get(m).setY(A * Math.sin(Math.toRadians(control.getDouble("theta"))) * Math.sin((2*Math.PI/T)*time));//Sets starting y position
			springs.get(m).setX(A* Math.cos(Math.toRadians(control.getDouble("theta"))) * Math.sin((2*Math.PI/T)*time)); //sets starting x position
			//A is the amplitude which vertically stretches the sin function (wt) where w = 2pi/T, T = the period in seconds, and t = time
			time += timestep;//Increments time counter by timestep each timestep

			for (int i = 1; i < springs.size()-1; i++) 
			{
				double dx_left = (Particle.getDist(springs.get(i), springs.get(i+1))-control.getDouble("Rest Length")); //calculates distance between the two adjacent particles (hypo of triangle)
				double force_left = k * dx_left;
				double sin_left =  (springs.get(i+1).getY()-springs.get(i).getY())/Particle.getDist(springs.get(i+1),springs.get(i)); //opposite/hypo to get sin of the angle
				double cos_left =  (springs.get(i+1).getX()-springs.get(i).getX())/Particle.getDist(springs.get(i+1),springs.get(i)); //adjacent/hypo to get cos of the angle
				double r_xAcc = ((force_left*cos_left)/control.getDouble("mass")); //calculate x acc
				springs.get(i).set_xacc(r_xAcc); //set x acc
				double r_yAcc = (force_left*sin_left)/control.getDouble("mass"); //calculate y acc		
				springs.get(i).set_yacc(r_yAcc); //set y acc

				//the next 9 lines are the same as the 9 above except for the particle to the right of the current one
				double dx_right = (Particle.getDist(springs.get(i-1), springs.get(i))) -control.getDouble("Rest Length");
				double force_right = k * dx_right;
				double sin_right =  (springs.get(i-1).getY()-springs.get(i).getY())/dx_right;
				double cos_right =  (springs.get(i-1).getX()-springs.get(i).getX())/dx_right;
				double xAcc = springs.get(i).get_xacc()+(force_right*cos_right)/control.getDouble("mass");
				springs.get(i).set_xacc(xAcc);
				double yAcc = springs.get(i).get_yacc()+((force_right*sin_right)/control.getDouble("mass"));
				springs.get(i).set_yacc(yAcc);
			}
			//if the end is not fixed, allows the end to move up and down
			if(control.getBoolean("Fix Final Mass") == false)
			{
				int last = springs.size()-1; //creates index number for the last spring
				double deltax_end = (Particle.getDist(springs.get(last), springs.get(last-1))-control.getDouble("Rest Length"));
				double force_end = k * deltax_end; //calculates the left force on the particle
				double sin_end = ((springs.get(last-1).getY()-springs.get(last).getY())/Particle.getDist(springs.get(last), springs.get(last-1))); //calculates the y acc of the particle via sin o/h
				springs.get(last).set_yacc((force_end*sin_end)/control.getDouble("mass")); //sets y acc
			}
	
			//the next 6 lines set the velocities and positions of all of the particles as per their x and y accelerations
			for (int s = 0; s < springs.size(); s++) 
			{
				springs.get(s).set_xvel(springs.get(s).get_xvel()+(springs.get(s).get_xacc()*timestep));
				springs.get(s).set_yvel(springs.get(s).get_yvel()+(springs.get(s).get_yacc()*timestep));
				springs.get(s).setX(springs.get(s).getX()+(springs.get(s).get_xvel()*timestep));
				springs.get(s).setY(springs.get(s).getY()+(springs.get(s).get_yvel()*timestep));
			}
		}
	}
	
	/**
	 * resets and changes all starting values as per the user's discretion
	 **/
	public void reset()
	{	
		control.setValue("timestep", .01); //sets the time incrementing step (lower for accuracy)
		control.setValue("Fix End at X = ", 0); //the point at which the string is fixed
		control.setValue("Rest Length", 0); //rest length value
		control.setValue("Initial length", 3); //value of initial length of each spring (ie spacing)
		control.setValue("Spring constant", 40); //k value
		control.setValue("Number of springs", 50); //number of springs
		control.setValue("mass", 10); //value of mass of each spring
		control.setValue("Amplitude", 5); //amplitude of the driver function
		control.setValue("T", 50); //period of the sin driver function
		control.setValue("theta", 90); //angle for 2D harmonic oscillation
		control.setValue("beta", 0); //value for air resistance (proportional to v^2)
		control.setValue("Fix Final Mass", true); //boolean to have loose or fixed end
		control.setValue("Mass Oscillator", 0);
		control.setValue("Fixed Mass Number", control.getInt("Number of springs")-1);
		frame.clearDrawables(); 
		springs.clear();
		springs.removeAll(springs);		
	}	
	
	/** 
	 * Initializes all the necessary starting values and sets the springs initial position depending of the spring.
	 **/
	public void initialize() 
	{
		double xint; //the initial x position of the first spring
		frame.setVisible(true);
		for (int i = 0; i < control.getInt("Number of springs"); i++) //for loop to go through all of the springs and set their positions
		{
			springs.add(i, new Particle()); //creates new spring
			xint = control.getDouble("Fix End at X = ") + (i * control.getDouble("Initial length")); //places the spring in the right place
			springs.get(i).setXY(xint, 0); //sets xy of spring
			springs.get(i).setMass(control.getDouble("mass")); //sets mass of each spring
			frame.addDrawable(springs.get(i));//adds spring to the screen
			springs.get(i).setAlpha(control.getDouble("beta")); //sets the air resistance constant
		}
		frame.setPreferredMinMax(control.getDouble("Fix End at X = ") - 20, springs.get(springs.size()-1).getX()+20, -40, 40);
	}
	
	/** The main runs the Spring_Oscillator app
	 */
	public static void main(String[] args) 
	{
		SimulationControl.createApp(new Spring_Oscillator());	
	}

}