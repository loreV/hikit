package org.hikit.web.request;

import org.hikit.common.data.Coordinates;
import org.hikit.common.data.UnitOfMeasurement;

public class TrailsGeoRequest {

    private final Coordinates coords;
    private final Number distance;
    private final UnitOfMeasurement uom;
    private final boolean isAnyHikePoint;
    private final int limit;

    public TrailsGeoRequest(Coordinates coordinates, Number distanceBoundary,
                            UnitOfMeasurement uom, boolean isAnyHikePoint, int limit) {
        this.coords = coordinates;
        this.distance = distanceBoundary.intValue();
        this.uom = uom;
        this.isAnyHikePoint = isAnyHikePoint;
        this.limit = limit;
    }

    public Coordinates getCoords() {
        return coords;
    }

    public Number getDistance() {
        return distance;
    }

    public UnitOfMeasurement getUom() {
        return uom;
    }

    public boolean getIsAnyHikePoint() {
        return isAnyHikePoint;
    }

    public int getLimit() {
        return limit;
    }

}
