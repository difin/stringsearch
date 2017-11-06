package stringsearch.suffixtree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfingerman on 10/31/17.
 */
public class SuffixTree {

    private Node root;

    public SuffixTree(String text){

        root.setText(text);

        root = new Node(-1);
        Node node1 = new Node(0);
        Edge edge1 = new Edge(0, text.length(), node1);
        root.addEdge(edge1);
    }

    public int addSuffix(int position){
        return root.addSuffix(position, position);
    }

    public List<Integer> search(String text, List<String> patterns){

        List<Integer> outputList = new ArrayList<>();

        for (String pattern : patterns.subList(1, patterns.size())) {
            outputList.add(root.search(pattern));
        }

        return outputList;
    }
}
