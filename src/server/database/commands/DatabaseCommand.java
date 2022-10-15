package server.database.commands;

import server.database.Response;

public interface DatabaseCommand {


    Response execute();
}
