package org.ltrails.common.response;

import org.ltrails.common.configuration.ConfigurationProperties;

import java.util.List;

public class RESTResponse {

    private Status status;
    private List<String> messages;

    private static final String VERSION = ConfigurationProperties.VERSION;

    public RESTResponse(Status status, List<String> messages) {
        this.status = status;
        this.messages = messages;
    }

    public Status getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }

}
