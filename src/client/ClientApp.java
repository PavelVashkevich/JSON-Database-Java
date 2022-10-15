package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class ClientApp {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int DATABASE_SERVER_PORT = 23456;
    private final Request request;

    public ClientApp(Request request) {
        this.request = request;
    }

    public void sendRequestToServer() {
        System.out.println("Client started!");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        try (Socket socket = new Socket(InetAddress.getByName(IP_ADDRESS), DATABASE_SERVER_PORT);
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            String jsonRequest = gson.toJson(request);
            dataOutputStream.writeUTF(jsonRequest);
            System.out.println("Sent: " + jsonRequest);
            String receivedResponse = dataInputStream.readUTF();
            System.out.println("Received: " + receivedResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
