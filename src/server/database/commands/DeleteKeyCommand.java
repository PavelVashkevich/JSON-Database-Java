package server.database.commands;

import server.database.DatabaseResponse;
import server.database.DatabaseHandler;

public class DeleteKeyCommand implements DatabaseCommand {

    private final DatabaseHandler databaseHandler;
    private String[] key;

    public DeleteKeyCommand(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    public DeleteKeyCommand(DatabaseHandler databaseHandler, String[] key) {
        this(databaseHandler);
        this.key = key;
    }

    @Override
    public DatabaseResponse execute() {
        return databaseHandler.deleteKey(key);
    }
}
