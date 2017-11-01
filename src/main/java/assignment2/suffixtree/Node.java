package assignment2.suffixtree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfingerman on 10/29/17.
 */
public class Node {

    private static String text;

    private int position;
    private List<Edge> edges;

    public Node(int position){
        this.position = position;
        edges = new ArrayList<>();
    }

    public void addSuffix(int position){

        int suffixLength = text.length() - position;

        for (Edge edge : edges){

            if (isStartsWith(edge.getStartPosition(), edge.getEndPosition(), position, text.length())){

                Edge edge2 = new Edge(edge.getStartPosition() + suffixLength, edge.getEndPosition(), edge.getNode());

                Node node1 = new Node(edge.getNode().getPosition());
                node1.addEdge(edge2);

                Edge edge1 = new Edge(edge.getStartPosition(), edge.getStartPosition() + suffixLength, node1);

                edges.remove(edge);
                edges.add(edge1);

                Node node2 = new Node(position);
                Edge edge3 = new Edge(-1, -1, node2);
                node1.addEdge(edge3);

                return;
            }
        }

        Node node1 = new Node(position);
        Edge edge1 = new Edge(position, position + suffixLength, node1);
        edges.add(edge1);
    }

    private boolean isStartsWith(int start1, int end1, int start2, int end2){

        boolean starts = true;

        while (starts && start2 < end2){

            if (start1 == end1){
                starts = false;
            }

            if (text.charAt(start2) != text.charAt(start1)){
                starts = false;
            }

            start2++;
            start1++;
        }

        return starts;
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

            if (edge.getStartPosition() == -1){

                if (pattern.length() > 0){
                    return -1;
                }
                else{
                    return edge.getNode().getPosition();
                }
            }

            String edgeValue = text.substring(edge.getStartPosition(), edge.getEndPosition());

            if (edgeValue.length() < pattern.length()){

                if (pattern.startsWith(edgeValue)){
                    pattern = pattern.substring(edgeValue.length());
                    return edge.getNode().search(pattern);
                }
            }

            if (edgeValue.length() >= pattern.length()){

                if (edgeValue.startsWith(pattern)){
                    return edge.getNode().getPosition();
                }
            }
        }

        return -1;
    }

    public static void setText(String text) {
        Node.text = text;
    }
}
