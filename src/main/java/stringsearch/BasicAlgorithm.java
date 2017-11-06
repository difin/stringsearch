package stringsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfingerman on 10/28/17.
 */
public class BasicAlgorithm {

    public static List<Integer> search(String text, List<String> patterns){

        List<Integer> outputList = new ArrayList<>();

        for (String pattern : patterns.subList(1, patterns.size())) {

            int found = -1;

            for (int i=0; i<=text.length()-pattern.length(); i++){
                if (text.substring(i, i+pattern.length()).equals(pattern)){
                    found = i;
                    break;
                }
            }

            outputList.add(found);
        }

        return outputList;
    }
}
