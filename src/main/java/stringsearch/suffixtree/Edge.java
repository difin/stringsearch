package stringsearch.suffixtree;

/**
 * Created by dfingerman on 10/30/17.
 */
public class Edge {

    private int startPosition;
    private int endPosition;
    private Node node;

    public Edge(int startPosition, int endPosition, Node node) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}
