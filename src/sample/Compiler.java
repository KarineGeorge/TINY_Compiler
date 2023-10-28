package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.io.IOException;
import java.io.StringReader;

public class Compiler extends Application {
    private Label tinyCompilerLabel;
    private Label selectCodeFileLabel;
    private Button selectButton;
    private Button runButton;
    private Label tinyCodeLabel;

    private TextArea codeTextArea;
    private FlowPane flowPane;
    private FlowPane runflowPane;

    private HBox hbox;
    private VBox vBox;

    String xmlText = " ";
    static String xmlFile = "";

    @Override
    public void start(Stage primaryStage) throws Exception{

        tinyCompilerLabel = new Label("TINY Compiler");
        selectCodeFileLabel = new Label(" Select file *.TINY");
        selectButton = new Button("Browse");
        tinyCodeLabel = new Label(" Enter TINY code");
        codeTextArea = new TextArea();
        runButton = new Button("run");



        tinyCompilerLabel.setTextFill(Color.DARKBLUE);
        tinyCompilerLabel.setFont(Font.font("Arial" , FontWeight.BOLD , FontPosture.ITALIC, 28));
        selectCodeFileLabel.setFont(Font.font("Arial" , FontWeight.NORMAL , FontPosture.ITALIC,20));
        selectButton.setPrefHeight(20);
        selectButton.setPrefWidth(90);
        tinyCodeLabel.setFont(Font.font("Arial" , FontWeight.NORMAL , FontPosture.ITALIC, 20));
        codeTextArea.setPrefHeight(500);
        runButton.setPrefHeight(30);
        runButton.setPrefWidth(90);

        flowPane = new FlowPane(tinyCompilerLabel);
        flowPane.setAlignment(Pos.CENTER);
        runflowPane = new FlowPane(runButton);
        runflowPane.setAlignment(Pos.CENTER);


        hbox = new HBox(selectCodeFileLabel,selectButton);
        hbox.setSpacing(15);
        vBox = new VBox(flowPane,hbox,tinyCodeLabel,codeTextArea,runflowPane);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 16;");


        //selectButton.setOnAction(new selectFileHandler());
        runButton.setOnAction(action -> {
            xmlText = codeTextArea.getText();
            xmlFile = String.valueOf(xmlText);
        });

        Scene scene = new Scene(vBox,700, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TINY Compiler");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}


