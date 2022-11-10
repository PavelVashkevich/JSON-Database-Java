package server.database;

import com.google.gson.reflect.TypeToken;
import miscellaneou.LogPrinter;
import miscellaneou.ServerConfig;
import miscellaneou.SharedGson;
import server.MessageResourceBundle;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

public class DatabaseHandler {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();


    public Response saveValue(String key, String value) {
        try {
            writeLock.lock();
            Map<String, String> databaseRepresentationInMap = null;
            try (Reader reader = new FileReader(ServerConfig.DATABASE_PATH)) {
                Type type = new TypeToken<Map<String, String>>() {
                }.getType();
                databaseRepresentationInMap = SharedGson.getGson().fromJson(reader, type);
                databaseRepresentationInMap.put(key, value);
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to read, following error occurred: " + e.getMessage());
            }
            try (Writer writer = new FileWriter(ServerConfig.DATABASE_PATH)) {
                SharedGson.getGson().toJson(databaseRepresentationInMap, writer);
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to read, following error occurred: " + e.getMessage());
            }
            return new Response.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).build();
        } finally {
            writeLock.unlock();
        }
    }

    public Response getValue(String key) {
        try {
            readLock.lock();
            Map<String, String> databaseRepresentationInMap;
            try (Reader reader = new FileReader(ServerConfig.DATABASE_PATH)) {
                Type type = new TypeToken<Map<String, String>>() {
                }.getType();
                databaseRepresentationInMap = SharedGson.getGson().fromJson(reader, type);
                if (databaseRepresentationInMap.containsKey(key)) {
                    String value = databaseRepresentationInMap.get(key);
                    return new Response.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).setValue(value).build();
                }
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to read, following error occurred: " + e.getMessage());
            }
            return new Response.ResponseBuilder().setResponse(MessageResourceBundle.ERROR_MSG)
                    .setReason(MessageResourceBundle.NO_SUCH_KEY_MSG).build();
        } finally {
            readLock.unlock();
        }
    }

    public Response deleteKey(String key) {
        try {
            writeLock.lock();
            Map<String, String> databaseRepresentationInMap = null;
            try (Reader reader = new FileReader(ServerConfig.DATABASE_PATH)) {
                Type type = new TypeToken<Map<String, String>>() {
                }.getType();
                databaseRepresentationInMap = SharedGson.getGson().fromJson(reader, type);
                if (databaseRepresentationInMap.containsKey(key)) databaseRepresentationInMap.remove(key);
                else return new Response.ResponseBuilder().setResponse(MessageResourceBundle.ERROR_MSG)
                        .setReason(MessageResourceBundle.NO_SUCH_KEY_MSG).build();

            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to read, following error occurred: " + e.getMessage());
            }
            try (Writer writer = new FileWriter(ServerConfig.DATABASE_PATH)) {
                SharedGson.getGson().toJson(databaseRepresentationInMap, writer);
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to write, following error occurred: " + e.getMessage());
            }
            return new Response.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).build();
        } finally {
            writeLock.unlock();
        }
    }
}