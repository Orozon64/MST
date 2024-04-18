import java.util.ArrayList;
import java.util.Scanner;
public class GraphNode {
    int key;
    double dis;
    GraphNode previous;
    ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();
    public GraphNode(int id){
        key = id;
    }

}