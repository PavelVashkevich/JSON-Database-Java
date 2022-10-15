package server;

import com.google.gson.Gson;
import client.Request;
import server.database.Response;
import server.database.SimulatedDatabase;
import server.database.commands.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DatabaseServer {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int DATABASE_SERVER_PORT = 23456;
    private static final int MAX_IN_CONN_QUEUE = 50;
    private final SimulatedDatabase database;

    public DatabaseServer(SimulatedDatabase database) {
        this.database = database;
    }

    public void run() {
        System.out.println("Server started!");
        DatabaseController databaseController = new DatabaseController();
        Gson gson = new Gson();
        boolean serverRunning = true;
        try (ServerSocket serverSocket = new ServerSocket(DATABASE_SERVER_PORT, MAX_IN_CONN_QUEUE, InetAddress.getByName(IP_ADDRESS))) {
            while (serverRunning) {
                try (Socket socket = serverSocket.accept();
                     DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                     DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {

                    Response databaseResponse;
                    Request userRequest = gson.fromJson(inputStream.readUTF(), Request.class);

                    switch (userRequest.getType()) {
                        case "exit":
                            databaseResponse = new Response.ResponseBuilder()
                                    .setResponse(MessageResourceBundle.OK_MSG).build();
                            serverRunning = false;
                            break;
                        case "set":
                            DatabaseCommand setCommand = new SetValueCommand(database, userRequest.getKey(), userRequest.getValue());
                            databaseController.setDatabaseCommand(setCommand);
                            databaseResponse = databaseController.executeCommand();
                            break;
                        case "get":
                            DatabaseCommand getCommand = new GetValueCommand(database, userRequest.getKey());
                            databaseController.setDatabaseCommand(getCommand);
                            databaseResponse = databaseController.executeCommand();
                            break;
                        case "delete":
                            DatabaseCommand deleteCommand = new DeleteKeyCommand(database, userRequest.getKey());
                            databaseController.setDatabaseCommand(deleteCommand);
                            databaseResponse = databaseController.executeCommand();
                            break;
                        default:
                            databaseResponse = new Response.ResponseBuilder()
                                    .setResponse(MessageResourceBundle.UNKNOWN_CMD_MSG).build();
                            break;
                    }
                    outputStream.writeUTF(gson.toJson(databaseResponse));
                } catch (IOException exception) {
                    throw new RuntimeException(exception);
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
