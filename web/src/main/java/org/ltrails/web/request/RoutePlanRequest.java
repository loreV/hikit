package org.ltrails.web.request;

import org.ltrails.common.data.CoordinatesDelta;

public class RoutePlanRequest {
    private CoordinatesDelta startPos;
    private CoordinatesDelta finalPos;

    public RoutePlanRequest(final CoordinatesDelta startPos,
                            final CoordinatesDelta finalPos) {
        this.startPos = startPos;
        this.finalPos = finalPos;
    }

    public CoordinatesDelta getStartPos() {
        return startPos;
    }

    public CoordinatesDelta getFinalPos() {
        return finalPos;
    }
}
