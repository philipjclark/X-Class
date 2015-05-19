package Matrix_Test;

import java.util.Scanner;

import org.opensourcephysics.frames.PlotFrame;

import Matrix_PC.VDM;
import polyfun.Polynomial;
import Matrix_PC.PolyInterpolation;
import riemann_sum.TrapezoidRule;
/*
 * This test class uses the VDM class to graph the derivative of a given poly and then uses the poly interpolation class and the VDM class in conjunction 
 * with one another to print out the equation of the derivative of the poly as well
 * This is, in many ways, the culminating class of the project
 */

public class VDM_Test { 
	static Scanner scan = new Scanner(System.in);

	public static void main (String[] args){
		
		
		PlotFrame frame = new PlotFrame("x-axis", "y-axis", "Slope Function Graph"); 

		VDM vdm = new VDM(); 
		Polynomial poly = new Polynomial(new double[]{0, 0, 0, 1}); //makes a random poly
		System.out.println("The original poly is (in red): ");
		poly.print();  //prints the poly
		vdm.slopeFunction(poly, frame); //graphs the accumulation function of the poly
		PolyInterpolation polyint = new PolyInterpolation();

		 double x_coor[] = new double [poly.getDegree()];
		 
		 double y_coor[] = new double [poly.getDegree()];	

		for (int i = 1; i < poly.getDegree()+1; i++) { //uses a for loop to find the slope at n (where n = poly's degree) points
		x_coor[i-1] = i;
		y_coor[i-1] = vdm.slopeAtPoint(poly, i);
	
		}
		
		 Polynomial interpolate = new Polynomial (x_coor.length - 1); //makes the polynomial with the necessary length (n-1)
		 
			interpolate = polyint.interp(x_coor, y_coor); //sets that poly = to the interpolated poly (the derivative)
			 System.out.println("The derivative poly (in green) is: ");
			 interpolate.print(); //prints the derivative
		
		 }
}
