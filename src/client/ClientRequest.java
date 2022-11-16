package client;

import com.google.gson.JsonElement;

public class ClientRequest {

    private final String type;
    private final JsonElement key;
    private final JsonElement value;

    public ClientRequest(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public String getType() {
        return type;
    }


    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }

    static class ClientRequestBuilder {
        private String type;
        private JsonElement key;
        private JsonElement value;

        public ClientRequestBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public ClientRequestBuilder setKey(JsonElement key) {
            this.key = key;
            return this;
        }

        public ClientRequestBuilder setValue(JsonElement value) {
            this.value = value;
            return this;
        }

        public ClientRequest build() {
            return new ClientRequest(type,key,value);
        }
    }
}