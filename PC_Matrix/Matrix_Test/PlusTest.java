package Matrix_Test;

public class PlusTest 
{
	public static void main(String[] args) 
	{			
		Matrix_PC.Matrix trixie = new Matrix_PC.Matrix(3,4);//The matrix on the left
				
		Matrix_PC.Matrix alice = new Matrix_PC.Matrix(3,4); //The matrix on the right
				
		for(int i=0; i<3; i++) //Filling the left matrix
		{
			for(int j=0; j<4; j++)
			{
				trixie.setEntry(i,j, i+j);
			}
		}
		trixie.print();//printing trixie
		System.out.println();
				
		for(int i=0; i<3; i++) //Filling the right matrix
		{
			for(int j=0; j<4; j++)
			{
				alice.setEntry(i,j, i-j);
			}
		}
		
		alice.print();//printing alice
		System.out.println();
				
		trixie.plus(alice).print(); //The sum of the two matrices			
	}
}