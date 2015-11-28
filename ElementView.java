package main.java;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
    		return 0;
    	}
    	return value;
    }
}
