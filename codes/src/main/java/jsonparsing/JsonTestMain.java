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
    public static void showFiles(File[] files) {
        System.out.println(files);
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println( file.getName());
            }
        }
    }
    public static void main(String[] args) throws Exception {
        // Initialise Ast Tree Builder and Comparison Worker.
        AstFactory astFactory = new AstFactory();
        Worker worker = new Worker();

        //Build AST from json files
        // String fileName = "src/main/resources/json/student1013.json";
        // String fileName2 = "src/main/resources/json/student1029.json";
        // AbstractSyntaxTree ast1= astFactory.makeAstFromJsonFile(fileName);
        // AbstractSyntaxTree ast2= astFactory.makeAstFromJsonFile(fileName2);
        // System.out.println(     worker.compareBreadthWise(ast1, ast2));

        // Print similarity score
        
        FileWriter fstream = new FileWriter("resultA2016Z5Z1.txt");
        BufferedWriter fileWriter = new BufferedWriter(fstream);

        File[] files = new File("src/main/resources/json").listFiles();
        Set<String> plagiarisedSet = new HashSet<String>();

        for (int i = 0; i<files.length;i++)  {
            String fileName1 =files[i].getName(); 

            String pathName1 = "src/main/resources/json/"+ fileName1; 
            AbstractSyntaxTree ast1= astFactory.makeAstFromJsonFile(pathName1);
            for (int u = i+1; u<files.length;u++)  {
                String fileName2 =files[u].getName();
           
               
                if (!plagiarisedSet.contains(fileName2)  && !fileName2.equals(fileName1)  ){
                    String pathName2 = "src/main/resources/json/"+ fileName2; 
                    AbstractSyntaxTree ast2= astFactory.makeAstFromJsonFile(pathName2);
                    double score = worker.compareBreadthWise(ast1, ast2);

                    if (score > 0.70){
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