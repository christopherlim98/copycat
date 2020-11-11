package jsonparsing;

import jsonparsing.copycat.Worker;

import jsonparsing.copycat.Worker2;
import jsonparsing.copycat.Worker3;
import jsonparsing.copycat.WorkerFactory;
import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.parser.AstFactory;


import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.*;


public class JsonTestMain {

    private static final String PATHROOT = "src/main/resources/json/";
    private static final String FILEOUTPUT = "john.txt";
    private static final double THRESHOLD = 80;
    public static void main(String[] args){
         Scanner sc = new Scanner(System.in);  // Create a Scanner object
         System.out.println("Enter Comparison Type");
         System.out.println("1. BreadthWise");
         System.out.println("2. Naive Comparison");
         System.out.println("3. SetWise");
         System.out.println("4. Compare All");
         int comparisonType = sc.nextInt();
        // Initialise Ast Tree Builder and Comparison Worker.

        // Get an array of all the file names in resources
        File[] files = new File(PATHROOT).listFiles((dir, name) -> !name.equals(".DS_Store"));
        ArrayList<AbstractSyntaxTree> astList = new ArrayList<AbstractSyntaxTree>();
        HashMap<AbstractSyntaxTree, String> astStudentMap = new HashMap<>();
        generateAstSet(files,  astList, astStudentMap);
        // Checks Comparison type and then point to the correct worker
        if (comparisonType != 4){
            WorkerFactory worker = null;
            if (comparisonType==1){
                worker = new Worker();

            }  else if (comparisonType==2){
                worker = new Worker2();

            } else if (comparisonType==3){
                worker = new Worker3();
            }
            compareAsts(astList, astStudentMap, worker);
        } 
    }

    


    public static void generateAstSet(File[] files, ArrayList<AbstractSyntaxTree> astList,
        HashMap<AbstractSyntaxTree, String> astStudentMap){
        // Complexity of O(n) to build all trees from their files
        AstFactory astFactory = new AstFactory();
        for (int i=0; i < files.length; i++){
     
            // Complexity of O(n) because we are parsing from JSON
            // to AST node by node
            try {
                String fileName =files[i].getName();
                String pathName = PATHROOT + fileName;
    
                AbstractSyntaxTree ast= astFactory.makeAstFromJsonFile(pathName);
                astStudentMap.put(ast, fileName);
                astList.add(ast);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

            if (i % 20 == 0){
                System.out.println("Built " + i + " trees...");
            }
        }
    }

    public static void compareAsts(ArrayList<AbstractSyntaxTree> astList,
                                    HashMap<AbstractSyntaxTree, String> astStudentMap,
                                   WorkerFactory worker){
        try {
     
            // Complexity O((n^2)/2)
            // Compare files pair wise
            FileWriter fstream = new FileWriter(FILEOUTPUT);
            BufferedWriter fileWriter = new BufferedWriter(fstream);
         
            for (int i = 0; i < astList.size(); i++)  {
                AbstractSyntaxTree ast1 = astList.get(i);
                for (int j = i + 1; j < astList.size(); j++){
                    AbstractSyntaxTree ast2 = astList.get(j);
                    double score = worker.compare(ast1, ast2);
         
                    if (score > THRESHOLD){
                        System.out.println( "Similarity score: " + score + " , " + astStudentMap.get(ast1) + " , " +astStudentMap.get(ast2));
                        fileWriter.write(astStudentMap.get(ast1)+" , "+ astStudentMap.get(ast2) + " , " + score);
                        fileWriter.newLine();
                    }
                }
            }
            fileWriter.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("heerrrr");
        }
    }
}