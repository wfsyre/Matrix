package main.java;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    			StackPane answerStack = new StackPane();
    			MatrixView matrix = new MatrixView(backingMatrix);
    			Button length = new Button("Length");
    			Button leftMultiply = new Button("Left Multiply");
    			length.setDisable(!backingMatrix.getIsVector());
    			Button rowReduce = new Button("Row reduce");
    			Button rightMultiply = new Button("Right Multiply");
    			leftMultiply.setOnAction(event2 -> {
    				Stage leftFourth = new Stage();
    				HBox leftOptions = new HBox();
    				Text leftRows = new Text("How many Rows?");
    				Spinner<Integer> rowNums = new Spinner<Integer>(1, 10, 3);
    				Button input = new Button("Select");
    				input.setOnAction(event3 -> {
    					answerStack.getChildren().clear();
    					MatrixView leftMatrix = new MatrixView(rowNums.getValue(), rows.getValue());
    					leftFourth.close();
    					Stage leftFifth = new Stage();
    					VBox holder = new VBox();
    					Button submit = new Button("Submit");
    					submit.setOnAction(event4 -> {
    						double[][] leftValues = new double[rowNums.getValue()][rows.getValue()];
    		    			for (int row = 0; row < rowNums.getValue(); row++ ) {
    		    				for (int col = 0; col < rows.getValue(); col++) {
    		    					ElementView leftElement = leftMatrix.getElementAtPosition(new Position(row, col));
    		    				    leftValues[row][col] = leftElement.getValue();
    		    				}
    		    			}
    		    			Matrix leftBackingMatrix = new Matrix(leftValues);
    		    			Matrix answer = backingMatrix.leftMultiply(leftBackingMatrix);
    		    			MatrixView leftBackingMatrixView = new MatrixView(answer);
    		    			answerStack.getChildren().addAll(leftBackingMatrixView.getGrid());
    		    			leftFifth.close();
    					});
    					holder.getChildren().addAll(leftMatrix.getGrid(), submit);
    					leftFifth.setScene(new Scene(holder));
    					leftFifth.setTitle("Enter Your Matrix");
    					leftFifth.show();
    				});
    				leftOptions.getChildren().addAll(leftRows, rowNums, input);
    				leftFourth.setScene(new Scene(leftOptions));
    				leftFourth.setTitle("Matrix Specifications");
    				leftFourth.show();
    			});
    			rightMultiply.setOnAction(event2 -> {
    				Stage fourth = new Stage();
    				HBox options = new HBox();
    				Text column = new Text("How many Columns?");
    				Spinner<Integer> columns = new Spinner<Integer>(1, 10, 3);
    				Button input = new Button("Select");
    				input.setOnAction(event3 -> {
    					answerStack.getChildren().clear();
    					MatrixView rightMatrix = new MatrixView(cols.getValue(), columns.getValue());
    					fourth.close();
    					Stage fifth = new Stage();
    					VBox holder = new VBox();
    					Button submit = new Button("Submit");
    					submit.setOnAction(event4 -> {
    						double[][] rightValues = new double[cols.getValue()][columns.getValue()];
    		    			for (int row = 0; row < cols.getValue(); row++ ) {
    		    				for (int col = 0; col < columns.getValue(); col++) {
    		    					ElementView rightElement = rightMatrix.getElementAtPosition(new Position(row, col));
    		    				    rightValues[row][col] = rightElement.getValue();
    		    				}
    		    			}
    		    			Matrix rightBackingMatrix = new Matrix(rightValues);
    		    			MatrixView answerMatrix = new MatrixView(backingMatrix.rightMutliply(rightBackingMatrix));
    		    			answerStack.getChildren().addAll(answerMatrix.getGrid());
    		    			fifth.close();
    					});
    					holder.getChildren().addAll(rightMatrix.getGrid(), submit);
    					fifth.setScene(new Scene(holder));
    					fifth.setTitle("Matrix Specifications");
    					fifth.show();
    				});
    				options.getChildren().addAll(column, columns, input);
    				fourth.setScene(new Scene(options));
    				fourth.setTitle("Enter Your Matrix");
    				fourth.show();
    			});
    			length.setOnAction(event3 -> {
    				answerStack.getChildren().clear();
    				answerStack.getChildren().addAll(
    						new Text("srqt(" + backingMatrix.getTranspose().rightMutliply(
    								backingMatrix).toString() + ")"));
    			});
    			rowReduce.setOnAction(event2 -> {
    				answerStack.getChildren().clear();
    				double[][] stuff = backingMatrix.getBackingArray();
    				double[][] a = new double[rows.getValue()][cols.getValue()];
    		        for (int i = 0; i < rows.getValue(); i++) {
    		            for (int j = 0; j < cols.getValue(); j++) {
    		                a[i][j] = stuff[i][j];
    		            }
    		        }
    		        Matrix temp = new Matrix(a);
    				temp.rowReduce();
    				MatrixView rowReduced = new MatrixView(temp);
    				answerStack.getChildren().addAll(rowReduced.getGrid());
    			});
    			Button echelon = new Button("Echelon Form");
    			echelon.setOnAction(event2 -> {
    				answerStack.getChildren().clear();
    				double[][] stuff = backingMatrix.getBackingArray();
    				double[][] a = new double[rows.getValue()][cols.getValue()];
    		        for (int i = 0; i < rows.getValue(); i++) {
    		            for (int j = 0; j < cols.getValue(); j++) {
    		                a[i][j] = stuff[i][j];
    		            }
    		        }
    		        Matrix temp = new Matrix(a);
    				temp.echelonForm();
    				MatrixView echelonMatrix = new MatrixView(temp);
    				answerStack.getChildren().addAll(echelonMatrix.getGrid());
    			});
    			Button cramer = new Button("Cramer's Rule");
    			cramer.setOnAction(event2 -> {
    				answerStack.getChildren().clear();
    				answerStack.getChildren().addAll(new Text("" + backingMatrix.cramersRule()));
    			});
    			Button determinant = new Button("Determinant");
    			determinant.setOnAction(event2 -> {
    				answerStack.getChildren().clear();
    				answerStack.getChildren().addAll(new Text("" + backingMatrix.doDeterminant()));
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
    			buttons2.getChildren().addAll(echelon, rightMultiply, leftMultiply, length);
    			calcVBox.getChildren().addAll(matrix.getGrid(), buttons, buttons2, answerStack);
    			answerStack.setTranslateX(120);
    			answerStack.setTranslateY(50);
    			matrix.getGrid().setTranslateX(120);
    			tertiaryStage.setScene(new Scene(calcVBox));
    			tertiaryStage.setHeight(300);
    			tertiaryStage.setTitle("Select a Calculation");
    			tertiaryStage.show();
    		});
    		
    		
    		//opening of second window
    		bottom.getChildren().addAll(back, doCalc);
    		base2.getChildren().addAll(matrixView.getGrid(), bottom);
    		secondaryStage.setScene(new Scene(base2));
    		secondaryStage.setTitle("Enter Your Matrix");
    		secondaryStage.show();
    	});
    	
    	
    	//opening of first window
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
