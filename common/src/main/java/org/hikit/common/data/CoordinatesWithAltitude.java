package org.hikit.common.data;

public class CoordinatesWithAltitude extends Coordinates {

    private double altitude;

    public CoordinatesWithAltitude(final double longitude,
                                   final double latitude,
                                   final double altitude) {
        super(longitude, latitude);
        this.altitude = altitude;
    }

    public double getAltitude() {
        return altitude;
    }
}
