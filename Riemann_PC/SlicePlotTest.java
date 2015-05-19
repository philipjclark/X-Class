package test_classes;

import polyfun.Polynomial;

import org.opensourcephysics.frames.*;

import riemann_sum.*;

public class SlicePlotTest 
{
	public static void main(String[] args) 
	{
		LeftHandRule LHR = new LeftHandRule();  //LeftHandRule extends Riemann
		RightHandRule RHR = new RightHandRule();  //RightHandRule extends Riemann
		TrapezoidRule TR= new TrapezoidRule(); //TrapezoidRule extends Riemann
		SimpsonsRule SR = new SimpsonsRule(); //Simpsons extends Riemann
		RandomRule RR = new RandomRule(); //RandomRule extends Riemann
		MinimumRule MR = new MinimumRule(); //MinimumRule extends Riemann
		MaximumRule MaR = new MaximumRule(); //MaximumRule extends Riemann
		MidpointRule Mid = new MidpointRule(); //Midpoint extends Riemann
	
		Polynomial p = new Polynomial(new double[] {0,4,0,3}); // p=3x^3+4x
			
		int x1 = 0;
		int y1 = 30;
		PlotFrame f = new PlotFrame("x","y","Left Hand Riemann Slice Graph"); //instantiates the frame
		f.setPreferredMinMaxX(-10, 10); //sets range from -10 to 10
		f.setDefaultCloseOperation(3); //
		f.setVisible(true); //makes tbe frame visible
		f.setLocation(x1, y1); //sets the location of the window to the upper-left hand corner
		
		
		PlotFrame g = new PlotFrame("x","y","Right Hand Riemann Slice Graph"); //see above
		g.setPreferredMinMaxX(-10, -10);
		g.setDefaultCloseOperation(3);
		g.setVisible(true);
		g.setLocation(x1+310, y1);

		PlotFrame x = new PlotFrame("x","y","Simpson Riemann Slice Graph"); //see above
		x.setPreferredMinMaxX(-10, 10);
		x.setDefaultCloseOperation(3);
		x.setVisible(true);
		x.setLocation(x1+620, y1);
		
		PlotFrame a = new PlotFrame("x","y","Random Riemann Slice Graph"); //see above
		a.setPreferredMinMaxX(-10, 10);
		a.setDefaultCloseOperation(3);
		a.setVisible(true);
		a.setLocation(x1+930, y1);
		
		PlotFrame b = new PlotFrame("x","y","Minimum Riemann Slice Graph"); //see above
		b.setPreferredMinMaxX(-10, 10);
		b.setDefaultCloseOperation(3);
		b.setVisible(true);
		b.setLocation(x1, y1+330);
		
		PlotFrame d = new PlotFrame("x","y","Maximum Riemann Slice Graph"); //see above
		d.setPreferredMinMaxX(-10, 10);
		d.setDefaultCloseOperation(3);
		d.setVisible(true);
		d.setLocation(x1+310, y1+330);
		
		PlotFrame c = new PlotFrame("x","y","Trap Riemann Slice Graph"); //see above
		c.setPreferredMinMaxX(-10, 10);
		c.setDefaultCloseOperation(3);
		c.setVisible(true);
		c.setLocation(x1+620, y1+330);
		
		PlotFrame e = new PlotFrame("x","y","Midpoint Riemann Slice Graph"); //see above
		e.setPreferredMinMaxX(-10, 10);
		e.setDefaultCloseOperation(3);
		e.setVisible(true);
		e.setLocation(x1+930, y1+330);
			
		LHR.slicePlot(f, p, 1, 1.1); //a left hand rule slice of 3x^3+4x, from x=1 to x=1.1
			
		RHR.slicePlot(g, p, 1, 1.1); //a right hand rule slice of 3x^3+4x, from x=1 to x=1.1
		
		SR.slicePlot(x, p, 1, 1.1); //a simpson rule "slice" (parabola) of 3x^3+4x, from x=1 to x=1.1

		RR.slicePlot(g, p, 1, 1.1); //a random rule slice of 3x^3+4x, from x=1 to x=1.1
		
		MR.slicePlot(g, p, 1, 1.1); //a minimum rule slice of 3x^3+4x, from x=1 to x=1.1

		MaR.slicePlot(g, p, 1, 1.1); //a maximum rule slice of 3x^3+4x, from x=1 to x=1.1
		
		Mid.slicePlot(g, p, 1, 1.1); //a midpoint rule slice of 3x^3+4x, from x=1 to x=1.1

		TR.slicePlot(c, p, 1, 1.1); //a trapezoid rule slice of 3x^3+4x, from x=1 to x=1.1

	}
}