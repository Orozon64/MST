
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
public class Main {
    static ArrayList<GraphNode> nodes = new ArrayList<>();
    public static void main(String[] args) {

        Scanner myScn = new Scanner(System.in);
        boolean should_stop = false;
        do{
            System.out.println("Podaj id wierzchołka");
            int nodekey = Integer.parseInt(myScn.nextLine());
            GraphNode n1 = new GraphNode(nodekey);
            nodes.add(n1);
            Connect(n1);
            System.out.println("Dodać jescze jakieś wierzchołki? 0 - tak, 1 - nie");
            int continue_response = Integer.parseInt(myScn.nextLine());
            switch (continue_response){
                case 0:
                    break;
                case 1:
                    should_stop = true;
                    break;
            }
        }while (!should_stop);
        for (GraphNode no: nodes){
            System.out.println("Obecny wierzchołek ma klucz równy " + no.key);
            System.out.println("Jest powiązany z:");
            for(GraphEdge ge: no.edges){
                System.out.println("Wierzchołkiem o kluczu " + ge.nodeToConnect.key + " połączeniem o wadze " + ge.weight);
            }
        }
        GraphNode dijkstra_src = new GraphNode(-99); //aby uniknąć komunikatów o niezinicjalizowanej zmiennej
        GraphNode dijkstra_des = new GraphNode(-99);
        System.out.println("Podaj id wierzchołka-źródła: ");
        int dij_src_id = Integer.parseInt(myScn.nextLine());
        System.out.println("Podaj id wierzchołka-celu: ");
        int dij_des_id = Integer.parseInt(myScn.nextLine());
        for(GraphNode gn : nodes){
            if(gn.key == dij_src_id){
                dijkstra_src = gn;
            }
            else if (gn.key == dij_des_id) {
                dijkstra_des = gn;
            }
        }
        System.out.println("Najkrótsza odległość pomiędzy tymi wierzchołkami wynosi " + Dijkstra(dijkstra_src, dijkstra_des));
    }
    public static void Connect(GraphNode con_node){
        Scanner myScn = new Scanner(System.in);
        boolean connector_exists = false;
        System.out.println("Podaj wagę połączenia");
        int edge_weight = Math.abs(Integer.parseInt(myScn.nextLine()));//używamy w. bezwzględnej, aby uniknąć wartości ujemnych - inaczej alg. Dijkstry nie zadziała
        System.out.println("Podaj id wierzchołka, z jakim chcesz połączyć ten wierzchołek.");
        int connector_id = Integer.parseInt(myScn.nextLine());
        for(GraphNode gn : nodes){
            if(gn.key == connector_id){
                GraphEdge myedge = new GraphEdge(edge_weight, gn);
                con_node.edges.add(myedge);
                GraphEdge con_edge = new GraphEdge(edge_weight, con_node);
                gn.edges.add(con_edge);
                connector_exists = true;
            }
        }
        if(!connector_exists){
            GraphNode con = new GraphNode(connector_id);
            nodes.add(con);
            GraphEdge myedge = new GraphEdge(edge_weight, con);
            con_node.edges.add(myedge);
            GraphEdge con_edge = new GraphEdge(edge_weight, con_node);
            con.edges.add(con_edge);
        }
    }
    public static int Dijkstra(GraphNode s, GraphNode dest){
        ArrayList<GraphNode> Q = nodes;
        for(GraphNode inf_setter : Q){
            inf_setter.dis = Double.POSITIVE_INFINITY;
        }
        s.dis = 0;
        while (!Q.isEmpty()){
            GraphNode v = new GraphNode(-20);
            int distance = 9999;
            for(GraphNode ge: Q){ //szukamy wierzchołka o najmniejszej odległości
                if(ge.dis < distance){
                    distance = (int) ge.dis;
                    v = ge;
                }
            }
            Q.remove(v);
            for(GraphEdge k : v.edges){
                GraphNode u = k.nodeToConnect;
                if(k.weight < u.dis){
                    u.dis = v.dis + k.weight;
                    u.previous = v;
                }
            }
        }
        return (int) dest.dis;
    }
    public static void selection_sort(GraphEdge ge_arr[]){
        int n = ge_arr.length;
        for (int i = 0; i < n-1; i++)
        {

            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (ge_arr[j].weight < ge_arr[min_idx].weight)
                    min_idx = j;


            GraphEdge temp = ge_arr[min_idx];
            ge_arr[min_idx] = ge_arr[i];
            ge_arr[i] = temp;
        }
    }
    public static void MSTKruskal(){
        ArrayList<GraphEdge> graph_edges = new ArrayList<>();
        ArrayList<GraphNode> tree_nodes = new ArrayList<>();
        ArrayList<GraphEdge> tree_edges = new ArrayList<>();
        int num_of_edges = 0;
        int v = nodes.size();
        for(GraphNode gn : nodes){
            for (GraphEdge ge: gn.edges){
                graph_edges.add(ge);

            }

        }

        GraphEdge[] edges_arr = (GraphEdge[]) graph_edges.toArray();
        selection_sort(edges_arr);
        List<GraphEdge> ge_list_final = new ArrayList<>(Arrays.asList(edges_arr));
        GraphNode startnode = new GraphNode(-99);
        while (num_of_edges != v-1){
            for(GraphEdge ge: ge_list_final){
                for(GraphNode gn : nodes){
                    if(gn.edges.contains(ge)){
                        startnode = gn;
                    }
                }

                if(!tree_nodes.contains(ge.nodeToConnect) || !tree_nodes.contains(startnode)){ //nie występuje cykl
                    tree_edges.add(ge);
                    num_of_edges++;
                    tree_nodes.add(ge.nodeToConnect);
                    tree_nodes.add(startnode);
                }

                ge_list_final.remove(ge);
            }

        }

    }
    public static void MSTPrim(){
        ArrayList<GraphNode> visited = new ArrayList<>();
        ArrayList<GraphEdge> tree_edges = new ArrayList<>();
        Random rand = new Random();
        int rand_index = rand.nextInt(nodes.size());
        for(GraphNode gn : nodes){
            if(nodes.indexOf(gn) == rand_index){
                visited.add(gn);
            }
        }
        GraphNode placeholder = new GraphNode(-99);
        GraphEdge shortest_ge = new GraphEdge(-99, placeholder);
        while (visited.size() != nodes.size()){

            for (GraphNode vis_gn : visited){
                int minweight = 999;

                for (GraphEdge ge: vis_gn.edges){
                    if(ge.weight < minweight && !visited.contains(ge.nodeToConnect)){
                        minweight = ge.weight;
                        shortest_ge = ge;

                    }

                } //znajdujemy najkrótszą krawędź
            }
            if (shortest_ge.weight != -99){
                tree_edges.add(shortest_ge);
                visited.add(shortest_ge.nodeToConnect);
            }
        }

    }
}
