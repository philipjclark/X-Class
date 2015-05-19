package riemann_sum;

import polyfun.*;
public class PolyPractice {

	public static double eval (Polynomial p, double x)
	{
	
		return p.evaluate(x).getTerms()[0].getTermDouble(); //evaluates p at a double x (returns coeff) turns the coeff into a term and then casts the term into a double
	}
	
	public void addXsquared(Polynomial p)
	{
			Polynomial squared = new Polynomial(new double[] {0,0,1}); //creates a polynomial x^2
			Polynomial squarednew = p.plus(squared); //adds p to the newly created polynomial so that 1x^2 is added to p
			squarednew.print(); //prints the new polynomial
	}
}
