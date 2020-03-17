package org.ltrails.common.data;

import com.google.gson.JsonElement;

import java.util.List;

public class Poi {

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String POSITION = "position";
    public static final String GEO = "geo";

    private Position position;
    private List<String> tags;
    private String name;
    private String description;
    private JsonElement geo;

    private Poi(Position position, String description, String name, JsonElement geo, List<String> tags) {
        this.position = position;
        this.name = name;
        this.description = description;
        this.geo = geo;
        this.tags = tags;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public JsonElement getGeo() {
        return geo;
    }

    public List<String> getTags() {
        return tags;
    }

    public static final class PoiBuilder {
        private Position position;
        private String name;
        private String description;
        private JsonElement geo;
        private List<String> tags;

        private PoiBuilder() {
        }

        public static PoiBuilder aPoi() {
            return new PoiBuilder();
        }

        public PoiBuilder withPosition(Position position) {
            this.position = position;
            return this;
        }

        public PoiBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PoiBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PoiBuilder withGeo(JsonElement geo) {
            this.geo = geo;
            return this;
        }

        public PoiBuilder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Poi build() {
            return new Poi(position, description, name, geo, tags);
        }
    }
}
