package server.database.commands;

import com.google.gson.JsonElement;
import server.database.DatabaseHandler;
import server.database.DatabaseResponse;

public class SetValueCommand implements DatabaseCommand {
    private final DatabaseHandler databaseHandler;
    private String[] key;
    private JsonElement value;

    public SetValueCommand(DatabaseHandler database) {
        this.databaseHandler = database;
    }

    public SetValueCommand(DatabaseHandler database, String[] key, JsonElement value) {
        this(database);
        this.key = key;
        this.value = value;
    }

    @Override
    public DatabaseResponse execute() {
        return databaseHandler.saveValue(key, value);
    }
}
