package org.ltrails.common.data;

import java.util.List;

public class TrailsPath {

    public static final String TRAILS = "trails";
    public static final String CONNECTING_POSITIONS = "connectingPositions";
    public static final String ETA = "eta";
    public static final String DISTANCE = "distance";
    public static final String TOTAL_ELEVATION_UP = "elevationUp";
    public static final String TOTAL_ELEVATION_DOWN = "elevationDown";

    private List<Trail> trails;
    private List<Position> connectingPositions;
    private double eta;
    private double distance;
    private double totalElevationDown;
    private double totalElevationUp;

    public TrailsPath(List<Trail> trails, List<Position> connectingPositions,
                      double eta, double distance,
                      double totalElevationDown, double totalElevationUp) {
        this.trails = trails;
        this.connectingPositions = connectingPositions;
        this.eta = eta;
        this.distance = distance;
        this.totalElevationDown = totalElevationDown;
        this.totalElevationUp = totalElevationUp;
    }

    public List<Trail> getTrails() {
        return trails;
    }

    public List<Position> getConnectingPositions() {
        return connectingPositions;
    }

    public double getEta() {
        return eta;
    }

    public double getDistance() {
        return distance;
    }

    public double getTotalElevationDown() {
        return totalElevationDown;
    }

    public double getTotalElevationUp() {
        return totalElevationUp;
    }


    public static final class TrailsPathBuilder {
        private List<Trail> trails;
        private List<Position> connectingPositions;
        private double eta;
        private double distance;
        private double totalElevationDown;
        private double totalElevationUp;

        private TrailsPathBuilder() {
        }

        public static TrailsPathBuilder aTrailsPath() {
            return new TrailsPathBuilder();
        }

        public TrailsPathBuilder withTrails(List<Trail> trails) {
            this.trails = trails;
            return this;
        }

        public TrailsPathBuilder withConnectingPositions(List<Position> connectingPositions) {
            this.connectingPositions = connectingPositions;
            return this;
        }

        public TrailsPathBuilder withEta(double eta) {
            this.eta = eta;
            return this;
        }

        public TrailsPathBuilder withDistance(double distance) {
            this.distance = distance;
            return this;
        }

        public TrailsPathBuilder withTotalElevationDown(double totalElevationDown) {
            this.totalElevationDown = totalElevationDown;
            return this;
        }

        public TrailsPathBuilder withTotalElevationUp(double totalElevationUp) {
            this.totalElevationUp = totalElevationUp;
            return this;
        }

        public TrailsPath build() {
            return new TrailsPath(trails, connectingPositions, eta, distance, totalElevationDown, totalElevationUp);
        }
    }
}
