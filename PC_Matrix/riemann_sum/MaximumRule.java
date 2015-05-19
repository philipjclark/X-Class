package riemann_sum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;

/**
 * The MaximumRule class extends Riemann via inheritance and uses a precision factor to evaluate a polynomial by taking f(maximum point on the interval)
 * Quantitatively, the getMin function is used to determined the maximum value of the polynomial in the interval (sleft, sright) via a sorting algorithim.
 * Graphically, the class graphs rectangles like the other Riemann classes, excpet it uses the f(max) as the height.
 * @author Philip Clark
 * @method slice: the function is based on the assumption that the values of polys change only gradually, so by creating a for loop that evaluates a very large number of points in the interval and sorting to find the one with the greatest value it finds the so-called "maximum" of the interval
 * @method slicePlot: the function plots a rec with the necessary dimensions
 */
public  class MaximumRule extends Riemann {		
	
	/** @method evaluates the slice uses the f(max) for the height of the slice
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @return area of the slice
	 */
	public double slice (polyfun.Polynomial poly, double sleft, double sright) //like the others, this function finds the area of the slice rectangle, but it uses getMax() as the x value for the height of the rect
	{
		return PolyPractice.eval(poly, getMax(poly, sleft, sright)) * (sright-sleft);
	}
	
	/** gets the local max of the interval
	 * @param the polynomial used for the function
	 * @param sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @return x coordinate for the local max of the interval
	 */
	double getMax (polyfun.Polynomial poly, double sleft, double sright) //the function is based on the assumption that the values of polys change only gradually, so by creating a for loop that evaluates a very large number of points in the interval and sorting to find the one with the greatest value it finds the so-called "maximum" of the interval
	{
		double max = sleft; //uses sleft as the starting value for max
		for (double i = sleft; i < sright; i+=.001) {
			if(poly.evaluate(i).getTerms()[0].getTermDouble() > poly.evaluate(max).getTerms()[0].getTermDouble()) //sees if the current f(i) > f(max)
			{
				max = i;
			}
		}
		return max;
	}
	
	/** graphs the slice using a rectangle with the necessary dimensions
	 * @pframe the frame used for graphing the riemann sum
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 */
	@Override
	public void slicePlot(PlotFrame pframe, Polynomial poly, double sleft, //the function plots a rec with the necessary dimensions
			double sright) {
		DrawableShape rec = DrawableShape.createRectangle(((sleft+sright)/2), PolyPractice.eval(poly, getMax(poly, sleft, sright))/2, sright-sleft, Math.abs(PolyPractice.eval(poly, getMax(poly, sleft, sright))));
		pframe.addDrawable(rec); //sets the rectangle to be visisble
	}
}
