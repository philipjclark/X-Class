package riemann_sum;

import java.awt.BasicStroke;
import java.awt.Color;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;
/*
 * Philip Clark
 * September, 2013
 * TrapezoidRule: The TrapezoidRule class extends Riemann.  Numerically, it evaluates the area
 * under a curve via taking (f(sleft)+f(sright))/2 for the height of slice rectangles.  RS.TrapezoidRule 
 * adds up all of these rectangles with dimensions (sleft, (f(sleft)+f(sright))/2).  Graphically, the class
 * graphs a trapezoid with the above dimensions.
 */
public  class TrapezoidRule extends Riemann {	
	
	/** @method evaluates the slice uses the f((sright+sleft)/2) for the height of the slice
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @return area of the slice
	 */
	
	public double slice (polyfun.Polynomial poly, double sleft, double sright)  //like the others the slice function evaluates the slice in the given interval; specifically, the trapezoid rule takes the avg bases of the trapezoid formed by the interval and multiplies it by the "height" (delta)
	{
		return (PolyPractice.eval(poly, sleft) +  PolyPractice.eval(poly, sright))/2 * (sright-sleft);
	}
	

	/** graphs the trapezoid using trails
	 * @see riemann_sum.Riemann#slicePlot(org.opensourcephysics.frames.PlotFrame, polyfun.Polynomial, double, double)
	 *  @param pframe the frame where the riemann in graphed
	 * @param poly the polynomial whose area is evaluated
	 * @param sright left endpoint of the interval
	 * @param sright right endpoint of the interval
	 */
	
	public void slicePlot(PlotFrame pframe, Polynomial poly, double sleft, double sright) { //this function creates a slice plot of a trapezoid by creating a trail from (sleft,0) to (sleft, f(sleft)) to (sright, f(sright)) to (sright, 0)
				
		Trail trail = new Trail(); //creates
		trail.addPoint(sleft, 0);
		trail.addPoint(sleft, PolyPractice.eval(poly, sleft));
		trail.addPoint(sright, PolyPractice.eval(poly, sright));
		trail.addPoint(sright, 0);
		trail.closeTrail(); //closes the trail
		BasicStroke stroke = new BasicStroke(5);
		trail.setStroke(stroke);
		trail.color = Color.GREEN;
		pframe.addDrawable(trail); //sets the trail to be visisble
		
	}
}
