package stringsearch.suffixtree;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dfingerman on 10/29/17.
 */
public class Node {

    private static String text;

    private int position;
    private Map<Character, Edge> edges;

    public static void setText(String text) {
        Node.text = text;
    }

    public Node(int position){
        this.position = position;
        edges = new HashMap<Character, Edge>();
    }

    public void addEdge(Edge edge){
        insertEdge(edge);
    }

    public int getPosition() {
        return position;
    }

    public int addSuffix(int position, int positionOriginal){

        Edge edge = findCommonPrefixEdge(position);
        int suffixLength = text.length() - position;

        if (edge != null){

            int edgeValueLength = edge.getEndPosition() - edge.getStartPosition();
            int commonCount = countCommonPrefixLength(edge.getStartPosition(), edge.getEndPosition(), position, text.length());

            if (commonCount < edgeValueLength){

                if (edge.getEndPosition() - (edge.getStartPosition() + commonCount) > 0){

                    Edge edge2 = new Edge(edge.getStartPosition() + commonCount, edge.getEndPosition(), edge.getNode());

                    Node node1 = new Node(edge.getNode().getPosition());
                    node1.addEdge(edge2);

                    Edge edge1 = new Edge(edge.getStartPosition(), edge.getStartPosition() + commonCount, node1);

                    edges.remove(text.charAt(edge.getStartPosition()));
                    addEdge(edge1);

                    Node node2 = new Node(positionOriginal);
                    int edge3StartPosition = -1;
                    int edge3EndPosition = -1;

                    if (commonCount < suffixLength){
                        edge3StartPosition = position + commonCount;
                        edge3EndPosition = text.length();
                    }

                    Edge edge3 = new Edge(edge3StartPosition, edge3EndPosition, node2);
                    node1.addEdge(edge3);
                }
                else{ // commonCount == edgeValueLength

                    Node node1 = new Node(positionOriginal);

                    int edge1StartPosition = -1;
                    int edge1EndPosition = -1;

                    Edge edge1 = new Edge(edge1StartPosition, edge1EndPosition, node1);
                    edge.getNode().addEdge(edge1);
                }
            }
            else{ // commonCount == edgeValueLength
                return 1 + edge.getNode().addSuffix(position + commonCount, positionOriginal);
            }
        }
        else{ // no edge with common prefix

            Node node1 = new Node(positionOriginal);

            int edge1StartPosition = -1;
            int edge1EndPosition = -1;

            if (position < text.length()){
                edge1StartPosition = position;
                edge1EndPosition = position + suffixLength;
            }

            Edge edge1 = new Edge(edge1StartPosition, edge1EndPosition, node1);
            addEdge(edge1);
        }

        return 1;
    }

    public Edge findCommonPrefixEdge(int position){

        if (position == -1 || position == text.length()){
            return edges.get(Character.MIN_VALUE);
        }
        else{
            return edges.get(text.charAt(position));
        }
    }

    public Edge findCommonPrefixEdge(String pattern){
        return edges.get(pattern.charAt(0));
    }

    public void insertEdge(Edge edgeToInsert){

        if (edgeToInsert.getStartPosition() == -1 || edgeToInsert.getStartPosition() == text.length()){
            edges.put(Character.MIN_VALUE, edgeToInsert);
        }
        else{
            edges.put(text.charAt(edgeToInsert.getStartPosition()), edgeToInsert);
        }
    }

    private int countCommonPrefixLength(int start1, int end1, int start2, int end2){

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

    private int countCommonPrefixLength(String s1, String s2){

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

        Edge edge = findCommonPrefixEdge(pattern);

        if (edge == null){
            return -1;
        }

        if (edge.getStartPosition() == -1){
            if (pattern.length() == 0){
                return edge.getNode().getPosition();
            }
            else{
                return -1;
            }
        }

        String edgeValue = text.substring(edge.getStartPosition(), edge.getEndPosition());
        int commonCount = countCommonPrefixLength(edgeValue, pattern);

        if (commonCount == pattern.length()){
            return edge.getNode().getPosition();
        }

        if (commonCount > 0){
            return edge.getNode().search(pattern.substring(commonCount));
        }

        return -1;
    }
}
