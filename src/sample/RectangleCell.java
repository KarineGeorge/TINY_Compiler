package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class RectangleCell extends Cell {

    public RectangleCell( String id, String name) {
        super( id);

        Rectangle view = new Rectangle(70,70);
        Text text = new Text(name);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(view, text);

        view.setStroke(Color.DODGERBLUE);
        view.setFill(Color.DODGERBLUE);

        setView(stackPane);

    }

}