package assignment2;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by dfingerman on 11/5/17.
 */
public class TextGenerator {

    public void generate(String fileName, int length){
        String randomString = RandomStringUtils.randomAlphabetic(length).toUpperCase();

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))){
            bufferedWriter.write(randomString);
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        TextGenerator textGenerator = new TextGenerator();
        textGenerator.generate("string.txt", 2500);
    }
}
