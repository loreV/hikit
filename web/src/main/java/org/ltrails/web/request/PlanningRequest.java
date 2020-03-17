package org.ltrails.web.request;

import org.ltrails.common.data.Position;
import org.ltrails.common.data.mapper.UnitOfMeasurement;

public class PlanningRequest {

    private final Position startPos;
    private final String destination;
    private final int distanceFromPosition;
    private final UnitOfMeasurement unitOfMeasurement;
    private final boolean isSearchDescription;
    private final boolean isDirectTrailsOnly;

    public PlanningRequest(Position startPos, String destination,
                           int distanceFromPosition,
                           UnitOfMeasurement unitOfMeasurement,
                           boolean isSearchDescription,
                           boolean isDirectTrailsOnly) {
        this.startPos = startPos;
        this.destination = destination;
        this.distanceFromPosition = distanceFromPosition;
        this.unitOfMeasurement = unitOfMeasurement;
        this.isSearchDescription = isSearchDescription;
        this.isDirectTrailsOnly = isDirectTrailsOnly;
    }

    public Position getStartPos() {
        return startPos;
    }

    public String getDestination() {
        return destination;
    }

    public int getDistanceFromPosition() {
        return distanceFromPosition;
    }

    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public boolean isSearchDescription() {
        return isSearchDescription;
    }

    public boolean isDirectTrailsOnly() {
        return isDirectTrailsOnly;
    }
}
