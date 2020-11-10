package jsonparsing.parser;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Json {
    private static ObjectMapper objectMapper = new ObjectMapper();
    

    public static JsonNode parse(String src) throws IOException {
        // JsonStringEncoder jsonStringEncoder = new JsonStringEncoder();
        // StringBuilder sb = new StringBuilder();
        // sb.append(jsonStringEncoder.quoteAsString(src));
        // src = sb.toString();
        // System.out.println("==============================================================");
        // System.out.println(src);

        return objectMapper.readTree(src);
    }

    public static String readFileAsString(String file) throws IOException{
        
        return new String( Files.readAllBytes(Paths.get(file)));
    }

}
