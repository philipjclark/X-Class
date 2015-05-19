package AML_Projectile;
import java.awt.Color;

import org.opensourcephysics.controls.*;  
import org.opensourcephysics.display.*;   
import org.opensourcephysics.frames.*;  

/**
 * This is the class ParticleApp.  Its functionality consists mainly of two parts: the doStep method and the minLaunch method.
 * With the doStep method it runs a simulation of a particle given starting quantities and generates the associated graphs described below.
 * With the minLaunch method (described below) it finds the minimum velocity necessary for the particle to clear the wall of the green monster (10,100)
 * given an angle theta.
 * @method The doStep contains the body of the particle simulations program, running every 1/10 of a second.
	 * In it, the particle's y acceleration, x acceleration are first set, allowing the x and y velocities to
	 * be set, in turn allowing the x and y positions to be set.  The do step then adds the necessary points to 
	 * the various graphs, adds a trial to mark the particles path, and increments times based on a specified delta
	 * @method reset resets the initial values of the particle and the frame states
	 * @method initialize Reads in the "x" and "y" value from the Control Panel and sets them as the x and y coordinates, velocities, accelerations, etc of the particle
	 * Makes sure the frame is visible, and adds the particle to the DisplayFrame 
	 * @method takes any angle theta and finds the minimal initial velocity necessary
	 * to launch the particle with that angle theta over the green monster (at (100,10)).
	 * @method velocity gets the magnitude quantity speed derived from the vector quantities of x and y velocity
	 * @method acceleration gets the magnitude of acceleration derived from the vector quantities of x and y velocity
/** **** NOTE THAT IF MINLAUNCH IS RUN IT WAS ALWAYS LAUNCH THE PROJECTILE AT THE MINIMUM VELOCITY NECESSARY TO MAKE
 * *** IT OVER THE GREEN MONSTER.  IF YOU WANT TO SET THE VALUES OF THE PROJECTILE YOURSELF YOU HAVE TO COMMENT OUT THE
 * **** LINE IN WHICH MINLAUNCH IS INTIALIZED (IN THE INITIALIZE FUNCTION) -- AROUND LINE 142
 * @author Philip Clark
 * I learned that, after a certain amount of time and given air resistance, the ball will stop accelerating and reach a constant velocity -- that is reach a terminal velocity 
 */
public class ParticleApp extends AbstractSimulation {

	static Particle p;	

	static int x1 = 0;
	static int y1 = 30;
	
	static PlotFrame frame = new PlotFrame("x-axis", "y-axis", "Projectile Graph"); 
	
	static PlotFrame yframe = new PlotFrame("time", "y-axis", "Y Pos Graph");
	static PlotFrame xframe =  new PlotFrame("time", "x-axis", "X Pos Graph");
	static PlotFrame vxframe = new PlotFrame("time", "xvelocity", "XVelocity Graph");
	static PlotFrame vyframe = new PlotFrame ("time", "yvelocity", "YVelocity Graph");
	static PlotFrame atframe = new PlotFrame("time", "acceleration", "Acceleration-Time Graph");

	public boolean clear = false;

	double time = 0;//sets the time counter to 0

	Trail park = new Trail();
	Trail fence = new Trail();

	Trail trail = new Trail(); //creates trail

	/** 
	 * @see org.opensourcephysics.controls.AbstractAnimation#doStep()
	 *The doStep contains the body of the particle simulations program, running every 1/10 of a second.
	 * In it, the particle's y acceleration, x acceleration are first set, allowing the x and y velocities to
	 * be set, in turn allowing the x and y positions to be set.  The do step then adds the necessary points to 
	 * the various graphs, adds a trial to mark the particles path, and increments times based on a specified delta
	 */
	protected void doStep() {
{
	if (time == 0) //if it is the first time the doStep is being run it sets the initial x and y positions and velocities for the particle
	{
		p.setInitialX(p.getX());
		p.setInitialY(p.getY());
		p.setvelocityx(p.x_vint, time, p.delta);
		p.setvelocityy(p.y_vint, time, p.delta);
	}
	time+=p.delta; //increments time according to a delta

	p.setyacc(p.getyacc()); //sets x and y acceleration according to methods in particle class
	p.setxacc(p.getxacc());

	p.setvelocityx(p.getvelocityx(), time, p.delta); //sets x and y velocities according to methods in particle class
	p.setvelocityy(p.getvelocityy(), time, p.delta);

	p.setX(p.getPositionx()); //sets x and y positions according to methods in particle class
	p.setY(p.getPositiony());

	trail.addPoint(p.getX(), p.getY()); //creates a trail to mark particles movement
	frame.addDrawable(trail);

	yframe.append(0, time, p.getY()); //adds points to necessary graphs
	xframe.append(3, time, p.getX());
	vxframe.append(1, time, p.getvelocityx());
	vyframe.append(2, time, p.getvelocityy());
	
	atframe.append(7, time, acceleration(p));
}
		
	}

	/**
	 *@method Set the default values of "x" and "y" which will later be read as the initial x and y coordinates, mass, velocities acceleration, etc of the particle
	 */
	public void reset() {

		control.setValue("X(p)", 0);
		control.setValue("Y(p)", 15);
		control.setValue("v_x(p)", 3);
		control.setValue("v_y(p)", 10);
		control.setValue("mass(p)", 1);
		control.setValue("xacc(p)", 0);
		control.setValue("yacc(p)", -10);
		control.setValue("time(p)", 0);
		control.setValue("delta-t(p)", .02);
		control.setValue("alpha(p)", 0);
		control.setValue("theta(p)", 35);
		control.setValue("v_int", 15);
		trail.clear();
	}

	/**
	 * @method Reads in the "x" and "y" value from the Control Panel and sets them as the x and y coordinates, velocities, accelerations, etc of the particle
	 * Makes sure the frame is visible, and adds the particle to the DisplayFrame 
	 */
	public void initialize() {
		p=null;
		frame.removeDrawable(p);
		p = new Particle();
		p.setdelta(.01);
		frame.setPreferredMinMax(-2, 10, -2, 10);
		p.setXY(control.getDouble("X(p)"), control.getDouble("Y(p)"));
		frame.addDrawable(p);
		
		p.setV_int(control.getDouble("v_int"));
		p.setMass(control.getDouble("mass(p)"));
		p.setyacc(control.getDouble("yacc(p)"));
		p.setxacc(control.getDouble("xacc(p)"));
		p.setdelta(control.getDouble("delta-t(p)"));
		p.setAlpha(control.getDouble("alpha(p)"));
		p.setTheta(control.getDouble("theta(p)"));
		/*p.setvelocityx(control.getDouble("v_x(p)"), time, p.delta);	
		p.x_vint = p.getvelocityx();
		p.setvelocityy(control.getDouble("v_y(p)"), time, p.delta);	
		p.y_vint = p.getvelocityy();*/

		p.y_vint = p.v_int * Math.sin(Math.toRadians(p.theta)); //takes an initial velocity and multiplies it by the sin of theta to get the initial y velocity
		p.x_vint = p.v_int * Math.cos(Math.toRadians(p.theta));	//takes an initial velocity and multiplies it by the sin of theta to get the initial x velocity
	
		minLaunch(); //runs minLaunch method described below
		
	park.addPoint(0,0); //makes the 100 meter long park with a trial
	park.addPoint(100,0);
		frame.addDrawable(park);
	
		fence.addPoint(100, 0); //makes green monster
		fence.addPoint(100, 10);
		fence.color = Color.GREEN; //makes the green monster green
		frame.addDrawable(fence);
	}

	/**
	 * This is the function minLaunch.  It takes any angle theta and finds the minimal initial velocity necessary
	 * to launch the particle with that angle theta over the green monster (at (100,10)).  It then prints out a 
	 * comparison value of that minimum initial velocity and the velocity of the particle with that initial velocity
	 * upon the particle's impact with the wall.  It then sets the initial velocity of the particle to that minimum value.
	 */
	public void minLaunch ()
	{
		
		int dataset = 0; //creates a dataset counter that will be used to change the colors of points
		double minVelocity = 200; //gives an unrealistically high starting value for minVelocity that will be changed
		double wall_v = 0; //will eventually be recorded as the impact velocity of the particle

		PlotFrame mingraph = new PlotFrame("Initial Velocity", "Height at Green Monster", "Mingraph Graph"); 
		PlotFrame hitgraph = new PlotFrame("Distance", "Height", "Arc of Ball Hit");

		boolean clearGreen = false; //will check to see if the green monster has been cleared

		double margin = 0;
		
		if(p.alpha == .001) //sets the optimized margin of evaluation for minLaunch depending on the value of alpha
		{
			margin = 1.5;
		}
		else
		{
			margin = .1;
		}
		for (int intvel = 10; intvel < 250; intvel ++) { //the for loop goes through a bunch of initial velocities (intvel) until it finds the lowest one that will allow the particle to clear the green monster
			reset();

			p.v_int = intvel; //lines 158 to 168 deal with resetting quantities
				time = 0;
				
				p.setXY(0, 1);

				p.y_vint = p.v_int * Math.sin(Math.toRadians(p.theta));
				p.x_vint = p.v_int * Math.cos(Math.toRadians(p.theta));	
								
				p.setyacc(control.getDouble("yacc(p)"));
				p.setxacc(0);
				
				
				while(p.getY() > 0) //runs while the particle is above the ground
				{					
					
					p.Step(time, p); //runs the step function described in particle (essentially a modified version of the doStep)
			
										
					if(p.getY() >= 10 && Math.abs(100-p.getX()) < margin) //checks to see if the particle has clear y=10 when it is at x=100
					{
						clearGreen = true; //sets the boolean to reflect that the particle has cleared
						if(intvel < minVelocity) //replaces the wall speeds and minimum velocities if the parameters hold
						{
							mingraph.setMarkerColor(dataset, Color.red); //makes the minimum velocity marker red
							hitgraph.setMarkerColor(dataset, Color.red); //makes minimum velocity trajectory curve red
							minVelocity = intvel;
							wall_v = velocity(p);
						}
						
					}
					
					if(clearGreen == true && !(intvel == minVelocity)) //makes a green point on the bell curve graph if the particle has cleared
					{
						mingraph.setMarkerColor(dataset, Color.green);
						hitgraph.setMarkerColor(dataset, Color.green);

					}
					
					if(Math.abs(100-p.getX()) < margin) //adds all the points to a bell curve to show where they are at x=100
					{
						mingraph.append(dataset, intvel, p.getY());
					}
					else if (intvel < minVelocity) //if it doesnt clear makes the points blue
					{
						mingraph.setMarkerColor(dataset, Color.blue);
						hitgraph.setMarkerColor(dataset, Color.blue);
					}
					
					
					time+=p.delta;//increments time
					hitgraph.append(dataset, p.getX(), p.getY());
				}
				
				dataset++;			
		}
		mingraph.setLocation(x1+620, y1+330);
		hitgraph.setLocation(x1+930, y1+370);
		
		
		System.out.println("The minimum velocity required is: " + minVelocity + " m/s");
		System.out.println("The wall velocity is: " + wall_v + " m/s");
		System.out.println("The difference in velocity (vwall - initial velocity) is: " + (wall_v - minVelocity) + " m/s");
		
		reset(); //the remaining lines in the method reset quantities in preparation for the doStep
		p.setV_int(minVelocity + .3);		
		time = 0;
	
		
		p.setXY(0, 1);

		p.y_vint = p.v_int * Math.sin(Math.toRadians(p.theta));
		p.x_vint = p.v_int * Math.cos(Math.toRadians(p.theta));	
		

		p.setvelocityx(p.x_vint, time, p.delta);
		p.setvelocityy(p.y_vint, time, p.delta);
						
		p.setyacc(control.getDouble("yacc(p)"));
		p.setxacc(control.getDouble("xacc(p)"));
		
	}

	/**
	 * This function uses the orthogonal projection method (essentially the pythagorean theorem) to takes the two vector quantities of x and y velocity and make them into a magnitude quantity
	 * @param p takes in any particle p as a parameter
	 * @return returns the magnitude quantity speed derived from the vector quantities of x and y velocity
	 */
	private double velocity(Particle p) {
		return Math.sqrt(p.getvelocityx()*p.getvelocityx() + p.getvelocityy()*p.getvelocityy());
	}
	
	/**
	 * This function uses the orthogonal projection method (essentially the pythagorean theorem) to takes the two vector quantities of x and y acceleration and make them into a magnitude quantity
	 * @param p takes in any particle p as a parameter
	 * @return returns the magnitude quantity acceleration derived from the vector quantities of x and y acceleration
	 */
	private double acceleration(Particle p)
	{
		return Math.sqrt(p.getxacc()*p.getxacc() + p.getyacc()*p.getyacc());
	}

	/** Runs the particle app
	 * @param args
	 */
	public static void main(String[] args) {
		frame.setLocation(x1, y1); //sets the location of the window to the upper-left hand corner
		
		yframe.setLocation(x1+310, y1);
	
		xframe.setLocation(x1+620, y1);
	
		vxframe.setLocation(x1+930, y1);
	
		vyframe.setLocation(x1, y1+370);
			
		atframe.setLocation(x1+310, y1+370);
		
		SimulationControl.createApp(new ParticleApp());
	}

}
