package Matrix_Test;

//import matrixmath.Matrix;

public class PrintTest {
	
	public static void main(String[] args) 
	{
		Matrix_PC.Matrix Test = new Matrix_PC.Matrix(5,5);
		
		for(int i=0; i<5; i++)
		{
			for(int j=0; j<5; j++)
			{
				Test.setEntry(i,j, .1+5*i+j);
			}
		}
		
        Test.print();
		
	}

}