package server.database.commands;

import server.database.Response;
import server.database.DatabaseHandler;

public class GetValueCommand implements DatabaseCommand {
    private final DatabaseHandler databaseHandler;
    private String key;

    public GetValueCommand(DatabaseHandler database) {
        this.databaseHandler = database;
    }

    public GetValueCommand(DatabaseHandler database, String key) {
        this(database);
        this.key = key;
    }

    @Override
    public Response execute() {
        return databaseHandler.getValue(key);
    }
}
