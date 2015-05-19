package riemann_sum;
/*
 * Philip Clark
 * September 2013
 * The Riemann class is an abstract that onto which all of the individual rules are extended.
 * It contains three key methods: rs, rsAcc, and rsPlot which estimates the area under a curve,
 * graphs the accumulation function of that area, and plots the slices used to evaluate that area
 * respectively.
 */

import java.awt.Color;

import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;

public abstract class Riemann   {
	
	/** This method calculates a Riemann sum.
	 * @param poly the imported poly
	 * @param left the leftmost point of the interval
	 * @param right the rightmost point of the interval
	 * @param subintervals number of subintervals used
	 * @return total area of riemann sum
	 */
	public double rs(polyfun.Polynomial poly, double left, double right, int subintervals) //the function adds up all of the slices of a particular type of rule via a for loop for the number of subintervals
	{
		
		double area = 0;
		for (int i = 0; i < subintervals; i++) //runs the for loop for the number of slices (subintervals)
		{
			area += this.slice(poly, left + ((right-left)/subintervals)*(i), left + ((right-left)/subintervals)*(i+1)); //calls the slice function for the different rules with (right-left)/subintervals)*(i) as sleft, left + ((right-left)/subintervals)*(i+1) as sright 
		}
		
		 return area;
	}
	
	/** This method graphs the accumulation function corresponding to the input polynomial and the input left hand endpoint.
	 * @param pframe the frame used to graph the accumulation function
	 * @param poly the imported poly
	 * @param index dataset index for the function
	 * @param precision the precision factor used to plot the function
	 * @param base base (a) point for the function
	 * @return total area of riemann sum
	 */
	 public void rsAcc(org.opensourcephysics.frames.PlotFrame pframe, polyfun.Polynomial poly, int index, double precision, double base)
	 {
		double end = 15; //determines the start and end of the accumulation function from 15 before the base point to 15 afterwards
		 for (double x = base-end; x < base + end; x+=precision) {
			double y = this.rs(poly, base, x, 200); //calculates the area under the curve for the interval and sets it to the y coordinate
			pframe.append(index, x, y);//adds the point (xcoordinate, accumualtion area at xcoordinate)
			pframe.setMarkerColor(index, Color.green);
		}
	 }
	 
	 /** This method graphs the accumulation function corresponding to the input polynomial and the input left hand endpoint.
		 * @param pframe the frame used to graph the accumulation function
		 * @param poly the imported poly
		 * @param index dataset index for the function
		 * @param precision the precision factor used to plot the function
		 * @param left leftmost point of the interval
		 * @param right rightmost point of the interval
		 * @param subintervals the number of subintervals used in the function
		 * @return total area of riemann sum
		 */
	 public void rsPlot(org.opensourcephysics.frames.PlotFrame pframe, polyfun.Polynomial poly, int index, double precision, double left, double right, int subintervals) 
	 {
		for (int i = 0; i < subintervals; i++) {
		this.slicePlot(pframe, poly, left + ((right-left)/subintervals)*(i), left + ((right-left)/subintervals)*(i+1)); //plots the rectangles (or shapes) via the same method as rs for loop
		} 
		
		for (double i = left; i < right; i+= precision) {
			pframe.append (0, i, poly.evaluate(i).getTerms()[0].getTermDouble()); //plots the actual curve put using a high precision of evaluated points
			pframe.setMarkerColor(0, Color.black);
		}
	 }
	 
	
	 abstract double slice (polyfun.Polynomial poly, double sleft, double sright);
	 
	 abstract void slicePlot (org.opensourcephysics.frames.PlotFrame pframe, polyfun.Polynomial poly, double sleft, double sright);
}
