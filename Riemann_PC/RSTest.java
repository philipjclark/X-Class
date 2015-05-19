package test_classes;

import polyfun.Polynomial;
import riemann_sum.*;

public class RSTest 
{
	public static void main(String[] args) 
	{
		LeftHandRule LHR = new LeftHandRule();  //LeftHandRule extends Riemann
		RightHandRule RHR = new RightHandRule();  //RightHandRule extends Riemann
		TrapezoidRule TR = new TrapezoidRule(); //TrapezoidRule extends Riemann
		SimpsonsRule SR = new SimpsonsRule(); //Simpsons extends Riemann
		RandomRule RR = new RandomRule(); //RandomRule extends Riemann
		MinimumRule MR = new MinimumRule(); //MinimumRule extends Riemann
		MaximumRule MaR = new MaximumRule(); //MaximumRule extends Riemann
		MidpointRule Mid = new MidpointRule(); //Midpoint extends Riemann
		
		Polynomial p = new Polynomial(new double[] {0, 0, 3}); //p=3x^2
		
		double min = -5.5; //variable for starting point of riemann approximation
		double max = 10.3; //variable for ending point of riemann approximation
		int rec = 2000;

		System.out.println(LHR.rs(p, min, max, rec)+"\n"); //the approximate area under 3x^2, from 0 to 1, using the left hand rule
		
		System.out.println(RHR.rs(p, min, max, rec)+"\n"); //the approximate area under 3x^2, from 0 to 1, using the right hand rule
		
		System.out.println(TR.rs(p, min, max, rec)+"\n"); //the approximate area under 3x^2, from 0 to 1, using the trapezoid rule
		
		System.out.println(SR.rs(p, min, max, rec)+"\n"); //the approximate area under 3x^2, from 0 to 1, using simpsons rule
		
		System.out.println(RR.rs(p, min, max, rec)+"\n"); //the approximate area under 3x^2, from 0 to 1, using random rule
		
		System.out.println(MR.rs(p, min, max, rec)+"\n"); //the approximate area under 3x^2, from 0 to 1, using minimum rule
		
		System.out.println(MaR.rs(p, min, max, rec)+"\n"); //the approximate area under 3x^2, from 0 to 1, using maximum rule
		
		System.out.println(Mid.rs(p, min, max, rec)+"\n"); //the approximate area under 3x^2, from 0 to 1, using midpoint rule





		
	}
}