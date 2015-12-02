package main.java;
import javafx.scene.layout.GridPane;

public class MatrixView {
    GridPane matrixPane;
    ElementView[][] elements;
    public MatrixView(int rows, int cols) {
    	elements = new ElementView[rows][cols];
    	matrixPane = new GridPane();
    	matrixPane.setStyle("-fx-background-color : goldenrod;");
    	for (int i = 0; i < rows; i++ ) {
    		for (int j = 0; j < cols; j++) {
    			elements[i][j] = new ElementView(new Position(i, j));
    			matrixPane.add(elements[i][j].getRootNode(), j, i);
    		}
    	}
    }
    
    public MatrixView(Matrix a) {
    	if (a != null) {
    	    int cols = a.getCols();
    	    int rows = a.getRows();
    	    elements = new ElementView[rows][cols];
    	    matrixPane = new GridPane();
    	    for (int i = 0; i < rows; i++ ) {
    		    for (int j = 0; j < cols; j++) {
    			    elements[i][j] = new ElementView(new Position(i, j));
    			    matrixPane.add(elements[i][j].getRootNode(a.getBackingArray()[i][j]), j, i);
    		    }
    	    }
    	}
    	else {
    		System.out.println(a);
    	}
    }
    
    public GridPane getGrid() {
    	return matrixPane;
    }
    
    public ElementView getElementAtPosition(Position pos) {
    	return getMatrix()[pos.getRow()][pos.getCol()];
    }
    
    public ElementView[][] getMatrix() {
    	return elements;
    }
}
