package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.annotations.Expose;

public class Request {
    private final String[] userCmdRequest;
    @Parameter(names = "-t", description = "the type of the request", required = true)
    @Expose
    private String type;

    @Parameter(names = "-k", description = "the key")
    @Expose
    private String key;
    @Parameter(names = "-v", description = "the value to save in the database; only need in case of 'set' request ")
    @Expose
    private String value;


    public Request(String[] userCmdRequest) {
        this.userCmdRequest = userCmdRequest;
    }

    public void parseUserCmdRequest() {
        JCommander.newBuilder().addObject(this).build().parse(userCmdRequest);
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}