package riemann_sum;

import java.awt.Color;

import org.opensourcephysics.frames.PlotFrame;

/* This is the maximum range calculator program. It takes in my initial conditions (1.0 m = y0, 0 = x0)
 * and an initial velocity and calculates for an optimizing theta and maximum distance.
 */
public class ProjectileMax {
	static PlotFrame max = new PlotFrame("Angle", "sreteM", "Max Distance One Click");
	static PlotFrame max2 = new PlotFrame("Angle", "sreteM", "Max Distance Two Click");
	static int click = 1;
	static double first_vel = 4.61;
	static int dataset = 1;
	static double sec_vel = 7.12;
	public static void getMax (double vel)
	{
		double maxdist = 0;
		double currentdist = 0;
		double bestangle = 0;
		for(double i = 0; i < 90; i+=.01)
		{
			currentdist = vel*Math.cos(Math.toRadians(i)) * (vel*Math.sin(Math.toRadians(i)) + Math.sqrt(2*9.80665*1.001 + vel*vel*Math.sin(Math.toRadians(i)) * Math.sin(Math.toRadians(i))))/9.80665;
			if (currentdist > maxdist)
			{
				maxdist = currentdist;
				bestangle = i;
			}
			
			if(click == 1)
			max.append(dataset, i, currentdist);
			
			else
			max2.append(dataset, i, currentdist);
			
			if(dataset > 1)
			max2.setMarkerColor(dataset, Color.red); //makes the minimum velocity marker red
		}
		System.out.println("The max distance for initial velocity of " + vel + " m/s (" + click + " click) is " + maxdist + " meters for an optimizing angle of " + bestangle + " degrees");
		max.setVisible(true);
		max2.setVisible(true);
	}
	public static void main(String[] args) {
	getMax(first_vel);
	dataset++;
	click++;
	getMax(sec_vel);
	}

}
