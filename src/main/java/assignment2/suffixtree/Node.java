package assignment2.suffixtree;

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

    public void addSuffix(String suffix, int position){

        for (Edge edge : edges){
            if (edge.getValue().startsWith(suffix)){

                Edge edge2 = new Edge(edge.getValue().substring(suffix.length()), edge.getNode());

                Node node1 = new Node(edge.getNode().getPosition());
                node1.addEdge(edge2);

                Edge edge1 = new Edge(suffix, node1);

                edges.remove(edge);
                edges.add(edge1);

                Node node2 = new Node(position);
                Edge edge3 = new Edge("", node2);
                node1.addEdge(edge3);

                return;
            }
        }

        Node node1 = new Node(position);
        Edge edge1 = new Edge(suffix, node1);
        edges.add(edge1);
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public int getPosition() {
        return position;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int search(String pattern){

        for (Edge edge : getEdges()){

            if (edge.getValue().length() < pattern.length()){

                if (pattern.startsWith(edge.getValue())){
                    pattern = pattern.substring(edge.getValue().length());
                    return edge.getNode().search(pattern);
                }
            }

            if (edge.getValue().length() >= pattern.length()){

                if (edge.getValue().startsWith(pattern)){
                    return edge.getNode().getPosition();
                }
            }
        }

        return -1;
    }
}
