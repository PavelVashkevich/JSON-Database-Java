package server;

import client.ClientRequest;
import miscellaneou.LogPrinter;
import miscellaneou.ServerConfig;
import miscellaneou.SharedGson;
import server.database.ClientRequestWorker;
import server.database.DatabaseHandler;
import server.database.DatabaseResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

public class DatabaseServer {
    private final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final DatabaseHandler databaseHandler = new DatabaseHandler();

    public void run() {
        LogPrinter.simpleConsolePrint("Server started!");
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        try (ServerSocket serverSocket = new ServerSocket(ServerConfig.DATABASE_SERVER_PORT,
                ServerConfig.DATABASE_SERVER_BACKLOG_QUEUE, InetAddress.getByName(ServerConfig.DATABASE_SERVER_IP_ADDRESS))) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                     DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                     DataInputStream inputStream = new DataInputStream(socket.getInputStream())) {
                    ClientRequest clientRequest = SharedGson.getGson().fromJson(inputStream.readUTF(), ClientRequest.class);
                    Future<DatabaseResponse> responseFuture;
                    if (!clientRequest.getType().equals("exit")) {
                        responseFuture =  executorService.submit(new ClientRequestWorker(databaseHandler, clientRequest));
                        DatabaseResponse response = responseFuture.get();
                        outputStream.writeUTF(SharedGson.getGson().toJson(response));
                    } else {
                        responseFuture = executorService.submit(new ClientRequestWorker(databaseHandler, clientRequest));
                        DatabaseResponse response = responseFuture.get();
                        outputStream.writeUTF(SharedGson.getGson().toJson(response));
                        executorService.shutdown();
                        break;
                    }
                } catch (ExecutionException | InterruptedException e) {
                    LogPrinter.log(Level.SEVERE, "while processing client request following error occurred: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            LogPrinter.log(Level.SEVERE, "server socket error: " + e.getMessage());;
        }
    }
}
