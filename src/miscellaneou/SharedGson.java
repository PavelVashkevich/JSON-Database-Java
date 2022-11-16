package miscellaneou;

import client.ClientRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SharedGson {
    private static Gson gsonInstance;

    private SharedGson() {
        // No instance
    }

    public static Gson getGson() {
        if (gsonInstance == null) {
            gsonInstance = new GsonBuilder().registerTypeAdapter(ClientRequest.class,
                    new ClientRequestDeserializer()).create();
        }
        return gsonInstance;
    }
}
