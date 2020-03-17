package org.ltrails.common.data;

public class Coordinates {

    public static final String LONG = "longitude";
    public static final String LAT = "latitude";

    private double longitude;
    private double latitude;

    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }


    public static final class CoordinateBuilder {
        private double longitude;
        private double latitude;

        private CoordinateBuilder() {
        }

        public static CoordinateBuilder aCoordinate() {
            return new CoordinateBuilder();
        }

        public CoordinateBuilder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public CoordinateBuilder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Coordinates build() {
            return new Coordinates(longitude, latitude);
        }
    }
}
