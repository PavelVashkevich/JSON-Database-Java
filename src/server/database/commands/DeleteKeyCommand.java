package server.database.commands;

import server.database.Response;
import server.database.SimulatedDatabase;

public class DeleteKeyCommand implements DatabaseCommand {

    private final SimulatedDatabase database;
    private String key;

    public DeleteKeyCommand(SimulatedDatabase database) {
        this.database = database;
    }

    public DeleteKeyCommand(SimulatedDatabase database, String key) {
        this(database);
        this.key = key;
    }

    @Override
    public Response execute() {
        return database.deleteKey(key);
    }
}
