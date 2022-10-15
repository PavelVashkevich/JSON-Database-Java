package server;

import server.database.SimulatedDatabase;

public class Main {

    public static void main(String[] args) {
        SimulatedDatabase database = new SimulatedDatabase();
        new DatabaseServer(database).run();
    }
}
