package Matrix_PC;

import org.opensourcephysics.numerics.Polynomial;

/**
 * This is the matrix class. It is the unifying class of the AML Matrix project.  It performs all of the Elementary Row Operations as well as the essential
 * methods of rowreduce and invert.  This class is later used in the VDM and Polynomial Interpolation classes to perform more interesting math.
 * @author Philip Clark
 */

public class Matrix {

		double[][] matrix; //matrix constructor
		 int row; //row number
		 int col; //col number
		 boolean invertible; //bolean to see if the matrix is invertible


		/**
		 * constructs the matrix
		 * @param i the number of rows
		 * @param j the number of columns
		 */
		public Matrix(int i, int j)
		{	
			this.row = i;
			this.col = j;
			this.matrix = new double[row][col];
		}
		/**
		 * This function clones the matrix
		 * @param Matrix that will be cloned
		 */
		public Matrix(Matrix matrix)
		{
			this(matrix.row, matrix.col); //creates matrix with necessary dimensions
			for (int i = 0; i < matrix.row; i++)  //loops through the matrix
			{
				for (int j = 0; j < matrix.col; j++) 
				{
					this.setEntry(i, j, matrix.matrix[i][j]); //clones entries
				}
			}
		}

		/**
		 * Takes a specific location in the matrix and sets that place to a certain value
		 * @param rows number of rows
		 * @param cols number of columns
		 * @param value the value that you will set the entry to
		 */
		public void setEntry (int rows, int cols, double value)
		{
		    this.matrix[rows][cols] = value;  //simply takes a specific row column pair and sets the value for that entry in the matrix
		}
		
		/**
		 * Runs a double for loop to print each entry of the matrix
		 */
		public void print() 
		{
			for (int i = 0; i < this.matrix.length; i++) //double loop
			{
				for (int j = 0; j < this.matrix[i].length; j++) 
				{
					System.out.print(Math.round(this.matrix[i][j]*1000.)/1000. + " | "); //uses rounding and prints matrix
				}
				System.out.println();
			}
		}
		/**
		 * This method uses matrix algebra to add the parameter matrix to your matrix object
		 * @param m_add the matrix you will be adding
		 * @return returns the summed matrix
		 */
		public Matrix plus (Matrix m_add)
		{
		    Matrix m_sum = new Matrix(m_add.matrix.length, m_add.matrix[0].length); //creates the sum matrix
		    
		    for (int i = 0; i < m_add.matrix.length; i++) //uses a double for loop to add each corresponding entry in the two matrices
		    {
		        for (int j = 0; j < m_add.matrix[0].length; j++)
		        {
		            m_sum.matrix[i][j] = this.matrix[i][j] + m_add.matrix[i][j]; //adds the two entries
		        }
		    }
		    return m_sum;
		}


		/**
		 * Uses a scalar factor to multiply all of the entries in the specified row of the matrix
		 * @param scalar the scalar factor that will multiply the entries
		 * @param r the row to be multiplied
		 * @return the scaled matrix
		 */
		public Matrix scalarTimesRow (double scalar, int r)
		{
		    Matrix scaled  = new Matrix (this); //creates the scaled matrix
		    
		    for (int i = 0; i < this.matrix[0].length; i++) {
		        scaled.matrix[r][i] = this.matrix[r][i] * scalar; //goes through each entry in the row to be scaled and multiplies each entry by the scalar
		    }
		    return scaled;
		}


		/**
		 * switches the two rows specified in the matrix
		 * @param r1 first row that is being switched switched 
		 * @param r2 second row that is being switched switched
		 * @return the new matrix with properly switched rows
		 */
		public Matrix switchRows(int r1, int r2) 
		{
			double[] place_holder = new double[this.matrix[0].length]; //creates a place holding double array to hold the row values while it is being switched
			
			Matrix switched = new Matrix(this);

			for (int i = 0; i < place_holder.length; i++) 
			{
				place_holder[i] = this.matrix[r1][i]; //fills the place holding double array
			}

			for (int k = 0; k < this.matrix[0].length; k++) //switches one row for the other using the place holder
			{
				switched.matrix[r1][k] = this.matrix[r2][k];
				switched.matrix[r2][k] = place_holder[k];
			}
			return switched;
		}

		/**
		 * linearCombRows
		 * Final Elementary-row-operation, adds the product of a row * a scalar to another row
		 * @param scalar to multiply row_mulitiplied by
		 * @param row_multiplied
		 * @param row_added_to 
		 * @return new Matrix with Rows switched
		 */
		public Matrix linearCombRows(double scalar, int row1, int row2) 
		{		
			Matrix combined = new Matrix(this); //creates the new matrix

			for (int i = 0; i < this.matrix[0].length; i++) //loops through each entry in the necessary row
			{
				combined.matrix[row2][i] = this.matrix[row2][i] +  (scalar * this.matrix[row1][i]); //adds row2 to itself + a scalar times row1
			}
			return combined;
		}
		

		/**
		 * The method takes a matrix parameter and multiplies it by the matrix object
		 * @param that the matrix that is being multiplied by your matrix object
		 * @return returns the product matrix
		 */
		public Matrix times (Matrix that)
		{
		    Matrix product = new Matrix(this.matrix.length, that.matrix[0].length); //creates the product matrix
		    
		    /*
		     * Matrix Multiplication Algorithim:
		     * 1. takes the first row in the first matrix and multiplies each corresponding entry in the first collumn of the second matrix; sum them to ge the first entry in the product matrix
		     * 2. repeat step one for every collumn in the second matrix
		     * 3. move on for every row in the first matrix
		     * 4. the resulting matrix is your product matrix
		     */
		    for (int i = 0; i < this.matrix.length; i++) //uses a triple for loop to do the multiplication operation
		    {
		        for (int j = 0; j < that.matrix[0].length; j++)
		        {
		            for (int k = 0; k < this.matrix[0].length; k++)
		            {
		                product.matrix[i][j] += this.matrix[i][k] * that.matrix[k][j];
		            }
		        }
		    }
			
		    return product;
		}
		
		/**
		 * @param matrix pivot the matrix this is being pivoted
		 * @param current_col the column which is being pivoted
		 * @return the matrix with the pivot in the necessary column created
		 */
		public Matrix Pivot (int current_col, Matrix pivot)
		{
		    for (int rows = 0; rows < pivot.matrix.length; rows++) //loops through the matrix
		    {
		        if(current_col<pivot.matrix[0].length) 
		        {
		            if (pivot.matrix[current_col][current_col] != 0) //if it is at the correct entry, uses an inverse scalar to make it a one
		            {
		            	if(pivot.matrix[rows][rows] != 0)
		                pivot = pivot.scalarTimesRow((1/pivot.matrix[rows][rows]), rows);
		            }
		            
		            else if(pivot.matrix[rows][current_col] == 0) //if the entry is a zero at the correct entry
		            {
		                for (int nextrow = rows+1; nextrow < pivot.matrix.length; nextrow++) //uses a for loops to switch rows to create the appropriate pivot
		                {
		                    if(pivot.matrix[nextrow][rows] != 0)
		                    {
		                        pivot = pivot.switchRows(rows, nextrow);
		                        pivot = pivot.scalarTimesRow(1/(pivot.matrix[rows][rows]), rows);
		                    }
		                }
		            }
		            
		            else if(pivot.matrix[rows][current_col] != 0) //if its not a zero, just uses the inverse scalar to make it a one
		            {
		            	pivot = pivot.switchRows(rows, current_col);
                        pivot = pivot.scalarTimesRow(1/(pivot.matrix[rows][rows]), rows);
		            }
		        }
		    }
		  
		    return pivot;
		}
		/**
		 * @param matrix zeroes the matrix from row reduce that needs to be reduced for the given column
		 * @param column that needs to be row reduced
		 * @return the zeros matrix with all the necessary zeros created in the column
		 */
		public Matrix kill(int current_col, Matrix zeros)
		{
			
		    for (int i = 0; i < zeros.matrix.length; i++) //loops through the matrix
		    {
		        if(zeros.matrix[i][current_col] != 0) //if the entry isnt zero takes the pivot entry and uses a -scalar to kill it and make it a zero
		        {
		            for (int j = 0; j < zeros.matrix.length; j++) //this for loop uses a second counter variable to find the pivot and use it to kill the non zero
		            {
		                if((j != i) && (Math.round(zeros.matrix[j][current_col]*10000)/10000 == 1))
		                {
		                    zeros = zeros.linearCombRows(((zeros.matrix[i][current_col])/(zeros.matrix[j][current_col]) * -1), j, i);
		                }
		            }
		        }
		    }
		    return zeros;
		}
		/**
		 * Calls on the helper classes of kill and zeros to row reduce the matrix
		 * @return the row reduced matrix
		 */
		public Matrix rowreduce() 
		{
			Matrix rowred = new Matrix(this); //creates a duplicate of the current matrix
		    		    
		    for (int i = 0; i < rowred.matrix.length; i++) //loops through the matrix for each column
		    {
		        rowred = kill(i, rowred); //calls kill (described above)
		      
		        rowred = Pivot(i, rowred); //calls pivot (described above)
		    }
		    
		    return rowred; //returns the row reduced matrix

		}
		/**
		 * This method calculates the inverse matrix given a matrix using the augmented matrix and the row reduce emthod
		 * @return the inverse matrix
		 */
		public Matrix invert() 
		{
	
		    Matrix augment = new Matrix(this.row, this.col*2); //creates a matrix with the dimensions of the augmented matrix
		    
		    for (int i = 0; i < augment.matrix.length; i++) { //loops through the augmented matrix
		        for (int j = 0; j < augment.matrix[0].length; j++) {
		            if(j < augment.matrix[0].length/2) //sets the left half of the matrix equal to the current matrix
		            {
		                augment.matrix[i][j] = this.matrix[i][j];
		            }
		            
		            else if (j == (augment.matrix[0].length/2 + i)) //for the first place where a non-zero number is needed in the augmented matrix, it creates a one 
		            {
		                augment.matrix[i][j] = 1;
		            }
		            
		            else //otherwise creates a zero
		            {
		                augment.matrix[i][j] = 0;
		            }
		        }
		    }

			augment = augment.rowreduce(); //row reduces the augmented matrix so we have one side with the identity matrix and one side with the inverse


			Matrix inverse = new Matrix(this); //creates the inverse matrix

			for (int j = this.matrix[0].length; j < augment.matrix[0].length; j++) //uses a double for loop to peel off the inverse matrix solution from the augmented matrix
			{
				for (int row = 0; row < augment.matrix.length; row++) 
				{
					inverse.matrix[row][j-this.matrix[0].length] = augment.matrix[row][j];
				}
			}

			return inverse; //returns the solution inverse matrix
		}

}
