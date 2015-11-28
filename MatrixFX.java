import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.text.Font;

public class MatrixFX extends Application {
	Matrix worker;
	boolean storePressed = true;
    public void start(Stage primaryStage) {
    	//first window
    	HBox base = new HBox();
    	Spinner<Integer> rows = new Spinner<Integer>(1, 10, 3);
    	Spinner<Integer> cols = new Spinner<Integer>(1, 10, 3);
        rows.setPrefWidth(58);
        cols.setPrefWidth(58);
    	Text rowText = new Text("Number of rows");
    	Text colText = new Text("Number of columns");
        rowText.setFont(new Font(15));
        colText.setFont(new Font(15));
    	Button showMatrix = new Button("Display");
    
    	
    	
    	//Second window begins
    	showMatrix.setOnAction(event -> {
    		MatrixView matrixView = new MatrixView(rows.getValue(), cols.getValue());
    		primaryStage.close();
    		Stage secondaryStage = new Stage();
    		VBox base2 = new VBox();
    		HBox bottom = new HBox();
    		Button doCalc = new Button("Do Calculations");
    		Button back = new Button("Back");
    		back.setOnAction(event1 -> {
    			secondaryStage.close();
    			start(new Stage());
    		});
    		
    		
    		//third window begins
    		doCalc.setOnAction(event1 -> {
    			double[][] values = new double[rows.getValue()][cols.getValue()];
    			for (int row = 0; row < rows.getValue(); row++ ) {
    				for (int col = 0; col < cols.getValue(); col++) {
    					ElementView element = matrixView.getElementAtPosition(new Position(row, col));
    				    values[row][col] = element.getValue();
    				}
    			}
    			Matrix backingMatrix = new Matrix(values);
    			secondaryStage.close();
    			Stage tertiaryStage = new Stage();
    			Text answer = new Text("");
    			Text matrix = new Text(backingMatrix.printMatrix());
    			Button rowReduce = new Button("Row reduce");
    			rowReduce.setOnAction(event2 -> {
    				double[][] stuff = backingMatrix.getBackingArray();
    				double[][] a = new double[rows.getValue()][cols.getValue()];
    		        for (int i = 0; i < rows.getValue(); i++) {
    		            for (int j = 0; j < cols.getValue(); j++) {
    		                a[i][j] = stuff[i][j];
    		            }
    		        }
    		        Matrix temp = new Matrix(a);
    				temp.rowReduce();
    				answer.setText(temp.printMatrix());
    			});
    			Button echelon = new Button("Echelon Form");
    			echelon.setOnAction(event2 -> {
    				double[][] stuff = backingMatrix.getBackingArray();
    				double[][] a = new double[rows.getValue()][cols.getValue()];
    		        for (int i = 0; i < rows.getValue(); i++) {
    		            for (int j = 0; j < cols.getValue(); j++) {
    		                a[i][j] = stuff[i][j];
    		            }
    		        }
    		        Matrix temp = new Matrix(a);
    				temp.echelonForm();
    				answer.setText(temp.printMatrix());
    			});
    			Button cramer = new Button("Cramer's Rule");
    			cramer.setOnAction(event2 -> {
    				answer.setText(backingMatrix.cramersRule());
    			});
    			Button determinant = new Button("Determinant");
    			determinant.setOnAction(event2 -> {
    				answer.setText("" + backingMatrix.doDeterminant());
    			});
    			VBox calcVBox = new VBox();
    			HBox buttons = new HBox();
    			HBox buttons2 = new HBox();
    			Button back1 = new Button("Back");
    			back1.setOnAction(event2 -> {
    				tertiaryStage.close();
    				start(new Stage());
    			});
    			buttons.getChildren().addAll(back1, rowReduce, cramer, determinant);
    			buttons2.getChildren().addAll(echelon);
    			calcVBox.getChildren().addAll(matrix, buttons, buttons2, answer);
    			answer.setTranslateX(120);
    			answer.setTranslateY(50);
    			matrix.setTranslateX(120);
    			tertiaryStage.setScene(new Scene(calcVBox));
    			tertiaryStage.setHeight(300);
    			tertiaryStage.show();
    		});
    		
    		
    		//opening of second window
    		bottom.getChildren().addAll(back, doCalc);
    		base2.getChildren().addAll(matrixView.getGrid(), bottom);
    		secondaryStage.setScene(new Scene(base2));
    		secondaryStage.show();
    	});
    	
    	
    	//openeing of third window
    	base.getChildren().addAll(rowText, rows, colText, cols, showMatrix);
    	Scene scene = new Scene(base);
    	primaryStage.setScene(scene);
    	primaryStage.setTitle("Select your dimensions");
    	primaryStage.show();
    }
    
    public static void main(String[] args) {
    	launch(args);
    }
}
