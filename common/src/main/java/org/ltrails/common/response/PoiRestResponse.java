package org.ltrails.common.response;

import org.ltrails.common.data.Poi;

import java.util.List;

public class PoiRestResponse extends RESTResponse {
    final List<Poi> pois;

    public PoiRestResponse(List<Poi> pois, Status status, List<String> messages) {
        super(status, messages);
        this.pois = pois;
    }

    public List<Poi> getPois() {
        return pois;
    }

    public static final class PoiRestResponseBuilder {
        private List<Poi> pois;
        private Status status;
        private List<String> messages;

        private PoiRestResponseBuilder() {
        }

        public static PoiRestResponseBuilder aPoiRestResponse() {
            return new PoiRestResponseBuilder();
        }

        public PoiRestResponseBuilder withTrails(List<Poi> pois) {
            this.pois = pois;
            return this;
        }

        public PoiRestResponseBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public PoiRestResponseBuilder withMessages(List<String> messages) {
            this.messages = messages;
            return this;
        }

        public PoiRestResponse build() {
            return new PoiRestResponse(pois, status, messages);
        }
    }
}
