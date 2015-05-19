package Matrix_Test;

public class LinearCombRowsTest {

	public static void main(String[] args) 
	{
		Matrix_PC.Matrix trixie = new Matrix_PC.Matrix(4,4); 
		
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
		
		trixie.linearCombRows(1.5,3,0).print(); //printing the Matrix which results when 1.5 times row 3 of trixie is added to row 0 of trixie
		
		System.out.println("\n");
		
		trixie.print(); //printing trixie again!
	}
}

