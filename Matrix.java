package main.java;
public class Matrix {
    private double[][] stuff;
    private int rows;
    private int cols;
    private boolean isSquare;
    private boolean isVector;
    public static void main(String[] args) {
        double[][] test44 = {{1, -1, -3, 0},
                           {0, 1, 8, 4},
                           {4, -2, -3, 4},
                           {-1, 2, 8, 5}};
        double[][] test33 = {{-10, 8, 4},
                              {8, -10, 4},
                              {4, 4, -16}};
        double[][] test23 = {{4, 20, 12},
                              {20, 110, 46}};
        double[][] test34 = {{12, 4, 4, 8},
                             {-3, 0, 3, 2},
                             {4, 2, 0, 2}};
        double[][] test13 = {{1, 3, 1}};
        Matrix testMatrix = new Matrix(test13);
        testMatrix.printMatrix();
        System.out.println("");
        testMatrix.rowReduce();
        System.out.println("");
        testMatrix.printMatrix();
        System.out.println("");
    }
    //creates a matrix object
    public Matrix(double[][] a) {
        if (a == null) {
            throw new IllegalArgumentException("the matrix can't be null");
        }
        stuff = a;
        rows = stuff.length;
        cols = stuff[0].length;
        isSquare = false;
        if (rows == cols) {
            isSquare = true;
        }
        isVector = false;
        if (cols == 1) {
        	isVector = true;
        }
    }
    //row reduces the matrix to reduced echelon form
    public void rowReduce() {
        printMatrix();
        boolean[] hasPivot = new boolean[cols];
        int startingPos = 0;
        while (startingPos < rows && startingPos < cols)
        {
            for (int j = startingPos; j < cols; j++) {
                for (int i = startingPos + 1; i < rows; i++) {
                    doReplacement(-stuff[i][j]/stuff[startingPos][startingPos], startingPos, i);
                    printMatrix();
            }
            startingPos++;
            }
        }
        for (int i = 0; i < cols && i < rows; i++) {
            hasPivot[i] = false;
            if (stuff[i][i] != 0) {
                hasPivot[i] = true;
            }
        }
        for (int i = 0; i < cols && i < rows; i++) {
            if (hasPivot[i]) {
                doScaling(stuff[i][i], i);
                for (int j = i-1; j >= 0; j--) {
                    doReplacement(-stuff[j][i], i, j);                }
            }
        }
    }
    //checks whether the matrix is invertible by calculationg
    //the determinant
    public boolean isInvertible(int[][] a) {
        if (isSquare && doDeterminant() != 0) {    
            return true;
        }
        return false;
    }
    //does the row operation "scaling"
    public void doScaling(double scale, int row) {
        for (int i = 0; i < cols; i++) {
            stuff[row][i] /= scale;
        }
    }
    //does the row operation "interchange"
    public void doInterchange(int row1, int row2) {
        double temp = 0;
        for (int i = 0; i < cols; i++) {
            temp = stuff[row1][i];
            stuff[row1][i] = stuff[row2][i];
            stuff[row2][i] = temp;
        }
    }
    //does the row operation "replacement"
    public void doReplacement(double scalar, int row1, int row2) {
        for (int i = 0; i < cols; i++) {
            stuff[row2][i] += scalar * stuff[row1][i];
        }
    }
    //prints the matrix
    public String printMatrix() {
    	String ans = "";
        for (int i = 0; i < rows; i++) {
            ans += "| ";
            for (int j = 0; j < cols; j++) {
                if (stuff[i][j] == -0.0) {
                    stuff[i][j] = 0.0;
                }
                ans += stuff[i][j] + " ";
            }
            ans += "|\n";
        }
        return ans;
    }
    //creates the echelon form of the matrix through row replacement only
    public void echelonForm() {
        int startingPos = 0;
        while (startingPos < rows && startingPos < cols)
        {
            for (int j = startingPos; j < cols; j++) {
                for (int i = startingPos + 1; i < rows; i++) {
                    doReplacement(-stuff[i][j]/stuff[startingPos][startingPos], startingPos, i);
            }
            startingPos++;
            }
        }
    }
    //find the determinant through reduction to echelon form
    //then diagonal multiplication
    public double doDeterminant() {
        double det = 1;
        if (rows != cols) {
            return 0;
        }
        double[][] a = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                a[i][j] = stuff[i][j];
            }
        }
        Matrix temp = new Matrix(a);
        temp.echelonForm();
        for (int i = 0; i < rows; i++) {
            det *= temp.stuff[i][i];
        }
        return det;
    }
    //computes x0 .. xn using Cramer's rule
    public String cramersRule() {
    	String ans = "";
        double[][] a = new double[rows][rows];
        double[][] b = new double[rows][rows];
        Matrix original = new Matrix(a);
        Matrix temp = new Matrix(b);
        double[][] vector = new double[rows][1];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                original.stuff[i][j] = stuff[i][j];
                temp.stuff[i][j] = stuff[i][j];
            }
        }
        for (int replaceCol = 0; replaceCol < cols - 1; replaceCol++) {
            for (int i = 0; i < rows; i++) {
                vector[i][0] = temp.stuff[i][replaceCol];
                temp.stuff[i][replaceCol] = stuff[i][cols-1];
            }
            ans += "x" + (replaceCol + 1) + " is " + temp.doDeterminant()
                + "/" + original.doDeterminant() + "\n";
            for (int i = 0; i < rows; i++) {
                temp.stuff[i][replaceCol] = vector[i][0];
            }
        }
        return ans;
    }
    //removes a specified row and column from a matrix 
    //and returns the new matrix
    public Matrix cofactorRemoveRowCol(int row, int col) {
        double[][] newArray = new double[rows-1][cols-1];
        Matrix newMatrix = new Matrix(newArray);
        int currentRow = 0;
        int currentCol = 0;
        for (int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                 if (i != row && j != col) {
                     newMatrix.stuff[currentRow][currentCol] = stuff[i][j];
                     currentCol++;
                 }
            }
            currentCol = 0;
            if (i != row) {
                currentRow++;
            }
        }
        return newMatrix;
    }
    //recursively find the determinant through cofactor expansion
    public double doCofactorExpansion() {
        if (cols != rows) {
            return 0; 
        } else if (rows == 2) {
            return doDeterminant();
        } else {
            double sum = 0;
            for (int i = 0; i < cols; i++) {
                sum += Math.pow(-1,i) * stuff[0][i]
                * cofactorRemoveRowCol(0,i).doCofactorExpansion();
            }
            return sum;
        }
    }

    public double[][] getBackingArray() {
        return stuff;
    }
    
    public int getRows() {
    	return rows;
    }
    
    public int getCols() {
    	return cols;
    }
    
    public Matrix rightMutliply(Matrix a) {
        if (getCols() == a.getRows()) {
        	double sum = 0;
        	double[][] newBackingArray = new double[getRows()][a.getCols()];
        	double[][] rightBackingArray = a.getBackingArray();
        	double[][] leftBackingArray = getBackingArray();
        	for (int first = 0; first < getRows(); first++) {
        		for (int last = 0; last < a.getCols(); last++) {
        			for(int inner = 0; inner < a.getRows(); inner++) {
        				sum += (leftBackingArray[first][inner] * rightBackingArray[inner][last]);
        			}
        			newBackingArray[first][last] = sum;
        			sum = 0;
        		}
        	}
        	Matrix returnMatrix = new Matrix(newBackingArray);
        	return returnMatrix;
        }
        else {
        	System.out.println("Matrix does not work");
        	return null;
        }
    }
    
    public Matrix leftMultiply(Matrix b) {
    	System.out.println(b.getRows() + "x"  + b.getCols());
    	System.out.println(this.getRows() + "x" + this.getCols());
    	return b.rightMutliply(this);
    }
    
    public boolean getIsVector() {
    	return isVector;
    }
    
    public Matrix getTranspose() {
    	double[][] transArray = new double[cols][rows];
    	for (int row = 0; row < rows; row++) {
    		for (int col = 0; col < cols; col++) {
    			transArray[col][row] = stuff[row][col]; 
    		}
    	}
    	Matrix trans = new Matrix(transArray);
    	return trans;
    }
    
    @Override
    public String toString() {
    	if (rows == 1 && cols == 1) {
    		return ("" + stuff[0][0]);
    	}
    	return printMatrix();
    }
}
