package org.ltrails.common.response;

import java.util.List;

public class RESTResponse {

    private Status status;
    private List<String> messages;

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
