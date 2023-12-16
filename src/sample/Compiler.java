//package sample;
//
//import javafx.application.Application;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.FlowPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontPosture;
//import javafx.scene.text.FontWeight;
//import javafx.stage.Stage;
//import javafx.stage.FileChooser;
//
//import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Queue;
//
//public class Compiler extends Application {
//    private Label tinyCompilerLabel;
//    private Label selectCodeFileLabel;
//    private Label tinyCodeLabel;
//    private Label tokensLabel;
//    private Button selectButton;
//    private Button runButton;
//    private Button returnButton;
//
//
//    private TextArea codeTextArea;
//    private TextArea tokensTextArea;
//    private FlowPane flowPane;
//    private FlowPane runflowPane;
//    private FlowPane returnFlowPane;
//
//
//    private HBox hbox;
//    private VBox vBox;
//
//    private FileChooser fileChooser;
//    File selectedFile = null;
//    String codeText = null;
//    String codeArea = null;
//
//    static Scanner tinyScanner = null;
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        PrimaryScene(stage);
//    }
//
//    public void PrimaryScene(Stage stage){
//
//        tinyCompilerLabel = new Label("TINY Compiler");
//        selectCodeFileLabel = new Label("Select *.TINY file");
//        selectButton = new Button("Browse");
//        tinyCodeLabel = new Label("Enter TINY code");
//        codeTextArea = new TextArea();
//        runButton = new Button("run");
//        fileChooser = new FileChooser();
//
//
//        tinyCompilerLabel.setTextFill(Color.DARKBLUE);
//        tinyCompilerLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 28));
//        selectCodeFileLabel.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 20));
//        selectButton.setPrefHeight(20);
//        selectButton.setPrefWidth(90);
//        tinyCodeLabel.setFont(Font.font("Arial", FontWeight.NORMAL, FontPosture.ITALIC, 20));
//        codeTextArea.setPrefHeight(500);
//        runButton.setPrefHeight(30);
//        runButton.setPrefWidth(90);
//
//        flowPane = new FlowPane(tinyCompilerLabel);
//        flowPane.setAlignment(Pos.CENTER);
//        runflowPane = new FlowPane(runButton);
//        runflowPane.setAlignment(Pos.CENTER);
//
//
//        hbox = new HBox(selectCodeFileLabel, selectButton);
//        hbox.setSpacing(15);
//        vBox = new VBox(flowPane, hbox, tinyCodeLabel, codeTextArea, runflowPane);
//        vBox.setSpacing(20);
//        vBox.setStyle("-fx-padding: 16;");
//
//
//        selectButton.setOnAction(action -> {
//            selectedFile = fileChooser.showOpenDialog(stage);
//        });
//
//        runButton.setOnAction(action -> {
//
//            codeArea = codeTextArea.getText();
//
//            if (selectedFile != null) {
//                try {
//                    codeText = Files.readString(Path.of(selectedFile.getPath()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else if (!codeArea.isBlank()) {
//                codeText = codeArea;
//            } else {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Error");
//                alert.setHeaderText("Please input your code");
//                alert.showAndWait();
//            }
//
//            if (codeText != null) {
//                tinyScanner = new Scanner(codeText);
//                codeText = null;
//                Queue<TokenRecord> tokenQueue = tinyScanner.getAllTokens();
//                String tokens = tinyScanner.print(tokenQueue);
//                tinyScanner.save();
//                OutputScene(stage,tokens);
//            }
//
//        });
//
//        Scene scene = new Scene(vBox, 850, 700);
//        stage.setScene(scene);
//        stage.setTitle("TINY Compiler");
//        stage.show();
//    }
//    public void OutputScene(Stage stage, String tokens){
//
//        tokensLabel = new Label("Tokens");
//        tokensTextArea = new TextArea(tokens);
//        returnButton = new Button("return");
//
//        tokensLabel.setTextFill(Color.DARKBLUE);
//        tokensLabel.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 28));
//        tokensTextArea.setEditable(false);
//        tokensTextArea.setWrapText(true);
//        tokensTextArea.setPrefHeight(600);
//
//        flowPane = new FlowPane(tokensLabel);
//        flowPane.setAlignment(Pos.CENTER);
//        returnFlowPane = new FlowPane(returnButton);
//        returnFlowPane.setAlignment(Pos.CENTER);
//
//        vBox = new VBox(flowPane, tokensTextArea, returnFlowPane);
//        vBox.setSpacing(20);
//        vBox.setStyle("-fx-padding: 16;");
//
//        returnButton.setOnAction(action -> {
//            PrimaryScene(stage);
//        });
//
//        Scene scene = new Scene(vBox, 850, 700);
//        stage.setTitle("Scheduler Project");
//        stage.setScene(scene);
//        stage.show();
//    }
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//
//


package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Queue;


public class Compiler extends Application {


    Graph graph = new Graph();
    Model model;
    static int xAxis = -200;
    static int yAxis = -50;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        graph = new Graph();

        root.setCenter(graph.getScrollPane());

        Scene scene = new Scene(root, 1024, 768);

        primaryStage.setScene(scene);
        primaryStage.show();

        addGraphComponents();

        //Layout layout = new RandomLayout(graph);
        //layout.execute();

    }

    void drawTree(TreeNode node, int parentId, int y){
        if (node != null ) {
            if(node.getLabel() != null){

                if(node.getShape().equals("oval")){
                    System.out.println("new: " + node.getId() + " " + node.getLabel());
                    model.addCell(node.getId() + "", CellType.OVAL, node.getLabel());

                }else{
                    System.out.println("new: " + node.getId()+ " " + node.getLabel());
                    model.addCell(node.getId() + "", CellType.RECTANGLE, node.getLabel());
                }

                int index = model.getAddedCells().size()-1;
                model.getAddedCells().get(index).relocate(xAxis,y);

                if(parentId!=-1){
                    model.addEdge(parentId+"", node.getId()+"");
                }

                if(node.getLeft()!=null || node.getRight()!=null){
                    yAxis+=100;
                }
//
//                if(node.getLeft()!=null){
//
//                }
                drawTree(node.getLeft(), node.getId(), y+100);
                if(node.getRight()!=null && node.getLeft()!=null){
                    xAxis+=150;
                }
                drawTree(node.getRight(), node.getId(), y+100);

            }
            else {
                System.out.println("null node: " + node.getId());

                xAxis+=50;
                yAxis+=25;
                drawTree(node.getLeft(), parentId, y);
                xAxis+=125;
                drawTree(node.getRight(), -1,y);
            }
        }
    }

    void drawEdges(TreeNode node, int parentId){
        if (node != null ) {
            if(node.getLabel() != null){
//                if(node.getLeft() != null && node.getLeft().getLabel() == null){
//                    if(node.getLeft().getLeft().getLabel() == null){}
//                    System.out.println(node.getId()+" -> "+ node.getLeft().getLeft().getId() );
//                    model.addEdge(node.getId()+"", node.getLeft().getLeft().getId()+"");
//                }
//                if(node.getRight()!=null && node.getRight().getLabel() == null){
//                    System.out.println(node.getId()+" -> "+ node.getRight().getRight().getId() );
//                    model.addEdge(node.getId()+"", node.getRight().getRight().getId()+"");
//
//                }


                drawEdges(node.getLeft(), node.getId());
                drawEdges(node.getRight(), node.getId());
            }
            else {
                if(node.getLeft().getLabel()==null) {

                    TreeNode temp = node.getLeft();
                    while(temp.getLabel()==null){
                        if(temp.getRight()!=null){
                            temp = temp.getRight();
                        }else{
                            temp = temp.getLeft();
                        }
                    }

                    model.addEdge(temp.getId() + "", node.getRight().getId() + "");
                }else if(node.getRight().getLabel()==null) {

                    TreeNode temp = node.getRight();
                    while(temp.getLabel()==null){
                        if(temp.getLeft()!=null){
                            temp = temp.getLeft();
                        }else{
                            temp = temp.getRight();
                        }
                    }

                    model.addEdge(node.getLeft().getId() + "", parentId + "");
                }
                else {
                    model.addEdge(node.getLeft().getId() + "", node.getRight().getId() + "");
                }

                drawEdges(node.getLeft(), parentId);
                drawEdges(node.getRight(), parentId);
            }

        }
    }
    private void addGraphComponents() {

        model = graph.getModel();

        graph.beginUpdate();

//        String tinyCode = "{ Fibonacci sequence }\n" +
//                "read n;\n" +
//                "a := 0;\n" +
//                "b := 0;\n" +
//                "write a;\n" +
//                "write b;\n" +
//                "repeat\n" +
//                "  c := a + b;\n" +
//                "  write c;\n" +
//                "  a := b;\n" +
//                "  b :=c\n" +
//             "until c<n";

        String tinyCode = "{ Sample program in TINY language – computes factorial\n" +
                "}\n" +
                "read x; {input an integer }\n" +
                "if 0 < x then { don’t compute if x <= 0 }\n" +
                "fact := 1;\n" +
                "repeat\n" +
                "fact := fact * x;\n" +
                "x := x - 1\n" +
                "until x = 0;\n" +
                "write fact { output factorial of x }\n" +
                "end"; // Replace with your TINY code
        
        Scanner scanner = new Scanner(tinyCode);
        Queue<TokenRecord> tokenRecordsQueue = new LinkedList<>(scanner.getAllTokens());

        Parser parser = new Parser(tokenRecordsQueue);
        TreeNode parseTree = parser.parse();

        drawTree(parseTree,-1, -50);
        drawEdges(parseTree,-1);


        graph.endUpdate();

    }

    public static void main(String[] args) {
        launch(args);
    }

}