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

    public static void main(String[] args){
        // Initialise Ast Tree Builder and Comparison Worker.
        String fileOutput = "A16Z5Z1Snapshots.txt";

        // Get an array of all the file names in resources
        File[] files = new File("src/main/resources/json").listFiles((dir, name) -> !name.equals(".DS_Store"));
        ArrayList<AbstractSyntaxTree> astList = new ArrayList<AbstractSyntaxTree>();
        HashMap<AbstractSyntaxTree, String> astStudentMap = new HashMap<>();

        // Generate AstSets
        generateAstSet(files,  astList, astStudentMap);

        // Compare Asts pair-wise
        compareAsts(astList, astStudentMap, fileOutput);

    }

    public static void generateAstSet(File[] files, ArrayList<AbstractSyntaxTree> astList,
        HashMap<AbstractSyntaxTree, String> astStudentMap){
        // Complexity of O(n) to build all trees from their files
        AstFactory astFactory = new AstFactory();
        for (int i=0; i < files.length; i++){
            String fileName =files[i].getName();
            String pathName = "src/main/resources/json/"+ fileName;

            // Complexity of O(n) because we are parsing from JSON
            // to AST node by node
            AbstractSyntaxTree ast= astFactory.makeAstFromJsonFile(pathName);
            astStudentMap.put(ast, fileName);
            astList.add(ast);
        }
    }

    public static void compareAsts(ArrayList<AbstractSyntaxTree> astList,
                                    HashMap<AbstractSyntaxTree, String> astStudentMap,
                                    String fileOutput){
        Worker worker = new Worker();
        // CHange the output file name here

        try {
            // Complexity O((n^2)/2)
            // Compare files pair wise
            FileWriter fstream = new FileWriter(fileOutput);
            BufferedWriter fileWriter = new BufferedWriter(fstream);
            for (int i = 0; i < astList.size(); i++)  {
                AbstractSyntaxTree ast1 = astList.get(i);
                for (int j = i + 1; j < astList.size(); j++){
                    AbstractSyntaxTree ast2 = astList.get(j);
                    double score = worker.compareSnapshots(ast1, ast2);
                    if (score > 40){
                        System.out.println( "Similarity score: " + score + " , " + astStudentMap.get(ast1) + " , " +astStudentMap.get(ast2));
                        fileWriter.write(astStudentMap.get(ast1)+" , "+ astStudentMap.get(ast2) + " , " + score);
                        fileWriter.newLine();
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}