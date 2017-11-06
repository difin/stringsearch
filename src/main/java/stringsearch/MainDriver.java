package stringsearch;

import stringsearch.suffixtree.SuffixTree;
import stringsearch.suffixtree.SuffixTreeBuilder;
import org.apache.commons.lang3.time.StopWatch;

import java.io.IOException;
import java.util.List;

import static java.lang.Math.min;

/**
 * Created by dfingerman on 9/23/17.
 */
public class MainDriver {

    private String stringFile;
    private String patternsFile;
    private String outputFile;

    private String text;
    private List<String> patterns;

    private List<Integer> basicAlgorithmOutput;
    private List<Integer> suffixCodesAlgorithmOutput;

    public MainDriver(){
        stringFile = "string.txt";
        patternsFile = "patterns.txt";
        outputFile = "output.txt";
    }

    public MainDriver(String directory, String outputFile) {
        this.stringFile = directory + "/" + "string.txt";
        this.patternsFile = directory + "/" + "patterns.txt";
        this.outputFile = directory + "/" + outputFile;
    }

    public static void main(String[] args) throws IOException {

        MainDriver mainDriver = new MainDriver();
        mainDriver.runPatternsFinder();
    }

    public void runPatternsFinder(){
        readFilesIntoMemory();
        runBasicAlgorithm();
        runSuffixTreesAlgorithm();
        verifyOutputEquality(basicAlgorithmOutput, suffixCodesAlgorithmOutput);
        createOutputFile(basicAlgorithmOutput);
    }

    private void readFilesIntoMemory(){

        StopWatch timer = new StopWatch();
        timer.start();

        text = FileUtils.fileToList(stringFile).get(0);
        patterns = FileUtils.fileToList(patternsFile);

        timer.stop();
        System.out.println("Files reading time: " + timer.toString());
    }

    private void runBasicAlgorithm(){

        StopWatch timer = new StopWatch();
        timer.start();

        basicAlgorithmOutput = BasicAlgorithm.search(text, patterns);

        timer.stop();
        System.out.println("Basic algorithm time: " + timer.toString());
    }

    private void runSuffixTreesAlgorithm(){

        StopWatch timer = new StopWatch();
        timer.start();

        SuffixTreeBuilder suffixTreeBuilder = new SuffixTreeBuilder();
        SuffixTree suffixTree = suffixTreeBuilder.buildSuffixTree(text);

        timer.stop();
        System.out.println("Suffix tree build time: " + timer.toString());

        timer.reset();
        timer.start();

        suffixCodesAlgorithmOutput = suffixTree.search(text, patterns);

        timer.stop();
        System.out.println("Suffix tree search time: " + timer.toString());
    }

    public static void verifyOutputEquality(List<Integer> output1, List<Integer> output2){

        StopWatch timer = new StopWatch();
        timer.start();

        if (output1.size() != output2.size()){
            throw new RuntimeException("Outputs are not equal: different length");
        }

        for (int i=0; i<output1.size(); i++){

            int o1 = output1.get(i).intValue();
            int o2 = output2.get(i).intValue();

            if (o1 != o2){
                throw new RuntimeException("Outputs are not equal");
            }
        }

        timer.stop();
        System.out.println("Checking time that 2 outputs are equal: " + timer.toString());
    }

    private void createOutputFile(List<Integer> output){

        StopWatch timer = new StopWatch();
        timer.start();

        FileUtils.listToFile(outputFile, output);

        timer.stop();
        System.out.println("Output file writing time: " + timer.toString());
    }
}