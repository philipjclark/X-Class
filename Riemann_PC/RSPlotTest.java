package test_classes;

import java.awt.Color;

import riemann_sum.*;
import polyfun.Polynomial;

import org.opensourcephysics.frames.*;

public class RSPlotTest 
{
	public static void main(String[] args) 
	{
		LeftHandRule LHR = new LeftHandRule();  //LeftHandRule extends Riemann
		RightHandRule RHR = new RightHandRule();  //RightHandRule extends Riemann
		TrapezoidRule TR = new TrapezoidRule(); //TrapezoidRule extends Riemann
		SimpsonsRule SR = new SimpsonsRule(); //Simpsons extends Riemann
		RandomRule RR = new RandomRule(); //RandomRule extends Riemann
		MinimumRule MR = new MinimumRule(); //MinimumRule extends Riemann
		MaximumRule MaR = new MaximumRule(); //MaximumRule extends Riemann
		MidpointRule Mid = new MidpointRule(); //Midpoint extends Riemann
		
		Polynomial p = new Polynomial(new double[] {10, 0, 3}); // p=x^3+10x
		
		int x1 = 0;
		int y1 = 30;
		PlotFrame f = new PlotFrame("x","y","Left Hand Riemann Sum Graph"); //instantiates the frame
		f.setPreferredMinMaxX(-10, 10); //sets range from -10 to 10
		f.setDefaultCloseOperation(3); //closes operation
		f.setVisible(true); //makes tbe frame visible
		f.setLocation(x1, y1); //sets the location of the window to the upper-left hand corner
		
		
		PlotFrame g = new PlotFrame("x","y","Right Hand Riemann Sum Graph"); //see above
		g.setPreferredMinMaxX(-10, -10);
		g.setDefaultCloseOperation(3);
		g.setVisible(true);
		g.setLocation(x1+310, y1);

		PlotFrame x = new PlotFrame("x","y","Simpson Riemann Sum Graph"); //see above
		x.setPreferredMinMaxX(-10, 10);
		x.setDefaultCloseOperation(3);
		x.setVisible(true);
		x.setLocation(x1+620, y1);
		
		PlotFrame a = new PlotFrame("x","y","Random Riemann Sum Graph"); //see above
		a.setPreferredMinMaxX(-10, 10);
		a.setDefaultCloseOperation(3);
		a.setVisible(true);
		a.setLocation(x1+930, y1);
		
		PlotFrame b = new PlotFrame("x","y","Minimum Riemann Sum Graph"); //see above
		b.setPreferredMinMaxX(-10, 10);
		b.setDefaultCloseOperation(3);
		b.setVisible(true);
		b.setLocation(x1, y1+330);
		
		PlotFrame d = new PlotFrame("x","y","Maximum Riemann Sum Graph"); //see above
		d.setPreferredMinMaxX(-10, 10);
		d.setDefaultCloseOperation(3);
		d.setVisible(true);
		d.setLocation(x1+310, y1+330);
		
		PlotFrame c = new PlotFrame("x","y","Trap Riemann Sum Graph"); //see above
		c.setPreferredMinMaxX(-10, 10);
		c.setDefaultCloseOperation(3);
		c.setVisible(true);
		c.setLocation(x1+620, y1+330);
		
		PlotFrame e = new PlotFrame("x","y","Midpoint Riemann Sum Graph"); //see above
		e.setPreferredMinMaxX(-10, 10);
		e.setDefaultCloseOperation(3);
		e.setVisible(true);
		e.setLocation(x1+930, y1+330);

		int recnum = 10; 
		double precision = .1;
		LHR.rsPlot(f, p, 1, precision, -4, 5, recnum); // the left hand rule from x=-4 to x=5, with 10 rectangles	
		
		RHR.rsPlot(g, p, 1, precision, -4, 5, recnum); // the right hand rule from x=-4 to x=5, with 10 rectangles	
		
		SR.rsPlot(x, p, 1, precision, -4, 5, 3); // the Simpson's rule from x=-4 to x=5, with 3 parabolas
		
		RR.rsPlot(a, p, 1, precision, -4, 5, recnum); // the Random rule from x=-4 to x=5, with 10 rectangles
		
		MR.rsPlot(b, p, 1, precision, -10, 10, recnum); //the Midpoint rule from x=-4 to x=5, with 10 rectangles
		
		MaR.rsPlot(d, p, 1, precision, -10, 10, recnum); //the Maximum rule from x=-4 to x=5, with 10 rectangles
		
		TR.rsPlot(c, p, 1, precision, -4, 5, recnum); //the trapezoid rule from x=-4 to x=5, with 10 rectangles
		
		Mid.rsPlot(e, p, 1, precision, -4, 5, recnum); //the midpoint rule from x=-4 to x=5, with 10 rectangles
	}
}	