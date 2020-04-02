package org.ltrails.web.request;

import org.ltrails.common.data.Coordinates;
import org.ltrails.common.data.UnitOfMeasurement;

public class TrailsGeoRequest {

    private final Coordinates coords;
    private final Number distance;
    private final UnitOfMeasurement uom;

    public TrailsGeoRequest(Coordinates coordinates, Number distance, UnitOfMeasurement uom) {
        this.coords = coordinates;
        this.distance = distance.intValue();
        this.uom = uom;
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
}
