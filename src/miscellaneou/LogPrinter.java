package miscellaneou;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogPrinter {

    private static Logger logger;

    static {
        try(FileInputStream propertiesFile = new FileInputStream("JSON Database\\task\\src\\logging.properties")) {
            LogManager.getLogManager().readConfiguration(propertiesFile);
            logger = Logger.getLogger(LogPrinter.class.getName());
        } catch (IOException e) {
            simpleConsolePrint("Logger cannot be instantiated, following error occurred: " + e.getMessage());
        }
    }
    public static void simpleConsolePrint(String message) {
        System.out.println(message);
    }

    public static synchronized void log(Level logLevel, String message) {
        logger.log(logLevel, message);
    }
}
