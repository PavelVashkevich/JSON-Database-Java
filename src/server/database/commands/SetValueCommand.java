package server.database.commands;

import server.database.Response;
import server.database.DatabaseHandler;

public class SetValueCommand implements DatabaseCommand {
    private final DatabaseHandler databaseHandler;
    private String key;
    private String value;

    public SetValueCommand(DatabaseHandler database) {
        this.databaseHandler = database;
    }

    public SetValueCommand(DatabaseHandler database, String key, String value) {
        this(database);
        this.key = key;
        this.value = value;
    }

    @Override
    public Response execute() {
        return databaseHandler.saveValue(key, value);
    }
}
