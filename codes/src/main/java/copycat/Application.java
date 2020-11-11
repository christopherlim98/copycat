package copycat;

import copycat.copycat.SnapshotWorker;

import copycat.copycat.NaiveWorker;
import copycat.copycat.ProgressiveWorker;
import copycat.copycat.WorkerFactory;
import copycat.entity.AbstractSyntaxTree;
import copycat.exception.JsonToTreeTimeoutException;
import copycat.parser.AstFactory;
import copycat.constants.Constants;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



public class Application {
    private static String pathRoot = "data/Z5/Z1/";
    private static final String FILEOUTPUT = "results/A2016Z5Z1";
    private static final double THRESHOLD = 80;
    private static List<String> filesNotProcessed = new ArrayList<String>();
    private static ArrayList<AbstractSyntaxTree> astList = new ArrayList<AbstractSyntaxTree>();
    private static HashMap<AbstractSyntaxTree, String> astStudentMap = new HashMap<>();
    public static void main(String[] args){
        clearScreen();

        int userOption = 0;
        while (userOption != 6){
            // Get user option
            userOption = getUserOption();

            // Start timer
            long startTime = System.nanoTime();

            processOptions(userOption);

            // End timer for performance measuring
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            System.out.println("Time taken: " + duration / 1000000000.0 + " seconds\n=======================\n");
        }

    }

    // Process the options that the user inputted
    private static void processOptions(int userOption){
        double threshold = THRESHOLD;

        // Initialise Ast Tree Builder and Comparison Worker.
        // Get an array of all the file names in resources
        File[] files = new File(pathRoot).listFiles((dir, name) -> !name.equals(".DS_Store"));
        String fileOutput = FILEOUTPUT;
        WorkerFactory worker = null;
        if (userOption == 1){
            // 1. Generate ASTs
            generateAstSet(files,  astList, astStudentMap);
            System.out.println("Finished generating trees");
            printLineBreak();;
            return;
        }


        if (astList.isEmpty() || astStudentMap.isEmpty()){
            generateAstSet(files,  astList, astStudentMap);
        }

        if (userOption == 5){
            compareAll();
            return;
        }
        if (userOption == 2){
            // 2. Compare using naive comparison
            fileOutput += "Naive.txt";
            worker = new NaiveWorker();

        }  else if (userOption == 3){
            // 3. Compare using naive progressive comparison
            fileOutput += "Progressive.txt";
            worker = new ProgressiveWorker();
            // Progressive checks if it is 0 or 1,
            // so having a threshold above 0.0 is sufficient.
            threshold = 0.0;

        } else if (userOption == 4){
            // 4. Compare using naive snapshot comparison
            fileOutput += "Snapshot.txt";
            worker = new SnapshotWorker();
        }
        System.out.println("Comparing trees...");
        compareAsts(astList, astStudentMap, worker, threshold, fileOutput);
        return;

    }

    public static void compareAll(){
        String fileOutput = FILEOUTPUT;
        WorkerFactory worker = new NaiveWorker();
        System.out.println("Comparing trees using naive comparison...");
        compareAsts(astList, astStudentMap, worker, THRESHOLD, fileOutput + "Naive.txt");
        printLineBreak();

        worker = new ProgressiveWorker();
        System.out.println("Comparing trees using progressive comparison...");
        compareAsts(astList, astStudentMap, worker, 0.0, fileOutput + "Progressive.txt" );
        printLineBreak();

        fileOutput += "Snapshot.txt";
        worker = new SnapshotWorker();
        System.out.println("Comparing trees using snapshot comparison...");
        compareAsts(astList, astStudentMap, worker, THRESHOLD, fileOutput += "Progressive.txt");


    }
    public static void generateAstSet(File[] files, ArrayList<AbstractSyntaxTree> astList,
        HashMap<AbstractSyntaxTree, String> astStudentMap){
        // Complexity of O(n) to build all trees from their files
        // because we are parsing from JSON
        // to AST node by node
        AstFactory astFactory = new AstFactory();
        for (int i=0; i < files.length; i++){

            String fileName =files[i].getName();
            try {
                String pathName = pathRoot + fileName;

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
        System.out.println("Trees generated. Processed " + astList.size() + " files.");
    }

    public static void compareAsts(ArrayList<AbstractSyntaxTree> astList,
                                    HashMap<AbstractSyntaxTree, String> astStudentMap,
                                   WorkerFactory worker, double threshold, String fileOutput){
        try {
            // Complexity O((n^2)/2)
            // Compare files pair wise
            FileWriter fstream = new FileWriter(fileOutput);
            BufferedWriter fileWriter = new BufferedWriter(fstream);
            int copyCount = 0;

            for (int i = 0; i < astList.size(); i++)  {
                AbstractSyntaxTree ast1 = astList.get(i);

                for (int j = i + 1; j < astList.size(); j++){
                    AbstractSyntaxTree ast2 = astList.get(j);
                    double score = worker.compare(ast1, ast2);

                    if (score > threshold){
                        System.out.println( "Similarity score: " + score + " , " + astStudentMap.get(ast1) + " , " +astStudentMap.get(ast2));
                        fileWriter.write(astStudentMap.get(ast1)+" , "+ astStudentMap.get(ast2) + " , " + score);
                        fileWriter.newLine();
                        copyCount++;
                    }
                }
            }

            // Handle failed cases
            fileWriter.write("=======================\n");
            fileWriter.write("Files not processed: ");
            fileWriter.write(filesNotProcessed.toString());

            // Close filewriter
            fileWriter.close();

            // Update user that process is completed.
            System.out.println("Finished comparing the files. " +
                                copyCount + " similar pairs found");
            printLineBreak();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printLineBreak(){
        System.out.println("=======================\n");
    }

    private static int getUserOption(){
        int input = 0;
        while ( input < 1 || input > 6 ){
            Scanner sc = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter Comparison Type");
            for (int i = 1 ; i <= Constants.USEROPTIONS.size(); i ++){
                System.out.println(Constants.USEROPTIONS.get(i));
            }
            System.out.print("Choose an input between 1 to 6: ");
            input = sc.nextInt();
            if (input == 6){
                System.exit(1);
            }
            clearScreen();
            if ( input < 1 || input > 6 ){
                System.out.println("Please choose a correct option.");
            }
            System.out.println("You chose option: "+ Constants.USEROPTIONS.get(input));
        }
        return input;
    }

    private void printInstructions(){
        System.out.println(Constants.INSTRUCTIONS);
    }
}