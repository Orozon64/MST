public class GraphEdge {
    int weight;
    GraphNode nodeToConnect;
    public GraphEdge(int edge_w, GraphNode node_con){
        weight = edge_w;
        nodeToConnect = node_con;
    }
}