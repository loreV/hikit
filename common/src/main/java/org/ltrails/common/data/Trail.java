package org.ltrails.common.data;

import com.google.gson.JsonElement;

import java.util.List;

public class Trail {

    static final String COLLECTION_NAME = "core.Trail";

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CODE = "code";
    public static final String STARTPOS = "startPos";
    public static final String FINALPOS = "finalPos";
    public static final String GEO = "geo";
    public static final String REPORTED_DIFFICULTY = "reportedDifficulty";
    public static final String CALCULATED_DIFFICULTY = "calculatedDifficulty";
    public static final String SUGGESTED_TIME_OF_YEAR = "suggestedTimeOfTheYear";
    public static final String TRACK_LENGTH = "trackLength";
    public static final String ETA = "eta";
    public static final String CONNECTING_TRAILS = "connectingWayPoints";
    public static final String POIS = "pois";
    public static final String OFFICIAL_DIFFICULTY = "officialDifficulty";
    public static final String AREA = "area";
    public static final String CITY = "city";
    public static final String COUNTY = "county";
    public static final String POST_CODE = "postCode";

    private String name;
    private String description;
    private String code;
    private Position startPos;
    private Position finalPos;
    private JsonElement geo;
    private double reportedDifficulty;
    private double calculatedDifficulty;
    private List<String> suggestedTimeOfTheYear;
    private double trackLength;
    private double eta;
    private List<ConnectingWayPoint> connectingWayPoints;
    private List<Poi> pois;
    private OfficialDifficulty officialDifficulty;
    private final String postCode;

    public Trail(String name, String description, String code, Position startPos, Position finalPos, double reportedDifficulty,
                 double calculatedDifficulty, List<String> suggestedTimeOfTheYear, double trackLength,
                 double eta, List<ConnectingWayPoint> connectingWayPoints, List<Poi> pois,
                 OfficialDifficulty officialDifficulty, JsonElement geo, String postCode) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.startPos = startPos;
        this.finalPos = finalPos;
        this.geo = geo;
        this.reportedDifficulty = reportedDifficulty;
        this.calculatedDifficulty = calculatedDifficulty;
        this.suggestedTimeOfTheYear = suggestedTimeOfTheYear;
        this.trackLength = trackLength;
        this.eta = eta;
        this.connectingWayPoints = connectingWayPoints;
        this.pois = pois;
        this.officialDifficulty = officialDifficulty;
        this.postCode = postCode;
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

    public double getCalculatedDifficulty() {
        return calculatedDifficulty;
    }

    public List<String> getSuggestedTimeOfTheYear() {
        return suggestedTimeOfTheYear;
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

    public OfficialDifficulty getOfficialDifficulty() {
        return officialDifficulty;
    }

    public Position getFinalPos() {
        return finalPos;
    }

    public String getPostCode() {
        return postCode;
    }

    public static final class TrailBuilder {

        private String name;
        private String description;
        private String code;
        private String postCode;
        private Position startPos;
        private Position finalPos;
        private JsonElement geo;
        private double reportedDifficulty;
        private double calculatedDifficulty;
        private List<String> suggestedTimeOfTheYear;
        private double trackLength;
        private double eta;
        private List<ConnectingWayPoint> connectingWayPoints;
        private List<Poi> pois;
        private OfficialDifficulty officialDifficulty;


        private TrailBuilder() {
        }

        public static TrailBuilder aTrail() {
            return new TrailBuilder();
        }

        public TrailBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public TrailBuilder withPostCode(String postCode) {
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

        public TrailBuilder withCalculatedDifficulty(double calculatedDifficulty) {
            this.calculatedDifficulty = calculatedDifficulty;
            return this;
        }

        public TrailBuilder withSuggestedTimeOfTheYear(List<String> suggestedTimeOfTheYear) {
            this.suggestedTimeOfTheYear = suggestedTimeOfTheYear;
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

        public TrailBuilder withOfficialDifficulty(OfficialDifficulty officialDifficulty) {
            this.officialDifficulty = officialDifficulty;
            return this;
        }

        public TrailBuilder withFinalPos(Position finalPos) {
            this.finalPos = finalPos;
            return this;
        }

        public Trail build() {
            return new Trail(name, description, code, startPos, finalPos,
                    reportedDifficulty, calculatedDifficulty, suggestedTimeOfTheYear,
                    trackLength, eta, connectingWayPoints, pois, officialDifficulty,
                    geo, postCode);
        }

    }
}
