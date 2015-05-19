package Matrix_Test;

public class RowReduceTest {


		public static void main(String[] args) 
		{
			Matrix_PC.Matrix alice = new Matrix_PC.Matrix (5,5); 
			
			for(int i=0; i<5; i++) //Filling alice
			{
				for(int j=0; j<5; j++)
				{
					if(i+j==2)
						alice.setEntry(i, j, i+1);
					else if(i+j==4)
						alice.setEntry(i, j, j+1);
					else
						alice.setEntry(i, j, 0);
				}
			}
			
			alice.setEntry(2,4, 4);
			
			alice.print(); //printing alice
			
			System.out.println("\n");
			
			alice.rowreduce().print(); //printing the Matrix which results when alice is row reduced
			
			System.out.println("\n");
			
			alice.print(); //printing alice again!
		
		}
}