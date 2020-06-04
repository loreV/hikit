package org.hikit.common.web.controller.response;

import org.hikit.common.data.TrailDistance;

import java.util.List;
import java.util.Set;

public class ConnectingWayPointsResponse extends RESTResponse {
    final List<TrailDistance> trailDistances;

    public ConnectingWayPointsResponse(final List<TrailDistance> trailDistances,
                                       final Status status,
                                       final Set<String> messages) {
        super(status, messages);
        this.trailDistances = trailDistances;
    }

    public List<TrailDistance> getTrailDistances() {
        return trailDistances;
    }
    
    public static final class ConnectingWayPointsResponseBuilder {
        List<TrailDistance> trailDistances;
        private Status status;
        private Set<String> messages;

        private ConnectingWayPointsResponseBuilder() {
        }

        public static ConnectingWayPointsResponseBuilder aConnectingWayPointsResponse() {
            return new ConnectingWayPointsResponseBuilder();
        }

        public ConnectingWayPointsResponseBuilder withTrailDistances(List<TrailDistance> trailDistances) {
            this.trailDistances = trailDistances;
            return this;
        }

        public ConnectingWayPointsResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public ConnectingWayPointsResponseBuilder withMessages(Set<String> messages) {
            this.messages = messages;
            return this;
        }

        public ConnectingWayPointsResponse build() {
            return new ConnectingWayPointsResponse(trailDistances, status, messages);
        }
    }
}
