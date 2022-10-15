package server.database;

import server.MessageResourceBundle;

import java.util.HashMap;
import java.util.Map;


public class SimulatedDatabase {
    private final Map<String, String> inMemoryDatabase = new HashMap<>();

    public Response saveValue(String key, String value) {
        inMemoryDatabase.put(key, value);
        return new Response.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).build();
    }

    public Response getValue(String key) {
        if (inMemoryDatabase.containsKey(key)) {
            String value = inMemoryDatabase.get(key);
            return new Response.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).setValue(value).build();
        }
        return new Response.ResponseBuilder().setResponse(MessageResourceBundle.ERROR_MSG)
                .setReason(MessageResourceBundle.NO_SUCH_KEY_MSG).build();
    }

    public Response deleteKey(String key) {
        if (inMemoryDatabase.containsKey(key)) {
            inMemoryDatabase.remove(key);
            return new Response.ResponseBuilder().setResponse(MessageResourceBundle.OK_MSG).build();
        }
        return new Response.ResponseBuilder().setResponse(MessageResourceBundle.ERROR_MSG)
                .setReason(MessageResourceBundle.NO_SUCH_KEY_MSG).build();
    }

}