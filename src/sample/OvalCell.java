package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class OvalCell extends Cell {

    public OvalCell(String id, String name) {
        super(id);

        double width = 100;
        double height = 100;

//        Polygon view = new Polygon( width / 2, 0, width, height, 0, height);
        Ellipse view = new Ellipse(60,45,60,45);

        Text text = new Text(name);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(view, text);

        text.setFont(new Font("Monospaced", 18));
        view.setStroke(Color.rgb(151, 158, 168));
        view.setStrokeWidth(2);
        view.setFill(Color.rgb(255, 217, 217));

        setView(stackPane);

    }

}