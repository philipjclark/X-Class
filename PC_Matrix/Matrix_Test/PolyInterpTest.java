package Matrix_Test;

import java.awt.Color;
import java.util.Scanner;

import org.opensourcephysics.frames.PlotFrame;

import polyfun.Polynomial;
import riemann_sum.TrapezoidRule;
import Matrix_PC.PolyInterpolation;
import Matrix_PC.VDM;


public class PolyInterpTest {
	
	static Scanner scan = new Scanner(System.in);

 public static void main(String[] args) {
	 //note that the test works best for random points on a non-random function; don't use 0 as a point!
	 //the interpolator freaks out if you use a lot of weird, disparate points (ie make x in a range of about 20), the smaller range the better
	 PlotFrame frame = new PlotFrame("x-axis", "y-axis", "Graph"); 

		PolyInterpolation polyint = new PolyInterpolation();
		//try not to use for a poly with degree > 5 
		//random points that lie on the function 2x^4 + 1
		//make sure you do n points for an n-1 degree function
		 double x_coor[] = new double [] {-1, 1, 3, 4, 5}; 
		 double y_coor[] = new double [] {3, 3, 163, 513, 1251};
		 
		 Polynomial poly = new Polynomial (x_coor.length - 1); //makes the polynomial with the necessary length
		 
		poly = polyint.interp(x_coor, y_coor); //sets that poly = to the interpolated poly
		 System.out.println("The interpolated poly is: ");
		 poly.print(); //prints the poly
		 for (double i = -3; i < 7; i+=.1) {
			frame.append(1, i, poly.evaluate(i).getTerms()[0].getTermDouble()); //graphs interpolated poly
		}
		 
		for (int i = 0; i < y_coor.length; i++) { //graphs the points being inteprolated
			frame.append(2, x_coor[i], y_coor[i]);
			frame.setMarkerColor(2, Color.red);
		}
		 frame.setVisible(true);
	}
 

}
