package org.example;

import com.networknt.schema.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class Main {

    private static final String JSON_SCHEMA_NAME ="IAMRolePolicySchema.json";

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            throw new IllegalArgumentException("ERROR: Please provide file path to the JSON file as argument");
        }

        String jsonFile = args[0];
        String json = getJsonFromFile(jsonFile);
        System.out.print("RESULT: " + validateJson(json));
    }

    private static String getJsonFromFile(String fileName) throws IOException {
        Path filePath = Paths.get(fileName).toAbsolutePath();
        try {
            return Files.readString(filePath);
        } catch (IOException exception) {
            throw new IOException("ERROR: Error reading file " + fileName + ".");
        }
    }

    private static boolean validateJson(String json) throws IOException {

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        JsonSchema schema = factory.getSchema(Paths.get(JSON_SCHEMA_NAME).toUri());

        Set<ValidationMessage> errors = schema.validate(json, InputFormat.JSON, executionContext -> {
            executionContext.getExecutionConfig().setFormatAssertionsEnabled(true);
        });

        if (!errors.isEmpty()) {
            StringBuilder message = new StringBuilder();
            for (ValidationMessage error : errors) {
                message.append(error).append("\n");
            }
            throw new IOException("ERROR: JSON IS NOT VALID\n" + message);
        }

        JSONObject policyDocument = new JSONObject(json).getJSONObject("PolicyDocument");

        Object statement = policyDocument.get("Statement");

        if (statement instanceof JSONArray statements) {
            JSONObject statementObject;

            for (int i = 0; i < statements.length(); i++) {
                statementObject = (JSONObject) statements.get(i);

                if (statementObject.get("Resource") instanceof String resource && resource.equals("*")) {
                    return false;
                }
            }
        }

        else {
            JSONObject statementObject = (JSONObject) statement;

            if (statementObject.get("Resource") instanceof String resource && resource.equals("*")) {
                return false;
            }
        }

        return true;
    }
}