package AML_Orbital;


import java.awt.Color;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;


/**
 * @author Philip Clark
 * This is the spec class for my orbital simulation. It uses Kepler's Laws of Motion and Newton's Law of Gravity to model the interactions between a sun and a planet.
 * This class also shows a simulation of Kepler's Three laws by 1) using an algorithim to find the ellipse formed by the orbiting planet, 2) use a graph to show that the aerial velocity of this ellipse is constant, and 3) shows Kepler's constant (a^3 proportion to T^2)
 * Bonuses: Due to a changed algorithim and some math research, I was able to modify my simulation of Kepler's 1st law such that the simulation can now trace ANY elliptical orbit, whether it be based on the horizontal axes or not. By multiplying by a rotational matrix often used in higher levels of conic section, the program is capable of a much more effective simulation of Kepler's Laws
 *
 */
public class OrbitalFinalApp extends AbstractSimulation {

	Trail star_trail = new Trail();//Trail for planet
	Trail planet_trail = new Trail();//Trail for graph
	Particle star = new Particle();//Sun
	Particle planet = new Particle();//Planet
	Particle ext = new Particle(); //extrema
	Particle p_int = new Particle(); //initial planet position
	Particle f_end = new Particle(); //other extrema
	static PlotFrame frame = new PlotFrame("x", "y", "App"); //frame
	static PlotFrame law_one = new PlotFrame("time", "ellipse", "Law One Graph (sum of distances from the foci)");
	static PlotFrame area_graph = new PlotFrame("time", "area", "Kepler 2"); //kepler's second law graph
	static int x1 = 0; //x position to set frame location on screen
	static int y1 = 30; //y position to set frame location on screen
	double counter = 0; //Counts
	double prev_y = 0; //measures the previous y pos of planet in orbit
	double prev_x = 0; //measures prev x pos of planet
	double x_max = 0; //max x pos
	double x_min = 0; //min x pos
	double y_max = 0; //max y pos
	double y_min = 0; //min y pos
	double distx = 0; //x dist from star
	double disty = 0; //y dist from star
	boolean major_x; //if the semimajor axis is on the x plane
	double semi_x; //the length of the axis on the x
	double semi_y; //the length of the axis on the y
	double T = 0; //period of orbit
	double semimajor_axis; //length of a
	double semiminor_axis; //length of b
	boolean has_printed = false; //if kepler's law has printed
	double int_area = 5E8; //initial area for Kepler's second
	double furthest_dist = 0; //furthest dist from extrema
	double fd_x; //furthest x pos from ext
	double fd_y; //furtherest y pos from ext
	double sun_dist = 0; //dist from sun
	boolean sun_check = true; //if the planet has reached the sun
	double first_dist = 0; //dist from initial planet pos to extrema pos
	double first_check = 0; //dist from star to initial planet pos
	boolean once = false;
	double focus_x; //x coor of focus
	double focus_y; //y coor of focus
	double law_one_first;
	double min_dist;
	double min_x;
	double min_y;
	

	/** 
	 * runs every 1/10 second to make the necessary calculations for the orbital simulation such as setting the velocities, accelerations, and positions
	 * Also runs Kepler's three laws
	 */
	protected void doStep() 
	{	
		double timestep = control.getDouble("timestep"); //sets timestep
		for(int h=0; h<3000; h++){//speeds up do step

			if (counter == 0) //if time is zero sets the initial x and y
			{
				planet.setInitialX(planet.getX()); 
				planet.setInitialY(planet.getY());
				min_dist = Particle.distance(star, p_int);
			}

			counter +=timestep;//adds counter
			double FgY = 0; //gravity force in y direction
			double FgX = 0; //gravity force in x direction
			
			double cos_angle = (star.getX()-planet.getX())/Particle.distance(star,planet); //cos of planet star angle
			double sin_angle = (star.getY()-planet.getY())/Particle.distance(star,planet); //sin of planet star angle

			FgX = ((star.getGravity()*star.getMass()*planet.getMass())/((Particle.distance(star,planet))*(Particle.distance(star,planet))))*cos_angle; //gravity formula for x
			FgY = ((star.getGravity()*star.getMass()*planet.getMass())/((Particle.distance(star,planet))*(Particle.distance(star,planet))))*sin_angle; //gravity formula for y					

			planet.setxacc(FgX/planet.getMass()); //sets planet x acc
			planet.setyacc(FgY/planet.getMass()); //sets planet y acc
			
			star.setxacc(-FgX/star.getMass()); //sets star x acc via Newton's Third Law
			star.setyacc(-FgY/star.getMass()); //sets star y acc via NTL

			star.setvelocityx(star.getvelocityx()+star.getxacc()* timestep); //sets star x vel
			star.setvelocityy(star.getvelocityy()+star.getyacc()* timestep); //seets star y vel
			
			star.setX(star.getX() + (star.getvelocityx()* timestep)); //sets star x pos
			star.setY(star.getY() + (star.getvelocityy()* timestep)); //sets star y pos

			planet.setvelocityx(planet.getvelocityx()+planet.getxacc()* timestep); //planet x vel
			planet.setvelocityy(planet.getvelocityy()+planet.getyacc()* timestep); //planet y vel

			planet.setX(planet.getX() + (planet.getvelocityx() * timestep)); //sets planet x pos
			planet.setY(planet.getY() + (planet.getvelocityy()* timestep)); //sets planet y pos
			
			star_trail.addPoint(star.getX(),star.getY());//adds star point to trail
			planet_trail.addPoint(planet.getX(), planet.getY()); //adds planet point to trail
			
			Particle focus = new Particle();
			focus.setXY(focus_x, focus_y);
			
			if(counter > T && once == true)
			{
				law_one.append(13, counter-T, law_one_first);
				law_one.setPreferredMinMaxY(law_one_first - 3E11, law_one_first+3E11);
			}
			//Kepler's First Law
			if(planet.getX() > x_max) //finds max x pos
			{
				x_max = planet.getX();
			}
			if(planet.getX() < x_min)//finds min x pos
			{
				x_min = planet.getX();
			}
			if(planet.getY() > y_max)//finds max y pos
			{
				y_max = planet.getY();
			}
			if(planet.getY() < y_min)//finds min y pos
			{
				y_min = planet.getY();
			}
			
			if(Particle.distance(planet, star) > sun_dist && sun_check == true && once == false) //finds max dist from sun
			{
				sun_dist = Particle.distance(planet, star); //sets sun dist
				if(counter - timestep == 0) //if time = 0 sets first_dist as dist between planet and star and first_check as dist between planet and initial planet pos
				{
					first_dist = Particle.distance(planet, star);
					first_check = Particle.distance(planet, p_int);
				}
			}
			else if (!(Particle.distance(planet, star) < Particle.distance(star, p_int)))
			{	
				if(sun_check == true) //if sun check is true
				{
					ext.setXY(planet.getX(), planet.getY()); //sets the extrema
				}
				sun_check = false; //makes sun check false
			}
			
			
			
			if(Particle.distance(planet, star) < min_dist && once == false) //finds max dist from sun
			{
				min_dist = Particle.distance(planet, star);
				min_x = planet.getX();
				min_y = planet.getY();
			}
		
			
			
			
			if(Particle.distance(planet, ext) > furthest_dist) //finds furthest dist from extrema
			{
				fd_y = planet.getY(); //furthest y dist
				fd_x = planet.getX(); //furthet x dist
				furthest_dist = Particle.distance(planet, ext); 
			}
			
			if(planet.completed(planet, star, p_int, counter, first_dist, first_check, timestep))
			{
							
				once = true;
			}

			if(planet.completed(planet, star, p_int, counter, first_dist, first_check, timestep) && has_printed == false && control.getBoolean("Law One") == true) //sees if orbit is complete
			{
				//frame.addDrawable(ext);
				ext.pixRadius = 20;
				f_end.setXY(fd_x, fd_y); //makes three particles to make triangle (center, vertically below center, and foci)
				Particle P1 = new Particle();
				P1.setXY(ext.getX(), ext.getY()); //center point
				Particle P2 = new Particle();
				P2.setXY(star.getX(), ext.getY()); //vertically below center point
				Particle P3 = new Particle();
				P3.setXY(star.getX(), star.getY()); //foci point

				double a_x = P2.getX() - P1.getX(); //vector pointing from P1 in x direction
				double a_y = P2.getY() - P1.getY(); //vector pointing from P1 in y direction
				double mag_a = Math.sqrt(a_x*a_x + a_y*a_y); //mag of vectory one
				double b_x = P1.getX() - P3.getX(); //vector pointing from P3 in x direction
				double b_y = P1.getY() - P3.getY(); //vector pointing from P3 in y direction
				double mag_b = Math.sqrt(b_x*b_x + b_y*b_y); //mag of vector 2
				double theta = Math.toDegrees(Math.acos((a_x*b_x + a_y*b_y)/(mag_a*mag_b))); //dot product to find angle
				double eqn = Particle.distance(star, ext)*Particle.distance(star, ext) + Math.pow(Math.abs(star.getX()-ext.getX()), 2) - Math.pow(Math.abs(star.getY()-ext.getY()), 2);
				eqn = eqn/(2*Particle.distance(star, ext)*Math.abs(star.getX()-ext.getX()));
				theta = Math.toDegrees(Math.acos(eqn));

				
				if(star.getX()-ext.getX() < -1E10 && star.getY()-ext.getY() < -1E10)
				{
					theta = 180-theta;
				}
				
				if(star.getX()-ext.getX() > 1E10 && star.getY()-ext.getY() > 1E10)
				{
					theta = 360-theta;
				}
				
				if(Math.toDegrees(Math.acos(eqn)) == 90)
				{
					theta = 0;
				}

				double center_x = (min_x+ext.getX())/2; //finds center x
				double center_y = (min_y+ext.getY())/2; //find center y
				Particle center = new Particle(); //center particle
				center.setXY(center_x, center_y);
				
				Particle min = new Particle();
				min.setXY(min_x, min_y);
				semi_x = Particle.distance(min, ext)/2; //semi x axis
				semi_y = Math.sqrt(semi_x*semi_x - Particle.distance(star, center) * Particle.distance(star, center)); //semi y axis
				Particle fd = new Particle(); //extrema particle
				fd.setXY(fd_x, fd_y);
				
				semimajor_axis = Particle.distance(min, ext)/2; //finds a

				fd.pixRadius = 10;
				if(theta < 2)
				{
					theta = 0;
				}
				Particle cen = new Particle();
				
				//frame.addDrawable(cen);
				cen.setXY(center_x, center_y);
				cen.color = Color.black;
				//frame.addDrawable(min);
				center_x = center_x*(Math.cos(Math.toRadians(-theta))) + center_y*(Math.sin(Math.toRadians(-theta))); //unrotates x coor of center
				center_y = -center_x*(Math.sin(Math.toRadians(-theta))) + center_y*(Math.cos(Math.toRadians(-theta))); //unrotates y coor of center
				
				
				System.out.println("theta is " + theta + " degrees"); //prints info
				System.out.println("a = " + semimajor_axis);
				System.out.println("b = " + semi_y); //prints b
				double c = Math.sqrt(semimajor_axis*semimajor_axis - semi_y*semi_y); //sets value of ellipse for c
				System.out.println("c = " + c); //prints c
				System.out.println("Eccentricity is " + (c/semimajor_axis));
				double focus_cosx = -1; //coefficient to make focus correct
				double focus_cosy = -1;
				if(star.getY() < center_y && star.getX() < center_x)
				{
					focus_cosx = 1; //makes focus coeff pos
					focus_cosy = 1; //makes focus coeff pos

				}
				if(star.getY() < center_y) //star center
				{
					focus_cosy = 1;
				}
				
				if(star.getX() < center_x) //star center x
				{
					focus_cosx = 1;
				}
				Particle foc = new Particle();
				focus_x = center_x + focus_cosx*c*Math.abs(Math.cos(Math.toRadians(-theta))); //finds x coor of focus
				focus_y = center_y + focus_cosy*c*Math.abs(Math.sin(Math.toRadians(-theta))); //finds y coor of focus
				foc.setXY(focus_x, focus_y);
			//	frame.addDrawable(foc);
				System.out.println("Focus 1 is at coordinates " + focus_x + ", " + focus_y); //prints focus and center
				System.out.println("Focus 2 is at coordinates "+ star.getX() + ", " + star.getY());
				System.out.println("The center of the ellipse is at coordinates " + center_x + ", " + center_y);
				System.out.println("The drawn figure is the ellipse: x^2/" + semi_x + " + y^2/" + semi_y + " = 1");
				
				center_y = 0; //puts unrotated center on the x axis
				
				for (double i = x_min-1E12; i < x_max+1E12; i+=semimajor_axis/303081 * 340) { //for loop to graph ellipse
					if((semi_y*semi_y*(1 - (i - center_x)*(i-center_x)/(semi_x*semi_x))) > 0) //makes sure its a real number
							{
						double x = i; //sets x to the i of the for loop
						double y = Math.pow(semi_y*semi_y*(1 - (i - center_x)*(i-center_x)/(semi_x*semi_x)), .5) + center_y; //upper half of ellipse
						double y2 = -Math.pow(semi_y*semi_y*(1 - (i - center_x)*(i-center_x)/(semi_x*semi_x)), .5) + center_y; //lower half of ellipse
						double x_prime1 = x*(Math.cos(Math.toRadians(theta))) + y*(Math.sin(Math.toRadians(theta))); //rotated x coor 1
						double y_prime1 = -x*(Math.sin(Math.toRadians(theta))) + y*(Math.cos(Math.toRadians(theta))); //rotated y coor 1
						double x_prime2 = x*(Math.cos(Math.toRadians(theta))) + y2*(Math.sin(Math.toRadians(theta))); //rotated x coor 2
						double y_prime2 = -x*(Math.sin(Math.toRadians(theta))) + y2*(Math.cos(Math.toRadians(theta))); //rotated y coor 2
						frame.append(2, x_prime1, y_prime1); //graphs lower half
						frame.append(2, x_prime2, y_prime2); //graphs upper half
						
						frame.setMarkerColor(2, Color.red);
							}
				}
				law_one_first = Particle.distance(star, planet) + Particle.distance(planet, focus);
				if(control.getBoolean("Law Three") == false)
				{
					has_printed = true; //if law three isnt being used stops printing
				}
			}
			
			//Kepler's Second Law
			if(control.getBoolean("Law Two") == true)
			{
				Particle prev = new Particle(); //particle to mark position of previous frame of the planet in orbit
				prev.setXY(prev_x, prev_y); //sets x and y
				double side_one = Particle.distance(star, prev); //finds the three sides of the area triangle
				double side_two = Particle.distance(star, planet);
				double side_three = Particle.distance(planet, prev);
				double s = (side_one+side_two+side_three)/2; //semi perimeter
				double area = Math.pow(s*(s-side_one)*(s-side_two)*(s-side_three), .5); //heron's formula to find area
				
				if(counter == timestep*10) 
				{
					int_area = area; //finds the initial area
					area_graph.setPreferredMinMaxY(.999*int_area, 1.001*int_area);
				}
				area_graph.append(0, counter, area);
				prev_x = planet.getX(); //resets previous x and y
				prev_y = planet.getY();
			}
			
			//Kepler's Third Law
			if(planet.completed(planet, star, p_int, counter, first_dist, first_check, timestep) && has_printed == false && control.getBoolean("Law Three") == true)
			{
				Particle min = new Particle();
				min.setXY(min_x, min_y);
				T = counter; //sets the period to elapsed time
				System.out.println("The period is " + T + " seconds"); //prints period
				System.out.println("The period in years " + Math.pow((T/(3600*24*7*51)), 2) + " = the semimajor axis in AU " + (Math.pow((T/(3600*24*7*51)), 2)-.03324356222437127));
				System.out.println("Kepler's constant is: " + (Math.pow(Particle.distance(min, ext)/2, 3))/(Math.pow(T, 2)) + " meters per second squared"); //prints Kepler's constant
				System.out.println("(GM)/4pi^2 = " + (star.getGravity()*star.getMass())/(4*3.14*3.14)); //checks kepler's constant mathematically
				//System.out.println("Period in years (" + T/(3600*24*7*51) + ") = a in AU (" + semimajor_axis/(1.496E11) + ")"); //T^2 in years = a^3 in astronomic units
				has_printed = true; //stops printing
			}
		}
	}


	/**
	 * sets the initial values of the planet and the sun
	 */
	public void reset()
	{
				control.setValue("timestep", 100); //timestep of simulation
				control.setValue("G", 6.67E-11); //Gravity constant
				control.setValue("X(star)", 0); //x pos of star
				control.setValue("Y(star)", 0); //y pos of star
				control.setValue("X_veloc(star)", 0); //x pos of planet
				control.setValue("Y_veloc(star)", 0); //y pos of planet
				control.setValue("mass star", 2E30); //mass of star
				control.setValue("X(planet)", 1.5E11); //x pos of planet
				control.setValue("Y(planet)", 0); //y pos of planet
				control.setValue("X_veloc(planet)", 0); //x vel of planet
				control.setValue("Y_veloc(planet)", 30000); //y vel of planet
				control.setValue("Mass(planet)", 6E24); //mass of planet
				control.setValue("Law One", true); //use law one
				control.setValue("Law Two", true); //use law two
				control.setValue("Law Three", true); //use law three

		star_trail.clear();
		planet_trail.clear();
	}	

	/**
	 * initializes the orbiting bodies and all of their necessary values
	 */
	public void initialize() 
	{
		frame.addDrawable(star_trail); //add star to frame
		star_trail.clear();
		frame.addDrawable(planet_trail); //add planet to frame
		planet_trail.clear();


		star.setXY(control.getDouble("X(star)"), control.getDouble("Y(star)")); //set x and y of star
		planet.setXY(control.getDouble("X(planet)"), control.getDouble("Y(planet)")); //set x and y of planet
		frame.setVisible(true);
		frame.addDrawable(star); //adds star
		frame.addDrawable(planet); //adds planet
		star.color = (Color.yellow); //make sun yellow
		star.pixRadius = 25;
		planet.pixRadius = 8;
		planet.color = Color.blue;

		double distance = Particle.distance(planet, star); //dist btw planet and star
		frame.setPreferredMinMax(-distance, distance, -distance, distance);
		star.setvelocityx(control.getDouble("X_veloc(star)")); //set star x and y vel
		star.setvelocityy(control.getDouble("Y_veloc(star)"));
		planet.setvelocityx(control.getDouble("X_veloc(planet)")); //set planet x and y vel
		planet.setvelocityy(control.getDouble("Y_veloc(planet)"));
		star.setMass(control.getDouble("mass star")); //set mass of star
		planet.setMass(control.getDouble("Mass(planet)"));//Sets mass of planet
		star.setGravity(control.getDouble("G")); //Set Gravitational Constant
		planet.setGravity(control.getDouble("G"));//Set Gravitational Constant
		prev_y = planet.getY(); //initial pos of prev_y and prev_x for kepler's 2nd
		prev_x = planet.getX();
		distx = Math.abs(planet.getX() - star.getX()); //initial x dist from star
		disty = Math.abs(planet.getY() - star.getY()); //initial y dist from star
		p_int.setXY(planet.getX(), planet.getY()); //initial pos of orbiting planet
		frame.setSize(600, 600);
	}
	/**
	 * Runs the simulation
	 * @param args
	 */
	public static void main(String[] args) 
	{
		frame.setLocation(x1, y1); //sets the location of the window to the upper-left hand corner
		area_graph.setLocation(x1+360, y1);
		law_one.setLocation(x1+720, y1);
		SimulationControl.createApp(new OrbitalFinalApp()); //runs simulation
	}

}
