package Matrix_PC;
import java.awt.Color;
import java.awt.Frame;

import org.opensourcephysics.frames.PlotFrame;

import polyfun.Coef;
import polyfun.Polynomial;


/**
 * This is the VDM class. It uses the VDM matrix method to find the slope of a point for a given poly and then uses that method to graph the 
 * derivative of the function.
 * @author Philip Clark
 */
public class VDM {
		
	/**
	 * The function uses the VDM matrix method (f(x) = (x-a)^2 * Q(x) + mx + b)to create a generic polynomial Q(x) multiply it by (x-a)^2 add a generic tangent line
	 * and use the coefficients of the poly to create a system of linear equations that the program uses matrices to solve
	 * @param poly the polynomial being differentiated
	 * @param x the point whose tangent line slope is being found
	 * @return returns the tangent line slope of the poly at x
	 */
	public double slopeAtPoint (Polynomial poly, double x)
	{
		int q_degree = poly.getDegree() - 2; //gets the degree of Q(x)
		Polynomial a_x = new Polynomial (new double[] {x*x, -1*2*x, 1}); //creates polynomial (x-a)^2

		Polynomial q_x = new Polynomial('q', q_degree); //creates a generic polynomial in q with degree of Q(x)
		Polynomial v_x = q_x.times(a_x); 
		v_x = v_x.addTangent(); //multiplies (x-a)^2 and Q(x) and adds mx + b to get f(x)
		

		
		Matrix_PC.Matrix coeff_m = new Matrix_PC.Matrix(poly.getDegree()+1,poly.getDegree()+1); //creates a matrix that will hold the coefficients of the different multiplied terms

		Matrix_PC.Matrix term_m = new Matrix_PC.Matrix(poly.getDegree()+1,1); //the matrix for the answers to the linear equations


		
		for (int i = 0; i <= poly.getDegree(); i++) {
			term_m.setEntry (i, 0, poly.getCoefficient(i).getTerms()[0].getTermDouble());
		} //fills the terms with the polynomial coefficients
		
		
				
		for (int i = 0; i < coeff_m.row; i++) { //this for loop goes through each element of the coeff matrix and finds its variable value (b, m, q0, q1, etc) and sets it to its corresponding value for each equating of the coefficients linear equation
			
			for (int j = 0; j < v_x.getCoefficient(i).getTerms().length; j++) {
				if (v_x.getCoefficient(i).getTerms()[j].getTermAtoms()[0].getLetter() == 'b') //checks to see if the term is a b
				{
					coeff_m.matrix[i][0] = v_x.getCoefficient(i).getTerms()[j].getTermDouble(); //if so sets it to the necessary value
				}
				
				else if (v_x.getCoefficient(i).getTerms()[j].getTermAtoms()[0].getLetter() == 'm') //checks to see if the term is an m
				{
					coeff_m.matrix[i][1] = v_x.getCoefficient(i).getTerms()[j].getTermDouble(); //if so sets it to the necessary value
				}
				for (int j2 = 0; j2 < q_x.getCoefficients().length; j2++) {
					if (v_x.getCoefficient(i).getTerms()[j].getTermAtoms()[0].getLetter() == 'q') //checks to see if the term is one of the q terms (q0, q1, q2, etc)
					{
						for (int k = 0; k < q_x.getCoefficients().length; k++) { //now goes through to find the subscript of the specific q
							if(v_x.getCoefficient(i).getTerms()[j].getTermAtoms()[0].getSubscript() == k)
							{
								coeff_m.matrix[i][2+k] = v_x.getCoefficient(i).getTerms()[j].getTermDouble(); //sets the necessary value
							}
						}
					}
				}
			}
			
		}
		
		Matrix_PC.Matrix final_m = new Matrix_PC.Matrix(term_m.matrix.length,1); //creates a new solution matrix by multiplying the inverse of the coefficient matrix by the term matrix
		
			for (int j = 0; j < final_m.matrix.length; j++) {
				final_m.matrix[j][0] = coeff_m.invert().times(term_m).matrix[j][0];
			}

			return final_m.matrix[1][0]; //return the m value from the solution matrix
		

	}

	/**
	 * This method simply calls the slopeAtPoint function for a certain precision and a right and left point to graph the derivative of the function over a given range
	 * @param poly the polynomial being differentiated
	 */
	public void slopeFunction (Polynomial poly, PlotFrame pframe)
	{
		double left = -5; //left hand endpoint
		double right = 5; //right hand endpoint
		double precision = .001; //precision value
		for (double i = left; i < right; i+=precision) { //this for loop simply uses a precision and right and left bound to graph the slope function values at each point

			pframe.append(1, i, slopeAtPoint(poly, i));
		}
		for (double j = left; j < right; j+=precision) { //graphs the actual polynomial for comparison
			pframe.append(2, j, poly.evaluate(j).getTerms()[0].getTermDouble());
		}
		pframe.setMarkerColor(2, Color.red); //makes the actual poly red
		pframe.setVisible(true);
		pframe.setPreferredMinMax(-10, 10, -10, 10);
	}

}
