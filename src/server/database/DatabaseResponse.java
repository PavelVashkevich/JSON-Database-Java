package server.database;

import com.google.gson.JsonElement;

public class DatabaseResponse {
    private String response;
    private JsonElement value;
    private String reason;

    public DatabaseResponse(String response, JsonElement value, String reason) {
        this.response = response;
        this.value = value;
        this.reason = reason;
    }

    public String getResponse() {
        return response;
    }

    public JsonElement getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public static class ResponseBuilder {
        private String response;
        private JsonElement value;
        private String reason;

        public ResponseBuilder setResponse(String response) {
            this.response = response;
            return this;
        }

        public ResponseBuilder setValue(JsonElement value) {
            this.value = value;
            return this;
        }

        public ResponseBuilder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public DatabaseResponse build() {
            return new DatabaseResponse(response, value, reason);
        }
    }
}