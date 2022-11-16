package miscellaneou;

import client.ClientRequest;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ClientRequestDeserializer implements JsonDeserializer<ClientRequest> {

    @Override
    public ClientRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String type = jsonObject.get("type").getAsString();
        JsonElement key = null;
        if (jsonObject.has("key")) {
            if (jsonObject.get("key").isJsonArray()) {
                key = jsonObject.getAsJsonArray("key");
            } else {
                key = (jsonObject.get("key").getAsJsonPrimitive());
            }
        }
        JsonElement value = null;
        if (jsonObject.has("value")) {
            if (jsonObject.isJsonObject()) {
                value = jsonObject.get("value");
            } else if (jsonObject.isJsonPrimitive()) {
                value = jsonObject.getAsJsonPrimitive();
            } else if (jsonObject.isJsonArray()) {
                value = json.getAsJsonArray();
            }

        }
        return new ClientRequest(type, key, value);
    }
}