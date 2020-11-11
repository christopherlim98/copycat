package jsonparsing;

import jsonparsing.copycat.Worker;

import jsonparsing.copycat.Worker2;
import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.exception.JsonToTreeTimeoutException;
import jsonparsing.parser.AstFactory;


import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class JsonTestMain {

    private static final String PATHROOT = "data/Z3/";
    private static final String FILEOUTPUT = "A16Z1Z1SnapshotsAlt.txt";
    private static final double THRESHOLD = 80;
    private static List<String> filesNotProcessed = new ArrayList<>();
    
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter Comparison Type");
        System.out.println("1. Naive Comparison");
        System.out.println("2. BreadthWise");
        System.out.println("3. SetWise");
        int comparisonType = sc.nextInt();
        // Initialise Ast Tree Builder and Comparison Worker.

        // Get an array of all the file names in resources
        File[] files = new File(PATHROOT).listFiles((dir, name) -> !name.equals(".DS_Store"));
        ArrayList<AbstractSyntaxTree> astList = new ArrayList<AbstractSyntaxTree>();
        HashMap<AbstractSyntaxTree, String> astStudentMap = new HashMap<>();

        // Generate AstSets
        generateAstSet(files,  astList, astStudentMap);
        // Compare Asts pair-wise

        compareAsts(astList, astStudentMap, comparisonType);

    }



    public static void generateAstSet(File[] files, ArrayList<AbstractSyntaxTree> astList,
        HashMap<AbstractSyntaxTree, String> astStudentMap){
        // Complexity of O(n) to build all trees from their files
        AstFactory astFactory = new AstFactory();
        for (int i=0; i < files.length; i++){
            String fileName =files[i].getName();
            String pathName = PATHROOT + fileName;

            // Complexity of O(n) because we are parsing from JSON
            // to AST node by node
            try {
                AbstractSyntaxTree ast= astFactory.makeAstFromJsonFile(pathName);
                astStudentMap.put(ast, fileName);
                astList.add(ast);
            } catch (JsonToTreeTimeoutException e){
                filesNotProcessed.add(fileName);
            } catch (IOException e){
                filesNotProcessed.add(fileName);
            }

            if (i % 20 == 0){
                System.out.println("Built " + i + " trees...");
            }
        }
    }

    public static void compareAsts(ArrayList<AbstractSyntaxTree> astList,
                                    HashMap<AbstractSyntaxTree, String> astStudentMap,
                                    Integer comparisonType){




        Worker worker = new Worker();
        // Map<String, >
        try {
            // Complexity O((n^2)/2)
            // Compare files pair wise
            FileWriter fstream = new FileWriter(FILEOUTPUT);
            BufferedWriter fileWriter = new BufferedWriter(fstream);
            for (int i = 0; i < astList.size(); i++)  {
                AbstractSyntaxTree ast1 = astList.get(i);
                for (int j = i + 1; j < astList.size(); j++){
                    AbstractSyntaxTree ast2 = astList.get(j);
                    // double score = worker.compareSnapshots(ast1, ast2)
                    // double score = 0.0;
                    // if (comparisonType==1){
                    //     score = worker.compareNaive(ast1, ast2);
                    // }else if (comparisonType == 2){
                    //     score = worker.compareNaive(ast1, ast2);
                    // }
                    double score = worker.compareNaive(ast1, ast2);
                    System.out.println( "Similarity score: " + score + " , " + astStudentMap.get(ast1) + " , " +astStudentMap.get(ast2));
                    if (score > THRESHOLD){
                        System.out.println( "Similarity score: " + score + " , " + astStudentMap.get(ast1) + " , " +astStudentMap.get(ast2));
                        fileWriter.write(astStudentMap.get(ast1)+" , "+ astStudentMap.get(ast2) + " , " + score);
                        fileWriter.newLine();
                    }
                }
            }
            // Handle failed cases
            fileWriter.write(filesNotProcessed.toString());
            fileWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}