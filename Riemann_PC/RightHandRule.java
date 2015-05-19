package riemann_sum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;
/*
 * Philip Clark
 * September, 2013
 * RightHandRUle: The LeftHandRule class extends Riemann.  Numerically, it evaluates the area
 * under a curve via taking f(sright) for the height of slice rectangles.  RS.LeftHandRule 
 * adds up all of these rectangles with dimensions ((sleft+sright)/2, f(sright)).  Graphically, the class
 * graphs a rectangle with the above dimensions.
 */
public  class RightHandRule extends Riemann {	
	
	/** @method evaluates the slice uses the f(sright) for the height of the slice
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @return area of the slice
	 */
	public double slice (polyfun.Polynomial poly, double sleft, double sright) //gets the area of the rectangular slice using the f(sleft) as its height
	{
		return PolyPractice.eval(poly, sright) * (sright-sleft);
	}
	

	/**
	 * @method graphs the slice using a rectangle with the necessary dimensions
	 * @pframe the frame used for graphing the riemann sum
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @see riemann_sum.Riemann#slicePlot(org.opensourcephysics.frames.PlotFrame, polyfun.Polynomial, double, double)
	 */
	@Override
	public void slicePlot(PlotFrame pframe, Polynomial poly, double sleft, //creates a rectangle with the necessary dimensions
			double sright) {
	 DrawableShape rec = DrawableShape.createRectangle(((sleft+sright)/2), PolyPractice.eval(poly, sright)/2, sright-sleft, Math.abs(PolyPractice.eval(poly, sright)));
		pframe.addDrawable(rec); //sets the rectangle to be visisble
	}
}
