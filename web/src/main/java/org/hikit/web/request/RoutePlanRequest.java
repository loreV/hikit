package org.hikit.web.request;

import org.hikit.common.data.CoordinatesDelta;

public class RoutePlanRequest {
    private CoordinatesDelta startPos;
    private CoordinatesDelta finalPos;
    private boolean directOnly;

    public RoutePlanRequest(final CoordinatesDelta startPos,
                            final CoordinatesDelta finalPos,
                            final boolean directOnly) {
        this.startPos = startPos;
        this.finalPos = finalPos;
        this.directOnly = directOnly;
    }

    public CoordinatesDelta getStartPos() {
        return startPos;
    }

    public CoordinatesDelta getFinalPos() {
        return finalPos;
    }

    public boolean isDirectOnly() {
        return directOnly;
    }
}
