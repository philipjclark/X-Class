package riemann_sum;

import java.awt.Color;

import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;

/**
 * Simpson's rule is perhaps the most accurate of all of the Reimann graphic classes. 
 * Numerically, it takes the weighted average of 2* the trapezoid rule + 1*the midpoint rule.
 * Graphically, it uses quadratic interpolation to fit a parabola to 3 points (left endpoint, midpoint, and right endpoint) over each interval.
 * @author Philip Clark
 * @method slice: approximates the area of the slice using Simpsons rule, which can be evaluated by doing a weighted avg of 2*trap rule + 1*midpoint rule
 * @method slicePlot uses a systems of equations to find the coefficients of the interpolated quadratic and then proceeds to graph the function on the interval using trails
 */

public  class SimpsonsRule extends Riemann {	
	MidpointRule MR = new MidpointRule();  //LeftHandRule extends Riemann
	TrapezoidRule TR = new TrapezoidRule();  //RightHandRule extends Riemann
	
	/** simpsons rule is numerically equal to the weighted average of 2*the midpoint rule + 1*the trapezoid rule/3 which is used to evaluate the interpolated parabola area
	 * @param poly the polynomial used for the function
	 * @param  sleft furthermost left point of the interval
	 * @param sright furthermost right point of the interval
	 * @return x coordinate for the local min of the interval
	 */
	public double slice (polyfun.Polynomial poly, double sleft, double sright) 
	{
		return 2*MR.rs(poly, sleft, sright, 1)/3 + TR.rs(poly, sleft, sright, 1)/3; //approximates the area of the slice using simpsons rule, which can be evaluated by doing a weighted avg of 2*trap rule + 1*midpoint rule
	}
	
	/*** the function solves for the coefficients of an interpolated parabola to approximate the curve at the interval and then graphs it using trails
	 * @see riemann_sum.Riemann#slicePlot(org.opensourcephysics.frames.PlotFrame, polyfun.Polynomial, double, double)
	 * @param pframe the frame where the riemann in graphed
	 * @param poly the polynomial whose area is evaluated
	 * @param sright left endpoint of the interval
	 * @param sright right endpoint of the interval
	 */
	@Override
	public
	void slicePlot(PlotFrame pframe, Polynomial poly, double sleft,
			double sright) { // this function plots a slice of the parabola on the specified interval by finding the coefficients of a quadratic that fits the points (selft, f(sleft)), (midpoint, f(midpoint)), and (sright, f(sright)) and then plotting that quadratic on the interval

		double X1 = sleft; 
		double Y1 =  PolyPractice.eval(poly, sleft);
		double X2 = (sleft+sright)/2;
		double Y2 = PolyPractice.eval(poly, (sleft + sright)/2);
		double X3 = sright;
		double Y3 =  poly.evaluate(sright).getTerms()[0].getTermDouble();
		
		double a = ((Y2-Y1)*(X1-X3) + (Y3-Y1)*(X2-X1))/((X1-X3)*(X2*X2 - X1*X1) + (X2-X1)*(X3*X3-X1*X1)); //solved for a coefficient of the fitted quadratic via systems of equations
		double b = ((Y2-Y1) - a * (X2*X2 - X1*X1))/(X2-X1); //solved for b coefficient
		double c = Y1 - a *X1*X1 - b*X1; //solved for c coefficient
		
		for (double i = sleft; i < sright; i+=.01) { //the for loop shades in the area under the quadtratic using trails for a visual effect
			pframe.append(0, i, a*Math.pow(i, 2) + b*i + c);
			Trail trail = new Trail();
			trail.addPoint(i, a*Math.pow(i, 2) + b*i + c);
			trail.addPoint(i, 0);
			trail.color = Color.red;
			pframe.addDrawable(trail); //sets the trail to be visible
		}
		
		
		// TODO Auto-generated method stub
		
	}
}
