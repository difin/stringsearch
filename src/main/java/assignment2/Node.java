package assignment2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfingerman on 10/29/17.
 */
public class Node {

    private int position;
    private List<Edge> edges;

    public Node(int position){
        this.position = position;
        edges = new ArrayList<>();
    }

    public void addValue(String value, int position){

        for (Edge edge : edges){
            if (edge.getValue().startsWith(value)){

                Edge edge2 = new Edge(edge.getValue().substring(value.length()), edge.getNode());

                Node node1 = new Node(-1);
                node1.addEdge(edge2);

                Edge edge1 = new Edge(value, node1);

                edges.remove(edge);
                edges.add(edge1);

                Node node2 = new Node(position);
                Edge edge3 = new Edge("", node2);
                node1.addEdge(edge3);

                return;
            }
        }

        Node node1 = new Node(position);
        Edge edge1 = new Edge(value, node1);
        edges.add(edge1);
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }
}
