package AML_Orbital;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.DisplayFrame;

/**
 * @author  Philip Clark
 *
 */
public class Particle extends Circle
{

	//setters and getters
	/**
	 * gets the G constant
	 * @return the value of the G constant
	 */
	public double getGravity() 
	{
		return gravity;
	}
	
	/**
	 * sets the timestep
	 * @param timestep the timestep of the simulation
	 */
	public void setTimestep(double timestep)
	{
		this.timestep = timestep;
	}
	
	/**
	 * gets the timestep
	 * @return the timestep of the simulation
	 */
	public double getTimestep()
	{
		return timestep;
	}

	/**
	 * sets the G constant
	 * @param gravity the G constant
	 */
	public void setGravity(double gravity) 
	{
		this.gravity = gravity;
	}

	/**
	 * gets the x velocity
	 * @return the x velocity
	 */
	public double getvelocityx() 
	{
		return velocityx;
	}

	/**
	 * sets the value of the x velocity
	 * @param velocityx the x velocity
	 */
	public void setvelocityx(double velocityx) 
	{
		this.velocityx = velocityx;
	}

	/**
	 * gets the y velocity
	 * @return the y velocity
	 */
	public double getvelocityy() 
	{
		return velocityy;
	}

	/**
	 * sets the value of the y velocity
	 * @param velocityy the y velocity
	 */
	public void setvelocityy(double velocityy) 
	{
		this.velocityy = velocityy;
	}

	/**
	 * gets the x acceleration
	 * @return the x acceleration
	 */
	public double getxacc() 
	{
		return xacc;
	}

	/**
	 * sets the value of the x acc
	 * @param xacc the x acceleration
	 */
	public void setxacc(double xacc) 
	{
		this.xacc = xacc;
	}



	
	
	/**
	 * gets the value of the y acc
	 * @return the yacc
	 */
	public double getyacc() 
	{
	return yacc;
	}
	/**
	 * gets the mass of the particle
	 * @return the mass of the particle
	 */
	public double getMass() 
	{
		return mass;
	}
	/**
	 * sets the mass of the particle
	 * @param mass the mass of the particle
	 */
	public void setMass(double mass)
	{
		this.mass = mass;
	}

	/**
	 * sets the value of the y acc
	 * @param yacc the value of the y acc
	 */
	public void setyacc(double yacc) {
		this.yacc = yacc;
	}
	
	private double initialX;//the initial x position
	private double initialY;//the initial y position
	private double gravity; //the G constant
	private double velocityx; //the x vel
	private double velocityy;// the y vel
	private double xacc;//the xacc
	private double mass;//the mass of the particle
	private double timestep;//the timestep
	private double yacc;//the yacc



	/**
	 * sets the initial x pos
	 * @param x the initial x pos
	 */
	public void setInitialX(double x)
	{
		this.initialX = x;
	}
	/**
	 * sets the initial y pos
	 * @param y the initial y pos
	 */
	public void setInitialY(double y)
	{
		this.initialY = y;
	}
	/**
	 * gets the initial x pos
	 * @return the initial x position
	 */
	public double getInitialX ()
	{
		return initialX;
	}
	/**
	 * gets the initial y pos
	 * @return the initial y position
	 */
	public double getInitialY ()
	{
		return initialY;
	}

	
	/**
	 * Uses measures of distance between the extrema, initial planetary position, and star to determine whether the orbit has been completed
	 * @param planet the orbiting planet
	 * @param star the star
	 * @param p_int a particle with the orbiting planet's initial x and y position
	 * @param time the current time
	 * @param first_dist the distance between the initial planet position and the star
	 * @param first_check the distance between the first extrema and the initial planet position
	 * @param timestep the timestep of the simulation
	 * @return whether the orbit has been completed
	 */
	public boolean completed(Particle planet, Particle star, Particle p_int, double time, double first_dist, double first_check, double timestep)
	{
		
			if(Particle.distance(planet, star) < first_dist*2 && Particle.distance(planet, p_int) < first_check*2)//checks to see if the distance of the planet is currently at the same spot as where it started
			{
				if(time > 50/.1*timestep) //makes sure enough time has elapsed
				{
					return true;
				}
		}
		return false;

	}


	/**
	 * Uses the distance formula to find the distance between two planets
	 * @param planet1 one of the planets whose distance is being measured
	 * @param planet2 the other planet whose distance is being measured
	 * @return the distance
	 */
	public static double distance(Particle planet1, Particle planet2)
	{
		return Math.sqrt((planet1.getX()-planet2.getX())*(planet1.getX()-planet2.getX())+(planet1.getY()-planet2.getY())*(planet1.getY()-planet2.getY())); //dist formula
	}



}
