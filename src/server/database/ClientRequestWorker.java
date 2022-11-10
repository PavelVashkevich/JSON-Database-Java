package server.database;

import client.ClientRequest;
import server.MessageResourceBundle;
import server.database.commands.DatabaseCommand;
import server.database.commands.DatabaseController;
import server.database.commands.DeleteKeyCommand;
import server.database.commands.GetValueCommand;
import server.database.commands.SetValueCommand;

import java.util.concurrent.Callable;

public class ClientRequestWorker implements Callable<Response> {

    private final DatabaseHandler databaseHandler;
    private final ClientRequest clientRequest;

    public ClientRequestWorker(DatabaseHandler simulatedDatabase, ClientRequest clientRequest) {
        this.databaseHandler = simulatedDatabase;
        this.clientRequest = clientRequest;
    }

    @Override
    public Response call() {
        DatabaseController databaseController = new DatabaseController();
        Response databaseResponse;
        switch (clientRequest.getType()) {
            case "set":
                DatabaseCommand setCommand = new SetValueCommand(databaseHandler, clientRequest.getKey(), clientRequest.getValue());
                databaseController.setDatabaseCommand(setCommand);
                databaseResponse = databaseController.executeCommand();
                break;
            case "get":
                DatabaseCommand getCommand = new GetValueCommand(databaseHandler, clientRequest.getKey());
                databaseController.setDatabaseCommand(getCommand);
                databaseResponse = databaseController.executeCommand();
                break;
            case "delete":
                DatabaseCommand deleteCommand = new DeleteKeyCommand(databaseHandler, clientRequest.getKey());
                databaseController.setDatabaseCommand(deleteCommand);
                databaseResponse = databaseController.executeCommand();
                break;
            case "exit":
                databaseResponse = new Response.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).build();
                break;
            default:
                databaseResponse = new Response.ResponseBuilder()
                        .setResponse(MessageResourceBundle.UNKNOWN_CMD_MSG).build();
                break;
        }
        return databaseResponse;
    }
}
