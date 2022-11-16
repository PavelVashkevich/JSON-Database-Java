package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import miscellaneou.LogPrinter;
import miscellaneou.SharedGson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;

public class ClientRequestHandler {

    @Parameter(names = "-in", description = "the filename with request in json form")
    private static String fileName;
    @Parameter(names = "-t", description = "the type of the request")
    private static String type;
    @Parameter(names = "-k", description = "the key")
    private static String key;
    @Parameter(names = "-v", description = "the value to save in the database; only need in case of 'set' request ")
    private static String value;

    public ClientRequest createRequest(String[] userCliInput) {
        System.out.println(Arrays.toString(userCliInput));
        JCommander.newBuilder().addObject(this).build().parse(userCliInput);
        try {
            if (fileName != null) {
                String currentUserDir = System.getProperty("user.dir");
                Path filePathWithClientRequest = Paths.get(currentUserDir + "\\src\\client\\data\\" + fileName);
                return SharedGson.getGson().fromJson(Files.readString(filePathWithClientRequest), ClientRequest.class);
            }
        } catch (IOException e) {
            LogPrinter.log(Level.WARNING, "Cannot open user json request file on read. Following error occurred: " + e.getMessage());
        }
        JsonArray jsonArrayAsKey = new JsonArray();
        jsonArrayAsKey.add(key);
        System.out.println(jsonArrayAsKey);
        return new ClientRequest.ClientRequestBuilder().setType(type).setKey(key == null ? null : new JsonPrimitive(key))
                .setValue(value == null ? null : new JsonPrimitive(value)).build();
    }
}
