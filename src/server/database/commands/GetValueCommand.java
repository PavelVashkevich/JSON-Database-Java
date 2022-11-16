package server.database.commands;

import server.database.DatabaseResponse;
import server.database.DatabaseHandler;

public class GetValueCommand implements DatabaseCommand {
    private final DatabaseHandler databaseHandler;
    private String[] key;

    public GetValueCommand(DatabaseHandler database) {
        this.databaseHandler = database;
    }

    public GetValueCommand(DatabaseHandler database, String[] key) {
        this(database);
        this.key = key;
    }

    @Override
    public DatabaseResponse execute() {
        return databaseHandler.getValue(key);
    }
}
