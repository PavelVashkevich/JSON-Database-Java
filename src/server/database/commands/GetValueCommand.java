package server.database.commands;

import server.database.Response;
import server.database.SimulatedDatabase;

public class GetValueCommand implements DatabaseCommand {
    private final SimulatedDatabase database;
    private String key;

    public GetValueCommand(SimulatedDatabase database) {
        this.database = database;
    }

    public GetValueCommand(SimulatedDatabase database, String key) {
        this(database);
        this.key = key;
    }

    @Override
    public Response execute() {
        return database.getValue(key);
    }
}
