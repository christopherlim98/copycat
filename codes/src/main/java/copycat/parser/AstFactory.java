package copycat.parser;

import com.fasterxml.jackson.databind.JsonNode;

import copycat.entity.AbstractSyntaxTree;
import copycat.exception.JsonToTreeTimeoutException;

import static copycat.parser.Json.parse;
import static copycat.parser.Json.readFileAsString;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AstFactory {
    private static AstFactory instance;
    public static AstFactory getInstance() {
        if (instance == null) {
            instance = new AstFactory();
        }
        return instance;
    }
    public AbstractSyntaxTree makeAstFromJsonFile(String fileName)
        throws JsonToTreeTimeoutException, IOException{
            String json = readFileAsString(fileName);
            JsonNode node = parse(json);
            return makeAst(node);
    }

    public AbstractSyntaxTree makeAst(JsonNode node) throws JsonToTreeTimeoutException{
        AbstractSyntaxTree ast= new AbstractSyntaxTree();
        JsonToTree jsonToTree = new JsonToTree(node, ast, null);
        Future<AbstractSyntaxTree> control
                = Executors.newSingleThreadExecutor().submit(jsonToTree);
        try {
            // If tree takes too long to make, we cancel the operation
            // This happens when the code is too long.
            ast = control.get(10, TimeUnit.SECONDS);
            return ast;

        } catch (TimeoutException ex) {
            // 10 seconds expired, we cancel the job !!!
            control.cancel(true);

        } catch (InterruptedException ex) {

        } catch (ExecutionException ex) {

        }
        throw new JsonToTreeTimeoutException("Failed to make tree");
    }
}