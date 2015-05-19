package Matrix_Test;

public class InvertTest 
{

	public static void main(String[] args) 
	{
		Matrix_PC.Matrix matt = new Matrix_PC.Matrix (3,3); //matt is invertible
		
		int counter = 0;
		
		for(int i=0; i<3; i++) //Filling matt
		{
			for(int j=0; j<3; j++)
			{
				counter++;
				matt.setEntry(i, j, counter);
			}
		}
		
		matt.setEntry(2, 2, 20);
		
		matt.print(); //printing matt
		
		System.out.println("\n");
		
		matt.invert().print(); //printing the inverse of matt
		
		System.out.println("\n");

		matt.times(matt.invert()).print(); //printing the product of trixie and her inverse 
		
		System.out.println("\n");
		
		
		Matrix_PC.Matrix trixie = new Matrix_PC.Matrix (4,4); //trixie is invertible
		
		for(int i=0; i<4; i++) //Filling trixie
		{
			for(int j=0; j<4; j++)
			{
				if(i+j==2)
					trixie.setEntry(i, j, i+1);
				else
					trixie.setEntry(i, j, 0);
			}
		}
		
		trixie.setEntry(3,3, 4);
		
		trixie.print(); //printing trixie
		
		System.out.println("\n");
		
		trixie.invert().print(); //printing the inverse of trixie
		
		System.out.println("\n");
		
		trixie.times(trixie.invert()).print(); //printing the product of trixie and her inverse 
	}
}

