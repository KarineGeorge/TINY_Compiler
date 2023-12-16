//package sample;
//
//import java.util.List;
//import java.util.Random;
//
//public class RandomLayout extends Layout {
//
//    Graph graph;
//    static int x = 0;
//    static int y = 0;
//
//    Random rnd = new Random();
//
//    public RandomLayout(Graph graph) {
//
//        this.graph = graph;
//
//    }
//
//    public void execute() {
//
//        List<Cell> cells = graph.getModel().getAllCells();
//
//        for (Cell cell : cells) {
//
////            double x = rnd.nextDouble() * 500;
////            double y = rnd.nextDouble() * 500;
//
//            cell.relocate(x * 100, y+5 * 100);
//            x++;
//            y++;
//            y++;
//
//
//        }
//
//    }
//
//}