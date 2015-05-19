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
 * This is the Spring_Oscillator2 app.  It is the bonus part of the second of the project's two sections.  Using spring physics and the driving sin function, it models simple and damped harmonic oscillation.
 * This bonus class models the vibrations of a string instrument string. The length of the string (.332 meters) is the average length of a violin string. The simulation involves a main oscillating driver point (the bow) and a bow length (how long the oscillator drives the string).
 * Additionally, it uses a secondary driver mass at a specified note (input takes in the base note key and note being played and calculates the distance on the string) that oscillates below the x axis to model vibrato. 
 * @method Oscillate  The Oscillate method is the core of the do-step. starting from the driver point it sets the x and y accelerations to the left and x and y acceleratiosn to the right as well as the resulting x and y positions.
	 * The method is useful because it allows the string to be essentially broken up into sub-components thus allowing the main driver mass to moved throughout the string and the vibrato point (described above) to drive to a lesser extent as well.
 * @method doStep the doStep method runs every 1/10 of a second and is the central function of the program.  The drive function sets of the first wave of x and y accelerations.  From there,
 * the doStep calculates each of the results left and right x and y accs from each mini spring and sets the velocities and positions accordingly.  Depending on the period, amplitude, and whether the final mass is fixed or not, the resulting wave can take many varing forms.
 * @method reset resets the initial values of the particle and the frame states
	 * @method initialize Reads in the "x" and "y" value from the Control Panel and sets them as the x and y coordinates, velocities, accelerations, etc of the spring
	 * Makes sure the frame is visible, and adds the particle to the DisplayFrame 
 * BONUSES: 1. Has the oscillator move in 2D given a inputed angle theta
 * 2. Models violin string vibration (variable main driver point to choose position of bow, can choose the scale degree (ie fingered note being played) for the fixed mass, and -- the coolest part -- can have the note model an oscillation pattern for vibrato, creating a secondary, smaller oscillation wave)
 */
public class Spring_Oscillator2 extends AbstractSimulation 
{
	PlotFrame frame = new PlotFrame("x", "y", "Frame");//Plotframe 
	ArrayList <Particle> springs = new ArrayList<Particle>(); //Array List
	double time = 0; //keeps track of elapsed time
	double m_xint = 0; //the initial x position of the driver mass point
	double v_int = 0;
	int m; //the mass which acts as the main driver point for the oscillation (ie the bow)
	int kill_vibrato = 1; //if the user sets vibrato usage to false, kills the vibrato driver function
	
	Map<String, Integer> key = new HashMap<String,Integer>();
	
	/**
	 * The Oscillate method is the core of the do-step. starting from the driver point it sets the x and y accelerations to the left and x and y acceleratiosn to the right as well as the resulting x and y positions.
	 * The method is useful because it allows the string to be essentially broken up into sub-components thus allowing the main driver mass to moved throughout the string and the vibrato point (described above) to drive to a lesser extent as well.
	 * @param i the number spring mass being oscillated
	 * @param k the spring constant
	 */
	public void Oscillate (int i, double k)
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
		springs.get(i).set_xacc(xAcc); //sets xacc
		double yAcc = springs.get(i).get_yacc()+((force_right*sin_right)/control.getDouble("mass"));
		springs.get(i).set_yacc(yAcc); //sets yacc
	}
	
	/**
	 * the doStep method runs every 1/10 of a second and is the central function of the program.  The drive function sets of the first wave of x and y accelerations.  From there,
 * the doStep calculates each of the results left and right x and y accs from each mini spring and sets the velocities and positions accordingly.  Depending on the period, amplitude, and whether the final mass is fixed or not, the resulting wave can take many varing forms.
	 **/
	protected void doStep() //Dostep method
	{			
		for (int j = 0; j < 40; j++) //Speeds up do-step
		{
			double k = control.getDouble("Spring constant");
			double A = control.getDouble("Amplitude");//Takes in user mag
			double T = control.getDouble("T");//Takes in user period
			double timestep = control.getDouble("timestep");//Takes timestep
			if(time < control.getDouble("Bow length")) //only uses oscillating driver point (bow) for as long as bow length is set for by the user
			{
			springs.get(m).setY(A * Math.sin(Math.toRadians(control.getDouble("theta"))) * Math.sin((2*Math.PI/T)*time));//Sets starting y position
			springs.get(m).setX(m_xint+A* Math.cos(Math.toRadians(control.getDouble("theta"))) * Math.sin((2*Math.PI/T)*time)); //sets starting x position
			}
			if(time >= control.getDouble("Bow length"))
			{
				springs.get(m).setY(0);
			}
			//this secondary driver function (if use vibrato is set to true) oscillates in a modified form, bringing down the string (ie the pitch) and back up, but never going above the initial y point (ie y = 0)
			//in this way it models the additional oscillation vibrations created when a fingered note is vibrated on the violin
			if(springs.get(control.getInt("Fixed Mass Number")-1).get_yvel() > 0 && time < control.getDouble("Bow length")+10)
			{
				if(control.getBoolean("vertical vibrato") == false)
				{
					springs.get(control.getInt("Fixed Mass Number")).setX(v_int+ control.getDouble("Vibrato Intensity")*Math.sin((2*Math.PI/control.getDouble("Vibrato Period"))*time));//Sets starting y position
					springs.get(control.getInt("Fixed Mass Number")).setY(.1*-control.getDouble("Vibrato Intensity")*Math.sin((2*Math.PI/control.getDouble("Vibrato Period"))*time+4.7)-1*kill_vibrato*control.getDouble("Vibrato Intensity"));//Sets starting y position

				}
				else
				{
					springs.get(control.getInt("Fixed Mass Number")).setY(-control.getDouble("Vibrato Intensity")*Math.sin((2*Math.PI/control.getDouble("Vibrato Period"))*time+4.7)-1*kill_vibrato*control.getDouble("Vibrato Intensity"));//Sets starting y position
				}
			}
			//A is the amplitude which vertically stretches the sin function (wt) where w = 2pi/T, T = the period in seconds, and t = time
			time += timestep;//Increments time counter by timestep each timestep
			for (int i = m-1; i > 0; i--) {
				Oscillate(i, k); //deals with oscillation to the left of the main driver mass (the bow)
			}
			for (int i = m+1; i < control.getDouble("Fixed Mass Number"); i++) 
			{
				Oscillate(i,k); //deals with oscillation to the right of the main driver mass to the fixed point (ie the fingered note)
			}
			
			for (int i = control.getInt("Fixed Mass Number")+1; i < springs.size()-1; i++) {
				Oscillate(i,k); //deals with oscillation to the right of the fixed (fingered) point
			}
			for (int i = control.getInt("Fixed Mass Number")-1; i > 0; i--) {
				Oscillate(i,k); //deals with oscillation to the left of the fixed (fingered) point 
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
		super.setDelayTime(0);
		control.setValue("timestep", .005); //sets the time incrementing step (lower for accuracy)
		control.setValue("Fix End at X = ", 0); //the point at which the string is fixed
		control.setValue("Rest Length", 0); //rest length value
		control.setValue("Spring constant", 1200); //k value
		control.setValue("Number of springs", 1000); //number of springs
		control.setValue("mass", .1); //value of mass of each spring
		control.setValue("Amplitude", .1); //amplitude of the driver function
		control.setValue("T", 17); //period of the sin driver function
		control.setValue("theta", 90); //angle for 2D harmonic oscillation
		control.setValue("beta", 0.01); //value for air resistance (proportional to v^2)
		control.setValue("Fix Final Mass", true); //boolean to have loose or fixed end
		control.setValue("Mass Oscillator", 0);
		control.setValue("Fixed Mass Number", control.getInt("Number of springs")-1);
		control.setValue("Use vibrato", true);
		control.setValue("Vibrato Intensity", .1); //the amplitude of the vibrato driver point
		control.setValue("Vibrato Period", 17/4); //the period (ie the time it takes for one vibrating cycle) of the vibrato driver point
		control.setValue("Scale length", 1); //the length of the violin string
		control.setValue("Base note", "c"); //key (base note) in which the note is being played
		control.setValue("Note", "c"); //note being fingered (played)
		control.setValue("Octave", 0);
		control.setValue("Bow length", 100);
		control.setValue("vertical vibrato", true);
		control.setValue("Initial length", control.getDouble("Scale length")/control.getDouble("Number of springs")); //value of initial length of each spring (ie spacing)
		frame.clearDrawables(); 
		springs.clear();
		springs.removeAll(springs);		
	}	
	
	/** 
	 * Initializes all the necessary starting values and sets the springs initial position depending of the spring.
	 **/
	public void initialize() 
	{
		//maps the half step values of all the notes on the violin
		key.put("a", 1);
		key.put("b-flat", 2);
		key.put("b", 3);
		key.put("c", 4);
		key.put("c-sharp", 5);
		key.put("d", 6);
		key.put("d-sharp", 7);
		key.put("e", 8);
		key.put("f", 9);
		key.put("f-sharp", 10);
		key.put("g", 11);
		key.put("g-sharp", 12);
	
		double scale_degree = 0;
		if(key.get(control.getObject("Note")) > key.get(control.getObject("Base note")))
		scale_degree = key.get(control.getObject("Note")) - key.get(control.getObject("Base note")) + 12*control.getInt("Octave"); //modulates and subtracts to find the scale ddegree based on the note and key signature
		else
		{
			scale_degree = key.get(control.getObject("Note")) + 12 - key.get(control.getObject("Base note")) + 12*control.getInt("Octave"); //same as above
		}
		
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
			springs.get(i).pixRadius = 3;
		}
		double L = control.getDouble("Initial length") * springs.size(); //calculates the length of the string
		double mindist = 1000; //creates a large value for mindist, so that it can be properly used in the later for loop
		int closest_spring = 0; //sets the closest_spring variable to be used in the below for loop to zero
		if(control.getBoolean("Use vibrato") == false) //if vibrato is false makes the vibrato driver point not oscillate
		{
		control.setValue("Vibrato Intensity", 0);
		kill_vibrato = 0;
		}
	
		//uses formula d = s-(s/(2^(n/12))) to calculate the distance from the base of the string where the fixed (fingered) point needs to be
		//where d = the distance; s = the scale (string) length; n = the scale degree (note being played)
		double d = control.getDouble("Scale length") - (control.getDouble("Scale length")/Math.pow(2, scale_degree/12));
		
		for (int i = 0; i < springs.size(); i++) { //for loop goes through all the x coordintes of the masses and sees which one is closest to d (ie the position of the fingered note)
			if(Math.abs(springs.get(i).getX()-(L-d)) < mindist)
			{
				closest_spring = i;
				mindist = Math.abs(springs.get(i).getX()-(L-d));
			}
		}
		control.setValue("Fixed Mass Number", closest_spring); //sets the fingered note to the corresponding mass number
		springs.get(closest_spring).color = Color.black; //makes the fingered note black
		springs.get(closest_spring).pixRadius = 7;
		m = control.getInt("Mass Oscillator");
		m_xint =  springs.get(m).getX(); //gets the initial x position of the main driver point
		
		v_int = springs.get(control.getInt("Fixed Mass Number")).getX();
		frame.setPreferredMinMax(control.getDouble("Fix End at X = "), springs.get(springs.size()-1).getX(), -1, 1);
	}
	
	/** The main runs the Spring_Oscillator app
	 */
	public static void main(String[] args) 
	{
		SimulationControl.createApp(new Spring_Oscillator2());	
	}

}