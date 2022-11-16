package server.database;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import miscellaneou.LogPrinter;
import miscellaneou.ServerConfig;
import miscellaneou.SharedGson;
import server.MessageResourceBundle;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

public class DatabaseHandler {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();
    private static final int CURRENT_KEY_INDEX = 0;


    public DatabaseResponse saveValue(String[] key, JsonElement value) {
        try {
            writeLock.lock();
            JsonObject updatedDBWithNewValue = null;
            try (Reader reader = new FileReader(ServerConfig.DATABASE_PATH)) {
                JsonElement root = JsonParser.parseReader(reader);
                updatedDBWithNewValue = saveValue(root.getAsJsonObject(), key, value);
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to read, following error occurred: " + e.getMessage());
            }
            try (Writer writer = new FileWriter(ServerConfig.DATABASE_PATH)) {
                SharedGson.getGson().toJson(updatedDBWithNewValue, writer);
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to read, following error occurred: " + e.getMessage());
            }
            return new DatabaseResponse.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).build();
        } finally {
            writeLock.unlock();
        }
    }
    private JsonObject saveValue(JsonObject jsonObject, String[] key, JsonElement value) {
        String currentKey = key[CURRENT_KEY_INDEX];
        if (key.length == 1) {
            jsonObject.add(currentKey, value);
            return jsonObject;
        }
        if (!jsonObject.has(currentKey)) {
            jsonObject.add(currentKey, new JsonObject());
        }

        JsonObject nestedJsonObject = jsonObject.getAsJsonObject(currentKey);
        String[] remainingKey = Arrays.copyOfRange(key, 1, key.length);
        JsonObject updatedNestedObject = saveValue(nestedJsonObject, remainingKey, value);
        jsonObject.add(currentKey, updatedNestedObject);
        return jsonObject;
    }

    public DatabaseResponse getValue(String[] key) {
        try {
            readLock.lock();
            try (Reader reader = new FileReader(ServerConfig.DATABASE_PATH)) {
                JsonElement root = JsonParser.parseReader(reader);
                JsonElement value = getValue(root.getAsJsonObject(), key);
                System.out.println(value);
                if (Objects.nonNull(value)) {
                    return new DatabaseResponse.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG)
                            .setValue(value).build();
                }
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to read, following error occurred: " + e.getMessage());
            }
            return new DatabaseResponse.ResponseBuilder().setResponse(MessageResourceBundle.ERROR_MSG)
                    .setReason(MessageResourceBundle.NO_SUCH_KEY_MSG).build();
        } finally {
            readLock.unlock();
        }
    }

    private JsonElement getValue(JsonObject jsonObject, String[] key) {
        String currentKey = key[CURRENT_KEY_INDEX];
        if(!jsonObject.has(currentKey)) {
            return null;
        } if (key.length == 1) {
            return jsonObject.get(currentKey);
        }

        JsonObject nestedJsonObject = jsonObject.getAsJsonObject(currentKey);
        String[] remainingKey = Arrays.copyOfRange(key, 1, key.length);
        return getValue(nestedJsonObject, remainingKey);
    }

    public DatabaseResponse deleteKey(String[] key) {
        try {
            writeLock.lock();
            JsonObject updatedDBWithNewValue = null;
            try (Reader reader = new FileReader(ServerConfig.DATABASE_PATH)) {
                JsonElement root = JsonParser.parseReader(reader);
                updatedDBWithNewValue = deleteKey(root.getAsJsonObject(), key);
                if (Objects.isNull(updatedDBWithNewValue)) return new DatabaseResponse.ResponseBuilder()
                        .setResponse(MessageResourceBundle.ERROR_MSG)
                        .setReason(MessageResourceBundle.NO_SUCH_KEY_MSG).build();
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to read, following error occurred: " + e.getMessage());
            }
            try (Writer writer = new FileWriter(ServerConfig.DATABASE_PATH)) {
                SharedGson.getGson().toJson(updatedDBWithNewValue, writer);
            } catch (IOException e) {
                LogPrinter.log(Level.WARNING, "cannot open " + ServerConfig.DATABASE_PATH
                        + " database file to write, following error occurred: " + e.getMessage());
            }
            return new DatabaseResponse.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).build();
        } finally {
            writeLock.unlock();
        }
    }

    private JsonObject deleteKey(JsonObject jsonObject,String[] key) {
        String currentKey = key[CURRENT_KEY_INDEX];
        if(!jsonObject.has(currentKey)) {
            return null;
        } if (key.length == 1) {
            jsonObject.remove(currentKey);
            return jsonObject;
        }
        JsonObject nestedJsonObject = jsonObject.getAsJsonObject(currentKey);
        String[] remainingKey = Arrays.copyOfRange(key, 1, key.length);
        JsonObject updatedNestedObject = deleteKey(nestedJsonObject, remainingKey);
        if (updatedNestedObject == null) {
            return null;
        } else {
            jsonObject.add(currentKey, updatedNestedObject);
        }
        return jsonObject;
    }
}