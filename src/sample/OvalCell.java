package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

public class OvalCell extends Cell {

    public OvalCell(String id, String name) {
        super(id);

        double width = 100;
        double height = 100;

//        Polygon view = new Polygon( width / 2, 0, width, height, 0, height);
        Ellipse view = new Ellipse(100,50,100,50);

        Text text = new Text(name);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(view, text);

        view.setStroke(Color.RED);
        view.setFill(Color.RED);

        setView(stackPane);

    }

}