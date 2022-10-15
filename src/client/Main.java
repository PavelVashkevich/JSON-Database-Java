package client;

public class Main {

    public static void main(String[] args) {
        Request userCmdRequest = new Request(args);
        userCmdRequest.parseUserCmdRequest();
        ClientApp clientApp = new ClientApp(userCmdRequest);
        clientApp.sendRequestToServer();
    }
}
