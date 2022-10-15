package server.database.commands;

import server.database.Response;
import server.database.SimulatedDatabase;

public class SetValueCommand implements DatabaseCommand {
    private final SimulatedDatabase database;
    private String key;
    private String value;

    public SetValueCommand(SimulatedDatabase database) {
        this.database = database;
    }

    public SetValueCommand(SimulatedDatabase database, String key, String value) {
        this(database);
        this.key = key;
        this.value = value;
    }

    @Override
    public Response execute() {
        return database.saveValue(key, value);
    }
}
