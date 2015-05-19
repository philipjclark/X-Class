package test_classes;

import polyfun.Polynomial;
import riemann_sum.PolyPractice;

public class AddXsquaredTest {

	public static void main(String[] args) 
	{
		PolyPractice popeye = new PolyPractice(); 
		Polynomial poly = new Polynomial(new double[] {-6,-1}); //creates polynomial -x-6
		popeye.addXsquared(poly); //popeye adds x^2 to poly, and then both prints and graphs the resulting polynomial
	}
}