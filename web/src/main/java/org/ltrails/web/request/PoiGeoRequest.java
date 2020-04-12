package org.ltrails.web.request;

import org.ltrails.common.data.Coordinates;
import org.ltrails.common.data.UnitOfMeasurement;

import java.util.List;

public class PoiGeoRequest {

    private final Coordinates coords;
    private final Number distance;
    private final UnitOfMeasurement uom;
    private final List<String> types;

    public PoiGeoRequest(Coordinates coordinates, Number distance, UnitOfMeasurement uom, List<String> types) {
        this.coords = coordinates;
        this.distance = distance.intValue();
        this.uom = uom;
        this.types = types;
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

    public List<String> getTypes() {
        return types;
    }
}
