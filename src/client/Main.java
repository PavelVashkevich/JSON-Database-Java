package client;

public class Main {

    public static void main(String[] args)  {
        ClientRequest clientRequest = new ClientRequestHandler().createRequest(args);
        ClientApp clientApp = new ClientApp(clientRequest);
        clientApp.sendRequestToServer();
    }
}
