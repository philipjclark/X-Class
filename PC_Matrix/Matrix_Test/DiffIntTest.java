package Matrix_Test;

import java.awt.Color;

import polyfun.Polynomial;
import riemann_sum.*;

import org.opensourcephysics.frames.*;

import Matrix_PC.PolyInterpolation;
import Matrix_PC.VDM;
/*
 * @author Philip Clark
 * This test class provides a semi-proof of the fundamental theorem of calculus by graphing a random function f(x), graphing the accumulation function
 * of f(x), and then graphing the derivative of the accumulation function using both the VDM and Polynomial Interpolation classes. The program
 * shows that f'(x_acc) = f(x) --- that is the graph and equations of f'(x_acc) and f(x) ARE THE SAME!
 * NOTE!!! Color Code: Green = f(x); Red = Accumulation function; Black = Derivative of the Accumulation Function
 */
public class DiffIntTest {
	public static void main(String[] args) {
		PlotFrame frame = new PlotFrame("x-axis", "y-axis", "Fundamental Theorem Graph"); 

		TrapezoidRule TR= new TrapezoidRule(); //TrapezoidRule extends Riemann
		PolyInterpolation polyint = new PolyInterpolation();
		Polynomial poly = new Polynomial (new double[] {1, 2, 3, 4}); //makes random poly
		System.out.println("f(x) = ");
		poly.print(); //prints f(x)
		
		for (double i = -2.9; i < 3.1; i+=.2) { //graphs f(x)
			frame.append(14, i, poly.evaluate(i).getTerms()[0].getTermDouble());
			frame.setMarkerColor(14, Color.green); //makes f(x) color green
		}
		
		double [] x_coor = new double [poly.getDegree() + 2]; //makes x coordinate data set with appropriate length
		for (int i = 1; i < x_coor.length + 1; i++) {
			x_coor[i-1] = i;
		}
		double [] y_coor = new double [poly.getDegree() + 2]; //makes y data set for accumulation function
		for (int i = 1; i < y_coor.length + 1; i++) {
			y_coor[i-1] = TR.rs(poly, 0, i, 100); //sets y coordinate = to the accumulated area
		}
		
		Polynomial acc = new Polynomial (x_coor.length-1); //makes accumulation poly with the necessary length
		acc = polyint.interp(x_coor, y_coor); //interpolates the accumulation poly
		System.out.println("Accumulation Function of f(x): ");
		
		acc.print(); //prints the acculumation function
		
		for (double i = -3; i < 3; i+=.1) {
			frame.append(13, i, acc.evaluate(i).getTerms()[0].getTermDouble()); //graphs the accumulation function
			frame.setMarkerColor(13, Color.red); //makes acc_f(x) red
		}
		
		VDM vdm = new VDM(); 
				
		double [] x_coor2 = new double [acc.getDegree() + 3]; //makes new data set for x coordinates
		for (int i = 1; i < x_coor2.length+1; i++) {
			x_coor2[i-1] = i;
		}
		double [] y_coor2 = new double [acc.getDegree() + 3]; //makes y coordinates for the derivative of the accumulation function
		for (int i = 1; i < x_coor2.length+1; i++) { 
			y_coor2[i-1] = vdm.slopeAtPoint(acc, i); //sets the y coordinate = to the derivative of acc_f(x) at that point
		}
		
		Polynomial acc_derivative = new Polynomial (x_coor2.length-1);
		
		acc_derivative = polyint.interp(x_coor2, y_coor2); //interpolates the d(x) of the accumulation function
		System.out.println("Derivative of the Accumulation Function: ");
		acc_derivative.print(); //prints the derivative
		double range = 3; //gives a range value
		double precision = .1; //gives a precision value
		for (double i = -range; i < range; i+=precision) { //graphs the accumulation function
			frame.append(12, i, acc_derivative.evaluate(i).getTerms()[0].getTermDouble());
			frame.setMarkerColor(12, Color.black); //sets color of f'(acc_f(x)) to black
		}
		frame.setVisible(true);
	}

}
