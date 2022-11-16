package client;

import miscellaneou.LogPrinter;
import miscellaneou.ServerConfig;
import miscellaneou.SharedGson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;


public class ClientApp {
    private final ClientRequest clientRequest;

    public ClientApp(ClientRequest clientRequest) {
        this.clientRequest = clientRequest;
    }

    public void sendRequestToServer() {
        LogPrinter.simpleConsolePrint("Client started!");
        try (Socket socket = new Socket(InetAddress.getByName(ServerConfig.DATABASE_SERVER_IP_ADDRESS), ServerConfig.DATABASE_SERVER_PORT);
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String jsonClientRequest = SharedGson.getGson().toJson(clientRequest);
            dataOutputStream.writeUTF(jsonClientRequest);
            LogPrinter.simpleConsolePrint(("Sent: " + jsonClientRequest));
            String receivedResponse = dataInputStream.readUTF();
            LogPrinter.simpleConsolePrint("Received: " + receivedResponse);
        } catch (IOException e) {
            LogPrinter.log(Level.SEVERE, "while sending request to the server following error occurred: " + e.getMessage());
        }
    }
}
