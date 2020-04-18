package org.hikit.web.request;

import org.hikit.common.data.Position;
import org.hikit.common.data.UnitOfMeasurement;

public class PlanningRestRequest {

    private final Position startPos;
    private final String destination;
    private final int distanceFromPosition;
    private final UnitOfMeasurement unitOfMeasurement;
    private final boolean isSearchDescription;
    private final boolean isDirectTrailsOnly;

    public PlanningRestRequest(final Position startPos, String destination,
                               final int distanceFromPosition,
                               final UnitOfMeasurement unitOfMeasurement,
                               final boolean isSearchDescription,
                               final boolean isDirectTrailsOnly) {
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
