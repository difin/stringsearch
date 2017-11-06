package stringsearch.suffixtree;

import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfingerman on 10/29/17.
 */
public class SuffixTreeBuilder {

    public SuffixTreeBuilder(){
    }

    public SuffixTree buildSuffixTree(String text){

        StopWatch timer = new StopWatch();
        SuffixTree suffixTree = new SuffixTree(text);
        List<Integer> nodeCheckedList = new ArrayList<>();

        timer.start();

        for (int i=1; i<text.length(); i++){

            int nodesChecked = suffixTree.addSuffix(i);
            nodeCheckedList.add(nodesChecked);

//            if (i % 10000 == 0 && i > 0) {
//                timer.stop();
//
//                long sum = 0;
//                for (int checked : nodeCheckedList){
//                    sum = sum + checked;
//                }
//
//                long averageNodes = sum/nodeCheckedList.size();
//                System.out.println("Suffixes processed: " + i + "; Average # of nodes checked per suffix: " + averageNodes + "; time: " + timer.toString());
//                nodeCheckedList = new ArrayList<>();
//
//                timer.reset();
//                timer.start();
//            }
        }

        return suffixTree;
    }
}
