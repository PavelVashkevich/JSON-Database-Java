package server.database;

public class Response {
    private String response;
    private String value;
    private String reason;

    public Response(String response, String value, String reason) {
        this.response = response;
        this.value = value;
        this.reason = reason;
    }

    public String getResponse() {
        return response;
    }

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public static class ResponseBuilder {
        private String response;
        private String value;
        private String reason;

        public ResponseBuilder setResponse(String response) {
            this.response = response;
            return this;
        }

        public ResponseBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public ResponseBuilder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Response build() {
            return new Response(response, value, reason);
        }

    }
}