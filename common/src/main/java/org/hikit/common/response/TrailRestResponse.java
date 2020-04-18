package org.hikit.common.response;

import org.hikit.common.data.Trail;

import java.util.List;

public class TrailRestResponse extends RESTResponse {
    final List<Trail> trails;

    public TrailRestResponse(List<Trail> trails, Status status, List<String> messages) {
        super(status, messages);
        this.trails = trails;
    }

    public List<Trail> getTrails() {
        return trails;
    }

    public static final class TrailRestResponseBuilder {
        private List<Trail> trails;
        private Status status;
        private List<String> messages;

        private TrailRestResponseBuilder() {
        }

        public static TrailRestResponseBuilder aTrailRestResponse() {
            return new TrailRestResponseBuilder();
        }

        public TrailRestResponseBuilder withTrails(List<Trail> trails) {
            this.trails = trails;
            return this;
        }

        public TrailRestResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public TrailRestResponseBuilder withMessages(List<String> messages) {
            this.messages = messages;
            return this;
        }

        public TrailRestResponse build() {
            return new TrailRestResponse(trails, status, messages);
        }
    }
}
