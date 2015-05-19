package Matrix_PC;

import java.awt.Color;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.opensourcephysics.frames.PlotFrame;

import polyfun.Coef;
import polyfun.Polynomial;

/**
 * This is the Polynomial Interpolation class.  It takes an array of double coordinates (x,y) of length n and fits a polynomial of length n-1 to the points 
 * @author Philip Clark
 */
public class PolyInterpolation {
	
	/**
	 * The interpolation function uses matrices to, given n points, find a polynomial that fits those points of degree n-1
	 * @param x the x coordinates of the points being interpolated
	 * @param y the y coordinates of the points being interpolated
	 * @return a polynomial that has been interpolated
	 */
	public Polynomial interp (double[] x, double [] y)
	{
		 int points = x.length; //documents the number of points in the x coordinate double array
		
		Matrix_PC.Matrix interp = new Matrix_PC.Matrix(points, points); //makes a square matrix that will hold all of the necessary points
		
		Matrix_PC.Matrix answer = new Matrix_PC.Matrix(points, 1); //makes the answer matrix (the y coordinates)
		
		for (int i = 0; i < points; i++) {
			answer.setEntry(i, 0, y[i]); //makes the answer matrix by setting the entries to the necessary values
		}
		
		for (int i = 0; i < points; i++)
		{
			for (int j = 0; j < points; j++) {
				interp.setEntry(i, j, Math.pow(x[i], points-j-1)); //sets the entries of the interpolation matrix by raising the x coordinates to the necessary powers in the necessary positions
			}
		}	
	
		interp = interp.invert().times(answer); //multiplies by the inverse of the interpolation matrix by the answer matrix to get the coefficients of the solution matrix

		double [] coefs = new double [points];
		for (int i = 0; i < coefs.length; i++) {
			coefs[i] = interp.matrix[points-i-1][0]; // Math.round((interp.matrix[points-i-1][0])*10000)/10000;
			if(Math.abs(coefs[i]) < .00001)
			{
				coefs[i] = 0;
			}
		//	coefs[i] = coefs[i]/10000;//peels off the solution matrix entries into a coefficient array
		}
		Polynomial interpolated = new Polynomial (points-1); //makes a polynomial with the necessary length
		interpolated.setCoefficients(coefs); //fills the polynomial with the coefficient array
	
		return interpolated; //returns the interpolated poly
		
	}



}
