package assignment2.suffixtree;

/**
 * Created by dfingerman on 10/30/17.
 */
public class Edge {

    private String value;
    private Node node;

    public Edge(String value, Node node) {
        this.value = value;
        this.node = node;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
