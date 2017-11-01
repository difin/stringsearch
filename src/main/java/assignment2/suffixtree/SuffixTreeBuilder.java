package assignment2.suffixtree;

/**
 * Created by dfingerman on 10/29/17.
 */
public class SuffixTreeBuilder {

    public SuffixTreeBuilder(){
    }

    public SuffixTree buildSuffixTree(String text){

        SuffixTree suffixTree = new SuffixTree(text);

        for (int i=1; i<text.length(); i++){

            if (i % 1000 == 0)
                System.out.println(i);

            suffixTree.addSuffix(i);
        }

        return suffixTree;
    }
}
