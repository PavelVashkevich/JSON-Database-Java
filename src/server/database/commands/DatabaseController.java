package server.database.commands;

import server.database.DatabaseResponse;

public class DatabaseController {
    private DatabaseCommand databaseCommand;


    public void setDatabaseCommand(DatabaseCommand databaseCommand) {
        this.databaseCommand = databaseCommand;
    }

    public DatabaseResponse executeCommand() {
        return databaseCommand.execute();
    }
}
