package AML_Projectile;


import java.awt.Frame;

import org.opensourcephysics.controls.*;
import org.opensourcephysics.display.*;
import org.opensourcephysics.frames.*;

import polyfun.Polynomial;
import riemann_sum.LeftHandRule;
import riemann_sum.SimpsonsRule;
import riemann_sum.TrapezoidRule;


/** This is the particle class.  It extends Circle and is responsible for creating the particle whose motion will be modeled
 * in particleApp.  The functions in this class deal with setting the initial y and x coordinates and velocities, constantly updating
 * the x and y accelerations, velocities, and coordinates, and all the getters and setters for the associated values of particle.
 * @author Philip Clark
 *
 */
/**

 *
 */
public class Particle extends Circle
{
	TrapezoidRule TR= new TrapezoidRule(); //TrapezoidRule extends Riemann
	double v_int; //the initial velocity of the launch
	double x_vint;
	double y_vint;
	//Setters and getters for various properties of the particles 

	double alpha;
	double theta;

	/**
	 * @return gets the x velocity
	 */
	public double getvelocityx() 
	{	
		return velocityx;
	}


	/**
	 * Calculates the new x position with riemann sum
	 * @return returns the x position
	 */
	public double getPositionx ()
	{
		return getX() + (getvelocityx()*delta); //updates the x position using a rimeann sum
		//creates polynomial 
		//Polynomial pos_graph = new Polynomial(new double[] {vint, acc});
		//System.out.println(TR.slice(pos_graph, time, time+delta));
		//return pos_old = pos_old + TR.slice (pos_graph,time,time+delta);
	}
	
	/**
	 * Calculates the new y position with riemann sum
	 * @return returns the y position
	 */
	public double getPositiony ()
	{
		return getY() + (getvelocityy()*delta); //updates the y position using a rimeann sum
		//creates polynomial 
		//Polynomial pos_graph = new Polynomial(new double[] {vint, acc});
		//System.out.println(TR.slice(pos_graph, time, time+delta));
		//return pos_old = pos_old + TR.slice (pos_graph,time,time+delta);
	}

	/**
	 * @param velocityx the current x velocity
	 * @param time the time counter for the program
	 * @param delta the delta for time
	 */
	public void setvelocityx(double velocityx, double time, double delta) 
	{
		//creates a polynomial whose value is that of the xacceleration 
		Polynomial vx_graph = new Polynomial(new double[] {xacc}); 
		this.velocityx = velocityx + TR.slice (vx_graph,time,time+delta); //uses the trapezoid rule to calculate the slice and update the x velocity
	}

	/**
	 * @return returns the y velocity
	 */
	public double getvelocityy() 
	{
		return velocityy;
	}

	/**
	 * Uses a riemann sum to calculate the new y velocity
	 * @param velocityy the current x velocity
	 * @param time the time counter for the program
	 * @param delta the delta for time
	 */
	public void setvelocityy(double velocityy, double time, double delta) 
	{
		Polynomial vy_graph = new Polynomial(new double[] {yacc}); 
		this.velocityy = velocityy + TR.slice (vy_graph,time,time+delta); //uses the trapezoid rule to calculate the slice and update the y velocity
		//this.velocityy = velocityy;
	}

	/**
	 * @return gets the current x acceleration
	 */
	public double getxacc() 
	{
		return xacc;
	}

	/**
	 * Uses a riemann sum to calculate the new x acceleration
	 * @param xacc takes in the current x acceleration
	 */
	public void setxacc(double xacc) 
	{
		this.xacc = - alpha * getvelocityx() *getvelocityx(); //calculates the x acceleration given air resistance
	}


	private double gravity;
	private double velocityx;
	private double velocityy;
	private double xacc;
	double mass;

	/**
	 * @return returns the curren y acceleration
	 */
	public double getyacc() {
		return yacc;
	}
	/**
	 * @return returns the mass of the particle
	 */
	public double getMass() 
	{
		return mass;
	}

	/** Sets the new value of the mass of the particle
	 * @param mass 
	 */
	public void setMass(double mass)
	{
		this.mass = mass;
	}

	/**
	 * @return returns the alpha constant
	 */
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
	 * @return returns the initial magnitude of velocity
	 */
	public double getV_int()
	{
		return v_int;
	}

	/**
	 * Sets the new value of the velocity
	 * @param v_int the initial magnitude of velocity
	 */
	public void setV_int(double v_int)
	{
		this.v_int = v_int;
	}

	/**
	 * @return returns the launch angle theta
	 */
	public double getTheta()
	{
		return v_int;
	}
	
	

	/**
	 * Returns the new launch angle
	 * @param theta the launch angle
	 */
	public void setTheta(double theta)
	{
		this.theta = theta;
	}

	/**
	 * Uses the air resistance model yacc = g - alpha*v^2 to modify the y accelerationof the particle
	 * @param yacc takes in the current y acceleration
	 */
	public void setyacc(double yacc) {		
			if((Math.abs(getvelocityy()) < Math.sqrt(10/alpha)-.001) || getvelocityy() > 0) //creates an artificial asymptote to avoid the complications associated as the particle approached terminal velocity in minLaunch
			{
				this.yacc = -10 - alpha*Math.abs(getvelocityy())*getvelocityy(); //calcualtes the y velocity given air resistance

			}
			else
			{
				this.yacc = -.01;
			}		
	}	

	private double yacc;

	private double initialX;
	private double initialY;
	double delta;

	/**
	 * sets the initial x pos
	 * @param xint takes in the initial x position
	 */
	public void setInitialX(double xint)
	{
		this.initialX = xint;
	}
	/**
	 * sets the initial y pos
	 * @param yint takes in the initial y position
	 */
	public void setInitialY(double yint)
	{
		this.initialY = yint;
	}
	/**
	 * @return returns the initial x position
	 */
	public double getInitialX ()
	{
		return initialX;
	}
	/**
	 * @return returns the initial y position
	 */
	public double getInitialY ()
	{
		return initialY;
	}

	public Particle() //sets the different values of the properties of the particle object
	{
		gravity = this.gravity;
		velocityx = this.velocityx;
		velocityy = this.velocityy;
		xacc = this.xacc;
		yacc = this.yacc;
		initialX = this.initialX;
		initialY = this.initialY;

	}
	
	/**
	 * This is the Step method.  It is simply a modified version of the doStep that is called in minLaunch.
	 * @param time the counter that keeps track of time
	 * @param p the particle
	 */
	public void Step(double time, Particle p) //the step function is essentially a modified version of doStep
	{
		if (time == 0) //if it is the first time the doStep is being run it sets the initial x and y positons and velocities for the particle
		{
			p.setInitialX(p.getX());
			p.setInitialY(p.getY());
			p.setvelocityx(p.x_vint, time, p.delta);
			p.setvelocityy(p.y_vint, time, p.delta);
		}
		
		p.setyacc(p.getyacc());
		p.setxacc(p.getxacc());

		p.setvelocityx(p.getvelocityx(), time, p.delta);
		p.setvelocityy(p.getvelocityy(), time, p.delta);

		p.setX(p.getPositionx());
		p.setY(p.getPositiony());
		
		time+=p.delta; //increments time
	}


	/**
	 * @param delta takes the value of the delta t
	 */
	public void setdelta(double delta) {
		this.delta=delta;

	}

}
