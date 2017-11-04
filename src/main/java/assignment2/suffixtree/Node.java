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
        insertEdge(0, edges.size(), edge);
    }

    public int getPosition() {
        return position;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addSuffix(int position, int positionOriginal){

        Edge edge = findCommonPrefixEdge(0, edges.size(), position);
        int suffixLength = text.length() - position;

        if (edge != null){

            int commonCount = countCommonPrefixLength(edge.getStartPosition(), edge.getEndPosition(), position, text.length());

            if (commonCount < edge.getEndPosition() - edge.getStartPosition()){

                if (edge.getEndPosition() - (edge.getStartPosition() + commonCount) > 0){

                    Edge edge2 = new Edge(edge.getStartPosition() + commonCount, edge.getEndPosition(), edge.getNode());

                    Node node1 = new Node(edge.getNode().getPosition());
                    node1.addEdge(edge2);

                    Edge edge1 = new Edge(edge.getStartPosition(), edge.getStartPosition() + commonCount, node1);

                    edges.remove(edge);
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
                else{

                    Node node1 = new Node(positionOriginal);

                    int edge1StartPosition = -1;
                    int edge1EndPosition = -1;

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

    public Edge findCommonPrefixEdge(int minEdgePosition, int maxEdgePosition, int position){

        if (position == text.length()){
            return null;
        }

        if (minEdgePosition >= maxEdgePosition){

            if (minEdgePosition == edges.size()){
                return null;
            }

            Edge minEdge = edges.get(minEdgePosition);
            int compare = compareCharacters(minEdge.getStartPosition(), position);

            if (compare == 0){
                return edges.get(minEdgePosition);
            }

            return null;
        }

        int middleEdgePosition = (minEdgePosition + maxEdgePosition) / 2;
        Edge middleEdge = edges.get(middleEdgePosition);

        int compare = compareCharacters(middleEdge.getStartPosition(), position);

        if (compare == 0){
            return edges.get(middleEdgePosition);
        }

        if (compare < 0){
            return findCommonPrefixEdge(middleEdgePosition+1, maxEdgePosition, position);
        }

        if (compare > 0){
            return findCommonPrefixEdge(minEdgePosition, middleEdgePosition-1, position);
        }

        return null;
    }

    public void insertEdge(int minEdgePosition, int maxEdgePosition, Edge edgeToInsert){

        if (minEdgePosition >= maxEdgePosition){

            if (minEdgePosition == edges.size()){
                edges.add(minEdgePosition, edgeToInsert);
                return;
            }

            Edge minEdge = edges.get(minEdgePosition);
            int compare = compareFirstChracter(minEdge, edgeToInsert);

            if (compare >= 0){
                edges.add(minEdgePosition, edgeToInsert);
                return;
            }

            if (compare < 0){
                edges.add(minEdgePosition+1, edgeToInsert);
                return;
            }
        }

        int middleEdgePosition = (minEdgePosition + maxEdgePosition) / 2;
        Edge middleEdge = edges.get(middleEdgePosition);
        int compare = compareFirstChracter(middleEdge, edgeToInsert);

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

    public void verifyEdgesSorting(){

        for (int i=0; i<edges.size(); i++){

            Edge edge = edges.get(i);

            if ((i+1) < edges.size()){

                Edge nextEdge = edges.get(i+1);

                if (compareFirstChracter(edge, nextEdge) > 0){
                    throw new RuntimeException("Edge sorting is wrong");
                }
            }

            edge.getNode().verifyEdgesSorting();
        }
    }

    public int compareFirstChracter(Edge edge1, Edge edge2){
        return compareCharacters(edge1.getStartPosition(), edge2.getStartPosition());
    }

    private int compareCharacters(int pos1, int pos2){

        if ((pos1 == -1) && (pos2 == -1)){
            return 0;
        }

        if (pos2 == -1){
            return -1;
        }

        if (pos1 == -1){
            return 1;
        }

        if (text.charAt(pos1) < text.charAt(pos2)){
            return -1;
        }

        if (text.charAt(pos1) > text.charAt(pos2)){
            return 1;
        }

        return 0;
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

        for (Edge edge : getEdges()){

            if (edge.getStartPosition() == -1){
                if (pattern.length() == 0){
                    return edge.getNode().getPosition();
                }
                else{
                    continue;
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
        }

        return -1;
    }
}
