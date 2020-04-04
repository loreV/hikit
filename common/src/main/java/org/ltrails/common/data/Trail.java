package org.ltrails.common.data;

import com.google.gson.JsonElement;

import java.util.List;

public class Trail {

    static final String COLLECTION_NAME = "core.Trail";

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CODE = "code";
    public static final String START_POS = "startPos";
    public static final String FINAL_POS = "finalPos";
    public static final String GEO = "geo";
    public static final String REPORTED_DIFFICULTY = "reportedDifficulty";
    public static final String TRACK_LENGTH = "trackLength";
    public static final String ETA = "eta";
    public static final String CONNECTING_TRAILS = "wayPoints";
    public static final String POIS = "pois";
    public static final String CLASSIFICATION = "classification";
    public static final String POST_CODE = "postCodes";
    public static final String COUNTRY = "country";

    private String name;
    private String description;
    private String code;
    private Position startPos;
    private Position finalPos;
    private JsonElement geo;
    private double reportedDifficulty;
    private double trackLength;
    private double eta;
    private List<ConnectingWayPoint> connectingWayPoints;
    private List<Poi> pois;
    private TrailClassification trailClassification;
    private final List<String> postCode;
    private final String country;

    public Trail(String name, String description, String code, Position startPos, Position finalPos, double reportedDifficulty, double trackLength,
                 double eta, List<ConnectingWayPoint> connectingWayPoints, List<Poi> pois,
                 TrailClassification trailClassification, JsonElement geo, List<String> postCode, String country) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.startPos = startPos;
        this.finalPos = finalPos;
        this.geo = geo;
        this.reportedDifficulty = reportedDifficulty;
        this.trackLength = trackLength;
        this.eta = eta;
        this.connectingWayPoints = connectingWayPoints;
        this.pois = pois;
        this.trailClassification = trailClassification;
        this.postCode = postCode;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public Position getStartPos() {
        return startPos;
    }

    public JsonElement getGeo() {
        return geo;
    }

    public double getReportedDifficulty() {
        return reportedDifficulty;
    }

    public double getTrackLength() {
        return trackLength;
    }

    public double getEta() {
        return eta;
    }

    public List<ConnectingWayPoint> getConnectingWayPoints() {
        return connectingWayPoints;
    }

    public List<Poi> getPois() {
        return pois;
    }

    public TrailClassification getTrailClassification() {
        return trailClassification;
    }

    public Position getFinalPos() {
        return finalPos;
    }

    public List<String> getPostCode() {
        return postCode;
    }

    public String getCountry() {
        return country;
    }

    public static final class TrailBuilder {

        private String name;
        private String description;
        private String code;
        private List<String> postCode;
        private Position startPos;
        private Position finalPos;
        private JsonElement geo;
        private double reportedDifficulty;
        private double trackLength;
        private double eta;
        private List<ConnectingWayPoint> connectingWayPoints;
        private List<Poi> pois;
        private TrailClassification trailClassification;
        private String country;


        private TrailBuilder() {
        }

        public static TrailBuilder aTrail() {
            return new TrailBuilder();
        }

        public TrailBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TrailBuilder withPostCode(List<String> postCode) {
            this.postCode = postCode;
            return this;
        }


        public TrailBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public TrailBuilder withCode(String code) {
            this.code = code;
            return this;
        }

        public TrailBuilder withStartPos(Position startPos) {
            this.startPos = startPos;
            return this;
        }

        public TrailBuilder withGeo(JsonElement geo) {
            this.geo = geo;
            return this;
        }

        public TrailBuilder withReportedDifficulty(double reportedDifficulty) {
            this.reportedDifficulty = reportedDifficulty;
            return this;
        }

        public TrailBuilder withTrackLength(double trackLength) {
            this.trackLength = trackLength;
            return this;
        }

        public TrailBuilder withEta(double eta) {
            this.eta = eta;
            return this;
        }

        public TrailBuilder withConnectingTrails(List<ConnectingWayPoint> connectingWayPoints) {
            this.connectingWayPoints = connectingWayPoints;
            return this;
        }

        public TrailBuilder withPois(List<Poi> pois) {
            this.pois = pois;
            return this;
        }

        public TrailBuilder withClassification(TrailClassification trailClassification) {
            this.trailClassification = trailClassification;
            return this;
        }

        public TrailBuilder withFinalPos(Position finalPos) {
            this.finalPos = finalPos;
            return this;
        }

        public TrailBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Trail build() {
            return new Trail(name, description, code, startPos, finalPos,
                    reportedDifficulty,
                    trackLength, eta, connectingWayPoints, pois, trailClassification,
                    geo, postCode, country);
        }

    }
}
