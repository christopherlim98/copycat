package jsonparsing;

import jsonparsing.copycat.Worker;
import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.parser.AstFactory;
import java.io.FileWriter; 
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class JsonTestMain {

    public static void main(String[] args) throws Exception {
        // Initialise Ast Tree Builder and Comparison Worker.
        AstFactory astFactory = new AstFactory();
        Worker worker = new Worker();
        JohnTest johnTest = new JohnTest();

        // CHange the output file name here
        FileWriter fstream = new FileWriter("resultA2016Z5Z1(SetWise).txt");
        BufferedWriter fileWriter = new BufferedWriter(fstream);

        // Get an array of all the file names in resources
        File[] files = new File("src/main/resources/json").listFiles();
        Set<String> plagiarisedSet = new HashSet<String>();
        // Complexity O((n^2)/2)
        for (int i = 0; i<files.length;i++)  {
            String fileName1 =files[i].getName(); 

            String pathName1 = "src/main/resources/json/"+ fileName1; 
            AbstractSyntaxTree ast1= astFactory.makeAstFromJsonFile(pathName1);
            for (int u = i+1; u<files.length;u++)  {
                String fileName2 =files[u].getName();
           
               
                if (!plagiarisedSet.contains(fileName2)  && !fileName2.equals(fileName1)  ){
                    String pathName2 = "src/main/resources/json/"+ fileName2; 
                    AbstractSyntaxTree ast2= astFactory.makeAstFromJsonFile(pathName2);
                    // Chris's
                    // double score = worker.compareBreadthWise(ast1, ast2);
                    //John's
                    double score = johnTest.compareSetWise(ast1, ast2);


                    if (score > 70){
                        System.out.println( "Similarity score: " + score);
                        fileWriter.write(fileName1+" , "+ fileName2 + " , " + score);
                        fileWriter.newLine();
                        
                    }

                }
                

            }
            plagiarisedSet.add(fileName1);

        }
        fileWriter.close();



    }
}