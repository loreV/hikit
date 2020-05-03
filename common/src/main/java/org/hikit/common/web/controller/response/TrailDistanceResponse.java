package org.hikit.common.web.controller.response;

import org.hikit.common.data.TrailDistance;

import java.util.List;
import java.util.Set;

public class TrailDistanceResponse extends RESTResponse {

    private final List<TrailDistance> trails;

    public TrailDistanceResponse(Status status, Set<String> messages, List<TrailDistance> trails) {
        super(status, messages);
        this.trails = trails;
    }

    public List<TrailDistance> getTrails() {
        return trails;
    }

    public static final class TrailDistanceResponseBuilder {
        private Status status;
        private Set<String> messages;
        private List<TrailDistance> trails;

        private TrailDistanceResponseBuilder() {
        }

        public static TrailDistanceResponseBuilder aTrailDistanceResponse() {
            return new TrailDistanceResponseBuilder();
        }

        public TrailDistanceResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public TrailDistanceResponseBuilder withMessages(Set<String> messages) {
            this.messages = messages;
            return this;
        }

        public TrailDistanceResponseBuilder withTrails(List<TrailDistance> trails) {
            this.trails = trails;
            return this;
        }

        public TrailDistanceResponse build() {
            return new TrailDistanceResponse(status, messages, trails);
        }
    }
}
