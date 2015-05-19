package riemann_sum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.*;

/**
 * This class is named the "MidpointRule." It extends Riemann.  Graphically and numerically, it works almost the same as the LeftHandRule and RightHandRule classes.
 * However, rather than evaluating each rectangle height as f(sleft) or f(sright) the class takes the midpoint of the interval (sleft+sright)/2
 * and takes the function value of the polynomial at that point.
 * @author Philip Clark
 * @slice: works just like RHR.slice except with (sleft+sright)/2 as a parameter
 * @slicePlot: creates a rectangle with the necessary dimensions
 */
public class MidpointRule extends Riemann
{
	
	/** evaluates the slice using the width and the height f((sleft+sright)/2)
	 *  @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @see riemann_sum.Riemann#slice(polyfun.Polynomial, double, double)
	 * @return area of the slice
	 */
	public double	slice(polyfun.Polynomial poly, double sleft, double sright) 
	{
		return PolyPractice.eval(poly, (sleft+sright)/2) * (sright-sleft); //approximates the area using a slice whose height is the f(midpoint of sleft and sright)
	}

	/** graphs the slice using a rectangle with the necessary dimensions
	 * @pframe the frame used for graphing the riemann sum
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 */
	@Override
	public void slicePlot(PlotFrame pframe, Polynomial poly, double sleft,
			double sright) { //creates a rectangle with the necessary dimensions
		DrawableShape rec = DrawableShape.createRectangle(((sleft+sright)/2), PolyPractice.eval(poly, (sleft+sright)/2)/2, sright-sleft, Math.abs(PolyPractice.eval(poly, (sleft+sright)/2)));
		pframe.addDrawable(rec); //makes rec visible
		
	}
	
}
