package AML_Orbital;

import org.opensourcephysics.controls.*;

import java.awt.Color;
import java.util.*;

import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.*;

import java.io.Console;
import java.lang.Math;
import java.util.Scanner;
import java.util.Random;
import java.util.Random;

/**
 * @author Philip Clark
 * This class of my orbital program allows for a simualtion involving any number of bodies specified by the user. By simply putting in the necessary velocities, positions, and masses for each of these bodies
 * the user can watch an entire universe of gravitational attraction unfold. It also has the rand gen simulator which randomly generates a solar system.
 *
 */
public class OrbitalMultApp extends AbstractSimulation {

	//Test Data
	//2E30, 0, 0, 0, 0, 6E24, 150000000000, 0, 0, 30000, 6E23, 227900000000, 0, 0, 24.077E3 (mars)

	//2E30, 0, 0, 0, 0, 6E24, 150000000000, 0, 0, 30000, 6E22, 227900000000, 0, 0, 19.077E3, 6E24, -200000000000, 0, 0, -31000, 6E23, -227900000000, 0, 0, -24.077E3

	//2E30, 0, 0, 0, 0; 6E24, 1.5E11, 0, 0, 30000; 7.34E22, 1.5E11+3.84E8, 0, 31023 //sun and earth with moon
	//2E30, -3.5E11, 0, 0, -10000, 2E30, 3.5E11, 0, 0, 10000, 6E24, 0, 4.5, -20000, 5000 (binary with one planet)

	//2E31, -4E11, 0, 0, -10000, 2E31, 4E11, 0, 0, 10000, 2E31, 0, 3E12, -30000, -1000   (trinary with no planets)

	static Random rand = new Random();
	static Scanner scan = new Scanner(System.in);

	Trail star_trail = new Trail();//Trail for planet
	ArrayList <Trail> ptrail = new ArrayList<Trail>(); //array list for lagrange point trails
	static int x1 = 0; //x position to set frame location on screen
	static int y1 = 0; //y position to set frame location on screen
	Particle star = new Particle();//star
	ArrayList <Particle> planets = new ArrayList<Particle>(); //array list of lagrange points
	static PlotFrame frame = new PlotFrame("x", "y", "App");  //two object which are created outside of any method so all methods can access them
	double counter = 0; //counts time
	double dist = 0; //dist from star
	boolean visible = true; //visibility of trail boolean
	static double greatest_x;

	/** 
	 * @method doStep the do step runs the simulation every 1/10 of a second, changing the positions, velocities, and accelerations of the orbiting bodies
	 **/
	protected void doStep() 
	{	
		set_view(); 
		double timestep = control.getDouble("timestep");//sets the timetep
		for(int h=0; h<3000; h++){//speeds up the do step

			counter +=timestep;//Adds counter
			for (int i = 0; i < planets.size(); i++) {
				double FgY = 0;//y force of gravity
				double FgX = 0;//x force of gravity
				for (int j = 0; j < planets.size(); j++) { //goes through all the planets to deal with gravity itneractions
					if(!(j == i))//makes sure it's not the current planet
					{
						double pcos_angle = (planets.get(j).getX() - planets.get(i).getX())/Particle.distance(planets.get(j), planets.get(i)); //finds the cos of the angle between the two bodies
						double psin_angle = (planets.get(j).getY() - planets.get(i).getY())/Particle.distance(planets.get(i), planets.get(j)); //finds the sin of the angle
						double pFx = pcos_angle * ((planets.get(0).getGravity() * planets.get(i).getMass()*planets.get(j).getMass())/(Particle.distance(planets.get(i), planets.get(j))*Particle.distance(planets.get(i), planets.get(j))));//uses gravity formula to measure x force interaction
						FgX += pFx; //adds to total tally of the gravity x force
						double pFy = psin_angle * ((planets.get(0).getGravity() * planets.get(i).getMass()*planets.get(j).getMass())/(Particle.distance(planets.get(i), planets.get(j))*Particle.distance(planets.get(i), planets.get(j))));//uses gravity formula to measure y force interaction
						FgY+=pFy;//adds to total tally of the gravity y force
					}
				}
				planets.get(i).setxacc(FgX/planets.get(i).getMass());//sets current planets x acc
				planets.get(i).setyacc(FgY/planets.get(i).getMass());//sets current planets y acc

				planets.get(i).setvelocityx(planets.get(i).getvelocityx()+planets.get(i).getxacc()* timestep);//sets current planets x vel
				planets.get(i).setvelocityy(planets.get(i).getvelocityy()+planets.get(i).getyacc()* timestep);//sets current planets y vel

				planets.get(i).setX(planets.get(i).getX() + (planets.get(i).getvelocityx() * timestep)); //sets current planets x pos
				planets.get(i).setY(planets.get(i).getY() + (planets.get(i).getvelocityy()* timestep));//sets current planets y pos

				ptrail.get(i).addPoint(planets.get(i).getX(), planets.get(i).getY()); //adds to current planet's trail
			}

		}
	}


	/** 
	 * @method reset the reset function resets all of the values of the orbiting bodies to the necessary quantities
	 */
	public void reset()
	{
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in); //sets scanner
		System.out.println("Would you like to usar Solar Gen?"); //asks if the user wants to use solar gen
		String s_ans = input.nextLine(); //records answer
		boolean r_gen = true; //boolean value
		int planetNum = 0; //intiates value to store planet
		if(s_ans.equalsIgnoreCase("no")) //if they want the normal simulation
		{
			r_gen = false;
			System.out.println("How many bodies would you like to simulate the multiple orbital app?"); //asks the user how many planets they want
			planetNum = input.nextInt(); //sets number of planets

		}
		control.setValue("timestep", 100); //timestep
		control.setValue("G", 6.67E-11); //G constant
		control.setValue("Planet Num", planetNum); //the number of planets
		control.setValue("Rand Gen", r_gen); //sets boolean for rand gen sim
		
		if(control.getBoolean("Rand Gen") == false)
		{
			for (int i = 0; i < control.getInt("Planet Num"); i++) { //goes through the planets and sets initial values
				control.setValue("Body "+(i+1)+" mass", 2E30); //mass
				control.setValue("Body "+(i+1)+" x pos", 0); //x pos
				control.setValue("Body "+(i+1)+" y pos", 0); // y pos
				control.setValue("Body "+(i+1)+" x vel", 0);//x vel
				control.setValue("Body "+(i+1)+" y vel", 0);//y vel
			}
		}
		

	}	

	public void set_view(){
		if(viewNum > 0)
		{
			frame.setPreferredMinMax(planets.get(viewNum-1).getX()-range,  //finds the view range and sets the window
					planets.get(viewNum-1).getX()+range, 
					planets.get(viewNum-1).getY()-range, planets.get(viewNum-1).getY()+range);
		}
		else
		{
		//	frame.setPreferredMinMax(-greatest_x, greatest_x, -greatest_x, greatest_x); //else sets normal view
		}
		
	}

	double range;
	int viewNum; 
	public void change_view(){
		if(viewNum < planets.size()){ //increases counter variable
			viewNum ++; 
		}
		else {
			viewNum = 0; //sets viewnum to 0
		}
		
		if(viewNum == 0)
		{
			frame.setPreferredMinMax(-greatest_x, greatest_x, -greatest_x, greatest_x); //sets frame size
		}
		else
		{
			frame.setPreferredMinMax(planets.get(viewNum-1).getX()-range, //scales the window to match the moving body
					planets.get(viewNum-1).getX()+range, 
					planets.get(viewNum-1).getY()-range, planets.get(viewNum-1).getY()+range);
		}
		
	}
	
	public void toggle()
	{
		visible = !visible; //sets boolean to opposite value
		if(visible == true)
		{
			for (int i = 0; i < ptrail.size(); i++) {
				frame.removeDrawable(ptrail.get(i)); //removes trail
			}
		}
		else
		{
			for (int i = 0; i < ptrail.size(); i++) {
				frame.addDrawable(ptrail.get(i)); //adds trail
			}
		}
	}

	/** 
	 * @method initialize intializes the orbiting bodies, their colors, the frame's scale, and their size
	 */
	public void initialize() 
	{
		if(control.getBoolean("Rand Gen") == false)
		{	
		greatest_x = 0;
		for (int i = 0; i < control.getInt("Planet Num"); i++) { //reads input for each of the planets
			planets.add(i, new Particle()); //makes new planet
			planets.get(i).setMass(control.getDouble("Body "+(i+1)+" mass"));
			planets.get(i).setXY(control.getDouble("Body "+(i+1)+" x pos"), control.getDouble("Body "+(i+1)+" y pos"));
			planets.get(i).setvelocityx(control.getDouble("Body "+(i+1)+" x vel"));
			planets.get(i).setvelocityy(control.getDouble("Body "+(i+1)+" y vel"));

			if(Math.abs(control.getDouble("Body "+(i+1)+" x pos")) > greatest_x)
			{
				greatest_x = control.getDouble("Body "+(i+1)+" x pos"); //finds fathest point
			}


			frame.addDrawable(planets.get(i));//adds planet
			ptrail.add(i, new Trail());//adds planet trail to array list
			frame.addDrawable(ptrail.get(i));//adds planet
			if(planets.get(i).getMass() > 2E30) //finds planet size
			{
				planets.get(i).pixRadius = 24; //sets radius
				
			}
			
			else if(planets.get(i).getMass() < 2E24)
			{
				planets.get(i).pixRadius = 5;
			}
			else
			{
				planets.get(i).pixRadius = (int) (9 + 11*planets.get(i).getMass()/(2E30)); //planet size proportional to mass
			}

			final float hue = rand.nextFloat(); //random hue
			final float saturation = rand.nextFloat();//1.0 for brilliant, 0.0 for dull
			final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
			Color color = Color.getHSBColor(hue, saturation, luminance); //makes the color

			ptrail.get(i).color = color; //sets the color of the planet
			planets.get(i).color = color; //sets the color of the trail
			planets.get(0).color = Color.yellow; //makes the sun yellow
			planets.get(i).setGravity(control.getDouble("G")); //sets the G constant
			range = greatest_x;
		}

		frame.setPreferredMinMax(-greatest_x, greatest_x, -greatest_x, greatest_x);//sets preferred min max window size
		}
		
		else
		{
			RandGen(planets, ptrail); //randgen
		}
		
		frame.addButton("change_view", "Change View", "Change Frame View", this);   //creates change view button
		frame.addButton("toggle", "Toggle Trails", "Turn Trails off or on", this);  //creates toggle button
		frame.setSize(600, 600);

		frame.setVisible(true); //makes frame visible
	}

	/**
	 * The RandGen method randomly generates a random solar system.
	 * @param planets the array list for the random planets
	 * @param ptrail the trails for the random planets
	 */
	public static void RandGen (ArrayList<Particle> planets, ArrayList<Trail> ptrail)
	{
	double rad = 4.44E12;// rand.nextInt((int) 4E12);
	//double perc = rand.nextInt(800) + 200;
	//rad = perc*rad;
	greatest_x = 0; //finds greatest x
	planets.add(0, new Particle());
	planets.get(0).color = Color.yellow; //makes the sun yellow
	planets.get(0).pixRadius = 10; //sets pix radius
	planets.get(0).setGravity(control.getDouble("G")); //sets the G constant
	planets.get(0).setXY(0, 0); //sets sun at 0, 0
	double mass_s = 2E30; //sets mass of sun
	planets.get(0).setMass(mass_s);
	frame.addDrawable(planets.get(0));
	ptrail.add(0, new Trail());//adds planet trail to array list
	frame.addDrawable(planets.get(0));
	
	
	int planet_num = rand.nextInt(7) + 5;  //generates the random number of planets
	for (int i = 1; i < planet_num+1; i++) { //goes through all the planets
	planets.add(i, new Particle());
	planets.get(i).setGravity(control.getDouble("G")); //sets the G constant

	double mass = 6E24;// rand.nextInt((int) 2E28) + 6E10;
	planets.get(i).setMass(mass);
	//set position
	int percent = rand.nextInt(375) + 25; //scales
	double x_pos = rad*percent/500; //sets x pos
	
	if(rand.nextBoolean() == true)
	{
	//	x_pos = -x_pos;
	}
	if(Math.abs(x_pos) > greatest_x)
	{
		greatest_x = x_pos;
	}
	
	planets.get(i).setXY(x_pos, 0); //sets x position of planet
	double max = Math.sqrt(2*planets.get(i).getGravity()*planets.get(0).getMass()/x_pos);//escape velocity
	double min = Math.sqrt(planets.get(i).getGravity()*planets.get(0).getMass()/x_pos);//circular velocity
	double velocity = (max-min)*(percent/500) + min; //sets planet velocity
	planets.get(i).setvelocityx(0);
	planets.get(i).setvelocityy(velocity); //sets velocity
	ptrail.add(i, new Trail());//adds planet trail to array list
	frame.addDrawable(planets.get(i));
	frame.addDrawable(ptrail.get(i));//adds planet
	
	final float hue = rand.nextFloat(); //random hue
	final float saturation = rand.nextFloat();//1.0 for brilliant, 0.0 for dull
	final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
	Color color = Color.getHSBColor(hue, saturation, luminance); //makes the color

	ptrail.get(i).color = color; //sets the color of the planet
	planets.get(i).color = color; //sets the color of the trail	

	}
	frame.setPreferredMinMax(-greatest_x, greatest_x, -greatest_x, greatest_x);//sets preferred min max window size

	}
	/**
	 * Runs the simulation
	 * @param args
	 */
	public static void main(String[] args) 
	{
		frame.setLocation(x1, y1); //sets the location of the window to the upper-left hand corner
		SimulationControl.createApp(new OrbitalMultApp());//runs the simulation
	}

}
