package riemann_sum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;

/**
 * The MinimumRule class extends Riemann via inheritance and uses a precision factor to evaluate a polynomial by taking f(minimum point on the interval)
 * Quantitatively, the getMin function is used to determined the minimum value of the polynomial in the interval (sleft, sright) via a sorting algorithim
 * Graphically, the class graphs rectangles like the other Riemann classes, excpet it uses the f(min) as the height
 * @author Philip Clark
 * @method slice: the function is based on the assumption that the values of polys change only gradually, so by creating a for loop that evaluates a very large number of points in the interval and sorting to find the one with the greatest value it finds the so-called "minimum" of the interval
 * @method slicePlot: the function plots a rec with the necessary dimensions
 */
public  class MinimumRule extends Riemann {		
	
	/** evaluates the slice uses the f(min) for the height of the slice
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @return area of the slice
	 */
	public double slice (polyfun.Polynomial poly, double sleft, double sright)  //same as MaximumRule.slice except for getMin
	{
		return PolyPractice.eval(poly, getMin(poly, sleft, sright)) * (sright-sleft);
	}
	
	/** gets the local min of the interval
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @return x coordinate for the local min of the interval
	 */
	double getMin (polyfun.Polynomial poly, double sleft, double sright) //the function is based on the assumption that the values of polys change only gradually, so by creating a for loop that evaluates a very large number of points in the interval and sorting to find the one with the least value it finds the so-called "minimum" of the interval
	{
		double min = sleft;
		for (double i = sleft; i < sright; i+=.001) {
			if(PolyPractice.eval(poly, i) < PolyPractice.eval(poly, min)) //sees if the current f(i) > f(min)
			{
				min = i;
			}
		}
		return min;
	}
	
	/** graphs the slice using a rectangle with the necessary dimensions
	 * @pframe the frame used for graphing the riemann sum
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 */
	@Override
	public void slicePlot(PlotFrame pframe, Polynomial poly, double sleft, //creates a rectangle with the necessary dimensions
			double sright) {
		DrawableShape rec = DrawableShape.createRectangle(((sleft+sright)/2), PolyPractice.eval(poly, getMin(poly, sleft, sright))/2, sright-sleft, Math.abs(PolyPractice.eval(poly, getMin(poly, sleft, sright))));
		pframe.addDrawable(rec); //sets the rectangle to be visisble
		
	}
}
