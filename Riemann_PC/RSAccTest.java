package test_classes;

import riemann_sum.*;
import polyfun.Polynomial;

import org.opensourcephysics.frames.*;

public class RSAccTest 
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
		
		Polynomial p = new Polynomial(new double[] {0,0, 1}); // p=x^2+x
	
		int x1 = 0;
		int y1 = 30;
		
		PlotFrame f = new PlotFrame("x","y","Left Hand Rule Accumulation Function Graph");
		f.setPreferredMinMaxX(-30, 30);
		f.setDefaultCloseOperation(3);
		f.setVisible(true);
		f.setLocation(x1, y1); //sets the location of the window to the upper-left hand corner

		PlotFrame g = new PlotFrame("x","y","Right Hand Rule Accumulation Function Graph");
		g.setPreferredMinMaxX(-30, 30);
		g.setDefaultCloseOperation(3);
		g.setVisible(true);
		g.setLocation(x1+310, y1);

		
		PlotFrame x = new PlotFrame("x","y","Simpson Accumulation Function Graph"); //see above
		x.setPreferredMinMaxX(-30, 30);
		x.setDefaultCloseOperation(3);
		x.setVisible(true);
		x.setLocation(x1+620, y1);
		
		PlotFrame a = new PlotFrame("x","y","Random Accumulation Function Graph"); //see above
		a.setPreferredMinMaxX(-30, 30);
		a.setDefaultCloseOperation(3);
		a.setVisible(true);
		a.setLocation(x1+930, y1);
		
		PlotFrame b = new PlotFrame("x","y","Minimum Accumulation Function Graph"); //see above
		b.setPreferredMinMaxX(-30, 30);
		b.setDefaultCloseOperation(3);
		b.setVisible(true);
		b.setLocation(x1, y1+330);
		
		PlotFrame d = new PlotFrame("x","y","Maximum Accumulation Function Graph"); //see above
		d.setPreferredMinMaxX(-3, 3);
		d.setDefaultCloseOperation(3);
		d.setVisible(true);
		d.setLocation(x1+310, y1+330);
		
		PlotFrame i = new PlotFrame("x","y","Trapezoid Rule Accumulation Function Graph"); //see above
		i.setPreferredMinMaxX(-30, 30);
		i.setDefaultCloseOperation(3);			
		i.setVisible(true);
		i.setLocation(x1+620, y1+330);
		
		PlotFrame e = new PlotFrame("x","y","Midpoint Accumulation Function Graph"); //see above
		e.setPreferredMinMaxX(-30, 30);
		e.setDefaultCloseOperation(3);
		e.setVisible(true);
		e.setLocation(x1+930, y1+330);
			
		
		//note that the last rule to plot (right now Maximum Rule) takes somewhat longer than the others
		LHR.rsAcc(f, p, 2, .1, -1.0); //plots the left hand rule accumulation function of x^2+x, with base x=-1;
			
		RHR.rsAcc(g, p, 2, .1, -1.0); //plots the right hand rule accumulation function of x^2+x, with base x=-1;
			
		TR.rsAcc(i, p, 2, .1, -1.0); //plots the trapezoid rule accumulation function of x^2+x, with base x=-1;
		
		Mid.rsAcc(e, p, 2, .1, -1.0); //plots midpoint rule accumulation function of x^2+x, with base x=-1;	
		
		SR.rsAcc(x, p, 2, .1, -1.0); //plots simpsons rule accumulation function of x^2+x, with base x=-1;	

		RR.rsAcc(a, p, 2, .1, -1.0); //plots random rule accumulation function of x^2+x, with base x=-1;	

		MR.rsAcc(b, p, 2, .1, -1.0); //plots minimum rule accumulation function of x^2+x, with base x=-1;	

		MaR.rsAcc(d, p, 2, .1, -1.0); //plots maximum rule accumulation function of x^2+x, with base x=-1;
	

	}
}	