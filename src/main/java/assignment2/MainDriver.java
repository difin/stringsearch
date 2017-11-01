package assignment2;

import assignment2.suffixtree.SuffixTree;
import assignment2.suffixtree.SuffixTreeBuilder;
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
        System.out.println("Suffix codes algorithm time: " + timer.toString());
    }

    public static void verifyOutputEquality(List<Integer> output1, List<Integer> output2){

        StopWatch timer = new StopWatch();
        timer.start();

        assert output1.size() == output2.size();

        for (int i=0; i<output1.size(); i++){
            assert output2.get(i) == output1.get(i);
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