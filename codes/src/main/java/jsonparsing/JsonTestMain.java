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

    private static String pathRoot = "src/main/resources/z1/";

    private static String fileOutput = "A16Z1Z1Snapshots.txt";
    public static void main(String[] args){
        // Initialise Ast Tree Builder and Comparison Worker.

        // Get an array of all the file names in resources
        File[] files = new File(pathRoot).listFiles((dir, name) -> !name.equals(".DS_Store"));
        ArrayList<AbstractSyntaxTree> astList = new ArrayList<AbstractSyntaxTree>();
        HashMap<AbstractSyntaxTree, String> astStudentMap = new HashMap<>();

        // Generate AstSets
        generateAstSet(files,  astList, astStudentMap);

        // Compare Asts pair-wise
        compareAsts(astList, astStudentMap);

    }

    public static void generateAstSet(File[] files, ArrayList<AbstractSyntaxTree> astList,
        HashMap<AbstractSyntaxTree, String> astStudentMap){
        // Complexity of O(n) to build all trees from their files
        AstFactory astFactory = new AstFactory();
        for (int i=0; i < files.length; i++){
            String fileName =files[i].getName();
            String pathName = pathRoot + fileName;

            // Complexity of O(n) because we are parsing from JSON
            // to AST node by node
            AbstractSyntaxTree ast= astFactory.makeAstFromJsonFile(pathName);
            astStudentMap.put(ast, fileName);
            astList.add(ast);
        }
    }

    public static void compareAsts(ArrayList<AbstractSyntaxTree> astList,
                                    HashMap<AbstractSyntaxTree, String> astStudentMap){
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
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}