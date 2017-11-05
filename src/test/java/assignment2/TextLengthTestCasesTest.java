package assignment2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dfingerman on 9/30/17.
 */
public class TextLengthTestCasesTest {

    private String resourcesDirectoryPath = new File("src/test/resources").getAbsolutePath();

    private List<Integer> readOutputFromFile(String directory, String fileName){
        String outputFile = directory + "/" + fileName;
        List<Integer> output = new ArrayList<>();

        List<String> lines = FileUtils.fileToList(outputFile);

        for (String line : lines){
            output.add(Integer.parseInt(line));
        }

        return output;
    }

    public void runTest(String testCaseFolder){

        String testCaseFolderFullPath = resourcesDirectoryPath + "/" + testCaseFolder;
        String outputFile = "myOutput.txt";
        String referenceOutputFile = "Output.txt";

        File outpuFileOnDisk = new File(testCaseFolderFullPath + "/" + outputFile);
        outpuFileOnDisk.delete();

        MainDriver mainDriver = new MainDriver(testCaseFolderFullPath, outputFile);
        mainDriver.runPatternsFinder();

        mainDriver.verifyOutputEquality(
                readOutputFromFile(testCaseFolderFullPath, outputFile),
                readOutputFromFile(testCaseFolderFullPath, referenceOutputFile)
        );

        outpuFileOnDisk.delete();
    }

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            System.out.println("-------------------------------------------------------");
            System.out.println("\tStarting test: " + description.getMethodName());
            System.out.println("-------------------------------------------------------");
        }
    };

    @Test
    public void shortText(){
        runTest("shorttext");
    }

    @Test
    public void mediumText(){
        runTest("mediumtext");
    }

    @Test
    public void longText(){
        runTest("longtext");
    }
}