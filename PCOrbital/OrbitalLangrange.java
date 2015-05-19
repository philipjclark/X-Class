package AML_Orbital;

import org.opensourcephysics.controls.*;
import java.awt.Color;
import java.util.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;
import org.opensourcephysics.*;
import java.lang.Math;
import java.util.Scanner;
import java.util.Random;
import java.util.Random;

/**
 * @author Philip Clark
 * This is one of the bonus classes of my Orbital Project which simulates the Lagrange points. It creates an array list of the number of the Langrange points between two specified massive bodies that the user inputs and then proceeds to model the system.
 * By setting the periods of the lagrange bodies equal to the orbiting mass and then using the result to create an equation for elliptical motion, the simulation combines all of our knowledge on orbits and Kepler's and Newton's laws.
 *
 */
public class OrbitalLangrange extends AbstractSimulation {

	Random rand = new Random();

	Trail star_trail = new Trail();//Trail for planet
	Trail planet_trail = new Trail();//Trail for graph
	ArrayList <Trail> l_trail = new ArrayList<Trail>(); //array list for lagrange point trails
	static int x1 = 0; //x position to set frame location on screen
	static int y1 = 0; //y position to set frame location on screen
	Particle star = new Particle();//star
	Particle planet = new Particle();//planet
	ArrayList <Particle> lpoints = new ArrayList<Particle>(); //array list of lagrange points
	ArrayList <Particle> planets = new ArrayList<Particle>();
	ArrayList<PlotFrame> lframe = new ArrayList<PlotFrame>(); //array list of lagrange point stability frames
	ArrayList<String> lnames = new ArrayList<String>();//array list of lagrange pointes names
	static PlotFrame frame = new PlotFrame("x", "y", "App");  //two object which are created outside of any method so all methods can access them
	double counter = 0; //counts time
	double dist = 0; //dist from star
	double L1_x; //x pos of first lagrange point
	double L1_y; //y pos of first lagrange point
	double L2_x; //x pos of second lagrange point
	double L2_y; //y pos of second lagrange point
	double L3_x; //x pos of third lagrange point
	double L3_y; //y pos of third lagrange point
	double L4_x; //x pos of four lagrange point
	double L4_y; //y pos of four lagrange point
	double L5_x = L4_x; //x pos of fifth lagrange point
	double L5_y = -L4_y; //y pos of fifth lagrange point
	int l_p = 1; //correction constant for planet
	int l_s = 1; //correction constant for sun

	/** 
	 * Runs every 1/10 of a second to perform the necessary calculations for the Langrange simulation
	 */
	protected void doStep() 
	{	
		double timestep = control.getDouble("timestep");
		for(int h=0; h<3000; h++){

			if (counter == 0) //if time is zero sets initial pos
			{
				planet.setInitialX(planet.getX());
				planet.setInitialY(planet.getY());
			}

			for (int i = 0; i < lpoints.size(); i++) {	//goes through all the masses
				boolean done = false; //sees if planet and star have already been moved
				if(i > 0)
				{
					done = true; //if its already looped once makes done true
				}
				if(counter == 0) //sets initial positions for lagrange points
				{
					lpoints.get(i).setInitialX(lpoints.get(i).getX());
				}
				if((lpoints.get(i).getX()> planet.getX() & lpoints.get(i).getInitialX()>star.getX()) || (lpoints.get(i).getX() < planet.getX() & lpoints.get(i).getInitialX() < star.getX())) //gets the direction of the pull of gravity from the planet
				{
					l_p = -1;
				}
				double lFgY = 0; //force of gravity on the lagrange point in the y
				double lFgX = 0; //force of gravity on the lagrange point in the x

				//finds the cos and sin of the angle between the two planets
				double lcos_angle = (star.getX()-lpoints.get(i).getX())/Particle.distance(star,lpoints.get(i)); //cos of the angle of the star and lagrange point
				double lsin_angle = (star.getY()-lpoints.get(i).getY())/Particle.distance(star,lpoints.get(i)); //sin of the angle of the star and lagrange point
				double lcos_angle2 = (planet.getX()-lpoints.get(i).getX())/Particle.distance(planet,lpoints.get(i)); //cos of the angle of the planet and lagrange point
				double lsin_angle2 = (planet.getY()-lpoints.get(i).getY())/Particle.distance(planet,lpoints.get(i)); //sin of the angle of the planet and lagrange point

				lFgX = ((star.getGravity()*star.getMass()*lpoints.get(i).getMass())/((Particle.distance(star,lpoints.get(i)))*(Particle.distance(star,lpoints.get(i)))))*lcos_angle; //sets gravity with star in the x
				lFgY = ((star.getGravity()*star.getMass()*lpoints.get(i).getMass())/((Particle.distance(star,lpoints.get(i)))*(Particle.distance(star,lpoints.get(i)))))*lsin_angle; //sets gravity with star in the y					
				lFgX += -1*((planet.getGravity()*planet.getMass()*lpoints.get(i).getMass())/((Particle.distance(planet,lpoints.get(i)))*(Particle.distance(planet,lpoints.get(i)))))*lcos_angle2; //sets gravity with planet in the x
				lFgY += -1*((planet.getGravity()*planet.getMass()*lpoints.get(i).getMass())/((Particle.distance(planet,lpoints.get(i)))*(Particle.distance(planet,lpoints.get(i)))))*lsin_angle2; //sets gravity with planet in the y

				lpoints.get(i).setxacc(lFgX/lpoints.get(i).getMass()); //sets x and y acc
				lpoints.get(i).setyacc(lFgY/lpoints.get(i).getMass());

				lpoints.get(i).setvelocityx(lpoints.get(i).getvelocityx()+lpoints.get(i).getxacc()* timestep); //sets x and y vel
				lpoints.get(i).setvelocityy(lpoints.get(i).getvelocityy()+lpoints.get(i).getyacc()* timestep);

				lpoints.get(i).setX(lpoints.get(i).getX() + (lpoints.get(i).getvelocityx() * timestep)); //sets x and y pos
				lpoints.get(i).setY(lpoints.get(i).getY() + (lpoints.get(i).getvelocityy()* timestep));
				l_trail.get(i).addPoint(lpoints.get(i).getX(), lpoints.get(i).getY()); //makes trail

				double pFgY = 0; //planet gravity y
				double pFgX = 0; //planet gravity x
				double a_x = planet.getX() - lpoints.get(i).getX(); //vector pointing from lagrange point to planet x comp
				double a_y = planet.getY() - lpoints.get(i).getY(); //vector pointing from lagrange point to planet y comp
				double mag_a = Math.sqrt(a_x*a_x + a_y*a_y); //mag of vector one
				double b_x = lpoints.get(i).getX() - star.getX(); //vector pointing from star to lagrange point in x
				double b_y = lpoints.get(i).getY() - star.getY(); //vector pointing from star to lagrange point in y
				double mag_b = Math.sqrt(b_x*b_x + b_y*b_y); //mag of vector two
				double theta = 180-Math.toDegrees(Math.acos((a_x*b_x + a_y*b_y)/(mag_a*mag_b))); //uses dot product to calculate angle

				double pcos_angle2 = (lpoints.get(i).getX()-planet.getX())/Particle.distance(star,planet); //cos of angle with planet
				double psin_angle2 = (lpoints.get(i).getY()-planet.getY())/Particle.distance(star,planet); //sin of angle with planet

				pFgX = l_p*((planet.getGravity()*planet.getMass()*lpoints.get(i).getMass())/((Particle.distance(planet,lpoints.get(i)))*(Particle.distance(planet,lpoints.get(i)))))*pcos_angle2; //force of gravity in x for planet
				pFgY = l_p*((planet.getGravity()*planet.getMass()*lpoints.get(i).getMass())/((Particle.distance(planet,lpoints.get(i)))*(Particle.distance(planet,lpoints.get(i)))))*psin_angle2; //force of gravity in y for planet
				
				if(done == false) //if the loop is on its first run
				{
					double pcos_angle = (star.getX()-planet.getX())/Particle.distance(star,planet); //cos and sin angles for star and planet
					double psin_angle = (star.getY()-planet.getY())/Particle.distance(star,planet);
					pFgX += ((star.getGravity()*star.getMass()*planet.getMass())/((Particle.distance(star,planet))*(Particle.distance(star,planet))))*pcos_angle; //force of gravity between star and planet in the x 
					pFgY += ((star.getGravity()*star.getMass()*planet.getMass())/((Particle.distance(star,planet))*(Particle.distance(star,planet))))*psin_angle; //force of gravity between star and planet in the y	
				
					planet.setxacc(pFgX/planet.getMass()); //planet x and y acc
					planet.setyacc(pFgY/planet.getMass());

					planet.setvelocityx(planet.getvelocityx()+planet.getxacc()* timestep); //planet x and y vel
					planet.setvelocityy(planet.getvelocityy()+planet.getyacc()* timestep);

					planet.setX(planet.getX() + (planet.getvelocityx() * timestep)); //planet x and y pos
					planet.setY(planet.getY() + (planet.getvelocityy()* timestep));
					double sFgX = 0; //sun x and y gravity
					double sFgY = 0;

					//the next 20 lines are the same as the lines above for the planet but just set functions for the sun
					double scos_angle = (planet.getX()-star.getX())/Particle.distance(star,planet);
					double ssin_angle = (planet.getY()-star.getY())/Particle.distance(star,planet);
					double scos_angle2 = (lpoints.get(i).getX() - star.getX())/Particle.distance(star,planet);
					double ssin_angle2 = (lpoints.get(i).getY()- star.getY())/Particle.distance(star,planet);

					sFgX = -(((star.getGravity()*star.getMass()*planet.getMass())/((Particle.distance(star,planet))*(Particle.distance(star,planet))))*scos_angle);
					sFgX -= ((star.getGravity()*star.getMass()*lpoints.get(i).getMass())/((Particle.distance(star,lpoints.get(i)))*(Particle.distance(star,lpoints.get(i)))))*scos_angle2;
					sFgY = -(((star.getGravity()*star.getMass()*planet.getMass())/((Particle.distance(star,planet))*(Particle.distance(star,planet))))*ssin_angle);							
					sFgY -= ((star.getGravity()*star.getMass()*lpoints.get(i).getMass())/((Particle.distance(star,lpoints.get(i)))*(Particle.distance(star,lpoints.get(i)))))*ssin_angle2;							
					star.setxacc(sFgX/star.getMass());
					star.setyacc(sFgY/star.getMass());

					star.setvelocityx(star.getvelocityx()+star.getxacc()* timestep);
					star.setvelocityy(star.getvelocityy()+star.getyacc()* timestep);

					star.setX(star.getX() + (star.getvelocityx()* timestep));
					star.setY(star.getY() + (star.getvelocityy()* timestep));
				}
			
				lframe.get(i).append(1, counter, theta); //adds new point to angle 

			}

			counter +=timestep;//adds time
			planet_trail.addPoint(planet.getX(),planet.getY());  //adds planet trail
		}
	}


	/**
	 *  resets the values of the bodies involved to the necessary quantities
	 */
	public void reset()
	{
		control.setValue("timestep", 10); //timestep
		control.setValue("G", 6.67E-11); //Gravity constant 
		control.setValue("X(star)", 0); //x pos of star
		control.setValue("Y(star)", 0); //y pos of star
		control.setValue("X_veloc(star)", 0); //x vel
		control.setValue("Y_veloc(star)", 0); //y vel
		control.setValue("mass star", 2E30); //2E20
		control.setValue("X(planet)", 1.5E11); //6E5
		control.setValue("Y(planet)", 0); //y planet
		control.setValue("X_veloc(planet)", 0);//x vel planet
		control.setValue("Y_veloc(planet)", 30000); //140
		control.setValue("Mass(planet)",6E24); //6E15
		control.setValue("L1", true);//L1 visible
		control.setValue("L2", true);//L2 visible
		control.setValue("L3", true);//L3 visible
		control.setValue("L4", true);//L4 visible
		control.setValue("L5", true);//L5 visible
		control.setValue("Mass(l)",2E3); //2E5
		star_trail.clear();
		planet_trail.clear();
		frame.removeDrawable(planet);
		frame.removeDrawable(star);
		for (int i = 0; i < lpoints.size(); i++) {
			frame.removeDrawable(lpoints.get(i));
			frame.removeDrawable(l_trail.get(i));
		}
	}	

	/**
	 * Intializes all the necessary data for the orbiting bodies, particularly calculating the positions of the Langrange points based on the masses of the bodies involved
	 */
	public void initialize() 
	{
		frame.addDrawable(planet_trail); //makes planet trail
		planet_trail.clear();
		frame.setPreferredMinMax(-control.getDouble("X(planet)"), control.getDouble("X(planet)"), -control.getDouble("X(planet)"), control.getDouble("X(planet)")); //set frame

		star.setXY(control.getDouble("X(star)"), control.getDouble("Y(star)")); //set star int pos
		planet.setXY(control.getDouble("X(planet)"), control.getDouble("Y(planet)")); //set planet int pos
		frame.setVisible(true);
		frame.addDrawable(star);
		frame.addDrawable(planet);
		star.setvelocityx(control.getDouble("X_veloc(star)")); //set star x vel
		star.setvelocityy(control.getDouble("Y_veloc(star)")); //set star y vel
		planet.setvelocityx(control.getDouble("X_veloc(planet)")); //set planet x vel
		planet.setvelocityy(control.getDouble("Y_veloc(planet)")); //set planet y vel
		star.setMass(control.getDouble("mass star"));//Sets mass of sun
		planet.setMass(control.getDouble("Mass(planet)"));//Sets mass of planet
		star.setGravity(control.getDouble("G")); //set gravity for sun
		planet.setGravity(control.getDouble("G"));//Set Gravitational Constant
		star.pixRadius = 25;
		star.color = Color.yellow; //makes star yellow
		planet.color = Color.blue; //makes planet blue
		planet.pixRadius = 9;

		int lcounter = 0;
		L1_x = star.getX() + Particle.distance(planet, star) - Particle.distance(planet, star) * Math.pow(planet.getMass()/(3*star.getMass()), .3333333); //sets x pos of L1
		L1_y = star.getY(); //sets y pos of L1

		L2_x = planet.getX() + Particle.distance(planet, star) * Math.pow(planet.getMass()/(3*star.getMass()), .3333333); //sets x pos of L2
		L2_y = star.getY(); //sets y pos of L2

		L3_x = star.getX() - (Particle.distance(planet, star) - Particle.distance(planet, star)*7*planet.getMass()/(12*star.getMass()));//sets x pos of L3
		L3_y = star.getY(); //sets y pos of L3

		L4_x = Particle.distance(planet, star)/2; //sets x pos of L4
		L4_y = Particle.distance(planet, star)/2 * Math.sqrt(3);//sets y pos of L4

		L5_x = L4_x; //sets x pos of L5
		L5_y = -L4_y; //sets y pos of L5 (opposite of y pos of L4)
		if(control.getBoolean("L1") == true) //necessary intialization for L1
		{
			lpoints.add(lcounter, new Particle());
			lpoints.get(lcounter).setXY(L1_x, L1_y);
			lpoints.get(lcounter).setvelocityx(planet.getvelocityx());
			lpoints.get(lcounter).setvelocityy(planet.getvelocityy());
			lnames.add(lcounter, "L1");
			lcounter++;

		}
		if(control.getBoolean("L2") == true)//necessary intialization for L2
		{
			lpoints.add(lcounter, new Particle());
			lpoints.get(lcounter).setXY(L2_x, L2_y);
			lpoints.get(lcounter).setvelocityx(planet.getvelocityx());
			lpoints.get(lcounter).setvelocityy(planet.getvelocityy());
			lnames.add(lcounter, "L2");
			lcounter++;
		}
		if(control.getBoolean("L3") == true)//necessary intialization for L3
		{
			lpoints.add(lcounter, new Particle());
			lpoints.get(lcounter).setXY(L3_x, L3_y);
			lpoints.get(lcounter).setvelocityx(planet.getvelocityx());
			lpoints.get(lcounter).setvelocityy(-planet.getvelocityy());
			lnames.add(lcounter, "L3");
			lcounter++;
		}
		double speed = Math.sqrt(planet.getvelocityx()*planet.getvelocityx() + planet.getvelocityy()*planet.getvelocityy());//necessary intialization for L4

		if(control.getBoolean("L4") == true)
		{
			lpoints.add(lcounter, new Particle());
			lpoints.get(lcounter).setXY(L4_x, L4_y);
			lpoints.get(lcounter).setvelocityx(-speed * Math.sin(Math.toRadians(60)));
			lpoints.get(lcounter).setvelocityy(speed * Math.cos(Math.toRadians(60)));
			lnames.add(lcounter, "L4");
			lcounter++;
		}
		if(control.getBoolean("L5") == true)//necessary intialization for L5
		{
			lpoints.add(lcounter, new Particle());
			lpoints.get(lcounter).setXY(L5_x, L5_y);
			lpoints.get(lcounter).setvelocityx(speed * Math.sin(Math.toRadians(60)));
			lpoints.get(lcounter).setvelocityy(speed * Math.cos(Math.toRadians(60)));
			lnames.add(lcounter, "L5");
			lcounter++;
		}
		int prev_x = x1;
		int prev_y = y1;
		for (int i = 0; i < lpoints.size(); i++) {

			lpoints.get(i).color = Color.black;
			lpoints.get(i).setMass(control.getDouble("Mass(l)"));//Sets mass of planet
			frame.addDrawable(lpoints.get(i));
			l_trail.add(i, new Trail());
			final float hue = rand.nextFloat();
			final float saturation = rand.nextFloat();//1.0 for brilliant, 0.0 for dull
			final float luminance = 1.0f; //1.0 for brighter, 0.0 for black
			Color color = Color.getHSBColor(hue, saturation, luminance); //sets hhe color

			l_trail.get(i).color = color;

			frame.addDrawable(l_trail.get(i));
			l_trail.get(i).clear();
			lframe.add(i, new PlotFrame("Time", "atehT", lnames.get(i) + " Stability Graph")); //graphs the stability fo the langrange points
			lframe.get(i).setVisible(true); //makes visible
			lframe.get(i).setPreferredMinMaxY(0, 180);
			int cur_x = 0; //the current x psotion
			int cur_y = prev_y; //the current y position
			if(prev_x == 1080)
			{
				cur_y = prev_y + 330; //sets mindow size
			}
			else 
			{
				cur_x = prev_x+360; //sets frame psotion
			}
			lframe.get(i).setLocation(cur_x, cur_y);
			prev_x = cur_x;
			prev_y = cur_y;
			lpoints.get(i).pixRadius = 5; //sets the size
			
			
		}
	}

	/**
	 * Runs the simulation
	 * @param args
	 */
	public static void main(String[] args) 
	{
		frame.setLocation(x1, y1); //sets the location of the window to the upper-left hand corner
		SimulationControl.createApp(new OrbitalLangrange()); //runs simulation
	}

}
