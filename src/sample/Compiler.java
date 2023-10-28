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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    private FileChooser fileChooser;
    File selectedFile = null;
    String codeText = " ";
    static String codeFile = "";

    @Override
    public void start(Stage primaryStage) throws Exception{

        tinyCompilerLabel = new Label("TINY Compiler");
        selectCodeFileLabel = new Label(" Select file *.TINY");
        selectButton = new Button("Browse");
        tinyCodeLabel = new Label(" Enter TINY code");
        codeTextArea = new TextArea();
        runButton = new Button("run");
        fileChooser = new FileChooser();



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


        selectButton.setOnAction(action -> {
            selectedFile = fileChooser.showOpenDialog(primaryStage);
        });
        runButton.setOnAction(action -> {
            codeText = codeTextArea.getText();

            if(selectedFile != null) {
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(selectedFile));
                } catch (FileNotFoundException e1) {
                    throw new RuntimeException(e1);
                }


            }else if(codeText != null){
                System.out.println(codeText);
            }
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


