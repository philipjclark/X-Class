package test_classes;

import polyfun.Polynomial;
import riemann_sum.LeftHandRule;
import riemann_sum.MaximumRule;
import riemann_sum.MidpointRule;
import riemann_sum.MinimumRule;
import riemann_sum.RandomRule;
import riemann_sum.RightHandRule;
import riemann_sum.SimpsonsRule;
import riemann_sum.TrapezoidRule;

public class SliceTest 
{
	public static void main(String[] args) 
	{
		LeftHandRule LHR = new LeftHandRule();  //LeftHandRule extends Riemann
		RightHandRule RHR = new RightHandRule();  //RightHandRule extends Riemann
		TrapezoidRule TR= new TrapezoidRule(); //TrapezoidRule extends Riemann
		SimpsonsRule SR = new SimpsonsRule(); //Simpsons extends Riemann
		RandomRule RR = new RandomRule(); //RandomRule extends Riemann
		MinimumRule MR = new MinimumRule(); //MinimumRule extends Riemann
		MaximumRule MaR = new MaximumRule(); //MaximumRule extends Riemann
		MidpointRule Mid = new MidpointRule(); //Midpoint extends Riemann
		
		Polynomial p = new Polynomial(new double[] {0,4,0,3}); // p=3x^3+4x
		
		System.out.println(LHR.slice(p,1,1.1)+"\n"); //the area of a left hand rule slice of 3x^3+4x, from x=1 to x=1.1
		
		System.out.println(RHR.slice(p,1,1.1)+"\n"); //the area of a rightt hand rule slice of 3x^3+4x, from x=1 to x=1.1
		
		System.out.println(TR.slice(p,1,1.1)+"\n"); //the area of a trapezoid rule slice of 3x^3+4x, from x=1 to x=1.1
		
		System.out.println(SR.slice(p,1,1.1)+"\n"); //the area of a simpsons rule slice of 3x^3+4x, from x=1 to x=1.1
		
		System.out.println(RR.slice(p,1,1.1)+"\n"); //the area of a random rule slice of 3x^3+4x, from x=1 to x=1.1
		
		System.out.println(MR.slice(p,1,1.1)+"\n"); //the area of a minimum rule slice of 3x^3+4x, from x=1 to x=1.1
		
		System.out.println(MaR.slice(p,1,1.1)+"\n"); //the area of a maximum rule rule slice of 3x^3+4x, from x=1 to x=1.1
		
		System.out.println(Mid.slice(p,1,1.1)+"\n"); //the area of a midpoint rule slice of 3x^3+4x, from x=1 to x=1.1





	}
}