package miscellaneou;

public class ServerConfig {
    public static final String DATABASE_SERVER_IP_ADDRESS = "127.0.0.1";
    public static final int DATABASE_SERVER_PORT = 23456;
    public static final int DATABASE_SERVER_BACKLOG_QUEUE = 50;
    public static final String DATABASE_PATH = System.getProperty("user.dir") + "\\src\\server\\data\\db.json";


    private ServerConfig() {
        // No instance
    }
}
