package server.database.commands;

import server.database.Response;

public class DatabaseController {
    private DatabaseCommand databaseCommand;


    public void setDatabaseCommand(DatabaseCommand databaseCommand) {
        this.databaseCommand = databaseCommand;
    }

    public Response executeCommand() {
        return databaseCommand.execute();
    }
}
