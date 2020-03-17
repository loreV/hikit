package org.ltrails.common.data;

import java.util.List;

public class Position {

    public static final String ALTITUDE = "alt";
    public static final String AREA = "area";
    public static final String COUNTY = "county";
    public static final String CITY = "city";
    public static final String POSTCODE = "postCode";
    public static final String COORD = "coord";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String TAGS = "tags";

    private String name;
    private String description;
    private List<String> tags;
    private Coordinates coords;
    private double alt;
    private String area;
    private String county;
    private String city;
    private String postCode;

    public Position(double alt, double lat, double longitude) {
        this.alt = alt;
        this.coords = new Coordinates(longitude, lat);
    }

    public Position(String name, String description, List<String> tags, Coordinates coords,
                    double alt, String area, String county, String city, String postCode) {
        this.name = name;
        this.description = description;
        this.tags = tags;
        this.coords = coords;
        this.alt = alt;
        this.area = area;
        this.county = county;
        this.city = city;
        this.postCode = postCode;
    }

    public Coordinates getCoords() {
        return coords;
    }

    public double getAlt() {
        return alt;
    }

    public String getArea() {
        return area;
    }

    public String getCounty() {
        return county;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public static final class PositionBuilder {
        private String name;
        private String description;
        private List<String> tags;
        private Coordinates coords;
        private double alt;
        private String area;
        private String county;
        private String city;
        private String postCode;

        private PositionBuilder() {
        }

        public static PositionBuilder aPosition() {
            return new PositionBuilder();
        }

        public PositionBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PositionBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public PositionBuilder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public PositionBuilder withCoords(Coordinates coords) {
            this.coords = coords;
            return this;
        }

        public PositionBuilder withAlt(double alt) {
            this.alt = alt;
            return this;
        }

        public PositionBuilder withArea(String area) {
            this.area = area;
            return this;
        }

        public PositionBuilder withCounty(String county) {
            this.county = county;
            return this;
        }

        public PositionBuilder withCity(String city) {
            this.city = city;
            return this;
        }

        public PositionBuilder withPostCode(String postCode) {
            this.postCode = postCode;
            return this;
        }

        public Position build() {
            return new Position(name, description, tags, coords, alt, area, county, city, postCode);
        }
    }
}
