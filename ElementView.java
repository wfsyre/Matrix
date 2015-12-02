package main.java;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ElementView {
    StackPane rootNode;
    TextField acceptor;
    Rectangle base;
    Position pos;
    public ElementView(Position pos) {
    	rootNode = new StackPane();
    	acceptor = new TextField();
    	acceptor.setMaxWidth(45);
    	acceptor.setMaxHeight(45);
    	acceptor.setAlignment(Pos.CENTER);
    	this.pos = pos;
    }
    
    public StackPane getRootNode() {
    	rootNode.getChildren().clear();
    	base = new Rectangle(75, 75, Color.TRANSPARENT);
    	base.setFill(Color.LIGHTGRAY);
    	if ((pos.getRow() + pos.getCol()) % 2 == 0) {
    		base.setFill(Color.LIGHTBLUE);
    	}
    	rootNode.getChildren().addAll(base, acceptor);
    	return rootNode;
    }
    
    public double getValue() {
    	double value;
    	try {
    		value = Double.parseDouble(acceptor.getText());
    	} catch (Exception e) {
    		if (acceptor.getText().contains("/")) {
    			double numerator = 0;
    			double denominator = 1;
    			String answer = acceptor.getText();
    			int index = answer.indexOf("/");
    			String numeratorString = answer.substring(0,index);
    			String denominatorString = answer.substring(index + 1);
    			try {
    				numerator = Double.parseDouble(numeratorString);
    				denominator = Double.parseDouble(denominatorString);
    				return Math.floor((numerator/denominator) * 100) / 100;
    			} catch (Exception f) {
    				return 0;
    			}
    		}
    		return 0;
    	}
    	return value;
    }
    
    public StackPane getRootNode(double value) {
    	rootNode.getChildren().clear();
    	base = new Rectangle(46, 46, Color.TRANSPARENT);
    	base.setFill(Color.BLACK);
    	Text text = new Text(value + "");
    	Rectangle backer = new Rectangle(45, 45, Color.WHITE);
    	if ((pos.getRow() + pos.getCol()) % 2 == 0) {
    		base.setFill(Color.BLACK);
    	}
    	rootNode.getChildren().addAll(base, backer, text);
    	return rootNode;
    }
}
