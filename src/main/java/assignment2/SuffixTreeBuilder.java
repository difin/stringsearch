package assignment2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dfingerman on 10/29/17.
 */
public class SuffixTreeBuilder {

    public SuffixTreeBuilder(){
    }

    public Node buildSuffixTree(String text){

        Node tree = new Node(-1);
        Node tree1 = new Node(0);
        Edge edge1 = new Edge(text, tree1);
        tree.addEdge(edge1);

        for (int i=1; i<text.length(); i++){

            String value = text.substring(i);

            tree.addValue(value, i);
        }

        return tree;
    }
}
