package server.database;

import client.ClientRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import server.MessageResourceBundle;
import server.database.commands.DatabaseCommand;
import server.database.commands.DatabaseController;
import server.database.commands.DeleteKeyCommand;
import server.database.commands.GetValueCommand;
import server.database.commands.SetValueCommand;

import java.util.concurrent.Callable;

public class ClientRequestWorker implements Callable<DatabaseResponse> {

    private final DatabaseHandler databaseHandler;
    private final ClientRequest clientRequest;

    public ClientRequestWorker(DatabaseHandler simulatedDatabase, ClientRequest clientRequest) {
        this.databaseHandler = simulatedDatabase;
        this.clientRequest = clientRequest;
    }

    @Override
    public DatabaseResponse call() {
        DatabaseController databaseController = new DatabaseController();
        DatabaseResponse databaseResponse;
        String[] key;
        switch (clientRequest.getType()) {
            case "set":
                key = convertJsonElementToStringArray(clientRequest.getKey());
                DatabaseCommand setCommand = new SetValueCommand(databaseHandler, key, clientRequest.getValue());
                databaseController.setDatabaseCommand(setCommand);
                databaseResponse = databaseController.executeCommand();
                break;
            case "get":
                key = convertJsonElementToStringArray(clientRequest.getKey());
                DatabaseCommand getCommand = new GetValueCommand(databaseHandler, key);
                databaseController.setDatabaseCommand(getCommand);
                databaseResponse = databaseController.executeCommand();
                break;
            case "delete":
                key = convertJsonElementToStringArray(clientRequest.getKey());
                DatabaseCommand deleteCommand = new DeleteKeyCommand(databaseHandler, key);
                databaseController.setDatabaseCommand(deleteCommand);
                databaseResponse = databaseController.executeCommand();
                break;
            case "exit":
                databaseResponse = new DatabaseResponse.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).build();
                break;
            default:
                databaseResponse = new DatabaseResponse.ResponseBuilder()
                        .setResponse(MessageResourceBundle.UNKNOWN_CMD_MSG).build();
                break;
        }
        return databaseResponse;
    }

    private String[] convertJsonElementToStringArray(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            String[] result = new String[jsonArray.size()];
            for (int i = 0; i < jsonArray.size(); i++) {
                result[i] = jsonArray.get(i).getAsString();
            }
            return result;
        } else {
            return new String[] {jsonElement.getAsString()};
        }
    }
}
