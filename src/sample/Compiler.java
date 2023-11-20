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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;

public class Compiler extends Application {
    private Label tinyCompilerLabel;
    private Label selectCodeFileLabel;
    private Label tinyCodeLabel;
    private Label tokensLabel;
    private Button selectButton;
    private Button runButton;
    private Button returnButton;


    private TextArea codeTextArea;
    private TextArea tokensTextArea;
    private FlowPane flowPane;
    private FlowPane runflowPane;
    private FlowPane returnFlowPane;


    private HBox hbox;
    private VBox vBox;

    private FileChooser fileChooser;
    File selectedFile = null;
    String codeText = null;
    String codeArea = null;

    static Scanner tinyScanner = null;

    @Override
    public void start(Stage stage) throws Exception {
        PrimaryScene(stage);
    }

    public void PrimaryScene(Stage stage){

        tinyCompilerLabel = new Label("TINY Compiler");
        selectCodeFileLabel = new Label("Select *.TINY file");
        selectButton = new Button("Browse");
        tinyCodeLabel = new Label("Enter TINY code");
        codeTextArea = new TextArea();
        runButton = new Button("run");
        fileChooser = new FileChooser();


        tinyCompilerLabel.setTextFill(Color.DARKBLUE);
        tinyCompilerLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 28));
        selectCodeFileLabel.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 20));
        selectButton.setPrefHeight(20);
        selectButton.setPrefWidth(90);
        tinyCodeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 20));
        codeTextArea.setPrefHeight(500);
        runButton.setPrefHeight(30);
        runButton.setPrefWidth(90);

        flowPane = new FlowPane(tinyCompilerLabel);
        flowPane.setAlignment(Pos.CENTER);
        runflowPane = new FlowPane(runButton);
        runflowPane.setAlignment(Pos.CENTER);


        hbox = new HBox(selectCodeFileLabel, selectButton);
        hbox.setSpacing(15);
        vBox = new VBox(flowPane, hbox, tinyCodeLabel, codeTextArea, runflowPane);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 16;");


        selectButton.setOnAction(action -> {
            selectedFile = fileChooser.showOpenDialog(stage);
        });

        runButton.setOnAction(action -> {

            codeArea = codeTextArea.getText();

            if (selectedFile != null) {
                try {
                    codeText = Files.readString(Path.of(selectedFile.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (!codeArea.isBlank()) {
                codeText = codeArea;
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Please input your code");
                alert.showAndWait();
            }

            if (codeText != null) {
                tinyScanner = new Scanner(codeText);
                codeText = null;
                Queue<TokenRecord> tokenQueue = tinyScanner.getAllTokens();
                String tokens = tinyScanner.print(tokenQueue);
                tinyScanner.save();
                OutputScene(stage,tokens);
            }

        });

        Scene scene = new Scene(vBox, 850, 700);
        stage.setScene(scene);
        stage.setTitle("TINY Compiler");
        stage.show();
    }
    public void OutputScene(Stage stage, String tokens){

        tokensLabel = new Label("Tokens");
        tokensTextArea = new TextArea(tokens);
        returnButton = new Button("return");

        tokensLabel.setTextFill(Color.DARKBLUE);
        tokensLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 28));
        tokensTextArea.setEditable(false);
        tokensTextArea.setWrapText(true);
        tokensTextArea.setPrefHeight(600);

        flowPane = new FlowPane(tokensLabel);
        flowPane.setAlignment(Pos.CENTER);
        returnFlowPane = new FlowPane(returnButton);
        returnFlowPane.setAlignment(Pos.CENTER);

        vBox = new VBox(flowPane, tokensTextArea, returnFlowPane);
        vBox.setSpacing(20);
        vBox.setStyle("-fx-padding: 16;");

        returnButton.setOnAction(action -> {
            PrimaryScene(stage);
        });

        Scene scene = new Scene(vBox, 850, 700);
        stage.setTitle("Scheduler Project");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);

//        tinyScanner = new Scanner(
//                "()");
//        Queue<TokenRecord> tokenQueue = tinyScanner.getAllTokens();
//        String tokens = tinyScanner.print(tokenQueue);
//        System.out.println(tokens);
//        tinyScanner.save();

    }
}


