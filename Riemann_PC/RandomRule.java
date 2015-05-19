package riemann_sum;

import org.opensourcephysics.display.DrawableShape;
import org.opensourcephysics.frames.PlotFrame;

import java.util.Random;

import polyfun.Polynomial;

/**
 * This is the RandomRule Class.  It extends Riemann via inheritance and uses a random double to evaluate a polynomial by taking f(random point on the interval)
 * Quantitatively, the RandomRule class is used in a manner similar to right hand rule and left hand rule in that it picks a point which serves as the height of the rectangles used.
 * Graphically, the class graphs rectangles like the other Riemann classes, except it uses the f(random) as the height.
 * @author Philip Clark
 * @method slice: evaluates the slice just like the other Riemann clases, but uses sleft+(sright-sleft)*ran as the x coordinate (ie sleft + the delta *ran)
 * @method slicePlot: the function plots a rec with the necessary dimensions
 */


public  class RandomRule extends Riemann {	
	Random gen = new Random(); //generates a random generator
	double ran = gen.nextDouble(); //generates a random number
	
	/** evaluates the slice uses f(random) as a height of the slice
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @return area of the slice
	 */
	public double slice (polyfun.Polynomial poly, double sleft, double sright) //this function uses f(ran) as its height in evaluating the area of the slice
	{
		return PolyPractice.eval(poly, sleft + (sright-sleft)*ran) * (sright-sleft);
	}
	

	/** graphs the slice using a rectangle with the necessary dimensions
	 * @pframe the frame used for graphing the riemann sum
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 */
	public void slicePlot(PlotFrame pframe, Polynomial poly, double sleft, //creates a rectangle with the necessary dimensions
			double sright) {
		DrawableShape rec = DrawableShape.createRectangle(((sleft+sright)/2), PolyPractice.eval(poly, sleft + (sright-sleft)*ran)/2, sright-sleft, Math.abs(PolyPractice.eval(poly, sleft + (sright-sleft)*ran)));
		pframe.addDrawable(rec); //sets the rectangle to be visisble
		
	}
}
