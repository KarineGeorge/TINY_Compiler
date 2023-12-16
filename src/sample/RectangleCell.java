package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


import java.awt.*;

public class RectangleCell extends Cell {

    public RectangleCell( String id, String name) {
        super( id);

        Rectangle view = new Rectangle(100,70);
        Text text = new Text(name);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(view, text);

        text.setFont(new Font("Monospaced", 18));
        view.setStroke(Color.rgb(151, 158, 168));
        view.setStrokeWidth(2);
        view.setFill(Color.rgb(207, 228, 255));

        setView(stackPane);

    }

}