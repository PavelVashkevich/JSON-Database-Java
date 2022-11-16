package server.database.commands;

import server.database.DatabaseResponse;

public interface DatabaseCommand {

    DatabaseResponse execute();
}
