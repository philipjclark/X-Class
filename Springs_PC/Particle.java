package SpringPackage;

import org.opensourcephysics.display.Circle;
import org.opensourcephysics.frames.*;

import riemann_sum.TrapezoidRule;

//Particle class
public class Particle extends Circle
{
	TrapezoidRule TR= new TrapezoidRule(); //TrapezoidRule extends Riemann
	double v_int; //the initial velocity of the launch
	double x_vint;
	double y_vint;
	double alpha; //air resistance constant
	//Setters and getters for various properties of the particles 
	
	private double gravity; //acc of gravity
	private double x_vel; //x velocity
	private double y_vel; //y velocity
	private double acc_x; //x acc
	private double acc_y; //y acc
	private double mass; //mass of particle
	private double xint; //initial x position of particle
	private double yint; // initial y position of particle
	private double timestep; //timestep

	public double getAlpha()
	{
		return alpha;
	}
	
	/**
	 * gets the current new alpha constant
	 * @param alpha the old alpha constant
	 */
	public void setAlpha(double alpha)
	{
		this.alpha = alpha;
	}
	
	/**
	 * getter for g
	 * @return gravity acceleration value
	 */
	public double getGravity()
	{
		return gravity;
	}

	/**
	 * setter for gravity
	 * @param g_acc the g constant being set
	 */
	public void setGravity(double g_acc)
	{
		this.gravity = g_acc;
	}

	/**
	 * getter for x velocity
	 * @return the x velocity of the Particle
	 */
	public double get_xvel() 
	{
		return x_vel;
	}

	/**
	 * setter for x velocity
	 * @param x_vel the x velocity valeu the particle is being set to
	 */
	public void set_xvel(double x_vel)
	{
		this.x_vel = x_vel;
	}

	/**
	 * getter for y velocity
	 * @return the particle's y velocity value
	 */
	public double get_yvel()
	{
		return y_vel;
	}

	/**
	 * setter for particle's y velocity
	 * @param y_vel the y velocity value being set
	 */
	public void set_yvel(double y_vel)
	{
		this.y_vel = y_vel;
	}

	/**
	 * getter for x acceleration
	 * @return the particle's x acceleration
	 */
	public double get_xacc()
	{
		return acc_x;
	}

	/**
	 * setter for x acceleration
	 * @param newx_acc sets the Particle's x acc
	 */
	public void set_xacc(double newx_acc)
	{
		this.acc_x = newx_acc - alpha * Math.abs(get_xvel()) *get_xvel(); //calculates the x acceleration given air resistance
	}

	/**
	 * getter for the Particle's y acceleration
	 * @return the value for the Particle's y acc
	 */
	public double get_yacc()
	{
		return acc_y;
	}
	
	/**
	 * setter for y acceleration
	 * @param newy_acc the value the Particle's y acc is being set to
	 */
	public void set_yacc(double newy_acc)
	{
		this.acc_y = newy_acc - alpha*Math.abs(get_yvel())*get_yvel(); //calculates the y velocity given air resistance
	}
	
	/**
	 * getter for the Particle's mass
	 * @return the particle's mass value
	 */
	public double getMass() //Getter for mass
	{
		return mass;
	}
	
	/**
	 * setter for Particle's mass
	 * @param mass the mass of the particle
	 */
	public void setMass(double mass) //Setter for mass
	{
		this.mass = mass;
	}
	
	/** getter for the starting x pos
	 * @return starting x pos
	 */
	public double getXstart ()
	{
		return xint;
	}
	
	/**
	 * sets the starting x pos
	 * @param start_x the x pos where the Particle starts
	 */
	public void setXstart(double start_x)
	{
		this.xint = start_x;
	}

	/**
	 * gets the starting y pos
	 * @return the starting y pos
	 */
	public double getStartingY ()
	{
		return yint;
	}
	/**
	 * sets the starting y position of the particle
	 * @param start_y the y position the particle is being set to at the beginning
	 */
	public void setStartingY(double start_y)
	{
		this.yint = start_y;
	}
	/**
	 * sets the timestep value
	 * @param ts the timestep value the particle is being set to
	 */
	public void setTimestep(double ts) 
	{
		this.timestep = ts;
	}
	/**
	 * gets the timestep interval
	 * @return the timestep value
	 */
	public double getTimestep()
	{
		return timestep;
	}
	
	/**
	 * the Particle constructor method sets values according to particle refresh
	 */
	public Particle()
	{
		x_vel = this.x_vel;
		y_vel = this.y_vel;
		timestep=this.timestep;
		gravity = this.gravity;
		acc_x = this.acc_x;
		acc_y = this.acc_y;
		xint = this.xint;
		yint = this.yint;
	}

	/**
	 * The getDist method takes in two particles and finds the magnitude of distance between them
	 * @param p1 one of the two particles whose distance is being measured
	 * @param p2 the other of the two particles
	 * @return the magnitude of the distance between p1 and p2
	 */
	public static double getDist(Particle p1, Particle p2) {
		double distance;
		double dist_x = p1.getX()-p2.getX();
		double dist_y = p1.getY()-p2.getY();
		double y = Math.pow(dist_y,2);
		if(y < .00001) //rounds off the squarted y distance component to prevent division by 0 (or an extremely small value that the computer reads as NaN)
		{
			y = .00001;
		}

		distance = Math.sqrt(y+Math.pow(dist_x,2));//plugs in dist formula
		return distance;
	}
}