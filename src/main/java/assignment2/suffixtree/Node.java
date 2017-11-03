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

    public static void setText(String text) {
        Node.text = text;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
        //insertEdge(0, edges.size(), edge);
    }

    public int getPosition() {
        return position;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addSuffix(int position, int positionOriginal){

        int suffixLength = text.length() - position;

        for (Edge edge : edges){

            int commonCount = commonCharactersCount(edge.getStartPosition(), edge.getEndPosition(), position, text.length());

            if (commonCount > 0){

                if (commonCount < edge.getEndPosition() - edge.getStartPosition()){

                    if (edge.getEndPosition() - (edge.getStartPosition() + commonCount)  > 1){

                        Edge edge2 = new Edge(edge.getStartPosition() + commonCount, edge.getEndPosition(), edge.getNode());

                        Node node1 = new Node(edge.getNode().getPosition());
                        node1.addEdge(edge2);

                        Edge edge1 = new Edge(edge.getStartPosition(), edge.getStartPosition() + commonCount, node1);

                        edges.remove(edge);
                        addEdge(edge1);

                        Node node2 = new Node(positionOriginal);
                        Edge edge3 = new Edge(position + commonCount, text.length(), node2);
                        node1.addEdge(edge3);
                    }
                    else{

                        Node node1 = new Node(positionOriginal);

                        int edge1StartPosition = -1;
                        int edge1EndPosition = -1;

                        if (position + commonCount != text.length()){
                            edge1StartPosition = position + commonCount;
                            edge1EndPosition = text.length();
                        }

                        Edge edge1 = new Edge(edge1StartPosition, edge1EndPosition, node1);
                        edge.getNode().addEdge(edge1);
                    }

                    return;
                }

                if (commonCount == edge.getEndPosition() - edge.getStartPosition()){
                    edge.getNode().addSuffix(position + commonCount, positionOriginal);
                    return;
                }
            }
        }

        Node node1 = new Node(positionOriginal);
        Edge edge1 = new Edge(position, position + suffixLength, node1);
        addEdge(edge1);
    }

    public Edge findPrefixEdge(int position){
        return null;
    }

    public void insertEdge(int minEdgePosition, int maxEdgePosition, Edge edgeToInsert){

        if (minEdgePosition >= maxEdgePosition){
            edges.add(minEdgePosition, edgeToInsert);
            return;
        }

        int middleEdgePosition = (minEdgePosition + maxEdgePosition) / 2;
        Edge middleEdge = edges.get(middleEdgePosition);
        int compare = compareSubstrings(middleEdge.getStartPosition(), middleEdge.getEndPosition(), edgeToInsert.getStartPosition(), edgeToInsert.getEndPosition());

        if (compare == 0){
            edges.add(middleEdgePosition, edgeToInsert);
            return;
        }

        if (compare < 0){
            insertEdge(middleEdgePosition+1, maxEdgePosition, edgeToInsert);
            return;
        }

        if (compare > 0){
            insertEdge(minEdgePosition, middleEdgePosition-1, edgeToInsert);
            return;
        }
    }

    private int compareSubstrings(int start1, int end1, int start2, int end2){

        while (start2 < end2){

            if (start1 == end1){
                return 1;
            }

            if (text.charAt(start1) < text.charAt(start2)){
                return -1;
            }

            if (text.charAt(start1) > text.charAt(start2)){
                return 1;
            }

            start2++;
            start1++;
        }

        return 0;
    }

    private int commonCharactersCount(int start1, int end1, int start2, int end2){

        int count = 0;

        while (start1 < end1 && start2 < end2){

            if (start1 == end1 || start2 == end2){
                return count;
            }

            if (text.charAt(start1) == text.charAt(start2)){
                count++;
            }
            else{
                return count;
            }

            start2++;
            start1++;
        }

        return count;
    }

    private int commonCharactersCount(String s1, String s2){

        int start1 = 0;
        int end1 = s1.length();
        int start2 = 0;
        int end2 = s2.length();

        int count = 0;

        while (start1 < end1 && start2 < end2){

            if (start1 == end1 || start2 == end2){
                return count;
            }

            if (s1.charAt(start1) == s2.charAt(start2)){
                count++;
            }
            else{
                return count;
            }

            start2++;
            start1++;
        }

        return count;
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
            int commonCount = commonCharactersCount(edgeValue, pattern);

            if (commonCount == pattern.length()){
                return edge.getNode().getPosition();
            }

            if (commonCount > 0){
                return edge.getNode().search(pattern.substring(commonCount));
            }
        }

        return -1;
    }
}
