package Matrix_Test;

public class TimesTest {

	public static void main(String[] args) {
		
		Matrix_PC.Matrix trixie = new Matrix_PC.Matrix(3,4); //The matrix on the left
		
		Matrix_PC.Matrix alice = new Matrix_PC.Matrix(4,5); //The matrix on the right
		
		for(int i=0; i<3; i++) //Filling the left matrix
		{
			for(int j=0; j<4; j++)
			{
				trixie.setEntry(i,j, i+j);
			}
		}
		trixie.print();
		System.out.println();
		
		for(int i=0; i<4; i++) //Filling the right matrix
		{
			for(int j=0; j<5; j++)
			{
				alice.setEntry(i,j, i-j);
			}
		}
		alice.print();
		System.out.println();
		
		trixie.times(alice).print(); //The product of the two matrices
		
	}

}